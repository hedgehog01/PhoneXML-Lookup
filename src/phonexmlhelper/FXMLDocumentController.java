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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lib.FileHandler;
import lib.FileProperty;
import lib.FileProperyCreator;
import lib.xml.utils.ReadXML;
import lib.xmlphonefeatures.PhoneFeatureCreator;
import lib.xmlphonefeatures.PhoneFeatureProperty;
import lib.xmlphonename.PhoneNameCreator;
import lib.xmlphonename.PhoneNameHandler;
import lib.xmlphonename.PhoneNameProperty;
import org.w3c.dom.Node;

/**
 *
 * @author Hedgehog01
 */
public final class FXMLDocumentController implements Initializable {

    private final String FOLDER_CHOOSER_TITLE = "Choose Directory";
    private final String MAIN_NODE_ELEMENT = "PHONE";
    private static final Logger LOG = Logger.getLogger(FXMLDocumentController.class.getName());
    private StringBuilder allNodeElements;

    private String selectedXMLFilePath = "";
    //file name table data
    private ObservableList<FileProperty> filePropertyData = FXCollections.observableArrayList();

    //phone list table data
    private ObservableList<PhoneNameProperty> phoneNamePropertyData = FXCollections.observableArrayList();
    
    //phone feature table data
    private ObservableList<PhoneFeatureProperty> phoneFeaturePropertyData = FXCollections.observableArrayList();

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TableView<FileProperty> fileListTableView;

    @FXML
    private TableView<PhoneFeatureProperty> phoneFeatureTableView;

    @FXML
    private TableView<PhoneNameProperty> phoneNameTableView;

    //file name column data
    @FXML
    private TableColumn<FileProperty, String> xmlNameColumn;

    //
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phoneFeatureModuleNameColumn;
    
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phoneFeatureContentColumn;

    //Phone name column data
    @FXML
    private TableColumn<PhoneNameProperty, String> phoneNameColumn;

    @FXML
    private Label selectFolderLabel;

    @FXML
    private Button selectFolderBtn;

    @FXML
    private TextField folderPathTextField;

    @FXML
    private TextArea phoneNodeTextArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up filename colomn
        xmlNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        //set up phone name column
        phoneNameColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNameProperty());
        
        //set up phone feature table columns
        phoneFeatureModuleNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());
        phoneFeatureContentColumn.setCellValueFactory(cellData -> cellData.getValue().elementAttributeProperty());
        

        Clipboard clipboard = Clipboard.getSystemClipboard();
        // add listner to your tableview selected itemp roperty of file list
        fileListTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            // this method will be called whenever user selected row

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue) {
                FileProperty selectedFile = (FileProperty) newValue;
                ClipboardContent content = new ClipboardContent();
                // make sure you override toString in UserClass
                content.putString(selectedFile.toString());
                clipboard.setContent(content);

                //get XML path
                System.out.println("File selected: " + selectedFile.getFileName());
                String xmlPath = (folderPathTextField.getText() + "\\" + selectedFile.getFileName());
                System.out.println(xmlPath);
                selectedXMLFilePath = xmlPath;

                //get list of phones in the specific XML
                ArrayList<String> phoneList = PhoneNameHandler.getPhoneNames(xmlPath);
                //System.out.println ("first phone in the list: "+ phoneList.get(0));
                //System.out.println ("second phone in the list: "+ phoneList.get(1));
                setPhoneNamePropertyData(phoneList);

            }
        });

        // add listner to tableview selected item property of phone list
        phoneNameTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            // this method will be called whenever user selected row

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue) {
                PhoneNameProperty selectedFile = (PhoneNameProperty) newValue;
                ClipboardContent content = new ClipboardContent();
                // make sure you override toString in UserClass
                content.putString(selectedFile.getPhoneName());
                clipboard.setContent(content);

                //get XML path
                String selectedPhone = selectedFile.getPhoneName();
                System.out.println("Phone selected: " + selectedPhone);

                System.out.println("File selected in file list table: " + selectedXMLFilePath);
                if ((allNodeElements != null) && (allNodeElements.length() > 0)) {
                    allNodeElements.setLength(0);
                }
                
                //Add phone info to text area
                //allNodeElements = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, selectedPhone);
                Node phoneNode = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, selectedPhone);
                if (phoneNode !=null)
                {
                  allNodeElements = ReadXML.getAllNodeListElements(phoneNode);
                  ArrayList<String> phoneInfoArrayList = ReadXML.getNodePhoneInfoList(phoneNode);
                  ArrayList<String> phoneAttributeList = ReadXML.getNodePhoneAttributeList(phoneNode);
                  setPhoneFeatureData (phoneInfoArrayList,phoneAttributeList);
                }
                
                phoneNodeTextArea.setText("");
                phoneNodeTextArea.setText(allNodeElements.toString());
                
                //Add phone info to phonefeature table view
                //get phone info in array list
                

                
            }
        });
    }

    @FXML
    private void selectFolderButtonAction(ActionEvent event) {
        Stage currentStage = (Stage) mainAnchor.getScene().getWindow();
        final DirectoryChooser dirChoose = new DirectoryChooser();
        dirChoose.setTitle(FOLDER_CHOOSER_TITLE);
        //final File initialDir = new File("C:\\Users\\Hedgehog01\\Documents\\NetBeansProjects");
        //final File initialDir = new File("C:\\Users\\nathanr\\Desktop\\TFS\\Soft\\Genesis\\XML DB\\DataFiles\\Phones");
        //dirChoose.setInitialDirectory(initialDir);
        dirChoose.setTitle("Select XML Folder");

        //open Dialog
        final File folderPath = dirChoose.showDialog(currentStage);

        String filePathStr = folderPath.getAbsolutePath();

        LOG.log(Level.INFO, "Folder path selected is: {0}", filePathStr);

        folderPathTextField.setText(filePathStr);

        LOG.log(Level.INFO, "attempt getting list of files in the folder");

        ArrayList<String> fileList = FileHandler.getFileList(filePathStr);
        if (!fileList.isEmpty()) {
            selectFolderLabel.setText("");
            setFilePropertyData(fileList);
        } else {
            selectFolderLabel.setText("No XML files found...");
        }

    }

    /*
     Add the data to file list table
     */
    private void setFilePropertyData(ArrayList<String> fileList) {
        filePropertyData.removeAll();
        filePropertyData.clear();

        filePropertyData = FileProperyCreator.createFilePropertyList(fileList);
        fileListTableView.setItems(filePropertyData);

    }

    private void setPhoneNamePropertyData(ArrayList<String> phoneNameList) {
        phoneNamePropertyData.clear();

        phoneNamePropertyData = PhoneNameCreator.createFilePropertyList(phoneNameList);
        phoneNameTableView.setItems(phoneNamePropertyData);

    }
    
    private void setPhoneFeatureData (ArrayList<String> phoneinfoList,ArrayList<String> phoneAttributeList)
    {
        phoneFeaturePropertyData.clear();
        
        phoneFeaturePropertyData = PhoneFeatureCreator.createPhoneFeatureList(phoneinfoList,phoneinfoList,phoneAttributeList);
        phoneFeatureTableView.setItems(phoneFeaturePropertyData);
    }

}
