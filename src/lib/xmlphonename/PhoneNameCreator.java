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

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author nathanr
 */
public class PhoneNameCreator {
    
     private static ObservableList<PhoneNameProperty> xmlPhonePropertyData = FXCollections.observableArrayList();
     
         /**
     * Method to return list of FileProperty objects
     * @param files an ArrayList (String) of files
     * @return an ObservableList (FileProperty) with the FileProperty object representing the file list
     */
    public static ObservableList<PhoneNameProperty> createFilePropertyList (ArrayList<String> files)
    {
        for (String file : files)
        {
            xmlPhonePropertyData.add(new PhoneNameProperty(file));
        }
        
        return xmlPhonePropertyData;
    }
    
}
