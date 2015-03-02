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
 * Class to handle read related operations on XML files
 * @author nathanr
 */
public class ReadXML {

    private static final Logger LOG = Logger.getLogger(ReadXML.class.getName());

    /**
     * Method to return all XML tag text from specific tags in the XML
     *
     * @param XMLName the XML to get results from (full XML path)
     * @param mainElement the main XML element (PHONE)
     * @param tagName the tag to get results from
     * @return list of all tag text from the requested tags 
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
     * Method to search for specific Node by text in one of it's elements - returns the node if found.
     *
     * @param XMLName the XML to get results from (full XML path)
     * @param mainElement the main XML element (PHONE)
     * @param tagText the text in the tag to find by (MUST BE LONGER THAN 2
     * CHARS)
     * @return the phone Node or null if not found
     */
    public static Node getAllNodeElements(String XMLName, String mainElement, String tagText) {
        //StringBuilder phoneInfo = new StringBuilder();
        Node phoneInfoNode = null;

        //Make sure search text is not
        if (tagText !=null && tagText.length() > 2) {
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
                        // check if text exist in the node if so it's node we want.
                        
                            
                        if ((getNodeByTagText(xmlChilderenNodes, tagText))) {
                            phoneInfoNode = node;
                        }
                        
                    }
                }

            } catch (ParserConfigurationException | SAXException | IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("Text to be search was too short or null");
        }
        return phoneInfoNode;
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
     * Method to return all a node Elements and attributes as StringBuilder object
     *
     * @param node the node to evaluate
     * @return StringBuilder containing element tag name + tag text +element attributes
     */
    public static StringBuilder getAllNodeListElements(Node node) {
        StringBuilder sb = new StringBuilder();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            NodeList xmlChilderenNodes = element.getChildNodes();
            for (int i = 0; i < xmlChilderenNodes.getLength(); i++) {

                Node phoneNode = xmlChilderenNodes.item(i);
                if (phoneNode.getNodeType() == Node.ELEMENT_NODE) {

                    NamedNodeMap attributesNodeMap = phoneNode.getAttributes();
                    StringBuilder attributeSB = new StringBuilder("");
                    if (attributesNodeMap != null && attributesNodeMap.getLength() > 0) {

                        for (int j = 0; j < attributesNodeMap.getLength(); j++) {
                            attributeSB.append(" ").append(attributesNodeMap.item(j).getNodeName()).append("=\"").append(attributesNodeMap.item(j).getNodeValue()).append("\"");
                        }

                    }

                    String elementInfo = ("<" + phoneNode.getNodeName() + attributeSB.toString() + ">" + phoneNode.getTextContent() + "</" + phoneNode.getNodeName() + ">" + "\n");

                    sb.append(elementInfo);

                    //System.out.println(elementInfo);
                }

            }
        }
        return sb;
    }

    /**
     * Method to return all a node phone tag names
     *
     * @param node the node to evaluate
     * @return ArrayList of String containing XML element tag names
     */
    public static ArrayList<String> getNodePhoneTagNameList(Node node) {
        ArrayList<String> phoneInfoList = new ArrayList<>();

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            NodeList xmlChilderenNodes = element.getChildNodes();
            for (int i = 0; i < xmlChilderenNodes.getLength(); i++) {

                Node phoneNode = xmlChilderenNodes.item(i);
                if (phoneNode.getNodeType() == Node.ELEMENT_NODE) {
                    String elementInfo = ("<" + phoneNode.getNodeName() + ">");
                    //System.out.println("Phone Tag Name: " + elementInfo);
                    phoneInfoList.add(elementInfo);
                }

            }
        }
        return phoneInfoList;
    }
    

    /**
     * Method to return all a node phone tag values
     *
     * @param node the node to evaluate
     * @return ArrayList of String containing element XML tag values
     */
    public static ArrayList<String> getNodePhoneTagValueList(Node node) {
        ArrayList<String> phoneInfoList = new ArrayList<>();

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            NodeList xmlChilderenNodes = element.getChildNodes();
            for (int i = 0; i < xmlChilderenNodes.getLength(); i++) {

                Node phoneNode = xmlChilderenNodes.item(i);
                if (phoneNode.getNodeType() == Node.ELEMENT_NODE) {
                    String elementInfo = (phoneNode.getTextContent());
                    //System.out.println("Phone Tag: " + elementInfo);
                    phoneInfoList.add(elementInfo);

                }

            }
        }
        return phoneInfoList;
    }

    /**
     * Method to return all a node phone Attributes
     *
     * @param node the node to evaluate
     * @return ArrayList of String containing XML element attributes if exists and
     * empty if not
     */
    public static ArrayList<String> getNodePhoneAttributeList(Node node) {
        ArrayList<String> attributeList = new ArrayList<>();

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            NodeList xmlChilderenNodes = element.getChildNodes();
            for (int i = 0; i < xmlChilderenNodes.getLength(); i++) {

                Node phoneNode = xmlChilderenNodes.item(i);
                if (phoneNode.getNodeType() == Node.ELEMENT_NODE) {

                    NamedNodeMap attributesNodeMap = phoneNode.getAttributes();
                    StringBuilder attributeSB = new StringBuilder("");
                    if (attributesNodeMap != null && attributesNodeMap.getLength() > 0) {

                        for (int j = 0; j < attributesNodeMap.getLength(); j++) {
                            attributeSB.append(" ").append(attributesNodeMap.item(j).getNodeName()).append("=\"").append(attributesNodeMap.item(j).getNodeValue()).append("\"");
                        }

                    }

                    String elementInfo = (attributeSB.toString());

                    attributeList.add(elementInfo);

                    //System.out.println("element attribute: " + elementInfo);
                }

            }
        }
        return attributeList;
    }
    
    /**
     * method to print all XML elements and attributes
     * @param xmlPath the full path to the XML
     * @param mainElement the main element of the XML (PHONE)
     */
    public static void printElementsAndAttributes(String xmlPath, String mainElement) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(xmlPath);
            NodeList base = doc.getElementsByTagName(mainElement);

            for (int j = 0; j < base.getLength(); j++) {
                Node basenode = base.item(j);
                System.out.println(basenode.getNodeName() + getAttributesAsString(basenode.getAttributes()));
                NodeList children = basenode.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node item = children.item(i);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println(item.getNodeName() + getAttributesAsString(item.getAttributes()));

                    }
                }
            }
        } catch (SAXException ex) {
            Logger.getLogger(ReadXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ReadXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String getAttributesAsString(NamedNodeMap attributes) {
        StringBuilder sb = new StringBuilder("\n");
        for (int j = 0; j < attributes.getLength(); j++) {
            sb.append("\t- ").append(attributes.item(j).getNodeName()).append(": ").append(attributes.item(j).getNodeValue()).append("\n");
        }
        return sb.toString();

    }
}
