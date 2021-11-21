import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Predicate;

public class InventoryController implements Initializable {

    InventoryWrapper inventory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //create instance of wrapper class
        inventory = new InventoryWrapper();

        //points to the Item class and sets the cell factory to string of type Item
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("price"));
        serialCol.setCellValueFactory(new PropertyValueFactory<Item, String>("serial"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

        //setting table to editable in order to edit items
        tableView.setEditable(true);

        //Set format for serial number

        //creates text field inside each row when item is made to edit
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serialCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        //sets table to sortable
        tableView.getSortOrder().add(priceCol);
        tableView.getSortOrder().add(nameCol);
        tableView.getSortOrder().add(serialCol);

        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Item, String> event) {
                Item item = event.getRowValue();
                item.setName(event.getNewValue());
                item.setSerial(event.getNewValue());
                item.setPrice(event.getNewValue());
            }
        });

    }
    //PARTIALLY COMPLETE
    @FXML
    void add(ActionEvent event) {

        String error = "Error, Invalid Input!";

        //check is name is blank/empty & check if length is less than or equal to 256 characters
        if (!nameField.getText().equals("") && !nameField.getText().equals(" ")) {
            Item item;
            if (nameField.getText().length() <= 256) {

                //make string pattern & check if string matches the pattern
               // Pattern pattern = Pattern.compile("[a-zA-Z]{1}-[a-zA-Z0-9]{9}");
              //  if (serialField.getText().matches("[[[a-zA-Z]{1}]-[[a-zA-Z0-9]]{9}]")){

                    //ANOTHER IF STATEMENT GOES HERE

                        // IF ITEM DOESN'T ALREADY EXIST CONTINUE
                    //ELSE DISPLAY ERROR WINDOW


                    //change string to double & check for price value
                    double doublePrice = Double.parseDouble(priceField.getText());
                    if (doublePrice >= 0) {

                        item = new Item(priceField.getText()," ",nameField.getText());
                        inventory.addItem(item);
                        tableView.setItems(inventory.getData());
                        priceField.clear();
                        serialField.clear();
                        nameField.clear();

                    } else {
                        //new window ENTER VALID PRICE
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(error);
                        alert.setHeaderText(null);
                        alert.setContentText("Input a valid price please!");
                        alert.showAndWait();
                        System.out.println("error 1");
                    }

             /*   } else {
                    //new window ENTER VALID SERIAL NUMBER
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(error);
                    alert.setHeaderText(null);
                    alert.setContentText("Input a valid serial number please!");
                    alert.showAndWait();
                    System.out.println("error 2");
                }
*/
            } else {
                //new window ENTER VALID NAME
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(error);
                alert.setHeaderText(null);
                alert.setContentText("Input a valid name length please!");
                alert.showAndWait();
                System.out.println("error 3");
            }

        } else {
            //new window ENTER VALID NAME
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(error);
            alert.setHeaderText(null);
            alert.setContentText("Input a valid name please!");
            alert.showAndWait();
            System.out.println("error 4");
        }
    }

    //CHECK
    @FXML
    void remove(ActionEvent event) {

        //creates object and set it to value of the selected item
        Object item = tableView.getSelectionModel().getSelectedItem();

        //remove it from the list
        inventory.removeItem((Item) item);
    }

    //CHECK
    @FXML
    void clear(ActionEvent event) {
        inventory.clearList();
    }

    //INCOMPLETE
    @FXML
    void edit(ActionEvent event) {

    }

    //TEST
    @FXML
    void searchInventory(ActionEvent event) {

        //create filtered list
        FilteredList<Item> searchFilter = new FilteredList<>(inventory.getData(), e -> true);
        searchBar.setOnKeyReleased(e -> {
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                searchFilter.setPredicate((Predicate<? super Item>) item -> {

                    //search bar is empty
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    //search bar - searching for price
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (item.getPrice().contains(newValue)) {
                        return true;
                    } else
                        //search bar - searching for name
                        if (item.getName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else
                            //search bar - searching for serial
                            if (item.getSerial().contains(newValue)) {

                                if (item.getSerial().toLowerCase().contains(lowerCaseFilter)) {
                                    return true;
                                }
                            }
                    return false;
                });
            });

            SortedList<Item> sortedData = new SortedList<>(searchFilter);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);

        });
    }

    //TEST
    @FXML
    void save(ActionEvent event) {
        inventory.saveList();
    }


    //TEST
    @FXML
        //open already existing list and load it into list app
    void open(ActionEvent event) throws IOException {

        //filter file type to text files only
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(filter);
        //opens load catalogue
        File file = fileChooser.showOpenDialog(null);

        try{
            //scan the file
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                //read into string array
                String[] line = reader.nextLine().split(", ");
                String price = line[0];
                String serial = line[1];
                String name = line[2];

                inventory.addItem(new Item(price,serial,name));
                tableView.setItems(inventory.getData());
            }
        }catch(Exception e){
            System.out.println("null");
        }
    }

    //INCOMPLETE
    @FXML
    void OpenUserGuide(ActionEvent event) {
    }

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, String> priceCol;

    @FXML
    private TableColumn<Item, String> serialCol;

    @FXML
    private TableColumn<Item, String> nameCol;

    @FXML
    private TextField priceField;

    @FXML
    private TextField serialField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField searchBar;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Menu openButton;

    @FXML
    private Button removeButton;

    @FXML
    private Menu saveButton;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

}
