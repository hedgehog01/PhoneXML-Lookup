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

/**
 *
 * @author nathanr
 */
public class ReadXMLTest {
    

    public ReadXMLTest() {
    }
    @Ignore
    @BeforeClass
    public static void setUpClass() {
    }
    @Ignore
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
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagName = "Name";
        String expResult = "YP-GB70 Yepp Galaxy S Player";
        ArrayList<String> testArray = ReadXML.getAllXMLTagTextByName(XMLName, mainElement, tagName);
        String result = testArray.get(3);
        assertEquals(expResult, result);
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
        ArrayList<String> result = ReadXML.getNodePhoneTagNameListXPATH(node);
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
     * Test of getNodeByTagValue method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodeByTagValue() {
        System.out.println("getAllNodeElements");
        String XMLName = "SamsungGSM.xml";
        String mainElement = "PHONE";
        String tagText = "GT-P6800 Galaxy Tab 7.7";
        Node expResult = null;
        Node tempNode = ReadXML.getNodeByTagValue(XMLName, mainElement, tagText);
        String result = tempNode.getFirstChild().getNodeName();
        
        System.out.println ("Result: " + result);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getNodeListByTagValue method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodeListByTagValue()
    {
        System.out.println("getNodeListByTagValue");
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagValue = "Name";
        boolean matchWholeWordSelected = false;
        ArrayList<Node> expResult = null;
        ArrayList<Node> result = ReadXML.getNodeListByTagValue(XMLName, mainElement, tagValue, matchWholeWordSelected);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodeListByTagName method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodeListByTagName()
    {
        System.out.println("getNodeListByTagName");
        String XMLName = "";
        String mainElement = "";
        String tagName = "";
        boolean matchWholeWordSelected = false;
        ArrayList<Node> expResult = null;
        ArrayList<Node> result = ReadXML.getNodeListByTagName(XMLName, mainElement, tagName, matchWholeWordSelected);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneTagValueList method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneTagValueList()
    {
        System.out.println("getNodePhoneTagValueList");
        Node node = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = ReadXML.getNodePhoneTagValueList(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneTagValue method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneTagValue()
    {
        System.out.println("getNodePhoneTagValue");
        Node node = null;
        String tagName = "";
        String expResult = "";
        String result = ReadXML.getNodePhoneTagValue(node, tagName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllXMLTagTextByNameXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetAllXMLTagTextByNameXPATH()
    {
        System.out.println("getAllXMLTagTextByNameXPATH");
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagName = "DataBaseModuleClass";
        String rootElement = "dataroot";
        ArrayList<String> expResultTest = new ArrayList<String>();
        expResultTest.add("AppsData");
        //ArrayList<String> expResult = ReadXML.getAllXMLTagTextByName(XMLName, mainElement, tagName);
        ArrayList<String> result = ReadXML.getAllXMLTagTextByNameXPATH(XMLName,rootElement, mainElement, tagName);
        System.out.println("expected result length of array: "+expResultTest.size());
        System.out.println("actual result length of array: "+result.size());
        System.out.println("getAllXMLTagTextByName");
        expResultTest.stream().forEach((exp) ->
        {
            System.out.println(exp);
        });
        System.out.println();
        System.out.println("getAllXMLTagTextByNameXPATH");
        result.stream().forEach((exp) ->
        {
            System.out.println(exp);
        });
        assertEquals(expResultTest, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNodeListByTagValueXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodeListByTagValueXPATH()
    {
        System.out.println("getNodeListByTagValueXPATH");
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagValue = "dffb8a35-c882-4385-af9e-82d11150c552";
        String rootElement = "dataroot";
        boolean matchWholeWordSelected = true;
        ArrayList<Node> expResult = ReadXML.getNodeListByTagValue(XMLName, mainElement, tagValue, matchWholeWordSelected);
        ArrayList<Node> result = ReadXML.getNodeListByTagValueXPATH(XMLName, mainElement, tagValue, matchWholeWordSelected);
        System.out.println ("expResult number of results: "+(expResult.size()));
        System.out.println (" actual node value: "+expResult.get(0).getNodeName() + " exp value: "+expResult.get(0).getTextContent());
        System.out.println ("result number of results: "+(result.size()));
        System.out.println (" actual node name: "+expResult.get(0).getNodeName() + " actual value: "+expResult.get(0).getTextContent());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAllXMLNodes method, of class ReadXML.
     */
    //@Ignore
    @Test
    public void testGetAllXMLNodesXPATH()
    {
        System.out.println("getAllXMLNodes");
        String XMLName = "F:\\Hedgehog01  DOCS\\Documents\\XML Phones\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagValue = "GSM";
        String rootElement = "dataroot";
       
        ArrayList<Node> result = ReadXML.getAllXMLNodesXPATH(XMLName, mainElement);
        /*
        for (int i=0;i<result.size();i++)
        {
            if (result.get(i).getNodeType() == Node.ELEMENT_NODE)
            {
                System.out.println("Found Node: " + result.get(i).getNodeName());
                
            }
        }
        */
        System.out.println("All node in XML are: "+result.size());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAllNodeElementsXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetAllNodeElementsXPATH()
    {
        System.out.println("getAllNodeElementsXPATH");
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagValue = "GSM";
        Node node = ReadXML.getNodeByTagValue(XMLName, mainElement, tagValue);
        ReadXML instance = new ReadXML();
        instance.getAllNodeElementsXPATH(node);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of isValueInNodeXPATH method, of class ReadXML.
     */
    //@Ignore
    @Test
    public void testIsValueInNodeXPATH()
    {
        System.out.println("isValueInNodeXPATH");
        String XMLName = "F:\\Hedgehog01  DOCS\\Documents\\XML Phones\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagValue = "Android_OS_Default";
        Node node = ReadXML.getNodeByTagValue(XMLName, mainElement, tagValue);
        String value = "Android.txt";
        ReadXML instance = new ReadXML();
        boolean expResult = true;
        boolean exactMatch = true;
        boolean result = instance.isValueInNodeXPATH(node, value,exactMatch);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneTagValueXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneTagValueXPATH()
    {
        
        System.out.println("getNodePhoneTagValueXPATH");
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String selectedPhone = "A500 Iconia Tab";
        Node node = ReadXML.getNodeByTagValue(XMLName, mainElement, selectedPhone);
        String tagName = "Guid";
        String expResult = "dffb8a35-c882-4385-af9e-82d11150c552";
        String result = ReadXML.getNodePhoneTagValueXPATH(node, tagName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNodeListByTagNameXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodeListByTagNameXPATH()
    {
        System.out.println("getNodeListByTagNameXPATH");
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagName = "DBFolderList";
        boolean matchWholeWordSelected = true;
        ArrayList<Node> expResult = ReadXML.getNodeListByTagName(XMLName, mainElement, tagName, matchWholeWordSelected);;
        ArrayList<Node> result = ReadXML.getNodeListByTagNameXPATH(XMLName, mainElement, tagName, matchWholeWordSelected);
        System.out.println("Expected Array Length: "+ expResult.size());
        System.out.println("Actual Array Length: "+ result.size());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of isTagInNodeXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testIsTagInNodeXPATH()
    {
        System.out.println("isTagInNodeXPATH");
        String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String selectedPhone = "A500 Iconia Tab";
        Node node = ReadXML.getNodeByTagValue(XMLName, mainElement, selectedPhone);
        String tagName = "ResetTime";
        boolean matchWholeWordSelected = true;
        boolean expResult = true;       
        boolean result = ReadXML.isTagInNodeXPATH(node, tagName, matchWholeWordSelected);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNodeByTagValueXPATH method, of class ReadXML.
     */
    //@Ignore
    @Test
    public void testGetNodeByTagValueXPATH()
    {
        System.out.println("getNodeByTagValueXPATH");
        String XMLName = "F:\\Hedgehog01  DOCS\\Documents\\XML Phones\\DataFiles\\Phones\\__Acer.xml";
        String mainElement = "PHONE";
        String tagValue = "Android_OS_Default";
        Node expResult = ReadXML.getNodeByTagValue(XMLName, mainElement, tagValue);;
        Node result = ReadXML.getNodeByTagValueXPATH(XMLName, mainElement, tagValue);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneTagNameListXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneTagNameListXPATH()
    {
        System.out.println("getNodePhoneTagNameListXPATH");
        //String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__SamsungGSM.xml";
        String XMLName = "F:\\Hedgehog01  DOCS\\Documents\\XML Phones\\DataFiles\\Phones\\__SamsungGSM.xml";
        String mainElement = "PHONE";
        //String selectedPhone = "YP-GB70 Yepp Galaxy S Player";
        String selectedPhone = "Android_OS_Default";
        Node node = ReadXML.getNodeByTagValue(XMLName, mainElement, selectedPhone);
        ArrayList<String> expResult = ReadXML.getNodePhoneTagNameList(node);
        ArrayList<String> result = ReadXML.getNodePhoneTagNameListXPATH(node);
        System.out.println("List expected length: "+expResult.size()+" Actual list length: "+ result.size());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneTagValueListXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneTagValueListXPATH()
    {
        System.out.println("getNodePhoneTagValueListXPATH");
        //String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__SamsungGSM.xml";
        String XMLName = "F:\\Hedgehog01  DOCS\\Documents\\XML Phones\\DataFiles\\Phones\\__SamsungGSM.xml";
        String mainElement = "PHONE";
        //String selectedPhone = "YP-GB70 Yepp Galaxy S Player";
        String selectedPhone = "Android_OS_Default";
        Node node = ReadXML.getNodeByTagValue(XMLName, mainElement, selectedPhone);
        ArrayList<String> expResult = ReadXML.getNodePhoneTagValueList(node);
        ArrayList<String> result = ReadXML.getNodePhoneTagValueListXPATH(node);
        System.out.println("List expected length: "+expResult.size()+" Actual list length: "+ result.size());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNodePhoneAttributeListXPATH method, of class ReadXML.
     */
    @Ignore
    @Test
    public void testGetNodePhoneAttributeListXPATH()
    {
        System.out.println("getNodePhoneAttributeListXPATH");
        //String XMLName = "C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones\\__SamsungGSM.xml";
        String XMLName = "F:\\Hedgehog01  DOCS\\Documents\\XML Phones\\DataFiles\\Phones\\__SamsungGSM.xml";
        
        String mainElement = "PHONE";
        //String selectedPhone = "YP-GB70 Yepp Galaxy S Player";
        String selectedPhone = "Android_OS_Default";
        Node node = ReadXML.getNodeByTagValue(XMLName, mainElement, selectedPhone);
        ArrayList<String> expResult = ReadXML.getNodePhoneAttributeList(node);
        ArrayList<String> result = ReadXML.getNodePhoneAttributeListXPATH(node);
        System.out.println("List expected length: "+expResult.size()+" Actual list length: "+ result.size());
        System.out.println("List expected length: "+expResult.size()+" Actual list length: "+ result.size());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }



    
}
