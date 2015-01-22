/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.xml.utils;


import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nathanr
 */
public class ReadXML {

    private static final Logger LOG = Logger.getLogger(ReadXML.class.getName());

    /**
     * Method to print all specific text in tags in the XML
     *
     * @param XMLName the XML to get results from (full XML path)
     * @param mainElement the main XML element (PHONE)
     * @param tagName the tag to get results from
     */
    public static ArrayList<String> getAllXMLTagTextByName(String XMLName, String mainElement, String tagName) {
        //the list to be returned
        ArrayList<String> list = new ArrayList<>();
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.parse(XMLName);

            // normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("Root element of the doc is "
                    + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName(mainElement);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    NodeList xmlChilderenNodes = element.getChildNodes();
                    //String phoneName = getNodeTextInfo(xmlChilderenNodes, tagName);
                    //list.add(phoneName);
                    for (int j = 0; j < xmlChilderenNodes.getLength(); j++) {
                        Node n = xmlChilderenNodes.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element name = (Element) n;
                            if (name.getTagName().equals(tagName) && !(name.hasAttributes())) {
                                list.add(name.getTextContent());
                                System.out.println("Adding " + name.getTextContent());
                            }

                        }
                    }

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOG.log(Level.SEVERE, "Exception:{0}", ex);
        }

        return list;
    }

    /*
     * private method that prints all tag text info from specific tag in all XML
     */
    private static String getNodeTextInfo(NodeList xmlChilderenNodes, String tagName) {
        String tagText = "";
        for (int i = 0; i < xmlChilderenNodes.getLength(); i++) {
            Node n = xmlChilderenNodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                if (name.getTagName().equals(tagName)) {
                    tagText = name.getTextContent();
                    System.out.println("Adding " + tagText);
                }

            }
        }
        return tagText;
    }

    /**
     * Method to print all XML elements under main node
     *
     * @param XMLName the XML to parse (Full path)
     * @param tagName The main XMl tag
     */
    public static void readXMLByName(String XMLName, String tagName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.parse(XMLName);

            // normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("Root element of the doc is "
                    + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName(tagName);
            //getAllNodeListElements(nodeList);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    NodeList xmlChilderenNodes = element.getChildNodes();
                    getNodeNameInfo(xmlChilderenNodes);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOG.log(Level.SEVERE, "Exception:{0}", ex);
        }
    }

    /*
     * private method that check Phone name and Guid name attribute
     */
    private static void getNodeNameInfo(NodeList xmlChilderenNodes) {
        String phoneName = null;
        String phoneGuidName = null;
        for (int j = 0; j < xmlChilderenNodes.getLength(); j++) {
            Node n = xmlChilderenNodes.item(j);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                if (name.getAttribute("name").isEmpty()) {
                    System.out.println(name.getTagName() + " " + name.getTextContent());
                }
                if (name.getTagName().equals("Guid")) {
                    phoneGuidName = name.getAttribute("name");
                }
                if (name.getTagName().equals("Name")) {
                    phoneName = name.getTextContent();
                }

                if ((phoneName != null) && (phoneGuidName != null) && (phoneName.equals(phoneGuidName) && (j == xmlChilderenNodes.getLength() - 2))) {

                    System.out.println("Guid name and phone name are equal");
                    phoneName = null;
                    phoneGuidName = null;

                }
                //System.out.println(name.getTagName() + " " + name.getTextContent() + " attribute: " + name.getAttribute("name"));

            }
        }
    }

    /**
     * Method to search for specific Node by text in one of it's elements.
     *
     * @param XMLName the XML to get results from (full XML path)
     * @param mainElement the main XML element (PHONE)
     * @param tagText the text in the tag to find by (MUST BE LONGER THAN 2
     * CHARS)
     */
    public static StringBuilder getAllNodeElements(String XMLName, String mainElement, String tagText) {
        StringBuilder phoneInfo = new StringBuilder();

        //Make sure search text is not
        if (tagText.length() > 2) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = docFactory.newDocumentBuilder();
                Document doc = builder.parse(XMLName);

                // normalize text representation
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName(mainElement);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        NodeList xmlChilderenNodes = element.getChildNodes();
                        //if text doesn't exist in the node remove from the list.
                        if (!(getNodeByTagText(xmlChilderenNodes, tagText))) {
                        } else if ((getNodeByTagText(xmlChilderenNodes, tagText))) {
                            phoneInfo = getAllNodeListElements(node);
                        }
                    }
                }

            } catch (ParserConfigurationException | SAXException | IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("Text to be search was too short");
        }
        return phoneInfo;
    }
    


    /*
     * Method to search for specific text the node elements.
     *
     * @param XMLName the XML to get results from (full XML path)
     * @param mainElement the main XML element (PHONE)
     * @param tagText the text in the tag (MUST BE LONGER THAN 2 CHARS)
     * return true if found
     */
    private static boolean getNodeByTagText(NodeList xmlChilderenNodes, String tagText) {

        boolean textExists = false;
        for (int j = 0; j < xmlChilderenNodes.getLength(); j++) {
            Node n = xmlChilderenNodes.item(j);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                //check if wanted text exists in the element
                //LOG.log(Level.INFO, "Tag content: {0}",name.getTextContent());
                String tempName = name.getTextContent();
                if (tempName.equals(tagText)) {
                    LOG.log(Level.INFO, "TagText {0} found", tagText);
                    textExists = true;
                }
            }
        }
        return textExists;
    }


    /**
     * Method to return all a nodes Elements
     * @param node the node to evaluate
     * @return StringBuilder containing element tag name + tag text
     */
    public static StringBuilder getAllNodeListElements (Node node)
    {
        StringBuilder sb = new StringBuilder();
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            Element element = (Element) node;

                NodeList xmlChilderenNodes = element.getChildNodes();
                for (int i = 0; i < xmlChilderenNodes.getLength(); i++) {

                    Node phoneNode = xmlChilderenNodes.item(i);
                    if (phoneNode.getNodeType() == Node.ELEMENT_NODE) {
                        
                        /*
                        NamedNodeMap attributes = phoneNode.getAttributes();
                        for (int j=0; i<attributes.getLength();j++)
                        {
                            Node attr = attributes.item(j);
                            System.out.println ("attribute name: " + attr.getNodeName() + " attribute Value: " + attr.getNodeValue());
                        }
                        */
                        
                        String elementInfo = ("<" + phoneNode.getNodeName()  +">" + phoneNode.getTextContent() + "\n");
                        
                        
                        sb.append(elementInfo);

                        System.out.println(elementInfo);
                    }

                }
        }
        return sb;
    }
}
