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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nathanr
 */
public final class PhoneNameProperty {
    private final StringProperty phoneName = new SimpleStringProperty("");

    public PhoneNameProperty(String phoneName) {
        setPhoneName(phoneName);
        
    }

    public String getPhoneName() {
        return phoneName.get();
    }

    public void setPhoneName(String value) {
        phoneName.set(value);
    }

    public StringProperty phoneNameProperty() {
        return phoneName;
    }

    @Override
    public String toString() {
        return "XMLPhoneNameProperty{" + "phoneName=" + phoneName.get() + '}';
    }
    
    
}
