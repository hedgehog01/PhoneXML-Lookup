/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonexmllookup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import phonexmllookup.about.PhoneXMLLookupAboutWindowController;

/**
 * The controller class for the PhoneXMLLookupFXML fxml file
 *
 * @author Hedgehog01
 */
public final class PhoneXMLLookupFXMLController implements Initializable
{

    private final String FOLDER_CHOOSER_TITLE = "Choose Directory";
    private final String MAIN_NODE_ELEMENT = "PHONE";
    private final String DEFAULT_SECTION = "Default";
    private final String OS_DEFAULT = "_OS_Default";
    private final String NOT_DEFAULT = "X";
    private final String ANDROID = "Android";
    private final String IOS = "iOS";
    private final String OSTYPETAGNAME = "OSType";
    private final String PHONE_NAME_TAG = "Name";
    private final String TAG_SOURCE_PHONE = "Phone";
    private final String TAG_SOURCE_DEFAULT = "Default";
    private final String TAG_SOURCE_DEFAULT_OS = "Default OS";
    private final String NO_XML_SELECTED_TITLE = "No XML selected";
    private final String NO_XML_SELECTED_BODY = "Please select an XML and try again...";
    private static final Logger LOG = Logger.getLogger(PhoneXMLLookupFXMLController.class.getName());
    private StringBuilder allNodeElements;
    private Node defaultSectionNode;
    private Node androidDefaultOSSection;
    private Node iOSDefaultOSSection;
    private Node currentPhoneNode = null;
    private boolean defaultSectionCheckBoxselected;
    private boolean defaultOSSectionCheckBoxselected;

    //about window settings
    private final boolean ABOUT_WIN_ALWAYS_ON_TOP = true;
    private final boolean ABOUT_WIN_SET_RESIZABLE = false;
    private final String ABOUT_WIN_STAGE_TITLE = "About";

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
    private TextField filteredTagNameTextField;

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
    
    //Phone tag origin column
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phoneFeatureTagOriginColumn;

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
    public void initialize(URL url, ResourceBundle rb)
    {
        //set up filename colomn
        xmlNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        //set up phone name column
        phoneNameColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNameProperty());

        //set up phone feature table columns
        phoneFeatureModuleNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());
        phoneFeatureContentColumn.setCellValueFactory(cellData -> cellData.getValue().elementAttributeProperty());
        phoneFeatureModuleValueColumn.setCellValueFactory(cellData -> cellData.getValue().elementValueProperty());
        phoneDefaultColumn.setCellValueFactory(cellData -> cellData.getValue().defaultSectionProperty());
        phoneFeatureTagOriginColumn.setCellValueFactory(cellData -> cellData.getValue().tagOriginProperty());
        

        //Clipboard clipboard = Clipboard.getSystemClipboard();
        // add listner to your tableview selected item property of file list
        fileListTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            // this method will be called whenever user selected row

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue)
            {
                //uncheck default checkboxes
                defaultOSSectionCheckBox.setSelected(false);
                defaultSectionCheckBox.setSelected(false);

                //make sure new value is not null (in case folder with no data selected)
                FileProperty selectedFile;
                if (newValue != null)
                {
                    selectedFile = (FileProperty) newValue;
                } else
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
                ArrayList<String> phoneList = PhoneNameHandler.getPhoneNames(xmlPath);
                setPhoneNamePropertyData(phoneList);
                //Default old OS removal     
                iOSDefaultOSSection = null;
                androidDefaultOSSection = null;
            }
        });

        // add listner to tableview selected item property of phone list
        phoneNameTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            // this method will be called whenever user selected row

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue)
            {

                PhoneNameProperty selectedFile;
                if (newValue != null)
                {
                    LOG.log(Level.INFO, "new phone selected is: {0}", newValue);
                    selectedFile = (PhoneNameProperty) newValue;
                } else
                {
                    LOG.log(Level.INFO, "new phone is null");
                    selectedFile = null;
                }

                /*
                 ClipboardContent content = new ClipboardContent();
                 // make sure you override toString in UserClass
                 content.putString(selectedFile.getPhoneName());
                 clipboard.setContent(content);
                 */
                //get XML path
                LOG.log(Level.INFO, "selectedFile: {0}", newValue);
                String selectedPhone = "";
                if (selectedFile != null)
                {
                    selectedPhone = selectedFile.getPhoneName();
                }

                LOG.log(Level.INFO, "File selected in file list table: {0}", selectedXMLFilePath);
                LOG.log(Level.INFO, "Phone selected: {0}", selectedPhone);

                if ((allNodeElements != null) && (allNodeElements.length() > 0))
                {
                    allNodeElements.setLength(0);
                }

                //Add phone info to text area
                if (selectedXMLFilePath.contains(".xml"))
                {
                    currentPhoneNode = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, selectedPhone);
                }
                if (currentPhoneNode != null)
                {

                    //clear phone data
                    if (oldValue != newValue)
                    {
                        LOG.log(Level.INFO, "clearing phone feature data table due to new phone selected");
                        if (oldValue != null)
                        {
                            LOG.log(Level.INFO, "Old value is: {0}", oldValue.toString());
                        }
                        if (newValue != null)
                        {
                            LOG.log(Level.INFO, "new value is: {0}", newValue.toString());
                        }

                    }

                    phoneFeaturePropertyData.clear();

                    allNodeElements = ReadXML.getAllNodeListElements(currentPhoneNode);
                    //Return specific phone section as String ArrayList's
                    ArrayList<String> phoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(currentPhoneNode);
                    ArrayList<String> phoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(currentPhoneNode);
                    ArrayList<String> phoneAttributeList = ReadXML.getNodePhoneAttributeList(currentPhoneNode);
                    LOG.log(Level.INFO, "Adding phone info to phone feature table");
                    setPhoneFeatureData(phoneTagNameArrayList, phoneTagValueArrayList, phoneAttributeList, NOT_DEFAULT, false,TAG_SOURCE_PHONE);

                    //set default OS & default on by default
                    //set new default OS
                    defaultOSSectionCheckBox.setSelected(true);
                    defaultOSSectionCheckBoxselected = defaultOSSectionCheckBox.isSelected();
                    LOG.log(Level.INFO, "defaultOSSectionCheckBox turned on automaticlly: {0}", defaultOSSectionCheckBoxselected);
                    setDefaultOSSection();

                    //Default section
                    defaultSectionCheckBox.setSelected(true);
                    defaultSectionCheckBoxselected = defaultSectionCheckBox.isSelected();
                    LOG.log(Level.INFO, "defaultSectionCheckBox turned on automaticlly: {0}", defaultSectionCheckBoxselected);
                    setDefaultSection();
                }

                phoneNodeTextArea.setText("");
                phoneNodeTextArea.setText(allNodeElements.toString());
                
                //set default sort to tag origin column
                phoneFeatureTagOriginColumn.setSortType(TableColumn.SortType.ASCENDING);
                phoneFeatureTableView.getSortOrder().add(phoneFeatureTagOriginColumn);
                
            }
        });

        //setup checkboxes
        defaultSectionCheckBox.setOnAction((event) ->
        {
            //make sure xml selected to avoid null
            if (!selectedXMLFilePath.equals(""))
            {
                defaultSectionCheckBoxselected = defaultSectionCheckBox.isSelected();
                LOG.log(Level.INFO, "defaultSectionCheckBox selected: {0}", defaultSectionCheckBoxselected);
                setDefaultSection();
            } else
            {
                LOG.log(Level.INFO, "No XML selected...");
                defaultSectionCheckBox.setSelected(false);
                showErrorMessage(NO_XML_SELECTED_TITLE, NO_XML_SELECTED_BODY);
            }

        });

        defaultOSSectionCheckBox.setOnAction((event) ->
        {
            //make sure xml selected to avoid null
            if (!selectedXMLFilePath.equals(""))
            {
                defaultOSSectionCheckBoxselected = defaultOSSectionCheckBox.isSelected();
                LOG.log(Level.INFO, "defaultOSSectionCheckBox selected: {0}", defaultOSSectionCheckBoxselected);
                //get phone OS type
                String phoneOSType = ReadXML.getNodePhoneTagValue(currentPhoneNode, OSTYPETAGNAME);
                LOG.log(Level.INFO, "phone OSType is: {0}", phoneOSType);
                setDefaultOSSection();
            } else
            {
                LOG.log(Level.INFO, "No XML selected...");
                defaultOSSectionCheckBox.setSelected(false);
                showErrorMessage(NO_XML_SELECTED_TITLE, NO_XML_SELECTED_BODY);

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
    private void selectFolderButtonAction(ActionEvent event)
    {
        Stage currentStage = (Stage) mainAnchor.getScene().getWindow();
        final DirectoryChooser dirChoose = new DirectoryChooser();
        //attempt to get saved folder location from prefrences
        File lastFolderSelected = PrefrencesHandler.getFolderPath();
        LOG.log(Level.INFO, "Saved file path retrieved");
        if (lastFolderSelected != null)
        {
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
    private void getFileList(String folderPath)
    {
        ArrayList<String> fileList = FileHandler.getFileList(folderPath);
        if (!fileList.isEmpty())
        {
            selectFolderLabel.setText("");
            setFilePropertyData(fileList);
        } else
        {
            removeAllTableData();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error - No XML");
            //alert.setHeaderText("Look, an Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Folder does not contain XML files...");

            alert.showAndWait();
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
    private void setFilePropertyData(ArrayList<String> fileList)
    {
        fileNamePropertyData.removeAll();
        fileNamePropertyData.clear();

        fileNamePropertyData = FileProperyCreator.createFilePropertyList(fileList);

        //====set up filtering of File Name===
        // File Name filter - Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<FileProperty> filteredFileNameData = new FilteredList<>(fileNamePropertyData, p -> true);

        // File Name filter - Set the filter Predicate whenever the filter changes.
        filteredFileNameTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filteredFileNameData.setPredicate(fileName ->
            {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty())
                {
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
    private void setPhoneNamePropertyData(ArrayList<String> phoneNameList)
    {
        phoneNamePropertyData.clear();
        filteredModelTextField.clear();
        phoneNamePropertyData = PhoneNameCreator.createFilePropertyList(phoneNameList);

        //====set up filtering of Phone Name===
        // Phone Name filter - Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<PhoneNameProperty> phoneNameFilteredList = new FilteredList<>(phoneNamePropertyData, p -> true);

        // Phone Name filter - Set the filter Predicate whenever the filter changes.
        filteredModelTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            phoneNameFilteredList.setPredicate(phoneName ->
            {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty())
                {
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
    private void setPhoneFeatureData(ArrayList<String> phoneTagNameList, ArrayList<String> phoneTagValueList, ArrayList<String> phoneAttributeList, String defaultType, boolean defaultSection, String tagSource)
    {
        //create list from the inputed strings
        ObservableList<PhoneFeatureProperty> phoneFeatureTempData;
        phoneFeatureTempData = PhoneFeatureCreator.createPhoneFeatureList(phoneTagNameList, phoneTagValueList, phoneAttributeList, defaultType, defaultSection,tagSource);
        // if the list is default section, remove duplicated tags 
        //take care of Default section - remove tags already in phone and Default OS
        if (defaultSection && (defaultType.equals(DEFAULT_SECTION)) && (phoneFeaturePropertyData.size() > 0))
        {
            phoneFeatureTempData = PhoneDataHandler.removeDupProperties(phoneFeatureTempData, phoneFeaturePropertyData);
        }

        /// take care of OS default section
        if (defaultSection && (defaultType.contains(OS_DEFAULT)) && phoneFeaturePropertyData.size() > 0)
        {
            phoneFeatureTempData = PhoneDataHandler.removeDupDefaultProperties1(phoneFeatureTempData, phoneFeaturePropertyData);
        }

        //get phone feature property
        LOG.log(Level.INFO, "get phone feature data as property");
        phoneFeaturePropertyData.addAll(phoneFeatureTempData);

        //set sorted tag name
        phoneFeatureTableView.setItems(getSortedTagNameList());
    }

    private SortedList<PhoneFeatureProperty> getSortedTagNameList()
    {
        //====set up filtering of Phone Feature tag name data===
        // Phone Feature tag name filter - Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<PhoneFeatureProperty> phoneTagNameFilteredList = new FilteredList<>(phoneFeaturePropertyData, p -> true);

        // Phone Feature filter - Set the filter Predicate whenever the filter changes.
        filteredTagNameTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            phoneTagNameFilteredList.setPredicate(phoneTagName ->
            {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty())
                {
                    return true;
                }

                // Compare filename with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                return phoneTagName.getElementName().toLowerCase().indexOf(lowerCaseFilter) != -1;
            });
        });

        // Wrap the FilteredList in a SortedList. 
        SortedList<PhoneFeatureProperty> sortedPhoneTagNameData = new SortedList<>(phoneTagNameFilteredList);

        // Bind the SortedList comparator to the TableView comparator.
        sortedPhoneTagNameData.comparatorProperty().bind(phoneFeatureTableView.comparatorProperty());
        
        //=======End of filtered phone name setup======
        return sortedPhoneTagNameData;
    }

    private void removePhoneFeatureData(ArrayList<String> phoneTagNameList, ArrayList<String> phoneTagValueList, ArrayList<String> phoneAttributeList, String defaultType, boolean defaultSection, String tagSource)
    {
        LOG.log(Level.INFO, "remove phone feature data");
        ObservableList<PhoneFeatureProperty> phonesDataToRemove = FXCollections.observableArrayList();
        phonesDataToRemove.addAll(PhoneFeatureCreator.createPhoneFeatureList(phoneTagNameList, phoneTagValueList, phoneAttributeList, defaultType, defaultSection,tagSource));
        ObservableList<PhoneFeatureProperty> tempPhoneFeaturePropertyData = FXCollections.observableArrayList();
        tempPhoneFeaturePropertyData.addAll(phoneFeaturePropertyData);
        LOG.log(Level.INFO, "phonesDataToRemove size: {0}", phonesDataToRemove.size());

        //remove tags from default that already exists in phone
        for (Iterator<PhoneFeatureProperty> itPhone = tempPhoneFeaturePropertyData.iterator(); itPhone.hasNext();)
        {
            String phoneTagName = itPhone.next().getElementName();

            LOG.log(Level.INFO, "removeing Phone tag for default: {0}", defaultType);
            //phoneFeaturePropertyData.removeIf(tag -> (tag.getElementName().equals(phoneTagName)));
            phoneFeaturePropertyData.removeIf(tag -> tag.getDefaultType().equals(defaultType));
            //phoneFeatureTableView.setItems(phoneFeaturePropertyData);

        }
    }

    @FXML
    private boolean loadAboutWindow()
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) phoneNameTableView.getScene().getWindow();
            //currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            String filePath = "/phonexmllookup/about/PhoneXMLLookupAboutWindow.fxml";
            URL location = PhoneXMLLookupAboutWindowController.class.getResource(filePath);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            PhoneXMLLookupAboutWindowController aboutWindowController = (PhoneXMLLookupAboutWindowController) fxmlLoader.getController();
            //PhoneXMLLookupAboutWindowController.setUserData(userUUID, collectionUUID,coinTableData);

            //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
            Stage aboutWindowStage = new Stage();
            Scene scene = new Scene(root);
            aboutWindowStage.setScene(scene);
            aboutWindowStage.setAlwaysOnTop(ABOUT_WIN_ALWAYS_ON_TOP);
            aboutWindowStage.setResizable(ABOUT_WIN_SET_RESIZABLE);
            aboutWindowStage.setTitle(ABOUT_WIN_STAGE_TITLE);
            aboutWindowStage.initOwner(currentStage); //Make sure main stage is the parent of add coin stage so minimizing and maximizing the main will minimize the child
            aboutWindowStage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            Logger.getLogger(PhoneXMLLookupAboutWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;
    }

    /*
     * method to return if the selected phone is of the same OS as the Default OS
     */
    /*
     private boolean isPhoneCorrectOS(Node phoneNode, Node osDefaultNode)
     {
     boolean isCorrectOS = false;
     if (phoneNode != null && osDefaultNode != null)
     {
     String phoneOSType = ReadXML.getNodePhoneTagValue(phoneNode, OSTYPETAGNAME);
     String defaultTagName = ReadXML.getNodePhoneTagValue(osDefaultNode, PHONE_NAME_TAG);
     LOG.log(Level.INFO, "Phone OSType tag value: {0}", phoneOSType);
     LOG.log(Level.INFO, "Default OS Name tag value: {0}", defaultTagName);
     if (defaultTagName.contains(phoneOSType))
     {
     LOG.log(Level.INFO, "default OS & phone OS type match");
     isCorrectOS = true;
     } else
     {
     LOG.log(Level.INFO, "default OS & phone OS type do NOT match");
     }

     }
     return isCorrectOS;
     }
     */

    /*
     * method to set default OS if valid or remove if not
     * @param defaultOSSelected is the OS that is to be retrieved from OS defaults
     */
    private void setDefaultOSSection()
    {
        String defaultOSSelected = ReadXML.getNodePhoneTagValue(currentPhoneNode, OSTYPETAGNAME);
        LOG.log(Level.INFO, "phone OSType is: {0}", defaultOSSelected);
        //get default OS sections
        if (defaultOSSectionCheckBoxselected && defaultOSSelected.equals(ANDROID) && (ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, ANDROID + OS_DEFAULT) != null) )
        {
            LOG.log(Level.INFO, "Get default OS section Node: {0} (Should be Android)", defaultOSSelected);
            androidDefaultOSSection = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, ANDROID + OS_DEFAULT);

            //return Android OS Default phone section as String ArrayList
            ArrayList<String> androidDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(androidDefaultOSSection);

            //set the default Android OS section
            setPhoneFeatureData(androidDefaultOSPhoneTagNameArrayList, androidDefaultOSPhoneTagValueArrayList, androidDefaultOSPhoneAttributeList, ANDROID + OS_DEFAULT, true,TAG_SOURCE_DEFAULT_OS);
        } else if (defaultOSSectionCheckBoxselected && defaultOSSelected.equals(IOS) && (ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, IOS + OS_DEFAULT)!=null))
        {
            LOG.log(Level.INFO, "Get default OS section Node: {0} (Should be iOS)", defaultOSSelected);
            iOSDefaultOSSection = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, IOS + OS_DEFAULT);

            //return iOS OS Default phone section as String ArrayList
            ArrayList<String> iOSDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(iOSDefaultOSSection);

            //set the default Android OS section
            setPhoneFeatureData(iOSDefaultOSPhoneTagNameArrayList, iOSDefaultOSPhoneTagValueArrayList, iOSDefaultOSPhoneAttributeList, IOS + OS_DEFAULT, true,TAG_SOURCE_DEFAULT_OS);
        } else
        {
            LOG.log(Level.INFO, "Mo match found for OSType: {0} or checkbox unchecked", defaultOSSelected);
            defaultOSSectionCheckBox.setSelected(false);

            //return Android OS Default phone section as String ArrayList
            ArrayList<String> androidDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(androidDefaultOSSection);

            //return iOS OS Default phone section as String ArrayList
            ArrayList<String> iOSDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(iOSDefaultOSSection);

            removePhoneFeatureData(androidDefaultOSPhoneTagNameArrayList, androidDefaultOSPhoneTagValueArrayList, androidDefaultOSPhoneAttributeList, ANDROID + OS_DEFAULT, true,TAG_SOURCE_DEFAULT_OS);
            removePhoneFeatureData(iOSDefaultOSPhoneTagNameArrayList, iOSDefaultOSPhoneTagValueArrayList, iOSDefaultOSPhoneAttributeList, IOS + OS_DEFAULT, true,TAG_SOURCE_DEFAULT_OS);
        }
    }

    /*
     * method to set default if valid or remove if not
     */
    private void setDefaultSection()
    {
        if (defaultSectionCheckBoxselected)
        {
            LOG.log(Level.INFO, "Parsing default section Node");
            defaultSectionNode = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, DEFAULT_SECTION);
            //Return default phone section as String ArrayList's
            ArrayList<String> defaultPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(defaultSectionNode);
            ArrayList<String> defaultPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(defaultSectionNode);
            ArrayList<String> defaultPhoneAttributeList = ReadXML.getNodePhoneAttributeList(defaultSectionNode);

            setPhoneFeatureData(defaultPhoneTagNameArrayList, defaultPhoneTagValueArrayList, defaultPhoneAttributeList, DEFAULT_SECTION, true,TAG_SOURCE_DEFAULT);

        } else
        {
            LOG.log(Level.INFO, "default section unselected - removing default section Node");
            defaultSectionNode = ReadXML.getAllNodeElements(selectedXMLFilePath, MAIN_NODE_ELEMENT, DEFAULT_SECTION);
            ArrayList<String> defaultPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(defaultSectionNode);
            ArrayList<String> defaultPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(defaultSectionNode);
            ArrayList<String> defaultPhoneAttributeList = ReadXML.getNodePhoneAttributeList(defaultSectionNode);

            removePhoneFeatureData(defaultPhoneTagNameArrayList, defaultPhoneTagValueArrayList, defaultPhoneAttributeList, DEFAULT_SECTION, true,TAG_SOURCE_DEFAULT);
        }
    }

    /*
     * method to show error messages
     */
    private void showErrorMessage(String title, String body)
    {
        LOG.log(Level.INFO, "Error message initiated. Error Title: {0}", title);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /*
     *method to exit the application 
     */
    @FXML
    private void doExit()
    {
        Platform.exit();
    }

}
