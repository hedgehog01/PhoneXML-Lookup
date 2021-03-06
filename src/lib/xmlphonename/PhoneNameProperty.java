/*
 * Copyright (C) 2015 nathanr
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
package lib.xmlphonename;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class that represent a phone name property
 * @author nathanr
 */
public final class PhoneNameProperty implements Serializable{
    private static final long serialVersionUID = 1L;
    private final StringProperty phoneName = new SimpleStringProperty("");

    /**
     * Default constructor
     */
    public PhoneNameProperty() {
    }

    /**
     * Constructor - sets phone name
     * @param phoneName the phone name
     */
    public PhoneNameProperty(String phoneName) {
        setPhoneName(phoneName);
        
    }

    /**
     * method to return the phone name
     * @return the phone name
     */
    public String getPhoneName() {
        return phoneName.get();
    }

    /**
     * method to set the phone name
     * @param value the phone name
     */
    public void setPhoneName(String value) {
        phoneName.set(value);
    }

    /**
     * method to return the phone name property
     * @return the phone name property
     */
    public StringProperty phoneNameProperty() {
        return phoneName;
    }

    /**
     * method to return the phone name String 
     * @return the phone name String
     */
    @Override
    public String toString() {
        return "XMLPhoneNameProperty{" + "phoneName=" + phoneName.get() + '}';
    }
    
    
}
