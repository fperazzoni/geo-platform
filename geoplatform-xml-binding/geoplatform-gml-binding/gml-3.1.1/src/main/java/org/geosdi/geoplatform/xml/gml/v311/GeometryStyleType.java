//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.17 at 10:27:36 PM CEST 
//


package org.geosdi.geoplatform.xml.gml.v311;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * [complexType of] The style descriptor for geometries of a feature.
 * 
 * <p>Java class for GeometryStyleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeometryStyleType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}BaseStyleDescriptorType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://www.opengis.net/gml}symbol"/>
 *           &lt;element name="style" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.opengis.net/gml}labelStyle" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="geometryProperty" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="geometryType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeometryStyleType", propOrder = {
    "symbol",
    "style",
    "labelStyle"
})
public class GeometryStyleType
    extends BaseStyleDescriptorType
    implements ToString
{

    protected SymbolType symbol;
    protected String style;
    @XmlElementRef(name = "labelStyle", namespace = "http://www.opengis.net/gml", type = LabelStylePropertyElement.class)
    protected LabelStylePropertyElement labelStyle;
    @XmlAttribute(name = "geometryProperty")
    protected String geometryProperty;
    @XmlAttribute(name = "geometryType")
    protected String geometryType;

    /**
     * Gets the value of the symbol property.
     * 
     * @return
     *     possible object is
     *     {@link SymbolType }
     *     
     */
    public SymbolType getSymbol() {
        return symbol;
    }

    /**
     * Sets the value of the symbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link SymbolType }
     *     
     */
    public void setSymbol(SymbolType value) {
        this.symbol = value;
    }

    public boolean isSetSymbol() {
        return (this.symbol!= null);
    }

    /**
     * Gets the value of the style property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the value of the style property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyle(String value) {
        this.style = value;
    }

    public boolean isSetStyle() {
        return (this.style!= null);
    }

    /**
     * Gets the value of the labelStyle property.
     * 
     * @return
     *     possible object is
     *     {@link LabelStylePropertyElement }
     *     
     */
    public LabelStylePropertyElement getLabelStyle() {
        return labelStyle;
    }

    /**
     * Sets the value of the labelStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelStylePropertyElement }
     *     
     */
    public void setLabelStyle(LabelStylePropertyElement value) {
        this.labelStyle = value;
    }

    public boolean isSetLabelStyle() {
        return (this.labelStyle!= null);
    }

    /**
     * Gets the value of the geometryProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeometryProperty() {
        return geometryProperty;
    }

    /**
     * Sets the value of the geometryProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeometryProperty(String value) {
        this.geometryProperty = value;
    }

    public boolean isSetGeometryProperty() {
        return (this.geometryProperty!= null);
    }

    /**
     * Gets the value of the geometryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeometryType() {
        return geometryType;
    }

    /**
     * Sets the value of the geometryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeometryType(String value) {
        this.geometryType = value;
    }

    public boolean isSetGeometryType() {
        return (this.geometryType!= null);
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
        super.appendFields(locator, buffer, strategy);
        {
            SymbolType theSymbol;
            theSymbol = this.getSymbol();
            strategy.appendField(locator, this, "symbol", buffer, theSymbol);
        }
        {
            String theStyle;
            theStyle = this.getStyle();
            strategy.appendField(locator, this, "style", buffer, theStyle);
        }
        {
            LabelStylePropertyElement theLabelStyle;
            theLabelStyle = this.getLabelStyle();
            strategy.appendField(locator, this, "labelStyle", buffer, theLabelStyle);
        }
        {
            String theGeometryProperty;
            theGeometryProperty = this.getGeometryProperty();
            strategy.appendField(locator, this, "geometryProperty", buffer, theGeometryProperty);
        }
        {
            String theGeometryType;
            theGeometryType = this.getGeometryType();
            strategy.appendField(locator, this, "geometryType", buffer, theGeometryType);
        }
        return buffer;
    }

}
