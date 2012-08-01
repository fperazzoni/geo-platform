/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2012 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details. You should have received a copy of the GNU General
 * Public License along with this program. If not, see http://www.gnu.org/licenses/
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library. Thus, the terms and
 * conditions of the GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you permission
 * to link this library with independent modules to produce an executable, regardless
 * of the license terms of these independent modules, and to copy and distribute
 * the resulting executable under terms of your choice, provided that you also meet,
 * for each linked independent module, the terms and conditions of the license of
 * that module. An independent module is a module which is not derived from or
 * based on this library. If you modify this library, you may extend this exception
 * to your version of the library, but you are not obligated to do so. If you do not
 * wish to do so, delete this exception statement from your version.
 *
 */
package org.geosdi.geoplatform.modelws;

import java.util.Iterator;
import java.util.List;
import org.geosdi.geoplatform.core.model.GPAuthority;
import org.geosdi.geoplatform.core.model.GPOrganization;
import org.geosdi.geoplatform.core.model.GPUser;
import org.geosdi.geoplatform.exception.AccountLoginFault;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.request.LikePatternType;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.responce.ApplicationDTO;
import org.geosdi.geoplatform.responce.ShortAccountDTO;
import org.geosdi.geoplatform.responce.UserDTO;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 *
 * @author Vincenzo Monteverde <vincenzo.monteverde@geosdi.org>
 */
public class WSAccountTest extends ServiceTest {

    @Test
    public void testAccountsDB() {
        List<ShortAccountDTO> accountList = gpWSClient.getAccounts();
        Assert.assertNotNull(accountList);
        logger.info("\n*** Number of Accounts into DB: {} ***", accountList.size());
        for (Iterator<ShortAccountDTO> it = accountList.iterator(); it.hasNext();) {
            ShortAccountDTO account = it.next();
            if (account instanceof UserDTO) {
                logger.info("\n*** User into DB:\n{}\n***", (UserDTO) account);
            } else if (account instanceof ApplicationDTO) {
                logger.info("\n*** Application into DB:\n{}\n***", (ApplicationDTO) account);
            }
        }
    }

    @Test
    public void testRetrieveUser() throws ResourceNotFoundFault {
        // Number of Accounts
        List<ShortAccountDTO> accountList = gpWSClient.getAccounts();
        Assert.assertNotNull(accountList);
        Assert.assertTrue("Number of Accounts stored into database", accountList.size() >= 1); // super.SetUp() added 1 user

        // Number of Account Like
        long numAccountsLike = gpWSClient.getAccountsCount(
                new SearchRequest(emailUserTest, LikePatternType.CONTENT_EQUALS));
        Assert.assertEquals("Number of Account Like", 1L, numAccountsLike);

        // Get User from Id
        // Get UserDTO from Id
        UserDTO userDTOFromWS = gpWSClient.getShortUser(idUserTest);
        Assert.assertNotNull(userDTOFromWS);
        Assert.assertEquals("Error found UserDTO from Id", idUserTest, userDTOFromWS.getId().longValue());
        // Get GPUser from Id
        GPUser userFromWS = gpWSClient.getUserDetail(idUserTest);
        Assert.assertNotNull(userFromWS);
        Assert.assertEquals("Error found GPUser from Id", idUserTest, userFromWS.getId().longValue());

        // Get User from Email
        // Get UserDTO from Email
        userDTOFromWS = gpWSClient.getShortUserByEmail(
                new SearchRequest(emailUserTest, LikePatternType.CONTENT_EQUALS));
        Assert.assertNotNull(userDTOFromWS);
        Assert.assertEquals("Error found UserDTO from Email", idUserTest, userDTOFromWS.getId().longValue());
        // Get GPUser from Email
        userFromWS = gpWSClient.getUserDetailByEmail(
                new SearchRequest(emailUserTest, LikePatternType.CONTENT_EQUALS));
        Assert.assertNotNull(userFromWS);
        Assert.assertEquals("Error found GPUser from Email", idUserTest, userFromWS.getId().longValue());
    }

    @Test(expected = IllegalParameterFault.class)
    public void testInsertUserWithNoRoles() throws IllegalParameterFault {
        super.createAndInsertUser("user-no-roles", organizationTest);
    }

    @Test
    public void testInsertUserWithSingleRole() throws ResourceNotFoundFault {
        List<GPAuthority> authorities = gpWSClient.getAuthoritiesDetail(emailUserTest);
        Assert.assertNotNull("Authorities null", authorities);
        Assert.assertEquals("Number of Authorities of " + emailUserTest, 1, authorities.size());

        GPAuthority authority = authorities.get(0);
        Assert.assertNotNull(authority);
        Assert.assertEquals("Authority string", ROLE_USER, authority.getAuthority());
        Assert.assertEquals("Authority email", emailUserTest, authority.getStringID());
    }

    @Test
    public void testInsertUserWithMultiRole() throws IllegalParameterFault, ResourceNotFoundFault {
        String emailMultiRole = "user-multi-role";
        Long idUser = super.createAndInsertUser(emailMultiRole, organizationTest, ROLE_ADMIN, ROLE_VIEWER);

        try {
            List<GPAuthority> authorities = gpWSClient.getAuthoritiesDetail(emailMultiRole);
            Assert.assertNotNull(authorities);
            Assert.assertEquals("Number of Authorities of " + emailMultiRole, 2, authorities.size());

            GPAuthority authority = authorities.get(0);
            Assert.assertNotNull(authority);
            Assert.assertEquals("Authority string", ROLE_ADMIN, authority.getAuthority());
            Assert.assertEquals("Authority email", emailMultiRole, authority.getStringID());

            authority = authorities.get(1);
            Assert.assertNotNull(authority);
            Assert.assertEquals("Authority string", ROLE_VIEWER, authority.getAuthority());
            Assert.assertEquals("Authority email", emailMultiRole, authority.getStringID());
        } finally {
            boolean check = gpWSClient.deleteAccount(idUser);
            Assert.assertTrue(check);
        }
    }

    @Test
    public void testInsertDuplicateUserWRTEmail() {
        GPUser user = super.createUser("duplicate-email", organizationTest, ROLE_USER);
        user.setEmailAddress(super.userTest.getEmailAddress());
        try {
            gpWSClient.insertAccount(user, false);
            Assert.fail("User already exist wrt email");
        } catch (IllegalParameterFault ex) {
            if (!ex.getMessage().toLowerCase().contains("email")) { // Must be fail for other reasons
                Assert.fail("Not fail for User already exist wrt email, but for: " + ex.getMessage());
            }
        }
    }

    @Test
    public void testInsertIncorrectUserWRTUOrganization() {
        GPUser user = super.createUser("no-organization",
                                       new GPOrganization("organization-inexistent"), ROLE_USER);

        try {
            gpWSClient.insertAccount(user, false);
            Assert.fail("User incorrect wrt organization");
        } catch (IllegalParameterFault ex) {
            if (!ex.getMessage().toLowerCase().contains("organization")) { // Must be fail for other reasons
                Assert.fail("Not fail for User incorrect wrt organization, but for: " + ex.getMessage());
            }
        }
    }

    @Test
    public void testGetUserDetailByEmailAndPassword1()
            throws IllegalParameterFault, ResourceNotFoundFault, AccountLoginFault {
        GPUser user = gpWSClient.getUserDetailByEmailAndPassword(emailUserTest, passwordTest);
        Assert.assertNotNull("User is null", user);
    }

    @Test
    public void testGetUserDetailByEmailAndPassword2()
            throws AccountLoginFault {
        GPUser user = null;
        try {
            String newEmail = emailUserTest + "_";
            user = gpWSClient.getUserDetailByEmailAndPassword(newEmail, passwordTest);
            Assert.fail("Test must fail because email is wrong");
        } catch (ResourceNotFoundFault ex) {
            Assert.assertNull("User is not null", user);
        } catch (IllegalParameterFault ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testGetUserDetailByEmailAndPassword3()
            throws AccountLoginFault {
        GPUser user = null;
        try {
            user = gpWSClient.getUserDetailByEmailAndPassword(emailUserTest, passwordTest + "_");
            Assert.fail("Test must fail because password is wrong");
        } catch (ResourceNotFoundFault ex) {
            Assert.fail(ex.getMessage());
        } catch (IllegalParameterFault ex) {
            Assert.assertNull("User is not null", user);
        }
    }

    @Test(expected = AccountLoginFault.class)
    public void testLoginFaultUserDisabled()
            throws ResourceNotFoundFault, IllegalParameterFault, AccountLoginFault {
        // Set disabled user
        userTest.setEnabled(false);
        gpWSClient.updateUser(userTest);

        // Must be throws AccountLoginFault because the user is disabled
        gpWSClient.getUserDetailByEmailAndPassword(emailUserTest, passwordTest);
    }

    @Test(expected = AccountLoginFault.class)
    public void testLoginFaultUserExpired()
            throws ResourceNotFoundFault, IllegalParameterFault, AccountLoginFault {
        // Set temporary user
        gpWSClient.forceTemporaryAccount(idUserTest);

        // Set expired user (user must be temporary)
        gpWSClient.forceExpiredTemporaryAccount(idUserTest);

        // Must be throws AccountLoginFault because the user is expired
        gpWSClient.getUserDetailByEmailAndPassword(emailUserTest, passwordTest);
    }

    @Test(expected = IllegalParameterFault.class)
    public void testUserErrorTemporarySetting()
            throws ResourceNotFoundFault, IllegalParameterFault {
        Assert.assertFalse("UserTest should be a standard account",
                           userTest.isAccountTemporary());

        // Set the standard user to temporary (wrongly)
        userTest.setAccountTemporary(true);

        // Must be throws IllegalParameterFault because the standard user will be temporary
        gpWSClient.updateUser(userTest);
    }
}
