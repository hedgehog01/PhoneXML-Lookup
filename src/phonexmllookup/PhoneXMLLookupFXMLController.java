/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonexmllookup;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lib.logUtil.MyLogger;
import lib.prefrences.PrefrencesHandler;
import lib.xmlphonefile.FileHandlerClass;
import lib.xmlphonefile.FileProperty;
import lib.xmlphonefile.FileProperyCreator;
import lib.xml.utils.PhoneDataHandler;
import lib.xml.utils.ReadXML;
import lib.xmlphonefeatures.PhoneFeatureCreator;
import lib.xmlphonefeatures.PhoneFeatureProperty;
import lib.xmlphonename.PhoneNameCreator;
import lib.xmlphonename.PhoneNameHandler;
import lib.xmlphonename.PhoneNameProperty;
import lib.xmlvendormodel.VendorModelCreator;
import lib.xmlvendormodel.VendorModelProperty;
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
    private final String INFO_LABEL_INTRO = "Value copied to clipboard:\n";
    private final String TAB_NAME_PHONE_LIST = "Phone List";
    private final String TAB_NAME_SEARCH_BY_TAG_VALUE = "Search by tag value";
    private final String TAB_NAME_SEARCH_BY_VENDOR = "Search by Vendor";
    private final String TAB_NAME_PHONE_PLAIN_TEXT = "Phone plain text";
    private final String ERROR_NO_XML_TITLE = "No XML's found";
    private final String ERROR_NO_XML_BODY = "No XML files found in the selected folder...\nSelect a folder with XML's.";
    private final String ERROR_FILE_NOT_FOUND_TITLE = "Error! File not found!";
    private final String ERROR_FILE_NOT_FOUND_BODY = "File was not found, please verify it exists in the folder.";
    private final String IMAGES_FOLDER = "\\Images";
    //private static final Logger MyLogger = Logger.getLogger(PhoneXMLLookupFXMLController.class.getName());
    private final Level LOG_LEVEL = Level.INFO;
    private final String APPLICATION_VERSION = "0.0.6";
    private final String[] SUPPORTED_IMAGE_EXTENTIONS =
    {
        "jpg", "png"
    };

    private StringBuilder allNodeElements;
    private Node defaultSectionNode;
    private Node androidDefaultOSSection;
    private Node iOSDefaultOSSection;
    private Node currentPhoneNode;
    private Node Phone1Node;
    private Node Phone2Node;
    private boolean defaultSectionCheckBoxselected;
    private boolean defaultOSSectionCheckBoxselected;
    private ArrayList<String> fileList;
    private Task searchByTagValueWorker;
    private String deviceImageFilePath;
    private Image deviceImage;
    private boolean matchWholeWordSelected;
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

    //vendorModel table data
    private ObservableList<VendorModelProperty> vendorModelPropertyData = FXCollections.observableArrayList();

    //phone1 table data
    private ObservableList<PhoneFeatureProperty> phone1FeatureData = FXCollections.observableArrayList();

    //phone2 table data
    private ObservableList<PhoneFeatureProperty> phone2FeatureData = FXCollections.observableArrayList();

    @FXML
    private ImageView deviceImageView;

    @FXML
    private Tab phoneListTab;

    @FXML
    private Tab searchByTagValueTab;

    @FXML
    private CheckBox matchWholeWordCheckBox;

    @FXML
    private Tab searchByVendorTab;

    @FXML
    private Tab phonePlainTextTab;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField filteredFileNameTextField;

    @FXML
    private TextField filteredModelTextField;

    @FXML
    private TextField filteredTagNameTextField;

    @FXML
    private TextField filteredTagValueTextField;

    @FXML
    private TableView<FileProperty> fileListTableView;

    @FXML
    private TableView<PhoneFeatureProperty> phoneFeatureTableView;

    @FXML
    private TableView<PhoneFeatureProperty> phone1FeaturesTableView;

    @FXML
    private TableView<PhoneFeatureProperty> phone2FeaturesTableView;

    @FXML
    private TableView<PhoneNameProperty> phoneNameTableView;

    @FXML
    private TableView<VendorModelProperty> phone1VendorModelTableView;

    @FXML
    private TableView<VendorModelProperty> phone2VendorModelTableView;

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

    //phone 1 vendor column
    @FXML
    private TableColumn<VendorModelProperty, String> phone1VendorColumn;

    //phone 2 vendor column
    @FXML
    private TableColumn<VendorModelProperty, String> phone2VendorColumn;

    //phone 1 Model column
    @FXML
    private TableColumn<VendorModelProperty, String> phone1ModelColumn;

    //phone 2 Model column
    @FXML
    private TableColumn<VendorModelProperty, String> phone2ModelColumn;

    //phone1 feature columns
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phone1TagNameColumn;
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phone1TagValueColumn;
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phone1TagAttributeColumn;

    //phone1 feature columns
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phone2TagNameColumn;
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phone2TagValueColumn;
    @FXML
    private TableColumn<PhoneFeatureProperty, String> phone2TagAttributeColumn;

    @FXML
    private Label selectFolderLabel;

    @FXML
    private Button selectFolderBtn;

    @FXML
    private Button startSearch;

    @FXML
    private Button cancelSearch;

    @FXML
    private TextField folderPathTextField;

    @FXML
    private TextArea phoneNodeTextArea;

    @FXML
    private CheckBox defaultSectionCheckBox;

    @FXML
    private CheckBox defaultOSSectionCheckBox;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField searchByTagTextField;

    @FXML
    private ProgressBar searchByValueProgressBar;

    @FXML
    private Label searchByValueResultLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //set App version
        if (PrefrencesHandler.getAppVersion() == null || !(PrefrencesHandler.getAppVersion().equals(APPLICATION_VERSION)))
        {
            PrefrencesHandler.setAppVersion(APPLICATION_VERSION);
        } else
        {
            MyLogger.log(Level.INFO, "App version: {0}", PrefrencesHandler.getAppVersion());
        }

        deviceImageFilePath = "";
        currentPhoneNode = null;

        //set phonelist tab focus
        setPhoneListFocus();

        //set Tab titles
        setTabTitles();

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

        //set up Vendor model table columns (phone 1)
        phone1VendorColumn.setCellValueFactory(cellData -> cellData.getValue().xmlNameProperty());
        phone1ModelColumn.setCellValueFactory(cellData -> cellData.getValue().modelNameProperty());

        //set up Vendor model table columns (phone 2)
        phone2VendorColumn.setCellValueFactory(cellData -> cellData.getValue().xmlNameProperty());
        phone2ModelColumn.setCellValueFactory(cellData -> cellData.getValue().modelNameProperty());

        //setup phone 1 features table columns
        phone1TagNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());
        phone1TagValueColumn.setCellValueFactory(cellData -> cellData.getValue().elementValueProperty());
        phone1TagAttributeColumn.setCellValueFactory(cellData -> cellData.getValue().elementAttributeProperty());
        //setup phone 2 features table columns
        phone2TagNameColumn.setCellValueFactory(cellData -> cellData.getValue().elementNameProperty());
        phone2TagValueColumn.setCellValueFactory(cellData -> cellData.getValue().elementValueProperty());
        phone2TagAttributeColumn.setCellValueFactory(cellData -> cellData.getValue().elementAttributeProperty());

        phoneFeatureTableView.getSelectionModel().setCellSelectionEnabled(true);
        //String t= phoneFeatureTableView.getFocusModel().getFocusedCell();

        //add listener to tabpane
        mainTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
        {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1)
            {
                //System.out.println("Tab Selection changed to: " + t1.getText());
                MyLogger.log(Level.INFO, "Tab Selection changed to: {0}", t1.getText());
                switch (t1.getText())
                {
                    case "Search by tag value":
                        setSearchByValueFocus();
                    case "Phone List":
                        setPhoneListFocus();
                }
            }
        }
        );

        //set listener for copy of selected cell to clipboard
        phoneFeatureTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue)
            {
                //Check whether item is selected and set value of selected item to Label
                if (phoneFeatureTableView.getSelectionModel().getSelectedItem() != null)
                {
                    TableViewSelectionModel selectionModel = phoneFeatureTableView.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val = tablePosition.getTableColumn().getCellData(newValue);
                    MyLogger.log(Level.INFO, "Cell select and copied to clipboard: {0}", val);
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(val.toString());
                    clipboard.setContent(content);
                    infoLabel.setText(INFO_LABEL_INTRO + val);
                }
            }
        });
        //XMl file list listener
        fileListTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue)
            {
                //reset infoLabel on file change
                infoLabel.setText("");

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

                //get XML path
                MyLogger.log(Level.INFO, "File selected: {0}", selectedFile.getFileName());
                String xmlPath = (folderPathTextField.getText() + "\\" + selectedFile.getFileName());
                MyLogger.log(Level.INFO, "Full XML path: {0}", xmlPath);
                selectedXMLFilePath = xmlPath;

                //get list of phones in the specific XML
                ArrayList<String> phoneList = PhoneNameHandler.getPhoneNames(xmlPath);

                setPhoneNamePropertyData(phoneList);
                //Default old OS removal     
                iOSDefaultOSSection = null;
                androidDefaultOSSection = null;
            }
        });

        //XML file list double click listener
        fileListTableView.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
                {

                    MyLogger.log(Level.INFO, "File double clicked and should open: {0}", fileListTableView.getSelectionModel().getSelectedItem().getFileName());
                    try
                    {
                        String filePath = folderPathTextField.getText() + "\\" + fileListTableView.getSelectionModel().getSelectedItem().getFileName();
                        //verify file exists
                        File file = new File(filePath);
                        if (file.exists())
                        {
                            MyLogger.log(Level.INFO, "Attemp to open file: {0}", filePath);
                            Desktop.getDesktop().open(new File(filePath));
                        } else
                        {
                            MyLogger.log(Level.SEVERE, "Attemp to open file {0} failed!", filePath);
                            showErrorMessage(ERROR_FILE_NOT_FOUND_TITLE, ERROR_FILE_NOT_FOUND_BODY);
                        }
                    } catch (IOException ex)
                    {
                        MyLogger.log(Level.SEVERE, "Exception when trying to open the file", ex);
                    }
                }
            }
        });

        // add listner to tableview selected item property of phone list
        phoneNameTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            // this method will be called whenever user selected row

            @Override
            public void changed(ObservableValue observale, Object oldValue, Object newValue)
            {
                //reset infoLabel on file change
                infoLabel.setText("");

                PhoneNameProperty selectedFile;
                if (newValue != null)
                {
                    MyLogger.log(Level.INFO, "new phone selected is: {0}", newValue);
                    selectedFile = (PhoneNameProperty) newValue;
                } else
                {
                    MyLogger.log(Level.INFO, "new phone is null");
                    selectedFile = null;
                }

                /*
                 ClipboardContent content = new ClipboardContent();
                 // make sure you override toString in UserClass
                 content.putString(selectedFile.getPhoneName());
                 clipboard.setContent(content);
                 */
                //get XML path
                MyLogger.log(Level.INFO, "selectedFile: {0}", newValue);
                String selectedPhone = "";
                if (selectedFile != null)
                {
                    selectedPhone = selectedFile.getPhoneName();
                }

                MyLogger.log(Level.INFO, "File selected in file list table: {0}", selectedXMLFilePath);
                MyLogger.log(Level.INFO, "Phone selected: {0}", selectedPhone);

                if ((allNodeElements != null) && (allNodeElements.length() > 0))
                {
                    allNodeElements.setLength(0);
                }

                //Add phone info to text area
                if (selectedXMLFilePath.contains(".xml"))
                {
                    currentPhoneNode = ReadXML.getNodeByTagValue(selectedXMLFilePath, MAIN_NODE_ELEMENT, selectedPhone);
                }
                if (currentPhoneNode != null)
                {

                    //clear phone data
                    if (oldValue != newValue)
                    {
                        MyLogger.log(Level.INFO, "clearing phone feature data table due to new phone selected");
                        if (oldValue != null)
                        {
                            MyLogger.log(Level.INFO, "Old value is: {0}", oldValue.toString());
                        }
                        if (newValue != null)
                        {
                            MyLogger.log(Level.INFO, "new value is: {0}", newValue.toString());
                        }

                    }

                    phoneFeaturePropertyData.clear();

                    allNodeElements = ReadXML.getAllNodeListElements(currentPhoneNode);
                    //Return specific phone section as String ArrayList's
                    ArrayList<String> phoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(currentPhoneNode);
                    ArrayList<String> phoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(currentPhoneNode);
                    ArrayList<String> phoneAttributeList = ReadXML.getNodePhoneAttributeList(currentPhoneNode);
                    MyLogger.log(Level.INFO, "Adding phone info to phone feature table");
                    setMainPhoneFeatureData(phoneTagNameArrayList, phoneTagValueArrayList, phoneAttributeList, NOT_DEFAULT, false, TAG_SOURCE_PHONE);

                    //set default OS & default on by default
                    //set new default OS
                    defaultOSSectionCheckBox.setSelected(true);
                    defaultOSSectionCheckBoxselected = defaultOSSectionCheckBox.isSelected();
                    MyLogger.log(Level.INFO, "defaultOSSectionCheckBox turned on automaticlly: {0}", defaultOSSectionCheckBoxselected);
                    setDefaultOSSection();

                    //Default section
                    defaultSectionCheckBox.setSelected(true);
                    defaultSectionCheckBoxselected = defaultSectionCheckBox.isSelected();
                    MyLogger.log(Level.INFO, "defaultSectionCheckBox turned on automaticlly: {0}", defaultSectionCheckBoxselected);
                    setDefaultSection();

                    //set phone image
                    deviceImageView.setImage(null);
                    setDeviceImage();
                }

                phoneNodeTextArea.setText("");
                phoneNodeTextArea.setText(allNodeElements.toString());

                //set default sort to tag origin column
                phoneFeatureTagOriginColumn.setSortType(TableColumn.SortType.ASCENDING);
                phoneFeatureTableView.getSortOrder().add(phoneFeatureTagOriginColumn);

            }
        });

        //Search by value - phone 1 listener
        phone1VendorModelTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {

                String xmlPath = "";
                String selectedPhone = "";
                VendorModelProperty selectedVendorModel;

                if (newValue != null)
                {
                    selectedVendorModel = (VendorModelProperty) newValue;
                    xmlPath = (folderPathTextField.getText() + "\\" + selectedVendorModel.getXmlName());
                    selectedPhone = selectedVendorModel.getModelName();
                    MyLogger.log(Level.INFO, "phone1 table xml selected {0}, model selected: {1}", new Object[]
                    {
                        xmlPath, selectedPhone
                    });
                } else
                {
                    MyLogger.log(Level.INFO, "phone1 table xml selected is null");
                }
                //get phone1 node
                if (xmlPath.contains(".xml"))
                {
                    MyLogger.log(Level.INFO, "getting phone1 node");
                    Phone1Node = ReadXML.getNodeByTagValue(xmlPath, MAIN_NODE_ELEMENT, selectedPhone);
                }

                //clear phone1 data
                phone1FeatureData.clear();

                ArrayList<String> phoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(Phone1Node);
                ArrayList<String> phoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(Phone1Node);
                ArrayList<String> phoneAttributeList = ReadXML.getNodePhoneAttributeList(Phone1Node);

                setSearchByValuePhoneFeatureData(phoneTagNameArrayList, phoneTagValueArrayList, phoneAttributeList, NOT_DEFAULT, false, TAG_SOURCE_PHONE, 1);
            }

        });

        //Search by value - phone 2 listener
        phone2VendorModelTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {

                String xmlPath = "";
                String selectedPhone = "";
                VendorModelProperty selectedVendorModel;

                if (newValue != null)
                {
                    selectedVendorModel = (VendorModelProperty) newValue;
                    xmlPath = (folderPathTextField.getText() + "\\" + selectedVendorModel.getXmlName());
                    selectedPhone = selectedVendorModel.getModelName();
                    MyLogger.log(Level.INFO, "phone2 table xml selected {0}, model selected: {1}", new Object[]
                    {
                        xmlPath, selectedPhone
                    });
                } else
                {
                    MyLogger.log(Level.INFO, "phone2 table xml selected is null");
                }
                //get phone1 node
                if (xmlPath.contains(".xml"))
                {
                    MyLogger.log(Level.INFO, "getting phone2 node");
                    Phone2Node = ReadXML.getNodeByTagValue(xmlPath, MAIN_NODE_ELEMENT, selectedPhone);
                }

                //clear phone1 data
                phone2FeatureData.clear();

                ArrayList<String> phoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(Phone2Node);
                ArrayList<String> phoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(Phone2Node);
                ArrayList<String> phoneAttributeList = ReadXML.getNodePhoneAttributeList(Phone2Node);

                setSearchByValuePhoneFeatureData(phoneTagNameArrayList, phoneTagValueArrayList, phoneAttributeList, NOT_DEFAULT, false, TAG_SOURCE_PHONE, 2);
            }

        });

        //setup checkboxes
        defaultSectionCheckBox.setOnAction((event) ->
        {
            //make sure xml selected to avoid null
            if (!selectedXMLFilePath.equals(""))
            {
                defaultSectionCheckBoxselected = defaultSectionCheckBox.isSelected();
                MyLogger.log(Level.INFO, "defaultSectionCheckBox selected: {0}", defaultSectionCheckBoxselected);
                setDefaultSection();
            } else
            {
                MyLogger.log(Level.INFO, "No XML selected...");
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
                MyLogger.log(Level.INFO, "defaultOSSectionCheckBox selected: {0}", defaultOSSectionCheckBoxselected);
                //get phone OS type
                String phoneOSType = ReadXML.getNodePhoneTagValue(currentPhoneNode, OSTYPETAGNAME);
                MyLogger.log(Level.INFO, "phone OSType is: {0}", phoneOSType);
                setDefaultOSSection();
            } else
            {
                MyLogger.log(Level.INFO, "No XML selected...");
                defaultOSSectionCheckBox.setSelected(false);
                showErrorMessage(NO_XML_SELECTED_TITLE, NO_XML_SELECTED_BODY);

            }

        });

        matchWholeWordCheckBox.setOnAction((event) ->
        {
            matchWholeWordSelected = matchWholeWordCheckBox.isSelected();
            MyLogger.log(LOG_LEVEL, "Search by value match whole word checkbox selected {0}", matchWholeWordSelected);
        });

        //load files from folder path saved in prefrences
        if (PrefrencesHandler.getFolderPath() != null)
        {

            String savedFolderPath = PrefrencesHandler.getFolderPath().getPath();
            //MyLogger.log(Level.INFO, MyLogger.class.getName());
            MyLogger.log(Level.INFO, "Laoding files useing default folder: {0}", savedFolderPath);
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
        MyLogger.log(Level.INFO, "Saved file path retrieved");
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
        MyLogger.log(Level.INFO, "attempting to save folder path to prefrences");
        PrefrencesHandler.setFolderPath(folderPath);
        String filePathStr = "";
        if (folderPath.getPath() != null)
        {
            filePathStr = folderPath.getPath();
        } else
        {
            MyLogger.log(Level.WARNING, "Folder path appears to be null");
        }

        MyLogger.log(Level.INFO, "Folder path selected is: {0}", filePathStr);

        folderPathTextField.setText(filePathStr);

        MyLogger.log(Level.INFO, "attempt getting list of files in the folder");
        getFileList(filePathStr);

    }

    /*
     * method to load the files in the folder
     */
    private void getFileList(String folderPath)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                fileList = FileHandlerClass.getFileList(folderPath);
                if (!fileList.isEmpty())
                {
                    selectFolderLabel.setText("");
                    setFilePropertyData(fileList);
                } else
                {
                    removeAllTableData();
                    showInfoMessage(ERROR_NO_XML_TITLE, ERROR_NO_XML_BODY);
                    selectFolderLabel.setText("No XML files found...");
                }
            }
        });

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
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
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
        });

    }

    /*
     * method to set the phone feature data into the search by value area
     */
    private void setSearchByValuePhoneFeatureData(ArrayList<String> phoneTagNameList, ArrayList<String> phoneTagValueList, ArrayList<String> phoneAttributeList, String defaultType, boolean defaultSection, String tagSource, int phoneToUpdate)
    {
        //create list from the inputed strings
        ObservableList<PhoneFeatureProperty> phoneFeatureTempData;
        phoneFeatureTempData = PhoneFeatureCreator.createPhoneFeatureList(phoneTagNameList, phoneTagValueList, phoneAttributeList, defaultType, defaultSection, tagSource);
        // if the list is default section, remove duplicated tags 
        //take care of Default section - remove tags already in phone and Default OS
        /*
         if (defaultSection && (defaultType.equals(DEFAULT_SECTION)) && (phoneFeaturePropertyData.size() > 0))
         {
         phoneFeatureTempData = PhoneDataHandler.removeDupProperties(phoneFeatureTempData, phoneFeaturePropertyData);
         }

         /// take care of OS default section
         if (defaultSection && (defaultType.contains(OS_DEFAULT)) && phoneFeaturePropertyData.size() > 0)
         {
         phoneFeatureTempData = PhoneDataHandler.removeDupDefaultProperties1(phoneFeatureTempData, phoneFeaturePropertyData);
         }
         */
        //get phone feature property
        if (phoneToUpdate == 1)
        {
            MyLogger.log(Level.INFO, "get search by tag phone 1 feature data as property");
            MyLogger.log(Level.INFO, "phone1 data size: {0}", phoneFeatureTempData.size());
            phone1FeatureData.addAll(phoneFeatureTempData);
            phone1FeaturesTableView.setItems(phone1FeatureData);
        } else if (phoneToUpdate == 2)
        {
            MyLogger.log(Level.INFO, "get search by tag phone 2 feature data as property");
            phone2FeatureData.addAll(phoneFeatureTempData);
            phone2FeaturesTableView.setItems(phone2FeatureData);
        } else
        {
            MyLogger.log(Level.WARNING, "Phone other then 1 or 2 in search by tag table. phoneToUpdate value: ", phoneToUpdate);
        }

    }

    /*
     * method to set the phone feature data into the phone feature table
     */
    private void setMainPhoneFeatureData(ArrayList<String> phoneTagNameList, ArrayList<String> phoneTagValueList, ArrayList<String> phoneAttributeList, String defaultType, boolean defaultSection, String tagSource)
    {
        //create list from the inputed strings
        ObservableList<PhoneFeatureProperty> phoneFeatureTempData;
        phoneFeatureTempData = PhoneFeatureCreator.createPhoneFeatureList(phoneTagNameList, phoneTagValueList, phoneAttributeList, defaultType, defaultSection, tagSource);
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
        MyLogger.log(Level.FINE, "get phone feature data as property");
        phoneFeaturePropertyData.addAll(phoneFeatureTempData);

        //set sorted tag name
        phoneFeatureTableView.setItems(getSortedPhoneFeatureDataList());
    }

    private SortedList<PhoneFeatureProperty> getSortedPhoneFeatureDataList()
    {
        //====set up filtering of Phone Feature tag name data===
        // Phone Feature tag name filter - Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<PhoneFeatureProperty> phoneTagNameFilteredList = new FilteredList<>(phoneFeaturePropertyData, p -> true);

        // Phone Feature tag value filter - Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<PhoneFeatureProperty> phoneTagValueFilteredList = new FilteredList<>(phoneFeaturePropertyData, p -> true);

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

        // Phone Feature filter - Set the filter Predicate whenever the filter changes.
        filteredTagValueTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            phoneTagNameFilteredList.setPredicate(phoneValueName ->
            {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty())
                {
                    return true;
                }

                // Compare filename with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                return phoneValueName.getElementValue().toLowerCase().indexOf(lowerCaseFilter) != -1;
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
        MyLogger.log(Level.FINE, "remove phone feature data");
        ObservableList<PhoneFeatureProperty> phonesDataToRemove = FXCollections.observableArrayList();
        phonesDataToRemove.addAll(PhoneFeatureCreator.createPhoneFeatureList(phoneTagNameList, phoneTagValueList, phoneAttributeList, defaultType, defaultSection, tagSource));
        ObservableList<PhoneFeatureProperty> tempPhoneFeaturePropertyData = FXCollections.observableArrayList();
        tempPhoneFeaturePropertyData.addAll(phoneFeaturePropertyData);
        MyLogger.log(Level.INFO, "phonesDataToRemove size: {0}", phonesDataToRemove.size());

        //remove tags from default that already exists in phone
        for (Iterator<PhoneFeatureProperty> itPhone = tempPhoneFeaturePropertyData.iterator(); itPhone.hasNext();)
        {
            String phoneTagName = itPhone.next().getElementName();

            MyLogger.log(Level.FINE, "removing Phone tag for default: {0}", defaultType);
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
     * method to set default OS if valid or remove if not
     * @param defaultOSSelected is the OS that is to be retrieved from OS defaults
     */
    private void setDefaultOSSection()
    {
        String defaultOSSelected = ReadXML.getNodePhoneTagValue(currentPhoneNode, OSTYPETAGNAME);
        MyLogger.log(Level.INFO, "phone OSType is: {0}", defaultOSSelected);
        //get default OS sections
        if (defaultOSSectionCheckBoxselected && defaultOSSelected.equals(ANDROID) && (ReadXML.getNodeByTagValue(selectedXMLFilePath, MAIN_NODE_ELEMENT, ANDROID + OS_DEFAULT) != null))
        {
            MyLogger.log(Level.INFO, "Get default OS section Node: {0} (Should be Android)", defaultOSSelected);
            androidDefaultOSSection = ReadXML.getNodeByTagValue(selectedXMLFilePath, MAIN_NODE_ELEMENT, ANDROID + OS_DEFAULT);

            //return Android OS Default phone section as String ArrayList
            ArrayList<String> androidDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(androidDefaultOSSection);

            //set the default Android OS section
            setMainPhoneFeatureData(androidDefaultOSPhoneTagNameArrayList, androidDefaultOSPhoneTagValueArrayList, androidDefaultOSPhoneAttributeList, ANDROID + OS_DEFAULT, true, TAG_SOURCE_DEFAULT_OS);
        } else if (defaultOSSectionCheckBoxselected && defaultOSSelected.equals(IOS) && (ReadXML.getNodeByTagValue(selectedXMLFilePath, MAIN_NODE_ELEMENT, IOS + OS_DEFAULT) != null))
        {
            MyLogger.log(Level.INFO, "Get default OS section Node: {0} (Should be iOS)", defaultOSSelected);
            iOSDefaultOSSection = ReadXML.getNodeByTagValue(selectedXMLFilePath, MAIN_NODE_ELEMENT, IOS + OS_DEFAULT);

            //return iOS OS Default phone section as String ArrayList
            ArrayList<String> iOSDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(iOSDefaultOSSection);

            //set the default Android OS section
            setMainPhoneFeatureData(iOSDefaultOSPhoneTagNameArrayList, iOSDefaultOSPhoneTagValueArrayList, iOSDefaultOSPhoneAttributeList, IOS + OS_DEFAULT, true, TAG_SOURCE_DEFAULT_OS);
        } else
        {
            MyLogger.log(Level.INFO, "Mo match found for OSType: {0} or checkbox unchecked", defaultOSSelected);
            defaultOSSectionCheckBox.setSelected(false);

            //return Android OS Default phone section as String ArrayList
            ArrayList<String> androidDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(androidDefaultOSSection);
            ArrayList<String> androidDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(androidDefaultOSSection);

            //return iOS OS Default phone section as String ArrayList
            ArrayList<String> iOSDefaultOSPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(iOSDefaultOSSection);
            ArrayList<String> iOSDefaultOSPhoneAttributeList = ReadXML.getNodePhoneAttributeList(iOSDefaultOSSection);

            removePhoneFeatureData(androidDefaultOSPhoneTagNameArrayList, androidDefaultOSPhoneTagValueArrayList, androidDefaultOSPhoneAttributeList, ANDROID + OS_DEFAULT, true, TAG_SOURCE_DEFAULT_OS);
            removePhoneFeatureData(iOSDefaultOSPhoneTagNameArrayList, iOSDefaultOSPhoneTagValueArrayList, iOSDefaultOSPhoneAttributeList, IOS + OS_DEFAULT, true, TAG_SOURCE_DEFAULT_OS);
        }
    }

    /*
     * method to set default if valid or remove if not
     */
    private void setDefaultSection()
    {
        if (defaultSectionCheckBoxselected)
        {
            MyLogger.log(Level.INFO, "Parsing default section Node");
            defaultSectionNode = ReadXML.getNodeByTagValue(selectedXMLFilePath, MAIN_NODE_ELEMENT, DEFAULT_SECTION);
            //Return default phone section as String ArrayList's
            ArrayList<String> defaultPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(defaultSectionNode);
            ArrayList<String> defaultPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(defaultSectionNode);
            ArrayList<String> defaultPhoneAttributeList = ReadXML.getNodePhoneAttributeList(defaultSectionNode);

            setMainPhoneFeatureData(defaultPhoneTagNameArrayList, defaultPhoneTagValueArrayList, defaultPhoneAttributeList, DEFAULT_SECTION, true, TAG_SOURCE_DEFAULT);

        } else
        {
            MyLogger.log(Level.INFO, "default section unselected - removing default section Node");
            defaultSectionNode = ReadXML.getNodeByTagValue(selectedXMLFilePath, MAIN_NODE_ELEMENT, DEFAULT_SECTION);
            ArrayList<String> defaultPhoneTagNameArrayList = ReadXML.getNodePhoneTagNameList(defaultSectionNode);
            ArrayList<String> defaultPhoneTagValueArrayList = ReadXML.getNodePhoneTagValueList(defaultSectionNode);
            ArrayList<String> defaultPhoneAttributeList = ReadXML.getNodePhoneAttributeList(defaultSectionNode);

            removePhoneFeatureData(defaultPhoneTagNameArrayList, defaultPhoneTagValueArrayList, defaultPhoneAttributeList, DEFAULT_SECTION, true, TAG_SOURCE_DEFAULT);
        }
    }

    private void setDeviceImage()
    {

        Task loadImageTask = loadDeviceImageTask();
        MyLogger.log(Level.INFO, "Starting loadDeviceImage method (Before call to thread)");
        Thread loadimageThread = new Thread(loadImageTask);
        loadimageThread.setDaemon(true);
        loadimageThread.start();

        try
        {
            MyLogger.log(Level.INFO, "Image load thread in Join try");
            loadimageThread.join();
        } catch (InterruptedException ex)
        {
            MyLogger.log(Level.SEVERE, "Exception in image load thread join: {0}", ex);
        }
    }

    private Task loadDeviceImageTask()
    {
        return new Task()
        {
            String AutoPK = ReadXML.getNodePhoneTagValue(currentPhoneNode, "Auto_PK");
            String phoneGuid = ReadXML.getNodePhoneTagValue(currentPhoneNode, "Guid");
            String familyID = ReadXML.getNodePhoneTagValue(defaultSectionNode, "FamilyID");
            String familyGuid = ReadXML.getNodePhoneTagValue(defaultSectionNode, "FGuid");

            Image localDeviceImage = null;

            @Override
            protected Image call() throws Exception
            {
                MyLogger.log(Level.INFO, "Starting get device image Task");
                while (isCancelled())
                {
                    MyLogger.log(Level.WARNING, "Search by tag value task was cancelled");
                    break;
                }
                String imageFolderPath = folderPathTextField.getText().concat(IMAGES_FOLDER);
                MyLogger.log(Level.INFO, "images folder path: {0}", imageFolderPath);

                File imageFolder = new File(imageFolderPath);
                if (imageFolder.exists() && imageFolder.isDirectory())
                {
                    File[] subFolderList = FileHandlerClass.getSubFolderList(imageFolderPath);
                    File imagePath = null;
                    for (int i = 0; i < subFolderList.length; i++)
                    {
                        if (subFolderList[i].getName().matches("^" + familyID + "[^0-9].*"))
                        {
                            imageFolderPath = imageFolderPath.concat("\\").concat(subFolderList[i].getName()).concat("\\");
                            System.out.println("test " + subFolderList[i].getName());
                            MyLogger.log(Level.INFO, "Family folder found: {0} ", subFolderList[i].getName());
                            MyLogger.log(Level.INFO, "Family folder found, new path: {0} ", imageFolderPath);
                            imagePath = FileHandlerClass.getFileByName(imageFolderPath, AutoPK, SUPPORTED_IMAGE_EXTENTIONS);
                        }
                    }
                    localDeviceImage = getDeviceImage(imagePath,familyID,phoneGuid,familyGuid);
                }
                return localDeviceImage;
            }
        };
    }

    private Image getDeviceImage(File imagePath,String familyID,String phoneGuid,String familyGuid)
    {
        Image localDeviceImage = null;
        MyLogger.log(Level.INFO, "image file path: {0}", imagePath);
        if (imagePath != null && imagePath.exists())
        {
            MyLogger.log(Level.INFO, "image found at path: {0}", imagePath.getPath());

            try
            {
                java.io.FileInputStream fis = new FileInputStream(imagePath);
                MyLogger.log(Level.INFO, "setting device image to image view");
                //deviceImageView.setImage(new Image(fis));
                deviceImageFilePath = imagePath.getPath();

                localDeviceImage = new Image(fis);
                MyLogger.log(Level.INFO, "Setting image to ImageView");
                deviceImageView.setImage(localDeviceImage);

            } catch (IOException ex)
            {
                MyLogger.log(Level.SEVERE, "IOException when trying to load image: {0}", ex);
            }

        } //check if dump family (FamilyID >10000) and if so check if logical counterpart has image
        else if (imagePath == null && Integer.parseInt(familyID) > 10000)
        {
            /*
            ArrayList<Node> resultNodes = new ArrayList<>();
            String folderPath = folderPathTextField.getText();
            MyLogger.log(Level.INFO, "In Dump XML - attempting to get logical counterpart image");
            for (String file : fileList)
            {
                resultNodes = (ReadXML.getNodeListByTagValue(folderPath + "/" + file, MAIN_NODE_ELEMENT, phoneGuid, true));
                
                
            }
            */
            
        }
        return localDeviceImage;
    }

    //Task to search for results by tag value

    private Task searchByTagValueTask()
    {
        return new Task<Integer>()
        {
            @Override
            protected Integer call() throws Exception
            {
                MyLogger.log(Level.INFO, "Starting search by tag value Task");
                String folderPath = folderPathTextField.getText();
                MyLogger.log(Level.INFO, "device folder path: {0}", folderPath);
                for (int i = 0; i < fileList.size(); i++)
                {

                    if (isCancelled())
                    {
                        MyLogger.log(Level.WARNING, "Search by tag value task was cancelled");
                        break;
                    }
                    updateProgress(i + 1, fileList.size());
                    searchInXml(folderPath, i);
                }
                MyLogger.log(Level.INFO,
                        "Done getting search by value info, number of phones found: {0}", vendorModelPropertyData.size());
                Integer searchResultInt = vendorModelPropertyData.size();

                return searchResultInt;
            }

            private void searchInXml(String folderPath, int i)
            {
                MyLogger.log(Level.INFO, "Searching file: {0}, for text: {1}", new Object[]
                {
                    folderPath + "/" + fileList.get(i), searchByTagTextField.getText()
                });
                updateMessage("Update message");
                ArrayList<Node> modelInFile = ReadXML.getNodeListByTagValue(folderPath + "/" + fileList.get(i), MAIN_NODE_ELEMENT, searchByTagTextField.getText(), matchWholeWordSelected);
                for (int j = 0; j < modelInFile.size(); j++)
                {

                    String phoneName = ReadXML.getNodePhoneTagValue(modelInFile.get(j), PHONE_NAME_TAG);
                    MyLogger.log(Level.INFO, "Adding Vendor model info for file: {0}, model: {1}", new Object[]
                    {
                        fileList.get(i), phoneName
                    });
                    VendorModelCreator.addVendorModelPropertyItem(vendorModelPropertyData, fileList.get(i), phoneName);
                }

            }
        };
    }

    /*
     * method to cancel search by value task while working
     */
    @FXML
    private void cancelSearchByTagValue()
    {
        if (searchByTagValueWorker != null && searchByTagValueWorker.isRunning())
        {
            MyLogger.log(Level.INFO, "Search by tag value canceled");
            searchByTagValueWorker.cancel();
        }

    }

    /*
     * method that searches through all XML's for phone with specific tag value and updates tables with results
     */
    @FXML
    private void searchByTagValue()
    {
        //reset progress bar
        //searchByValueProgressBar.progressProperty().unbind();
        //MyLogger.log(Level.INFO, "Unbind the seach by tag progress bar");
        //searchByValueProgressBar.setProgress(0);
        //LOG.log(Level.INFO, "Progress: {0}",searchByValueProgressBar.getProgress());
        if (searchByTagTextField.getText() != null && fileList != null && !(searchByTagTextField.getText().isEmpty()) && !(fileList.isEmpty()))
        {
            searchByTagValueWorker = searchByTagValueTask();
            //clear existing data in the tables
            vendorModelPropertyData.clear();
            phone1FeatureData.clear();
            phone2FeatureData.clear();
            searchByValueProgressBar.progressProperty().unbind();
            //searchByValueProgressBar.setProgress(0.0);

            //setup progress bar  
            searchByValueProgressBar.progressProperty().bind(searchByTagValueWorker.progressProperty());

            Thread startSearch = new Thread(searchByTagValueWorker);
            startSearch.setDaemon(true);
            startSearch.start();

            //searchByValueResultLabel.setText(searchResultInt.toString());
            phone1VendorModelTableView.setItems(vendorModelPropertyData);

            phone2VendorModelTableView.setItems(vendorModelPropertyData);

            searchByTagValueWorker.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                    new EventHandler<WorkerStateEvent>()
                    {
                        @Override
                        public void handle(WorkerStateEvent t)
                        {
                            String result = searchByTagValueWorker.getValue().toString();
                            MyLogger.log(Level.INFO, "result for task: {0}", result);
                            searchByValueResultLabel.setText(result);
                        }
                    });
        }

    }

    @FXML
    private void openDeviceImage()
    {
        File imageFile = new File(deviceImageFilePath);
        if (imageFile.exists() && imageFile.isFile())
        {
            try
            {
                Desktop.getDesktop().open(imageFile);
            } catch (IOException ex)
            {
                MyLogger.log(Level.SEVERE, "Attempt to open device image file failed:\n{0}", ex);
            } catch (NullPointerException ex)
            {
                MyLogger.log(Level.SEVERE, "Attempt to open device image file failed:\n{0}", ex);
            } catch (IllegalArgumentException ex)
            {
                MyLogger.log(Level.SEVERE, "Attempt to open device image file failed:\n{0}", ex);
            }
        } else if (!(imageFile.exists()) || !(imageFile.isFile()))
        {
            MyLogger.log(Level.WARNING, "device image file doesnt exists: {0}", imageFile.getPath());
        }
    }

    //method that sets search by value default focus when tab selected
    private void setSearchByValueFocus()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                searchByTagTextField.requestFocus();
            }
        });
    }

    //method that sets Phone List default focus when tab selected
    private void setPhoneListFocus()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                filteredFileNameTextField.requestFocus();
            }
        });
    }

    private void setTabTitles()
    {
        phoneListTab.setText(TAB_NAME_PHONE_LIST);
        searchByTagValueTab.setText(TAB_NAME_SEARCH_BY_TAG_VALUE);
        searchByVendorTab.setText(TAB_NAME_SEARCH_BY_VENDOR);
        phonePlainTextTab.setText(TAB_NAME_PHONE_PLAIN_TEXT);
    }

    /*
     * method to show error messages
     */
    private void showErrorMessage(String title, String body)
    {
        MyLogger.log(Level.INFO, "Error message initiated. Error Title: {0}", title);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /*
     * method to show information messages
     */
    private void showInfoMessage(String title, String body)
    {
        MyLogger.log(Level.INFO, "Info message initiated. Info Title: {0}", title);
        Alert alert = new Alert(AlertType.INFORMATION);
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
