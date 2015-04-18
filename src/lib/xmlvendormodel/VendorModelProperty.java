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
package lib.xmlvendormodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hedgehog01
 */
public final class VendorModelProperty
{
    private final StringProperty xmlName = new SimpleStringProperty("");
    private final StringProperty modelName = new SimpleStringProperty("");

    /**
     * default constructor
     */
    public VendorModelProperty()
    {
        
    }

    /**
     * constructor
     * @param xmlName the XML name
     * @param modelName the model name
     */
    public VendorModelProperty(String xmlName, String modelName)
    {
        setXmlName(xmlName);
        setModelName(modelName);
    }
    
    public String getModelName()
    {
        return modelName.get();
    }

    public void setModelName(String value)
    {
        modelName.set(value);
    }

    public StringProperty modelNameProperty()
    {
        return modelName;
    }
    
    public String getXmlName()
    {
        return xmlName.get();
    }

    public void setXmlName(String value)
    {
        xmlName.set(value);
    }

    public StringProperty xmlNameProperty()
    {
        return xmlName;
    }
    
}
