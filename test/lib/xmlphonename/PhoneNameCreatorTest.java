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
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nathanr
 */
public class PhoneNameCreatorTest {
    
    public PhoneNameCreatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of createFilePropertyList method, of class PhoneNameCreator.
     */
    @Test
    public void testCreateFilePropertyList() {
        System.out.println("createFilePropertyList");
        String expResult = "phone 2";
        ArrayList<String> phoneNames = new ArrayList<>();
        phoneNames.add("Phone 1");
        phoneNames.add(expResult);
        ObservableList<PhoneNameProperty> testProperty = PhoneNameCreator.createFilePropertyList(phoneNames);
        String result = testProperty.get(1).getPhoneName();
        //System.out.println("expected: " + expResult);
        //System.out.println("Actual: " + result);
        assertEquals(expResult, result);
        
    }
    
}
