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
package lib.prefrences;

import java.io.File;
import java.util.logging.Level;
import java.util.prefs.Preferences;
import lib.logUtil.MyLogger;

/**
 * Class to handle preferences
 * @author nathanr
 */
public final class PrefrencesHandler 
{


    /**
     * Returns the saved folder path preference
     *
     * @return the folder location saved in the preferences
     */
    public static File getFolderPath() 
    {
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            MyLogger.log(Level.INFO, "file path returned from prefrences: {0}",filePath);
            return new File(filePath);
        } else {
            MyLogger.log(Level.INFO, "No file path found in prefrences");
            return null;
        }
    }

    /**
     * Sets the folder path of the currently selected folder.
     *
     * @param file the folder location or null to remove the path
     */
    public static void setFolderPath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        if (file != null) {
            MyLogger.log(Level.INFO, "file path prefrences set to: {0}", file.getPath());
            prefs.put("filePath", file.getPath());

        } else {
            MyLogger.log (Level.INFO, "file path null , remving from prefrences");
            prefs.remove("filePath");
        }
    }
    
    /**
     * Sets the folder path of the currently selected folder.
     *
     * @param version the Application version
     */
    public static void setAppVersion (String version)
    {
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        if (version != null && !(version.isEmpty())) {
            MyLogger.log(Level.INFO, "version prefrences set to: {0}", version);
            prefs.put("AppVersion", version);

        } else {
            MyLogger.log (Level.INFO, "version null or empty , remving from prefrences");
            prefs.remove("AppVersion");
        }
    }
    /**
     * Returns the saved application version from preference
     *
     * @return the Application version saved in the preferences
     */
    public static String getAppVersion() 
    {
        Preferences prefs = Preferences.userNodeForPackage(PrefrencesHandler.class);
        String version = prefs.get("AppVersion", null);
        if (version != null && (!version.isEmpty())) {
            MyLogger.log(Level.INFO, "Application version returned from prefrences: {0}",version);
            return version;
        } else {
            MyLogger.log(Level.INFO, "No version found in preferences");
            return null;
        }
    }
}
