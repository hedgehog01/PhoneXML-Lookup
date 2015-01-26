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
package lib.xml.utils;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author nathanr
 */
public class ReadXMLTest {
    
    public ReadXMLTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getAllXMLTagTextByName method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetAllXMLTagTextByName() {
        System.out.println("getAllXMLTagTextByName");
        String XMLName = "SamsungGSM.xml";
        String mainElement = "PHONE";
        String tagName = "Name";
        ArrayList<String> expResult = null;
        ArrayList<String> result = ReadXML.getAllXMLTagTextByName(XMLName, mainElement, tagName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLByName method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testReadXMLByName() {
        System.out.println("readXMLByName");
        String XMLName = "SamsungGSM.xml";
        String tagName = "GT-I9192 Galaxy S4 Mini Duos";
        ReadXML.readXMLByName(XMLName, tagName);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of getAllNodeListElements method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetAllNodeListElements() {
        System.out.println("getAllNodeListElements");
        Node node = null;
        StringBuilder expResult = null;
        StringBuilder result = ReadXML.getAllNodeListElements(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printElementsAndAttributes method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testPrintElementsAndAttributes()
    {
        System.out.println("printElementsAndAttributes");
        String xmlPath = "SamsungGSM.xml";
        String mainElement = "PHONE";
        ReadXML.printElementsAndAttributes(xmlPath, mainElement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneTagNameList method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneTagNameList() {
        System.out.println("getNodePhoneInfoList");
        Node node = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = ReadXML.getNodePhoneTagNameList(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneAttributeList method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneAttributeList() {
        System.out.println("getNodePhoneAttributeList");
        Node node = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = ReadXML.getNodePhoneAttributeList(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllNodeElements method, of class ReadXML.
     */
    @Test
    public void testGetAllNodeElements() {
        System.out.println("getAllNodeElements");
        String XMLName = "SamsungGSM.xml";
        String mainElement = "PHONE";
        String tagText = "GT-P6800 Galaxy Tab 7.7";
        Node expResult = null;
        Node result = ReadXML.getAllNodeElements(XMLName, mainElement, tagText);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }



    
}
