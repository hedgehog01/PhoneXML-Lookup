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
package lib.xmlvendormodel;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hedgehog01
 */
public class VendorModelCreator
{
    private static ObservableList<VendorModelProperty> xmlPhonePropertyNameData = FXCollections.observableArrayList();
    private static final Logger LOG = Logger.getLogger(VendorModelCreator.class.getName());
    
        /**
     * Method to return list of VendorModelProperty objects
     *
     * @param files the file names
     * @param models the model names
     * @return an observable list of VendorModelProperty type
     */
    public static ObservableList<VendorModelProperty> createFilePropertyList(ArrayList<String> files,ArrayList<String> models)
    {
        if (files.size()==models.size())
        {
            LOG.log(Level.INFO, "Creating vendor model list...");
            for (int i=0;i<files.size();i++)
            {
                xmlPhonePropertyNameData.add(new VendorModelProperty(files.get(i),models.get(i)));
            }
        }
        else
        {
            LOG.log(Level.WARNING, "File list and model list not equal as should be!");
        }
        return xmlPhonePropertyNameData;       
    }
    
    /**
     * method to add VendorModelProperty item to an existing list
     * @param vendorModelList the list to add the item to
     * @param fileName the file name
     * @param modelName the model name
     */
    public static void addVendorModelPropertyItem (ObservableList<VendorModelProperty> vendorModelList,String fileName,String modelName)
    {
        vendorModelList.add(new VendorModelProperty(fileName,modelName));
        LOG.log(Level.INFO, "Adding VendorModel info. Vendor: {0}, Model {1}", new Object[] {fileName,modelName});
    }
    
}
