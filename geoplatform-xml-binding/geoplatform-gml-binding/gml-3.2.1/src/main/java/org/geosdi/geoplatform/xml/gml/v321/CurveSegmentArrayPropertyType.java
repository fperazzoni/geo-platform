//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.18 at 11:58:17 AM CEST 
//


package org.geosdi.geoplatform.xml.gml.v321;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * gml:CurveSegmentArrayPropertyType is a container for an array of curve segments.
 * 
 * <p>Java class for CurveSegmentArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurveSegmentArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}AbstractCurveSegment"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurveSegmentArrayPropertyType", propOrder = {
    "abstractCurveSegment"
})
public class CurveSegmentArrayPropertyType
    implements ToString
{

    @XmlElementRef(name = "AbstractCurveSegment", namespace = "http://www.opengis.net/gml/3.2", type = JAXBElement.class)
    protected List<JAXBElement<? extends AbstractCurveSegmentType>> abstractCurveSegment;

    /**
     * Gets the value of the abstractCurveSegment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abstractCurveSegment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbstractCurveSegment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link ArcByCenterPointType }{@code >}
     * {@link JAXBElement }{@code <}{@link ArcType }{@code >}
     * {@link JAXBElement }{@code <}{@link BezierType }{@code >}
     * {@link JAXBElement }{@code <}{@link AbstractCurveSegmentType }{@code >}
     * {@link JAXBElement }{@code <}{@link CubicSplineType }{@code >}
     * {@link JAXBElement }{@code <}{@link ArcStringByBulgeType }{@code >}
     * {@link JAXBElement }{@code <}{@link ClothoidType }{@code >}
     * {@link JAXBElement }{@code <}{@link CircleType }{@code >}
     * {@link JAXBElement }{@code <}{@link CircleByCenterPointType }{@code >}
     * {@link JAXBElement }{@code <}{@link GeodesicStringType }{@code >}
     * {@link JAXBElement }{@code <}{@link ArcByBulgeType }{@code >}
     * {@link JAXBElement }{@code <}{@link ArcStringType }{@code >}
     * {@link JAXBElement }{@code <}{@link GeodesicType }{@code >}
     * {@link JAXBElement }{@code <}{@link LineStringSegmentType }{@code >}
     * {@link JAXBElement }{@code <}{@link BSplineType }{@code >}
     * {@link JAXBElement }{@code <}{@link OffsetCurveType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends AbstractCurveSegmentType>> getAbstractCurveSegment() {
        if (abstractCurveSegment == null) {
            abstractCurveSegment = new ArrayList<JAXBElement<? extends AbstractCurveSegmentType>>();
        }
        return this.abstractCurveSegment;
    }

    public boolean isSetAbstractCurveSegment() {
        return ((this.abstractCurveSegment!= null)&&(!this.abstractCurveSegment.isEmpty()));
    }

    public void unsetAbstractCurveSegment() {
        this.abstractCurveSegment = null;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            List<JAXBElement<? extends AbstractCurveSegmentType>> theAbstractCurveSegment;
            theAbstractCurveSegment = (this.isSetAbstractCurveSegment()?this.getAbstractCurveSegment():null);
            strategy.appendField(locator, this, "abstractCurveSegment", buffer, theAbstractCurveSegment);
        }
        return buffer;
    }

    public void setAbstractCurveSegment(List<JAXBElement<? extends AbstractCurveSegmentType>> value) {
        this.abstractCurveSegment = null;
        List<JAXBElement<? extends AbstractCurveSegmentType>> draftl = this.getAbstractCurveSegment();
        draftl.addAll(value);
    }

}
