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
import lib.logUtil.MyLogger;

/**
 * Class to handle file operations
 *
 * @author Hedgehog01
 */
public final class FileHandlerClass
{

    private static final Level LOG_LEVEL_INFO = Level.INFO;
    private static final Level LOG_LEVEL_SEVER = Level.SEVERE;
    private static final Level LOG_LEVEL_FINE = Level.FINE;

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
        if (folder.exists() && folder.isDirectory())
        {
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
        }
        else
        {
            MyLogger.log(LOG_LEVEL_SEVER, "Folder doesn't exists or is null: {0}", folderPath);
            return null;
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

    public static File getFileByName(String folderPathToFile, String fileNameSearched, String[] ext)
    {
        FilenameFilter extFilter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                String lowercaseName = name.toLowerCase();
                for (int i = 0; i < ext.length; i++)
                {
                    if (lowercaseName.endsWith(ext[i]))
                    {
                        MyLogger.log(Level.FINE, "File with Ext {0} found. File name: {1}", new Object[]
                        {
                            ext[i], lowercaseName
                        });
                        return true;
                    } else
                    {
                        return false;
                    }
                }
                return false;
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
                    MyLogger.log(Level.INFO, "file found: {0}", file.getPath());
                    return file;
                }

            }
        }
        return null;
    }

    /**
     * method to return Logical counterpart of physical XML
     *
     * @param physicalXMLName the Physical XML name
     * @param fileList the list of files to search through
     * @return the logical counterpart if found or null if non found
     */
    public static String getLogicalCounterpart(String physicalXMLName, ArrayList<String> fileList)
    {
        MyLogger.log(Level.INFO, "In Dump XML - attempting to get logical counterpart Logical XML");
        //get xml logical name to find Physical counterpart
        String newLogicalFileName = physicalXMLName.replace("_", "").replace("Dump", "");
        MyLogger.log(LOG_LEVEL_INFO, "XML physical family before cut name: {0}", physicalXMLName);
        MyLogger.log(LOG_LEVEL_INFO, "XML physical family cut name: {0}", newLogicalFileName);
        newLogicalFileName = "__" + newLogicalFileName;
        for (String file : fileList)
        {
            if (file.equals(newLogicalFileName))
            {

                MyLogger.log(LOG_LEVEL_INFO, "Logical counterpart file found for Physical: {0}", file);
                return file;
            }
        }
        return null;
    }

    /**
     * method to test if a file exists
     *
     * @param filePath the file to check
     * @return true if file exists or false if not
     */
    public static boolean fileExists(File filePath)
    {
        if (filePath != null && filePath.exists())
        {
            MyLogger.log(LOG_LEVEL_INFO, "File exists: {0}", filePath.getPath());
            return true;
        } else
        {
            MyLogger.log(LOG_LEVEL_INFO, "File does NOT exists");
            return false;
        }
    }

    public static File getImageFolderPath(String imageFolderPath, String familyID, String autoPK, String[] SUPPORTED_IMAGE_EXTENTIONS)
    {
        File[] subFolderList = FileHandlerClass.getSubFolderList(imageFolderPath);
        File imagePath = null;
        for (int i = 0; i < subFolderList.length; i++)
        {
            if (subFolderList[i].getName().matches("^" + familyID + "[^0-9].*"))
            {
                imageFolderPath = imageFolderPath.concat("\\").concat(subFolderList[i].getName()).concat("\\");
                MyLogger.log(Level.INFO, "Family folder found: {0} ", subFolderList[i].getName());
                MyLogger.log(Level.INFO, "Family folder found, new path: {0} ", imageFolderPath);
                imagePath = FileHandlerClass.getFileByName(imageFolderPath, autoPK, SUPPORTED_IMAGE_EXTENTIONS);

            }
        }
        return imagePath;
    }
}
