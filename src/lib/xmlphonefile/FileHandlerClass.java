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
package lib.xmlphonefile;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.logUtil.MyLogger;

/**
 * Class to handle file operations
 *
 * @author Hedgehog01
 */
public final class FileHandlerClass
{

    /**
     * method to return a list of files with .XML extention in a given folder
     *
     * @param folderPath the folder path to search in
     * @return the list of XML files in the folder
     */
    public static ArrayList<String> getFileList(String folderPath)
    {
        ArrayList<String> fileListArray = new ArrayList<>();
        File folder = new File(folderPath);
        File[] fileList = folder.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File fileList, String name)
            {
                return name.toLowerCase().endsWith(".xml");
            }
        });
        for (File file : fileList)
        {
            if (file.isFile())
            {
                //System.out.println(file.getName());
                fileListArray.add(file.getName());
            }
        }
        return fileListArray;
    }

    public static File[] getSubFolderList(String folderPath)
    {
        File file = new File(folderPath);
        if (file.isDirectory())
        {
            File[] directories = file.listFiles(File::isDirectory);
            MyLogger.log(Level.INFO, "List of sub directories found");
            return directories;
        }
        return null;
    }

    public static File getFileByName(String folderPathToFile, String fileNameSearched ,String ext)
    {
        FilenameFilter extFilter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(ext))
                {
                    MyLogger.log(Level.INFO, "File with Ext {0} found. File name: {1}", new Object[]{ext, lowercaseName});
                    return true;
                } else
                {
                    return false;
                }
            }
        };

        File folderPathFile = new File(folderPathToFile);
        if (folderPathFile.isDirectory() && folderPathFile.exists())
        {
            File[] files = folderPathFile.listFiles(extFilter);
            for (File file : files)
            {
                if (file.getName().contains(fileNameSearched))
                {
                    MyLogger.log(Level.INFO, "file found: {0}",file.getPath());
                    return file;
                } 
                
            }
       }
        return null;
    }
}
