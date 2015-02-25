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
package lib.xmlphonename;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nathanr
 */
public class PhoneNameHandlerTest {
    
    public PhoneNameHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getPhoneNames method, of class PhoneNameHandler.
     */
    @Test
    public void testGetPhoneNames() {
        System.out.println("getPhoneNames");
        String xmlPath = "SamsungGSM.xml";
        ArrayList<String> testList = PhoneNameHandler.getPhoneNames(xmlPath);
        
        String result = testList.get(0);
        String expResult = "Default";
        
        System.out.println ("test file first phone: " + result);
        
        assertEquals(expResult, result);

    }
    
}
