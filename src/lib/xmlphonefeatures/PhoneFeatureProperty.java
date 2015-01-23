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
public final class PhoneFeatureProperty implements Serializable {
    private static final long serialVersionUID = 1L;

    private final StringProperty elementName = new SimpleStringProperty("");
    private final StringProperty elementContent = new SimpleStringProperty("");



    public PhoneFeatureProperty() {
        this (null,null);
    }

    /**
     * Constructor with initial data
     * @param elementName the element name
     * @param elmentContent the element content
     */
    public PhoneFeatureProperty(String elementName,String elmentContent) {
        setElementName(elementName);
        setElementContent(elmentContent);        
    }

    public String getElementName() {
        return elementName.get();
    }

    public void setElementName(String value) {
        elementName.set(value);
    }

    public StringProperty elementNameProperty() {
        return elementName;
    }

    public String getElementContent() {
        return elementContent.get();
    }

    public void setElementContent(String value) {
        elementContent.set(value);
    }

    public StringProperty elementContentProperty() {
        return elementContent;
    }
    

    @Override
    public String toString() {
        return "PhoneFeatureProperty{" + "elementName=" + elementName.get() + "elementContent= " + elementContent.get() +'}';
    }

}
