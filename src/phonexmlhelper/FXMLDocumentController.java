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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import lib.xml.utils.PhoneDataHandler;
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
    private final String DEFAULT_SECTION = "Default";
    private static final Logger LOG = Logger.getLogger(FXMLDocumentController.class.getName());
    private StringBuilder allNodeElements;
    private Node defaultSectionNode;

    private String selectedXMLFilePath = "";
    //file name table data
    private ObservableList<FileProperty> fileNamePropertyData = FXCollections.observableArrayList();

    //phone list table data
    private ObservableList<PhoneNameProperty> phoneNamePropertyData = FXCollections.observableArrayList();

    //phone feature table data
    private ObservableList<PhoneFeatureProperty> phoneFeaturePropertyData = FXCollections.observableArrayList();

  
    @FXML
    private AnchorPane mainAnchor;
    
    @FXML
    private TextField filteredFileNameTextField;
    
    @FXML
    private TextField filteredModelTextField;

    @FXML
    private TableView<FileProperty> fileListTableView;

    @FXML
    private TableView<PhoneFeatureProperty> phoneFeatureTableView;

    @FXML
    private TableView<PhoneNameProperty> phoneNameTableView;

    //file name column data
    @FXML
    private TableColumn<FileProperty, String> xmlNameColumn;

    //Module name column
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phoneFeatureModuleNameColumn;

    //Module value column
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phoneFeatureModuleValueColumn;

    @FXML
    private TableColumn<PhoneFeatureProperty, String> phoneFeatureContentColumn;

    //Phone name column data
    @FXML
    private TableColumn<PhoneNameProperty, String> phoneNameColumn;
    
    //Phone default column data
    @FXML
    private TableColumn<PhoneFeatureProperty, Boolean> phoneDefaultColumn;

    @FXML
    private Label selectFolderLabel;

    @FXML
    private Button selectFolderBtn;

    @FXML
    private TextField folderPathTextField;

    @FXML
    private TextArea phoneNodeTextArea;

    @FXML
    private CheckBox defaultSectionCheckBox;

    @FXML
    private CheckBox defaultOSSectionCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up filename colomn
        xmlNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        //set up phone name column
        phoneNameColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNameProperty());

        //set up phone feature table columns
        phoneFeatureModuleNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());
        phoneFeatureContentColumn.setCellValueFactory(cellData -> cellData.getValue().elementAttributeProperty());
        phoneFeatureModuleValueColumn.setCellValueFactory(cellData -> cellData.getValue().elementValueProperty());
        phoneDefaultColumn.setCellValueFactory(cellData -> cellData.getValue().defaultSectionProperty());
        
 
        
        Clipboard clipboard = Clipboard.getSystemClipboard();
        // add listner to your tableview selected item property of file list
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
                defaultSectionNode = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, DEFAULT_SECTION);
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
                if (phoneNode != null && defaultSectionNode != null) 
                {
                    allNodeElements = ReadXML.getAllNodeListElements(phoneNode);
                    ArrayList<String> phoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(phoneNode);
                    ArrayList<String> phoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(phoneNode);
                    ArrayList<String> phoneAttributeList = ReadXML.getNodePhoneAttributeList(phoneNode);
                    
                    ArrayList<String> defaultPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(defaultSectionNode);
                    ArrayList<String> defaultPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(defaultSectionNode);
                    ArrayList<String> defaultPhoneAttributeList = ReadXML.getNodePhoneAttributeList(defaultSectionNode);
                    
                    
                    
                    setPhoneFeatureData(phoneTagNameArrayList, phoneTagValueArrayList, phoneAttributeList,defaultPhoneTagNameArrayList,defaultPhoneTagValueArrayList,defaultPhoneAttributeList );
                }
                

                phoneNodeTextArea.setText("");
                phoneNodeTextArea.setText(allNodeElements.toString());

            }
        });

        //setup checkboxes
        defaultSectionCheckBox.setOnAction((event) -> {
            boolean selected = defaultSectionCheckBox.isSelected();
            LOG.log(Level.INFO, "defaultSectionCheckBox selected: {0}", selected);
        });

        defaultOSSectionCheckBox.setOnAction((event) -> {
            boolean selected = defaultOSSectionCheckBox.isSelected();
            LOG.log(Level.INFO, "defaultOSSectionCheckBox selected: {0}", selected);
            System.out.println("Checkbox");
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
     * method to add file list data to file list table
     */
    private void setFilePropertyData(ArrayList<String> fileList) {
        fileNamePropertyData.removeAll();
        fileNamePropertyData.clear();

        fileNamePropertyData = FileProperyCreator.createFilePropertyList(fileList);
        
        
        
        //====set up filtering of File Name===
        // File Name filter - Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<FileProperty> filteredFileNameData = new FilteredList<>(fileNamePropertyData, p -> true);
        
        // File Name filter - Set the filter Predicate whenever the filter changes.
         filteredFileNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredFileNameData.setPredicate(fileName -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare filename with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                return fileName.getFileName().toLowerCase().indexOf(lowerCaseFilter) != -1; 
            });
        });
         
         // Wrap the FilteredList in a SortedList. 
        SortedList<FileProperty> sortedFileNameData = new SortedList<>(filteredFileNameData);
        
        // Bind the SortedList comparator to the TableView comparator.
        sortedFileNameData.comparatorProperty().bind(fileListTableView.comparatorProperty());
        
        //=======End of filtered filename setup======
        
        fileListTableView.setItems(sortedFileNameData);

    }

    /*
    * method to set the phone name into the phone name table
    */
    private void setPhoneNamePropertyData(ArrayList<String> phoneNameList) {
        phoneNamePropertyData.clear();

        phoneNamePropertyData = PhoneNameCreator.createFilePropertyList(phoneNameList);
        
        
        //====set up filtering of Phone Name===
        // Phone Name filter - Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<PhoneNameProperty> phoneNameFilteredList = new FilteredList<>(phoneNamePropertyData, p -> true);
        
        // File Name filter - Set the filter Predicate whenever the filter changes.
         filteredModelTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            phoneNameFilteredList.setPredicate(phoneName -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare filename with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                return phoneName.getPhoneName().toLowerCase().indexOf(lowerCaseFilter) != -1; 
            });
        });
         
         // Wrap the FilteredList in a SortedList. 
        SortedList<PhoneNameProperty> sortedPhoneNameData = new SortedList<>(phoneNameFilteredList);
        
        // Bind the SortedList comparator to the TableView comparator.
        sortedPhoneNameData.comparatorProperty().bind(phoneNameTableView.comparatorProperty());
        
        //=======End of filtered phone name setup======
        
        phoneNameTableView.setItems(sortedPhoneNameData);

    }

    /*
    * method to set the phone feature data into the phone feature table
    */
    private void setPhoneFeatureData(ArrayList<String> phoneTagNameList, ArrayList<String> phoneTagValueList, ArrayList<String> phoneAttributeList,ArrayList<String> defaultPhoneTagNameList, ArrayList<String> defaultPhoneTagValueList, ArrayList<String> defaultPhoneAttributeList) {
        
        //System.out.println ("print of Default phone arraylists");
        //printArrayLists (defaultPhoneTagNameList,defaultPhoneTagValueList);
        //get default node property
        ObservableList<PhoneFeatureProperty> defaultPhoneFeaturePropertyData = FXCollections.observableArrayList();
        defaultPhoneFeaturePropertyData = PhoneFeatureCreator.createPhoneFeatureList(defaultPhoneTagNameList, defaultPhoneTagValueList, defaultPhoneAttributeList);
        
        //get phone node property
        phoneFeaturePropertyData.clear();
        phoneFeaturePropertyData = PhoneFeatureCreator.createPhoneFeatureList(phoneTagNameList, phoneTagValueList, phoneAttributeList,false);
        defaultPhoneFeaturePropertyData = PhoneFeatureCreator.createPhoneFeatureList (defaultPhoneTagNameList,defaultPhoneTagValueList,defaultPhoneAttributeList,true);
        //remove from default property elements already in the phone
         ObservableList<PhoneFeatureProperty> updatedDefaultPhoneFeaturePropertyData = PhoneDataHandler.removeDupProperties(defaultPhoneFeaturePropertyData,phoneFeaturePropertyData);
        
        phoneFeaturePropertyData.addAll(updatedDefaultPhoneFeaturePropertyData);
        
        
        
        
        phoneFeatureTableView.setItems(phoneFeaturePropertyData);
    }
    
    private void printArrayLists (ArrayList<String> defaultPhoneTagNameList, ArrayList<String> defaultPhoneTagValueList)
    {
        for (int i=0;i<defaultPhoneTagNameList.size();i++)
        {
            System.out.println (defaultPhoneTagNameList.get(i) + " " + defaultPhoneTagValueList.get(i));
        }
    }

}
