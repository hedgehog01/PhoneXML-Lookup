/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonexmllookup;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import lib.prefrences.PrefrencesHandler;
import lib.xmlphonefile.FileHandler;
import lib.xmlphonefile.FileProperty;
import lib.xmlphonefile.FileProperyCreator;
import lib.xml.utils.PhoneDataHandler;
import lib.xml.utils.ReadXML;
import lib.xmlphonefeatures.PhoneFeatureCreator;
import lib.xmlphonefeatures.PhoneFeatureProperty;
import lib.xmlphonename.PhoneNameCreator;
import lib.xmlphonename.PhoneNameHandler;
import lib.xmlphonename.PhoneNameProperty;
import org.w3c.dom.Node;

/**
 * The controller class for the PhoneXMLLookupFXML fxml file
 * @author Hedgehog01
 */
public final class PhoneXMLLookupFXMLController implements Initializable {

    private final String FOLDER_CHOOSER_TITLE = "Choose Directory";
    private final String MAIN_NODE_ELEMENT = "PHONE";
    private final String DEFAULT_SECTION = "Default";
    private final String OS_DEFAULT = "_OS_Default";
    private final String ANDROID = "Android";
    private final String IOS = "iOS";
    private static final Logger LOG = Logger.getLogger(PhoneXMLLookupFXMLController.class.getName());
    private StringBuilder allNodeElements;
    private Node defaultSectionNode;
    private Node androidDefaultOSSection;
    private Node iOSDefaultOSSection;
    private boolean defaultSectionCheckBoxselected;
    private boolean defaultOSSectionCheckBoxselected;

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

        //Clipboard clipboard = Clipboard.getSystemClipboard();
        
        // add listner to your tableview selected item property of file list
        fileListTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            // this method will be called whenever user selected row

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue) {
                
                //make sure new value is not null (in case folder with no data selected)
                FileProperty selectedFile;
                if (newValue!=null)
                {
                    selectedFile = (FileProperty) newValue;
                }
                else 
                {
                    selectedFile = new FileProperty();
                }
                
                //copy selection to clipboard
                /*
                 ClipboardContent content = new ClipboardContent();
                 // make sure you override toString in UserClass
                 content.putString(selectedFile.toString());
                 clipboard.setContent(content);
                 */
                
                //get XML path
                
                LOG.log(Level.INFO, "File selected: {0}", selectedFile.getFileName());
                String xmlPath = (folderPathTextField.getText() + "\\" + selectedFile.getFileName());
                LOG.log(Level.INFO, "Full XML path: {0}", xmlPath);
                selectedXMLFilePath = xmlPath;

                //get list of phones in the specific XML
                if (xmlPath.contains(".xml"))
                {
                    ArrayList<String> phoneList = PhoneNameHandler.getPhoneNames(xmlPath);
                    setPhoneNamePropertyData(phoneList);
                }
                
                
 
                /*
                 //get default section node if default section checkbox selected
                 if (defaultSectionCheckBoxselected)
                 {
                 defaultSectionNode = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, DEFAULT_SECTION);
                 }
                 else
                 {
                 defaultSectionNode = null;
                 }

                 //get default OS section nodes if OS default section checkbox selected
                 if (defaultOSSectionCheckBoxselected)
                 {
                 androidDefaultOSSection = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, ANDROID + OS_DEFAULT);
                 iOSDefaultOSSection = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, IOS + OS_DEFAULT);
                 }
                 else
                 {
                 androidDefaultOSSection = null;
                 iOSDefaultOSSection = null;
                 }
                 */
            }
        });

        // add listner to tableview selected item property of phone list
        phoneNameTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            // this method will be called whenever user selected row

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue) {
                PhoneNameProperty selectedFile = (PhoneNameProperty) newValue;
                /*
                ClipboardContent content = new ClipboardContent();
                // make sure you override toString in UserClass
                content.putString(selectedFile.getPhoneName());
                clipboard.setContent(content);
                */
                //get XML path
                
                String selectedPhone = "";
                if (selectedFile.getPhoneName() != null)
                {
                    selectedPhone = selectedFile.getPhoneName();
                }
                    
                LOG.log(Level.INFO, "File selected in file list table: {0}", selectedXMLFilePath);
                LOG.log(Level.INFO, "Phone selected: {0}", selectedPhone);

                if ((allNodeElements != null) && (allNodeElements.length() > 0)) {
                    allNodeElements.setLength(0);
                }

                //Add phone info to text area
                Node phoneNode = null;
                if (selectedXMLFilePath.contains(".xml"))
                     phoneNode= ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, selectedPhone);
                if (phoneNode != null) {
                    allNodeElements = ReadXML.getAllNodeListElements(phoneNode);
                    //Return specific phone section as String ArrayList's
                    ArrayList<String> phoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(phoneNode);
                    ArrayList<String> phoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(phoneNode);
                    ArrayList<String> phoneAttributeList = ReadXML.getNodePhoneAttributeList(phoneNode);

                    //Return default phone section as String ArrayList's
                    ArrayList<String> defaultPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(defaultSectionNode);
                    ArrayList<String> defaultPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(defaultSectionNode);
                    ArrayList<String> defaultPhoneAttributeList = ReadXML.getNodePhoneAttributeList(defaultSectionNode);

                    setPhoneFeatureData(phoneTagNameArrayList, phoneTagValueArrayList, phoneAttributeList, defaultPhoneTagNameArrayList, defaultPhoneTagValueArrayList, defaultPhoneAttributeList);
                }

                phoneNodeTextArea.setText("");
                phoneNodeTextArea.setText(allNodeElements.toString());

            }
        });

        //setup checkboxes
        defaultSectionCheckBox.setOnAction((event) -> {
            defaultSectionCheckBoxselected = defaultSectionCheckBox.isSelected();
            LOG.log(Level.INFO, "defaultSectionCheckBox selected: {0}", defaultSectionCheckBoxselected);
            if (defaultSectionCheckBoxselected) {
                LOG.log(Level.INFO, "Parsing default section Node");
                defaultSectionNode = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, DEFAULT_SECTION);
            } else {
                defaultSectionNode = null;
            }

        });

        defaultOSSectionCheckBox.setOnAction((event) -> {
            defaultOSSectionCheckBoxselected = defaultOSSectionCheckBox.isSelected();
            LOG.log(Level.INFO, "defaultOSSectionCheckBox selected: {0}", defaultOSSectionCheckBoxselected);
            if (defaultOSSectionCheckBoxselected) {
                LOG.log(Level.INFO, "Parsing default OS section Nodes");
                androidDefaultOSSection = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, ANDROID + OS_DEFAULT);
                iOSDefaultOSSection = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, IOS + OS_DEFAULT);
            } else {
                androidDefaultOSSection = null;
                iOSDefaultOSSection = null;
            }

        });
        
        //load files from folder path saved in prefrences
        if (PrefrencesHandler.getFolderPath() != null)
        {
          
            String savedFolderPath = PrefrencesHandler.getFolderPath().getPath();
            LOG.log(Level.INFO, "Laoding files useing default folder: {0}", savedFolderPath);
            folderPathTextField.setText(savedFolderPath);
            getFileList(savedFolderPath);
        }
    }

    /*
    * method to get the user selected folder
    */
    @FXML
    private void selectFolderButtonAction(ActionEvent event) {
        Stage currentStage = (Stage) mainAnchor.getScene().getWindow();
        final DirectoryChooser dirChoose = new DirectoryChooser();
        //attempt to get saved folder location from prefrences
        File lastFolderSelected = PrefrencesHandler.getFolderPath();
        LOG.log(Level.INFO, "Saved file path retrieved");
        if (lastFolderSelected != null) {
            final File initialDir = new File(lastFolderSelected.getPath());
            dirChoose.setInitialDirectory(initialDir);
        }

        dirChoose.setTitle(FOLDER_CHOOSER_TITLE);

        dirChoose.setTitle("Select XML Folder");

        //open Dialog
        final File folderPath = dirChoose.showDialog(currentStage);
        //save folder path to prefrences
        LOG.log(Level.INFO, "attempting to save folder path to prefrences");
        PrefrencesHandler.setFolderPath(folderPath);

        String filePathStr = folderPath.getPath();

        LOG.log(Level.INFO, "Folder path selected is: {0}", filePathStr);

        folderPathTextField.setText(filePathStr);

        LOG.log(Level.INFO, "attempt getting list of files in the folder");
        getFileList(filePathStr);

    }

    /*
    * method to load the files in the folder
    */
    private void getFileList(String folderPath) {
        ArrayList<String> fileList = FileHandler.getFileList(folderPath);
        if (!fileList.isEmpty()) {
            selectFolderLabel.setText("");
            setFilePropertyData(fileList);
        } else {
            removeAllTableData();
            
            selectFolderLabel.setText("No XML files found...");
        }

    }
    
    /*
    * private method to remove all data loaded in UI
    */
    private void removeAllTableData()
    {
        fileNamePropertyData.clear();
        phoneNamePropertyData.clear();
        phoneFeaturePropertyData.clear();
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
    private void setPhoneFeatureData(ArrayList<String> phoneTagNameList, ArrayList<String> phoneTagValueList, ArrayList<String> phoneAttributeList, ArrayList<String> defaultPhoneTagNameList, ArrayList<String> defaultPhoneTagValueList, ArrayList<String> defaultPhoneAttributeList) {

        //get default node property
        ObservableList<PhoneFeatureProperty> defaultPhoneFeaturePropertyData = PhoneFeatureCreator.createPhoneFeatureList(defaultPhoneTagNameList, defaultPhoneTagValueList, defaultPhoneAttributeList);

        //get phone node property
        phoneFeaturePropertyData.clear();
        phoneFeaturePropertyData = PhoneFeatureCreator.createPhoneFeatureList(phoneTagNameList, phoneTagValueList, phoneAttributeList, false);
        defaultPhoneFeaturePropertyData = PhoneFeatureCreator.createPhoneFeatureList(defaultPhoneTagNameList, defaultPhoneTagValueList, defaultPhoneAttributeList, true);
        //remove from default property elements already in the phone
        ObservableList<PhoneFeatureProperty> updatedDefaultPhoneFeaturePropertyData = PhoneDataHandler.removeDupProperties(defaultPhoneFeaturePropertyData, phoneFeaturePropertyData);

        phoneFeaturePropertyData.addAll(updatedDefaultPhoneFeaturePropertyData);

        phoneFeatureTableView.setItems(phoneFeaturePropertyData);
    }

    /*
     *method to exit the application 
     */
    @FXML
    private void doExit() {
        Platform.exit();
    }

}
