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
package lib.indexxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nathanr
 */
public final class IndexXMLProperty
{
    private final StringProperty regions = new SimpleStringProperty("");
    private final StringProperty operators = new SimpleStringProperty("");
    private final StringProperty tableName = new SimpleStringProperty("");
    private final StringProperty fileName = new SimpleStringProperty("");
    private final StringProperty sortOrder = new SimpleStringProperty("");

    /**
     * Default constructor
     */
    public IndexXMLProperty()
    {
        
    }
    
    /**
     * Constructor
     * @param region the region
     * @param operator the operator
     * @param tableName the table name
     * @param fileName the file name
     * @param sortOrder the sort order
     */
    public IndexXMLProperty (String region,String operator, String tableName,String fileName, String sortOrder)
    {
        setRegions(region);
        setOperators(operator);
        setTableName(tableName);
        setFileName(fileName);
        setSortOrder(sortOrder);
    }
    public String getSortOrder()
    {
        return sortOrder.get();
    }

    public void setSortOrder(String value)
    {
        sortOrder.set(value);
    }

    public StringProperty sortOrderProperty()
    {
        return sortOrder;
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
    

    public String getTableName()
    {
        return tableName.get();
    }

    public void setTableName(String value)
    {
        tableName.set(value);
    }

    public StringProperty tableNameProperty()
    {
        return tableName;
    }
    

    public String getOperators()
    {
        return operators.get();
    }

    public void setOperators(String value)
    {
        operators.set(value);
    }

    public StringProperty operatorsProperty()
    {
        return operators;
    }
    

    public String getRegions()
    {
        return regions.get();
    }

    public void setRegions(String value)
    {
        regions.set(value);
    }

    public StringProperty regionsProperty()
    {
        return regions;
    }
    
    /**
     * method to return the index property details
     * @return 
     */
    @Override
    public String toString ()
    {
        StringBuilder index = new StringBuilder("");
        
        index.append("Regions: ").append(regions.get()).append("\nOperators: ").append(operators.get()).append("\nTable name: ").append(tableName.get()).append("\nFile name: ").append(fileName.get()).append("\nSort order: ").append(sortOrder.get());
        
        return index.toString();
    }
    
}
