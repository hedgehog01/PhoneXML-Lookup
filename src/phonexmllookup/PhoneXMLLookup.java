/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonexmllookup;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main Application class
 * @author Hedgehog01
 */
public class PhoneXMLLookup extends Application
{
    
    /**
     * Launches the main stage
     * @param stage the main stage 
     * @throws java.io.IOException 
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("PhoneXMLLookupFXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Phone XML Lookup");
         // set icon
        stage.getIcons().add(new Image("resources/images/app_icon2.png"));
        stage.show();
    }
    


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
