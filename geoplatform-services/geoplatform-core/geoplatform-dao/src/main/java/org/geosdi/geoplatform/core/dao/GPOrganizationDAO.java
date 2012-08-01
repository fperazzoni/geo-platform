/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2012 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General  License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General  License 
 * for more details. You should have received a copy of the GNU General 
 *  License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General  License cover the whole combination. 
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
package org.geosdi.geoplatform.core.dao;

import com.googlecode.genericdao.search.ISearch;
import java.util.List;
import org.geosdi.geoplatform.core.model.GPOrganization;

/**
 *
 * @author Vincenzo Monteverde <vincenzo.monteverde@geosdi.org>
 */
public interface GPOrganizationDAO {

    List<GPOrganization> findAll();

    GPOrganization find(Long id);

    GPOrganization[] find(Long[] ids);

    void persist(GPOrganization... organizations);
    
    GPOrganization save(GPOrganization organization);

    GPOrganization merge(GPOrganization organization);

    GPOrganization[] merge(GPOrganization... organization);

    boolean remove(GPOrganization organization);

    boolean removeById(Long id);

    List<GPOrganization> search(ISearch search);

    int count(ISearch search);

    GPOrganization findByName(String name);
}
