//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.10 at 05:35:20 PM CEST 
//


package org.geosdi.geoplatform.xml.gml.v311;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DrawingTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DrawingTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="POLYLINE"/>
 *     &lt;enumeration value="ORTHOGONAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DrawingTypeType")
@XmlEnum
public enum DrawingTypeType {

    POLYLINE,
    ORTHOGONAL;

    public String value() {
        return name();
    }

    public static DrawingTypeType fromValue(String v) {
        return valueOf(v);
    }

}