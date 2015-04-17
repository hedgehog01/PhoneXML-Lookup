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
package phonexmllookup.about;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author nathanr
 */
public class PhoneXMLLookupAboutWindowController implements Initializable {
    
    private final String BUILD_VERSION = "0.0.3";
    private final String ABOUT_MESSAGE = "This software if in beta testing.\nIf you have any suggestion or issues, \nplease contact Nathan Randelman\nnathan.randelman@cellebrite.com";

    @FXML
    private Label titleLabel;
    @FXML
    private TextArea aboutTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        titleLabel.setText("PhoneXML Lookup - " + BUILD_VERSION);
        titleLabel.setAlignment(Pos.CENTER);
        aboutTextArea.setEditable(false);
        
        
        Hyperlink link = new Hyperlink("nathan.randelman@cellebrite.com");
       
        aboutTextArea.setText(ABOUT_MESSAGE );
        
        
    }
    

}
