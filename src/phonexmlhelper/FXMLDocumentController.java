/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonexmlhelper;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.DirectoryChooser;
import lib.FileHandler;
import lib.FileProperty;
import lib.FileProperyCreator;

/**
 *
 * @author Hedgehog01
 */
public class FXMLDocumentController implements Initializable
{

    private static final Logger LOG = Logger.getLogger(FXMLDocumentController.class.getName());

    private ObservableList<FileProperty> filePropertyData = FXCollections.observableArrayList();

    @FXML
    private Label selectFolderLabel;

    @FXML
    private Button selectFolderBtn;

    @FXML
    private TextField folderPathTextField;

    @FXML
    private TableView<FileProperty> fileListTableView;

    @FXML
    private TableColumn<FileProperty, String> xmlNameColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //set up filename colomn
        xmlNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());

        /*
         //set listener for rows of fileListTableView table view
         fileListTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
         {
         @Override
         public void changed(ObservableValue observableValue, Object oldValue, Object newValue)
         {
         //Check whether item is selected and set value of selected item to Label
         if (fileListTableView.getSelectionModel().getSelectedItem() != null)
         {
         TableViewSelectionModel selectionModel = fileListTableView.getSelectionModel();
         ObservableList selectedCells = selectionModel.getSelectedCells();
         TablePosition tablePosition = (TablePosition) selectedCells.get(0);
         Object val = tablePosition.getTableColumn().getCellData(newValue);
         System.out.println("Selected Value" + val);
         }
         }
         });
         */
        Clipboard clipboard = Clipboard.getSystemClipboard();
  // add listner to your tableview selecteditemproperty   
        fileListTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            // this method will be called whenever user selected row

            public void changed(ObservableValue observale, Object oldValue, Object newValue)
            {
                FileProperty selectedFile = (FileProperty) newValue;
                ClipboardContent content = new ClipboardContent();
                // make sure you override toString in UserClass
                content.putString(selectedFile.toString());
                clipboard.setContent(content);
                System.out.println("File selected: " + selectedFile);
            }
        });
    }

    @FXML
    private void selectFolderButtonAction(ActionEvent event)
    {
        DirectoryChooser dirChoose = new DirectoryChooser();
        dirChoose.setTitle("Select XML Folder");

        //open Dialog
        File folderPath = dirChoose.showDialog(null);
        String filePathStr = folderPath.getAbsolutePath();

        LOG.log(Level.INFO, "Folder path selected is: {0}", filePathStr);

        folderPathTextField.setText(filePathStr);

        LOG.log(Level.INFO, "attempt getting list of files in the folder");

        ArrayList<String> fileList = FileHandler.getFileList(filePathStr);
        if (!fileList.isEmpty())
        {
            selectFolderLabel.setText("");
            getFilePropertyData(fileList);
        } else
        {
            selectFolderLabel.setText("No XML files found...");
        }

    }

    private void getFilePropertyData(ArrayList<String> fileList)
    {
        filePropertyData.removeAll();
        filePropertyData.clear();

        filePropertyData = FileProperyCreator.createFilePropertyList(fileList);
        fileListTableView.setItems(filePropertyData);

    }

}
