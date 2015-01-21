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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hedgehog01
 */
public class PhoneFeatureProperty
{

    private final StringProperty phoneDate = new SimpleStringProperty("");

    public PhoneFeatureProperty()
    {
    }

    public PhoneFeatureProperty(String name)
    {
        setPhoneDate(name);
    }

    public String getPhoneDate()
    {
        return phoneDate.get();
    }

    public void setPhoneDate(String value)
    {
        phoneDate.set(value);
    }

    public StringProperty phoneDateProperty()
    {
        return phoneDate;
    }

    @Override
    public String toString()
    {
        return "PhoneFeatureProperty{" + "phoneDate=" + phoneDate.get() + '}';
    }
    

}
