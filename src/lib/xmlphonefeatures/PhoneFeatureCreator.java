/*
 * Copyright (C) 2015 Avishag
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

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Avishag
 */
public final class PhoneFeatureCreator {

    //private static ObservableList<PhoneFeatureProperty> phoneData = FXCollections.observableArrayList();

    /**
     * method to create PhoneFeatureProperty's and add them to ObservableList
     * @param elementName the element tag name
     * @param elementValue the element tag value
     * @param elementAttribute the element attribute
     * @return list of elements in an ObservableList
     */
    public static ObservableList<PhoneFeatureProperty> createPhoneFeatureList(ArrayList<String> elementName, ArrayList<String> elementValue,ArrayList<String> elementAttribute) {
        ObservableList<PhoneFeatureProperty> phoneData = FXCollections.observableArrayList();
        if ((elementName!=null && elementValue!=null) && (elementName.size() == elementValue.size())) 
        {
            for (int i=0; i<elementName.size(); i++)
            {
                phoneData.add(new PhoneFeatureProperty(elementName.get(i), elementValue.get(i),elementAttribute.get(i)));
            }
        }

        return phoneData;
    }
    
        /**
     * method to create PhoneFeatureProperty's and add them to ObservableList
     * @param elementName the element tag name
     * @param elementValue the element tag value
     * @param elementAttribute the element attribute
     * @return list of elements in an ObservableList
     */
    public static ObservableList<PhoneFeatureProperty> createPhoneFeatureList(ArrayList<String> elementName, ArrayList<String> elementValue,ArrayList<String> elementAttribute,boolean isDefault) {
        ObservableList<PhoneFeatureProperty> phoneData = FXCollections.observableArrayList();
        if ((elementName!=null && elementValue!=null) && (elementName.size() == elementValue.size())) 
        {
            for (int i=0; i<elementName.size(); i++)
            {
                phoneData.add(new PhoneFeatureProperty(elementName.get(i), elementValue.get(i),elementAttribute.get(i),isDefault));
            }
        }

        return phoneData;
    }
}
