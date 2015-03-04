/*
 * Copyright (C) 2015 Hedgehog01
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package lib.xmlphonefeatures;

import java.io.Serializable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class that represents a Phone Feature Property
 *
 * @author Hedgehog01
 */
public final class PhoneFeatureProperty implements Serializable
{

    private static final long serialVersionUID = 2L;

    private final StringProperty elementName = new SimpleStringProperty("");
    private final StringProperty elementAttribute = new SimpleStringProperty("");
    private final StringProperty elementValue = new SimpleStringProperty("");
    private final BooleanProperty defaultSection = new SimpleBooleanProperty(false);
    private final StringProperty defaultType = new SimpleStringProperty("");

    /**
     * Default constructor - sets all class instances to null
     */
    public PhoneFeatureProperty()
    {
        this(null, null, null);
    }

    /**
     * Constructor for setting property values
     *
     * @param elementName the element name
     * @param elmentValue the element value
     * @param attributes the element content
     */
    public PhoneFeatureProperty(String elementName, String elmentValue, String attributes)
    {
        setElementName(elementName);
        setElementValue(elmentValue);
        setElementAttribute(attributes);
    }

    /**
     * Constructor for setting property values
     *
     * @param elementName the element name
     * @param elmentValue the element value
     * @param attributes the element content
     * @param defaultType the element default type (OS default, regular default etc)
     * @param defaultSection is the element from default section
     */
    public PhoneFeatureProperty(String elementName, String elmentValue, String attributes,String defaultType, boolean defaultSection)
    {
        setElementName(elementName);
        setElementValue(elmentValue);
        setElementAttribute(attributes);
        setDefaultType(defaultType);
        setDefaultSection(defaultSection);
    }

    /**
     * method to get the element name
     *
     * @return the element name
     */
    public String getElementName()
    {
        return elementName.get();
    }

    /**
     * method to set the element name
     *
     * @param value the element name
     */
    public void setElementName(String value)
    {
        elementName.set(value);
    }

    /**
     * method to return the element name property
     *
     * @return the element name property
     */
    public StringProperty elementNameProperty()
    {
        return elementName;
    }

    /**
     * method to return the element attribute
     *
     * @return the element attribute
     */
    public String getElementAttribute()
    {
        return elementAttribute.get();
    }

    /**
     * method to set the element attribute
     *
     * @param value the element attribute
     */
    public void setElementAttribute(String value)
    {
        elementAttribute.set(value);
    }

    /**
     * method to return the element attribute property
     *
     * @return the element attribute property
     */
    public StringProperty elementAttributeProperty()
    {
        return elementAttribute;
    }

    /**
     * method to return the element value
     *
     * @return the element value
     */
    public String getElementValue()
    {
        return elementValue.get();
    }

    /**
     * method to set the element value
     *
     * @param value the element value
     */
    public void setElementValue(String value)
    {
        elementValue.set(value);
    }

    /**
     * method to return the element value property
     *
     * @return the element value property
     */
    public StringProperty elementValueProperty()
    {
        return elementValue;
    }

    /**
     * method to return the default section value
     *
     * @return the default section value (boolean)
     */
    public boolean isDefaultSection()
    {
        return defaultSection.get();
    }

    /**
     * method to set the default section value
     *
     * @param value the default section value
     */
    public void setDefaultSection(boolean value)
    {
        defaultSection.set(value);
    }

    /**
     * method to return the default section property
     *
     * @return the default section property
     */
    public BooleanProperty defaultSectionProperty()
    {
        return defaultSection;
    }

    /**
     * method to get the default type
     * @return the default type
     */
    public String getDefaultType()
    {
        return defaultType.get();
    }

    /**
     * method to set the default type
     * @param value the default type
     */
    public void setDefaultType(String value)
    {
        defaultType.set(value);
    }

    /**
     * method to return the default type property
     *
     * @return the default type property
     */
    public StringProperty defaultTypeProperty()
    {
        return defaultType;
    }

    /**
     * method to print the property as String
     *
     * @return the property as String
     */
    @Override
    public String toString()
    {
        StringBuilder phoneFeatureString = new StringBuilder("");
        phoneFeatureString.append("PhoneFeatureProperty{" + "elementName=").append(elementName.get()).append("elementContent= ").append(elementValue.get()).append("element attrubute=").append(elementAttribute.get()).append("default= ").append(defaultSection.get()).append('}');

        return phoneFeatureString.toString();
    }

}
