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
import lib.xml.utils.ReadXML;

/**
 * Class that handles getting phone names
 *
 * @author nathanr
 */
public final class PhoneNameHandler
{

    private static final String MAIN_ELEMENT = "PHONE";
    private static final String PHONE_NAME = "Name";

    /**
     * method to get the list of phones in an XML
     *
     * @param xmlPath the full path the the XML to be searched
     * @return an ArrayList of phone names (String)
     */
    public static ArrayList<String> getPhoneNames(String xmlPath)
    {
        ArrayList<String> list = new ArrayList<>();
        if (!xmlPath.isEmpty() && xmlPath.contains(".xml"))
        {
            list = ReadXML.getAllXMLTagTextByName(xmlPath, MAIN_ELEMENT, PHONE_NAME);
        }

        return list;
    }
}
