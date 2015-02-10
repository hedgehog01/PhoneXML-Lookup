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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lib.xmlphonefeatures.PhoneFeatureProperty;


/**
 *
 *
 * @author Hedgehog01
 */
public class PhoneDataHandler
{
    private static final Logger LOG = Logger.getLogger(PhoneDataHandler.class.getName());
    
    
    
    public static ObservableList<PhoneFeatureProperty> removeDupProperties(ObservableList<PhoneFeatureProperty> defaultPhoneFeaturePropertyData, ObservableList<PhoneFeatureProperty> phoneFeaturePropertyData)
    {     
        ObservableList<PhoneFeatureProperty> updatedDefaultProperty = FXCollections.observableArrayList();
        updatedDefaultProperty.addAll(defaultPhoneFeaturePropertyData);

        
        //remove tags from default that already exists in phone
        for (Iterator<PhoneFeatureProperty> itPhone =phoneFeaturePropertyData.iterator() ;itPhone.hasNext();)
        {
            String phoneTagName = itPhone.next().getElementName();
            
            //LOG.log(Level.INFO, "Phone tag Name: {0}",phoneTagName);
            updatedDefaultProperty.removeIf(tag -> tag.getElementName().equals(phoneTagName));
            
        }

        return updatedDefaultProperty;

    }
    
    
    
}
