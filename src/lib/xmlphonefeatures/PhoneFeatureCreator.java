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
public class PhoneFeatureCreator {

    ObservableList<PhoneFeatureProperty> phoneData = FXCollections.observableArrayList();

    /**
     *
     * @param elementName
     * @param elementContent
     * @param elementAttribute
     * @return
     */
    public ObservableList<PhoneFeatureProperty> createPhoneFeatureList(ArrayList<String> elementName, ArrayList<String> elementContent,ArrayList<String> elementAttribute) {
        if ((elementName!=null && elementContent!=null) && (elementName.size() == elementContent.size())) 
        {
            for (int i=0; i<elementName.size(); i++)
            {
                phoneData.add(new PhoneFeatureProperty(elementName.get(i), elementContent.get(i),elementAttribute.get(i)));
            }
        }

        return phoneData;
    }
}
