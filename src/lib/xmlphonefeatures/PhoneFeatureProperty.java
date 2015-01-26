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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hedgehog01
 */
public final class PhoneFeatureProperty implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final StringProperty elementName = new SimpleStringProperty("");
    private final StringProperty elementAttribute = new SimpleStringProperty("");
    private final StringProperty elementValue = new SimpleStringProperty("");



    public PhoneFeatureProperty()
    {
        this(null, null,null);
    }

    /**
     * Constructor with initial data
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

    public String getElementName()
    {
        return elementName.get();
    }

    public void setElementName(String value)
    {
        elementName.set(value);
    }

    public StringProperty elementNameProperty()
    {
        return elementName;
    }

    public String getElementAttribute()
    {
        return elementAttribute.get();
    }

    public void setElementAttribute(String value)
    {
        elementAttribute.set(value);
    }

    public StringProperty elementAttributeProperty()
    {
        return elementAttribute;
    }
    
        public String getElementValue() {
        return elementValue.get();
    }

    public void setElementValue(String value) {
        elementValue.set(value);
    }

    public StringProperty elementValueProperty() {
        return elementValue;
    }

    @Override
    public String toString()
    {
        StringBuilder phoneFeatureString = new StringBuilder("");
         phoneFeatureString.append("PhoneFeatureProperty{" + "elementName=").append(elementName.get()).append("elementContent= ").append(elementValue.get()).append("element attrubute=").append(elementAttribute.get()).append('}');
         
         return phoneFeatureString.toString();
    }

}
