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
package lib.xml.utils;

import java.util.Iterator;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lib.xmlphonefeatures.PhoneFeatureProperty;


/**
 * Class to handle operations on phone data
 *
 * @author Hedgehog01
 */
public class PhoneDataHandler
{
    private static final Logger LOG = Logger.getLogger(PhoneDataHandler.class.getName());
    
    
    /**
     * Method to remove duplicate data from default phone data property data if exists in another phone property data
     * @param defaultPhoneFeaturePropertyData the default property data that should be manipulated
     * @param phoneFeaturePropertyData the phone to compare the default data to
     * @return the updated default property data with removed duplicates
     */
    public static ObservableList<PhoneFeatureProperty> removeDupProperties(ObservableList<PhoneFeatureProperty> defaultPhoneFeaturePropertyData, ObservableList<PhoneFeatureProperty> phoneFeaturePropertyData)
    {     
        ObservableList<PhoneFeatureProperty> updatedDefaultProperty = FXCollections.observableArrayList();
        updatedDefaultProperty.addAll(defaultPhoneFeaturePropertyData);

        
        //remove tags from default that already exists in phone
        for (Iterator<PhoneFeatureProperty> itPhone = phoneFeaturePropertyData.iterator() ;itPhone.hasNext();)
        {
            String phoneTagName = itPhone.next().getElementName();
            
            //LOG.log(Level.INFO, "Phone tag Name: {0}",phoneTagName);
            updatedDefaultProperty.removeIf(tag -> tag.getElementName().equals(phoneTagName));
            
        }

        return updatedDefaultProperty;

    }
    
    
    
}
