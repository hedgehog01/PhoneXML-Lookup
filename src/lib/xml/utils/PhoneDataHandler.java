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
import lib.logUtil.MyLogger;
import lib.xmlphonefeatures.PhoneFeatureProperty;

/**
 * Class to handle operations on phone data
 *
 * @author Hedgehog01
 */
public final class PhoneDataHandler
{

    private final static String OS_DEFAULT = "_OS_Default";
    private final static String DEFAULT_SECTION = "Default";
    private static final Level LOG_LEVEL_INFO = Level.INFO;
    private static final Level LOG_LEVEL_SEVER = Level.SEVERE;
    private static final Level LOG_LEVEL_FINE = Level.FINE;

    /**
     * Method to remove duplicate data from default phone data property data if
     * exists in another phone property data
     *
     * @param defaultPhoneFeaturePropertyData the default property data that
     * should be manipulated
     * @param phoneFeaturePropertyData the phone to compare the default data to
     * @return the updated default property data with removed duplicates
     */
    public static ObservableList<PhoneFeatureProperty> removeDupProperties(ObservableList<PhoneFeatureProperty> defaultPhoneFeaturePropertyData, ObservableList<PhoneFeatureProperty> phoneFeaturePropertyData)
    {
        ObservableList<PhoneFeatureProperty> updatedDefaultProperty = FXCollections.observableArrayList();
        updatedDefaultProperty.addAll(defaultPhoneFeaturePropertyData);

        //remove tags from default that already exists in phone
        for (Iterator<PhoneFeatureProperty> itPhone = phoneFeaturePropertyData.iterator(); itPhone.hasNext();)
        {
            String phoneTagName = itPhone.next().getElementName();

            //String phoneTagDefaultType = itPhone.next().getDefaultType();
            //LOG.log(Level.INFO, "Phone tag Name: {0}",phoneTagName);
            updatedDefaultProperty.removeIf(tag -> tag.getElementName().equals(phoneTagName));
            //updatedDefaultProperty.removeIf(tag -> tag.getElementName().equals(phoneTagName) || (tag.getElementName().equals(phoneTagName) && tag.getDefaultType().contains("_OS_Default")));
        }

        return updatedDefaultProperty;

    }

    /**
     * method to remove default tags in case default OS added
     *
     * @param defaultOSPhoneFeaturePropertyData the default OS section
     * @param phoneFeaturePropertyData the selected phone
     * @return the selected phone without tags from default section that already
     * appear in the default OS section
     */
    public static ObservableList<PhoneFeatureProperty> removeDupDefaultProperties(ObservableList<PhoneFeatureProperty> defaultOSPhoneFeaturePropertyData, ObservableList<PhoneFeatureProperty> phoneFeaturePropertyData)
    {
        ObservableList<PhoneFeatureProperty> updatedDefaultOSProperty = FXCollections.observableArrayList();
        updatedDefaultOSProperty.addAll(defaultOSPhoneFeaturePropertyData);

        //remove tags from phone that already exists in default
        for (Iterator<PhoneFeatureProperty> itPhone = phoneFeaturePropertyData.iterator(); itPhone.hasNext();)
        {
            String phoneTagName;
            String phoneTagDefaultType;
            if (itPhone.hasNext())
            {
                phoneTagName = itPhone.next().getElementName();
                MyLogger.log(LOG_LEVEL_FINE, "phone name tag: {0}", phoneTagName);
                phoneTagDefaultType = itPhone.next().getDefaultType();
                MyLogger.log(LOG_LEVEL_FINE, "phone default type: {0}", phoneTagDefaultType);
            } else
            {
                phoneTagName = "";
                phoneTagDefaultType = "";
            }

            //LOG.log(Level.INFO, "Phone tag Name: {0}",phoneTagName);
            updatedDefaultOSProperty.removeIf(tag -> tag.getElementName().equals(phoneTagName) && !(phoneTagDefaultType.contains(OS_DEFAULT)));
        }

        return updatedDefaultOSProperty;
    }

    public static ObservableList<PhoneFeatureProperty> removeDupDefaultProperties1(ObservableList<PhoneFeatureProperty> defaultOSPhoneFeaturePropertyData, ObservableList<PhoneFeatureProperty> phoneFeaturePropertyData)
    {
        ObservableList<PhoneFeatureProperty> updatedDefaultOSProperty = FXCollections.observableArrayList();
        updatedDefaultOSProperty.addAll(defaultOSPhoneFeaturePropertyData);

        /**
         * //remove tags from phone that already exists in default for
         * (Iterator<PhoneFeatureProperty> itPhone =
         * phoneFeaturePropertyData.iterator(); itPhone.hasNext();) { String
         * phoneTagName; String phoneTagDefaultType; if (itPhone.hasNext()) {
         * phoneTagName = itPhone.next().getElementName(); LOG.log(Level.INFO,
         * "phone name tag: {0}", phoneTagName); phoneTagDefaultType =
         * itPhone.next().getDefaultType(); LOG.log(Level.INFO, "phone default
         * type: {0}", phoneTagDefaultType); } else { phoneTagName="";
         * phoneTagDefaultType=""; }
         *
         * //LOG.log(Level.INFO, "Phone tag Name: {0}",phoneTagName);
         * updatedDefaultOSProperty.removeIf(tag ->
         * tag.getElementName().equals(phoneTagName) &&
         * !(phoneTagDefaultType.contains(OS_DEFAULT))); }
         */
        for (int i = 0; i < phoneFeaturePropertyData.size(); i++)
        {
            String phoneTagName;
            String phoneTagDefaultType;

            phoneTagName = phoneFeaturePropertyData.get(i).getElementName();
            MyLogger.log(LOG_LEVEL_FINE, "phone name tag: {0}", phoneTagName);
            phoneTagDefaultType = phoneFeaturePropertyData.get(i).getDefaultType();
            MyLogger.log(LOG_LEVEL_FINE, "phone default type: {0}", phoneTagDefaultType);

            //LOG.log(Level.INFO, "Phone tag Name: {0}",phoneTagName);
            //updatedDefaultOSProperty.removeIf(tag -> tag.getElementName().equals(phoneTagName) && (phoneTagDefaultType.contains(DEFAULT_SECTION)));
            updatedDefaultOSProperty.removeIf(tag -> tag.getElementName().equals(phoneTagName));
        }

        return updatedDefaultOSProperty;
    }

}
