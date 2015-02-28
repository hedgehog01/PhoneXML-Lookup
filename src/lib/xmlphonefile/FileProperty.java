/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.xmlphonefile;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class that represent a file property
 * @author Hedgehog01
 */
public final class FileProperty implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final StringProperty fileName = new SimpleStringProperty("");

    /**
     * Default constructor - set file name property to be empty
     */
    public FileProperty()
    {
        setFileName("");
    }
    
    /**
     * Constructor - sets file name
     * @param fileName the file name
     */
    public FileProperty(String fileName)
    {
        setFileName(fileName);
    }

    
    /**
     * method to return the file name
     * @return the file name
     */
    public String getFileName()
    {
        return fileName.get();
    }
/**
     * method to set the file name
     * @param value the file name to set
     */
    public void setFileName(String value)
    {
        fileName.set(value);
    }

    /**
     * method to return the file name property
     * @return the file name property
     */
    public StringProperty fileNameProperty()
    {
        return fileName;
    }

    /**
     * method to print the file name
     * @return the file name as String
     */
    @Override
    public String toString()
    {
        return "FileProperty{" + "fileName=" + fileName.get() + '}';
    }

    

    
    
}
