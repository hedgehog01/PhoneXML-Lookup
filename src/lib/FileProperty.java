/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hedgehog01
 */
public final class FileProperty
{
    private final StringProperty fileName = new SimpleStringProperty("");

    public FileProperty()
    {
        setFileName("");
    }

    public FileProperty(String fileName)
    {
        setFileName(fileName);
    }

    
    
    public String getFileName()
    {
        return fileName.get();
    }

    public void setFileName(String value)
    {
        fileName.set(value);
    }

    public StringProperty fileNameProperty()
    {
        return fileName;
    }

    @Override
    public String toString()
    {
        return "FileProperty{" + "fileName=" + fileName.get() + '}';
    }

    

    
    
}
