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
package org.geosdi.geoplatform.connector;

import java.io.StringWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import org.geosdi.geoplatform.connector.jaxb.GPConnectorJAXBContext;
import org.geosdi.geoplatform.connector.jaxb.CSWConnectorJAXBContext;
import org.geosdi.geoplatform.connector.jaxb.GeoPlatformJAXBContextRepository;
import org.geosdi.geoplatform.xml.csw.v202.ElementSetNameType;
import org.geosdi.geoplatform.xml.csw.v202.ElementSetType;
import org.geosdi.geoplatform.xml.csw.v202.GetRecordsType;
import org.geosdi.geoplatform.xml.csw.v202.QueryConstraintType;
import org.geosdi.geoplatform.xml.csw.v202.QueryType;
import org.geosdi.geoplatform.xml.csw.v202.ResultType;
import org.geosdi.geoplatform.xml.filter.v110.BBOXType;
import org.geosdi.geoplatform.xml.filter.v110.BinaryComparisonOpType;
import org.geosdi.geoplatform.xml.filter.v110.BinaryLogicOpType;
import org.geosdi.geoplatform.xml.filter.v110.BinarySpatialOpType;
import org.geosdi.geoplatform.xml.filter.v110.FilterType;
import org.geosdi.geoplatform.xml.filter.v110.LiteralType;
import org.geosdi.geoplatform.xml.filter.v110.PropertyIsLikeType;
import org.geosdi.geoplatform.xml.filter.v110.PropertyNameType;
import org.geosdi.geoplatform.xml.gml.v311.DirectPositionType;
import org.geosdi.geoplatform.xml.gml.v311.EnvelopeType;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vincenzo Monteverde <vincenzo.monteverde@geosdi.org>
 */
public class CatalogRequestTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    private final GPConnectorJAXBContext cswContext = GeoPlatformJAXBContextRepository.getProvider(
            CSWConnectorJAXBContext.CSW_CONTEXT_KEY);
    private org.geosdi.geoplatform.xml.filter.v110.ObjectFactory filterFactory;
    private org.geosdi.geoplatform.xml.gml.v311.ObjectFactory gmlFactory;

    @Before
    public void setUp() {
        filterFactory = new org.geosdi.geoplatform.xml.filter.v110.ObjectFactory();
        gmlFactory = new org.geosdi.geoplatform.xml.gml.v311.ObjectFactory();
    }

    @Test
    public void testGetRecordsCount() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.HITS);

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("csw:Record")));

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"hits\""));

        assertTrue(request.contains("<csw:Query typeNames=\"csw:Record\""));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsCountQuery() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.HITS);

        getRecords.setOutputSchema("http://www.opengis.net/cat/csw/2.0.2");

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("csw:Record")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.BRIEF);
        query.setElementSetName(elementSetNameType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"hits\""));
        assertTrue(request.contains("outputSchema=\"http://www.opengis.net/cat/csw/2.0.2\""));

        assertTrue(request.contains("<csw:Query typeNames=\"csw:Record\""));
        assertTrue(request.contains("<csw:ElementSetName>brief</csw:ElementSetName>"));
        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsSearchFilterTextAnytext() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.RESULTS);

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("gmd:MD_Metadata")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.SUMMARY);
        query.setElementSetName(elementSetNameType);

        PropertyIsLikeType propertyIsLikeType = this.createPropertyIsLikeType("AnyText", "%venezia%");

        JAXBElement<PropertyIsLikeType> propertyIsLike = filterFactory.createPropertyIsLike(propertyIsLikeType);

        FilterType filterType = new FilterType();
        filterType.setComparisonOps(propertyIsLike);

        QueryConstraintType queryConstraintType = new QueryConstraintType();
        queryConstraintType.setVersion("1.1.0");
        queryConstraintType.setFilter(filterType);

        query.setConstraint(queryConstraintType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"results\""));

        assertTrue(request.contains("<csw:Query typeNames=\"gmd:MD_Metadata\""));
        assertTrue(request.contains("<csw:ElementSetName>summary</csw:ElementSetName>"));

        assertTrue(request.contains("<csw:Constraint version=\"1.1.0\">"));

        assertTrue(request.contains("<ogc:Filter>"));

        assertTrue(request.contains("<ogc:PropertyIsLike wildCard=\"%\" singleChar=\".\" escapeChar=\"\\\">"));
        assertTrue(request.contains("<ogc:PropertyName>AnyText</ogc:PropertyName>"));
        assertTrue(request.contains("<ogc:Literal>%venezia%</ogc:Literal>"));
        assertTrue(request.contains("</ogc:PropertyIsLike>"));

        assertTrue(request.contains("</ogc:Filter>"));

        assertTrue(request.contains("</csw:Constraint>"));

        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsSearchFilterTextTitleOrAbstract() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.RESULTS);

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("gmd:MD_Metadata")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.SUMMARY);
        query.setElementSetName(elementSetNameType);

        PropertyIsLikeType titleIsLikeType = this.createPropertyIsLikeType("dc:title", "%limiti%");
        PropertyIsLikeType abstractIsLikeType = this.createPropertyIsLikeType("dc:abstract", "%italia%");

        List<JAXBElement<?>> propertyList = new ArrayList<JAXBElement<?>>(2);
        propertyList.add(filterFactory.createPropertyIsLike(titleIsLikeType));
        propertyList.add(filterFactory.createPropertyIsLike(abstractIsLikeType));

        BinaryLogicOpType binary = new BinaryLogicOpType();
        binary.setComparisonOpsOrSpatialOpsOrLogicOps(propertyList);

        JAXBElement<BinaryLogicOpType> orType = filterFactory.createOr(binary);

        FilterType filterType = new FilterType();
        filterType.setLogicOps(orType);

        QueryConstraintType queryConstraintType = new QueryConstraintType();
        queryConstraintType.setVersion("1.1.0");
        queryConstraintType.setFilter(filterType);

        query.setConstraint(queryConstraintType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"results\""));

        assertTrue(request.contains("<csw:Query typeNames=\"gmd:MD_Metadata\""));
        assertTrue(request.contains("<csw:ElementSetName>summary</csw:ElementSetName>"));

        assertTrue(request.contains("<csw:Constraint version=\"1.1.0\">"));

        assertTrue(request.contains("<ogc:Filter>"));

        assertTrue(request.contains("<ogc:Or>"));

        assertTrue(request.contains("<ogc:PropertyIsLike wildCard=\"%\" singleChar=\".\" escapeChar=\"\\\">"));
        assertTrue(request.contains("<ogc:PropertyName>dc:title</ogc:PropertyName>"));
        assertTrue(request.contains("<ogc:Literal>%limiti%</ogc:Literal>"));
        assertTrue(request.contains("</ogc:PropertyIsLike>"));

        assertTrue(request.contains("<ogc:PropertyIsLike wildCard=\"%\" singleChar=\".\" escapeChar=\"\\\">"));
        assertTrue(request.contains("<ogc:PropertyName>dc:abstract</ogc:PropertyName>"));
        assertTrue(request.contains("<ogc:Literal>%italia%</ogc:Literal>"));
        assertTrue(request.contains("</ogc:PropertyIsLike>"));

        assertTrue(request.contains("</ogc:Or>"));

        assertTrue(request.contains("</ogc:Filter>"));

        assertTrue(request.contains("</csw:Constraint>"));

        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsSearchFilterTextTitleOrSubject() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.RESULTS);

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("gmd:MD_Metadata")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.SUMMARY);
        query.setElementSetName(elementSetNameType);

        PropertyIsLikeType titleIsLikeType = this.createPropertyIsLikeType("dc:title", "%limiti%");
        BinaryComparisonOpType subjectIsEqualTo = this.createBinaryComparisonOpType("dc:subject", "GEOSERVER");

        List<JAXBElement<?>> propertyList = new ArrayList<JAXBElement<?>>(2);
        propertyList.add(filterFactory.createPropertyIsLike(titleIsLikeType));
        propertyList.add(filterFactory.createPropertyIsEqualTo(subjectIsEqualTo));

        BinaryLogicOpType binary = new BinaryLogicOpType();
        binary.setComparisonOpsOrSpatialOpsOrLogicOps(propertyList);

        FilterType filterType = new FilterType();
        filterType.setLogicOps(filterFactory.createOr(binary));

        QueryConstraintType queryConstraintType = new QueryConstraintType();
        queryConstraintType.setVersion("1.1.0");
        queryConstraintType.setFilter(filterType);

        query.setConstraint(queryConstraintType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"results\""));

        assertTrue(request.contains("<csw:Query typeNames=\"gmd:MD_Metadata\""));
        assertTrue(request.contains("<csw:ElementSetName>summary</csw:ElementSetName>"));

        assertTrue(request.contains("<csw:Constraint version=\"1.1.0\">"));

        assertTrue(request.contains("<ogc:Filter>"));

        assertTrue(request.contains("<ogc:Or>"));

        assertTrue(request.contains("<ogc:PropertyIsLike wildCard=\"%\" singleChar=\".\" escapeChar=\"\\\">"));
        assertTrue(request.contains("<ogc:PropertyName>dc:title</ogc:PropertyName>"));
        assertTrue(request.contains("<ogc:Literal>%limiti%</ogc:Literal>"));
        assertTrue(request.contains("</ogc:PropertyIsLike>"));

        assertTrue(request.contains("<ogc:PropertyIsEqualTo>"));
        assertTrue(request.contains("<ogc:PropertyName>dc:subject</ogc:PropertyName>"));
        assertTrue(request.contains("<ogc:Literal>GEOSERVER</ogc:Literal>"));
        assertTrue(request.contains("</ogc:PropertyIsEqualTo>"));

        assertTrue(request.contains("</ogc:Or>"));

        assertTrue(request.contains("</ogc:Filter>"));

        assertTrue(request.contains("</csw:Constraint>"));

        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsSearchFilterAreaBBox() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.RESULTS);

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("gmd:MD_Metadata")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.SUMMARY);
        query.setElementSetName(elementSetNameType);

        BBOXType bbox = new BBOXType();

        PropertyNameType propertyNameType = new PropertyNameType();
        propertyNameType.setContent(Arrays.<Object>asList("ows:BoundingBox"));
        bbox.setPropertyName(propertyNameType);

        EnvelopeType envelope = this.createEnvelopeItaly();
        bbox.setEnvelope(gmlFactory.createEnvelope(envelope));

        FilterType filterType = new FilterType();
        filterType.setSpatialOps(filterFactory.createBBOX(bbox));

        QueryConstraintType queryConstraintType = new QueryConstraintType();
        queryConstraintType.setVersion("1.1.0");
        queryConstraintType.setFilter(filterType);

        query.setConstraint(queryConstraintType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"results\""));

        assertTrue(request.contains("<csw:Query typeNames=\"gmd:MD_Metadata\""));
        assertTrue(request.contains("<csw:ElementSetName>summary</csw:ElementSetName>"));

        assertTrue(request.contains("<csw:Constraint version=\"1.1.0\">"));

        assertTrue(request.contains("<ogc:Filter>"));

        assertTrue(request.contains("<ogc:BBOX>"));

        assertTrue(request.contains("<ogc:PropertyName>ows:BoundingBox</ogc:PropertyName>"));

        assertTrue(request.contains("<gml:Envelope srsName=\"EPSG:4326\">"));
        assertTrue(request.contains("<gml:lowerCorner>18.521 35.492</gml:lowerCorner>"));
        assertTrue(request.contains("<gml:upperCorner>6.627 47.092</gml:upperCorner>"));
        assertTrue(request.contains("</gml:Envelope>"));

        assertTrue(request.contains("</ogc:BBOX>"));

        assertTrue(request.contains("</ogc:Filter>"));

        assertTrue(request.contains("</csw:Constraint>"));

        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsSearchFilterAreaEncloses() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.RESULTS);

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("gmd:MD_Metadata")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.SUMMARY);
        query.setElementSetName(elementSetNameType);

        BinarySpatialOpType binarySpatial = new BinarySpatialOpType();

        PropertyNameType propertyNameType = new PropertyNameType();
        propertyNameType.setContent(Arrays.<Object>asList("ows:BoundingBox"));
        binarySpatial.setPropertyName(propertyNameType);

        EnvelopeType envelope = this.createEnvelopeItaly();
        binarySpatial.setEnvelope(gmlFactory.createEnvelope(envelope));

        FilterType filterType = new FilterType();
        filterType.setSpatialOps(filterFactory.createWithin(binarySpatial));

        QueryConstraintType queryConstraintType = new QueryConstraintType();
        queryConstraintType.setVersion("1.1.0");
        queryConstraintType.setFilter(filterType);

        query.setConstraint(queryConstraintType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"results\""));

        assertTrue(request.contains("<csw:Query typeNames=\"gmd:MD_Metadata\""));
        assertTrue(request.contains("<csw:ElementSetName>summary</csw:ElementSetName>"));

        assertTrue(request.contains("<csw:Constraint version=\"1.1.0\">"));

        assertTrue(request.contains("<ogc:Filter>"));

        assertTrue(request.contains("<ogc:Within>"));

        assertTrue(request.contains("<ogc:PropertyName>ows:BoundingBox</ogc:PropertyName>"));

        assertTrue(request.contains("<gml:Envelope srsName=\"EPSG:4326\">"));
        assertTrue(request.contains("<gml:lowerCorner>18.521 35.492</gml:lowerCorner>"));
        assertTrue(request.contains("<gml:upperCorner>6.627 47.092</gml:upperCorner>"));
        assertTrue(request.contains("</gml:Envelope>"));

        assertTrue(request.contains("</ogc:Within>"));

        assertTrue(request.contains("</ogc:Filter>"));

        assertTrue(request.contains("</csw:Constraint>"));

        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsSearchFilterTemporal() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.RESULTS);

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("gmd:MD_Metadata")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.SUMMARY);
        query.setElementSetName(elementSetNameType);

        BinaryComparisonOpType begin = this.createBinaryComparisonOpType(
                "TempExtent_begin", "2000-01-01T00:00:00Z");
        BinaryComparisonOpType end = this.createBinaryComparisonOpType(
                "TempExtent_end", "2012-01-01T00:00:00Z");

        List<JAXBElement<?>> propertyList = new ArrayList<JAXBElement<?>>(2);
        propertyList.add(filterFactory.createPropertyIsGreaterThanOrEqualTo(begin));
        propertyList.add(filterFactory.createPropertyIsLessThanOrEqualTo(end));

        BinaryLogicOpType binary = new BinaryLogicOpType();
        binary.setComparisonOpsOrSpatialOpsOrLogicOps(propertyList);

        FilterType filterType = new FilterType();
        filterType.setLogicOps(filterFactory.createAnd(binary));

        QueryConstraintType queryConstraintType = new QueryConstraintType();
        queryConstraintType.setVersion("1.1.0");
        queryConstraintType.setFilter(filterType);

        query.setConstraint(queryConstraintType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"results\""));

        assertTrue(request.contains("<csw:Query typeNames=\"gmd:MD_Metadata\""));
        assertTrue(request.contains("<csw:ElementSetName>summary</csw:ElementSetName>"));

        assertTrue(request.contains("<csw:Constraint version=\"1.1.0\">"));

        assertTrue(request.contains("<ogc:Filter>"));

        assertTrue(request.contains("<ogc:And>"));

        assertTrue(request.contains("<ogc:PropertyIsGreaterThanOrEqualTo>"));
        assertTrue(request.contains("<ogc:PropertyName>TempExtent_begin</ogc:PropertyName>"));
        assertTrue(request.contains("<ogc:Literal>2000-01-01T00:00:00Z</ogc:Literal>"));
        assertTrue(request.contains("</ogc:PropertyIsGreaterThanOrEqualTo>"));

        assertTrue(request.contains("<ogc:PropertyIsLessThanOrEqualTo>"));
        assertTrue(request.contains("<ogc:PropertyName>TempExtent_end</ogc:PropertyName>"));
        assertTrue(request.contains("<ogc:Literal>2012-01-01T00:00:00Z</ogc:Literal>"));
        assertTrue(request.contains("</ogc:PropertyIsLessThanOrEqualTo>"));

        assertTrue(request.contains("</ogc:And>"));

        assertTrue(request.contains("</ogc:Filter>"));

        assertTrue(request.contains("</csw:Constraint>"));

        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    @Test
    public void testGetRecordsSearchCQL() throws JAXBException {
        GetRecordsType getRecords = new GetRecordsType();

        getRecords.setResultType(ResultType.RESULTS);

        getRecords.setStartPosition(BigInteger.ONE);
        getRecords.setMaxRecords(BigInteger.TEN);

        getRecords.setOutputSchema("http://www.opengis.net/cat/csw/2.0.2");

        QueryType query = new QueryType();
        getRecords.setAbstractQuery(query);

        query.setTypeNames(Arrays.asList(QName.valueOf("gmd:MD_Metadata")));

        ElementSetNameType elementSetNameType = new ElementSetNameType();
        elementSetNameType.setValue(ElementSetType.FULL);
        query.setElementSetName(elementSetNameType);

        QueryConstraintType queryConstraintType = new QueryConstraintType();
        queryConstraintType.setVersion("1.1.0");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar startCalendar = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        Calendar endCalendar = new GregorianCalendar(2012, Calendar.JANUARY, 1);

        StringBuilder constraint = new StringBuilder();
        constraint.append("AnyText LIKE '%venezia%'");
        constraint.append(" AND ");
        constraint.append("TempExtent_begin AFTER ").append(formatter.format(startCalendar.getTime()));
        constraint.append(" AND ");
        constraint.append("TempExtent_end BEFORE ").append(formatter.format(endCalendar.getTime()));
        queryConstraintType.setCqlText(constraint.toString());

        query.setConstraint(queryConstraintType);

        Marshaller marshaller = cswContext.acquireMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(getRecords, writer);

        String request = writer.toString();
        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n{}",
                new Scanner(request).useDelimiter("\\A").next());

        assertTrue(request.contains("<csw:GetRecords"));
        assertTrue(request.contains("service=\"CSW\""));
        assertTrue(request.contains("version=\"2.0.2\""));
        assertTrue(request.contains("resultType=\"results\""));
        assertTrue(request.contains("outputSchema=\"http://www.opengis.net/cat/csw/2.0.2\""));
        assertTrue(request.contains("startPosition=\"1\""));
        assertTrue(request.contains("maxRecords=\"10\""));

        assertTrue(request.contains("<csw:Query typeNames=\"gmd:MD_Metadata\""));
        assertTrue(request.contains("<csw:ElementSetName>full</csw:ElementSetName>"));

        assertTrue(request.contains("<csw:Constraint version=\"1.1.0\">"));

        assertTrue(request.contains("<csw:CqlText>AnyText LIKE '%venezia%' "
                + "AND TempExtent_begin AFTER 2000-01-01T00:00:00Z "
                + "AND TempExtent_end BEFORE 2012-01-01T00:00:00Z</csw:CqlText>"));

        assertTrue(request.contains("</csw:Constraint>"));

        assertTrue(request.contains("</csw:Query>"));

        assertTrue(request.contains("</csw:GetRecords>"));
    }

    private PropertyIsLikeType createPropertyIsLikeType(String propertyName, String literal) {

        PropertyIsLikeType propertyIsLikeType = new PropertyIsLikeType();
        propertyIsLikeType.setWildCard("%");
        propertyIsLikeType.setSingleChar(".");
        propertyIsLikeType.setEscapeChar("\\");

        PropertyNameType propertyNameType = new PropertyNameType();
        propertyNameType.setContent(Arrays.<Object>asList(propertyName));
        propertyIsLikeType.setPropertyName(propertyNameType);

        LiteralType literalType = new LiteralType();
        literalType.setContent(Arrays.<Object>asList(literal));
        propertyIsLikeType.setLiteral(literalType);

        return propertyIsLikeType;
    }

    private BinaryComparisonOpType createBinaryComparisonOpType(String propertyName, String literal) {

        BinaryComparisonOpType binaryComparison = new BinaryComparisonOpType();

        PropertyNameType propertyNameType = new PropertyNameType();
        propertyNameType.setContent(Arrays.<Object>asList(propertyName));

        LiteralType literalType = new LiteralType();
        literalType.setContent(Arrays.<Object>asList(literal));

        List<JAXBElement<?>> expressionList = new ArrayList<JAXBElement<?>>(2);
        expressionList.add(filterFactory.createPropertyName(propertyNameType));
        expressionList.add(filterFactory.createLiteral(literalType));
        binaryComparison.setExpression(expressionList);

        return binaryComparison;
    }

    private EnvelopeType createEnvelopeItaly() {
        EnvelopeType envelope = new EnvelopeType();
        envelope.setSrsName("EPSG:4326");

        DirectPositionType lower = new DirectPositionType();
        lower.setValue(Arrays.asList(18.521, 35.492)); // maxX, minY
        envelope.setLowerCorner(lower);

        DirectPositionType upper = new DirectPositionType();
        upper.setValue(Arrays.asList(6.627, 47.092)); // minX, maxY
        envelope.setUpperCorner(upper);

        return envelope;
    }
}
