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
package lib.xmlphonefeatures;

import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lib.logUtil.MyLogger;

/**
 * class to compare PhoneFeature Properties
 * @author nathanr
 */
public class PhoneFeaturePropertyCompare
{
    private static final Level LOG_LEVEL_INFO = Level.INFO;
    private static final Level LOG_LEVEL_WARNING = Level.WARNING;
    private static final Level LOG_LEVEL_FINE = Level.FINE;

    
    /**
     * method to compare 2 phone feature properties
     * @param phone1 the first object to compare
     * @param phone2 the second object to compare
     * @return true if all object elements are equal and false if not
     */
    public static boolean isPhoneFeaturesPropertyEqual(PhoneFeatureProperty phone1,PhoneFeatureProperty phone2 )
    {
        MyLogger.log(LOG_LEVEL_INFO,"Comparing phone feature properties");
        if (phone1.getDefaultType().equals(phone2.getDefaultType()) && phone1.getElementAttribute().equals(phone2.getElementAttribute()) && phone1.getElementName().equals(phone2.getElementName()) && phone1.getElementValue().equals(phone2.getElementValue()) && phone1.getTagOrigin().equals(phone2.getTagOrigin()))
        {
            MyLogger.log(LOG_LEVEL_FINE, "Phone feature properties equal, tag name: {0}", phone2.getElementName());
            return true;
        }
        else
        {
            MyLogger.log(LOG_LEVEL_FINE, "Phone feature properties NOT equal, tag name: {0}", phone2.getElementName());
            return false;
        }
        
    }
    
    
}
