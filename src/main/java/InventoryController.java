/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Samuel Bauta
 */
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

        //global string for error handling
        String error = "error";

        serialCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            //handles the serial edit
              @Override
              public void handle(TableColumn.CellEditEvent<Item, String> event) {
                  Item item = event.getRowValue();
                  boolean alreadyExists = false;
                  //boolean for checking if item already exists
                  if(event.getNewValue().length() == 13){
                      //checks the length of the serial number
                      if(event.getNewValue().matches("[a-zA-Z]-[\\w]{3}-[\\w]{3}-[\\w]{3}")){
                          //checks format of string to see if it matches serial format: A-XXX-XXX-XXX
                          for(Item thing : inventory.getData()){
                              //for every item in the inventory see if it already exists
                              if(thing.getSerial().contains(event.getNewValue())) {
                                  alreadyExists = true;

                                  if (event.getOldValue().contains(event.getNewValue())) {
                                      alreadyExists = true;
                                  }
                              }
                          }if(!alreadyExists) {
                              //if it doesn't already exist, add it to list
                              item.setSerial(event.getNewValue());
                          }else{
                              //if it does, let the user know with an error window
                              Alert alert = new Alert(Alert.AlertType.ERROR);
                              alert.setTitle(error);
                              alert.setHeaderText(null);
                              alert.setContentText("This serial number already exist! Enter a new serial number!");
                              alert.showAndWait();
                              System.out.println("error init serial 1");
                          }
                      }else{
                          //if user entered wrong format, let the user know with an error window
                          Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle(error);
                          alert.setHeaderText(null);
                          alert.setContentText("Serial number must be in the format: A-XXX-XXX-XXX\nA must be a letter and X may either be a letter or a digit!");
                          alert.showAndWait();
                          System.out.println("error init serial 2");
                      }
                  }else {
                      //if user entered wrong format, let the user know with an error window
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle(error);
                      alert.setHeaderText(null);
                      alert.setContentText("Serial number must be in the format: A-XXX-XXX-XXX!");
                      alert.showAndWait();
                      System.out.println("error init serial 3");
                  }
              }
          });

        priceCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            //handles the price edit
             @Override
             public void handle(TableColumn.CellEditEvent<Item, String> event) {
                 Item item = event.getRowValue();
                 //checks if price is a number
                 if (event.getNewValue().matches("\\d*.\\d*")||event.getNewValue().matches("\\d*")) {
                     //if it is a number, parse it from a string to a double
                     double doublePrice = Double.parseDouble(event.getNewValue());
                     //check if its non-negative
                     if (doublePrice >= 0) {
                         //if it is, add to inventory
                         item.setPrice(event.getNewValue());
                     } else {
                         //if it isn't, let the user know
                         Alert alert = new Alert(Alert.AlertType.ERROR);
                         alert.setTitle(error);
                         alert.setHeaderText(null);
                         alert.setContentText("Price must be zero or greater!");
                         alert.showAndWait();
                         System.out.println("error init price 1");
                     }
                 } else {
                     //if they entered non-numerical value, let the user know that, that's not allowed
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle(error);
                     alert.setHeaderText(null);
                     alert.setContentText("Price must be entered as a numerical value!");
                     alert.showAndWait();
                     System.out.println("error init price 2");
                 }
             }
            });

        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item, String>>() {
            //handles name edit
            @Override
            public void handle(TableColumn.CellEditEvent<Item, String> event) {
                Item item = event.getRowValue();

                //checks that new value isn't blank and is between 2 and 256 characters
                if (!event.getNewValue().equals("") && !event.getNewValue().equals(" ")) {
                    if (event.getNewValue().length() >= 2 && event.getNewValue().length() <= 256) {
                        item.setName(event.getNewValue());
                    } else {
                        //if the value is not between 2 and 256, let the user know with an error window
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(error);
                        alert.setHeaderText(null);
                        alert.setContentText("Name must be between 2 and 256 characters!");
                        alert.showAndWait();
                        System.out.println("error init name 1");
                    }
                } else {
                    //let the user know item name cannot be left blank
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(error);
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a name for the item!");
                    alert.showAndWait();
                    System.out.println("error init name 2");
                }
            }
        });

    }
    @FXML
    void add(ActionEvent event) {

        String error = "Error, Invalid Input!";
        boolean alreadyExists = false;

        //check is name is blank/empty
        if (!nameField.getText().equals("") && !nameField.getText().equals(" ")) {
            Item item;
            //check if name is between 2 and 256 characters
            if (nameField.getText().length() >= 2 && nameField.getText().length() <= 256) {
                //check if serial is the proper length
                if (serialField.getText().length() == 13) {
                    //check if serial is the proper format
                    if (serialField.getText().matches("[a-zA-Z]-[\\w]{3}-[\\w]{3}-[\\w]{3}")) {
                        //check every item in the inventory and see if serial number already exists
                        for(Item thing : inventory.getData()){
                            alreadyExists = thing.getSerial().contains(serialField.getText());
                        }if(!alreadyExists){
                            //check if price is a numerical value
                            if (priceField.getText().matches("\\d*.\\d*")||priceField.getText().matches("\\d*")) {
                                //change string to double & check for price value
                                double doublePrice = Double.parseDouble(priceField.getText());
                                if (doublePrice >= 0) {
                                    //if all the conditions are met, add item to inventory
                                    item = new Item(priceField.getText(), serialField.getText(), nameField.getText());
                                    inventory.addItem(item);
                                    tableView.setItems(inventory.getData());
                                    priceField.clear();
                                    serialField.clear();
                                    nameField.clear();
                                } else {
                                    //let user know that price can not be negative number
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle(error);
                                    alert.setHeaderText(null);
                                    alert.setContentText("Price must be zero or greater!");
                                    alert.showAndWait();
                                    System.out.println("error 1");
                                }
                            } else {
                                //let user know that the price must be numerical
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle(error);
                                alert.setHeaderText(null);
                                alert.setContentText("Price must be entered as a numerical value!");
                                alert.showAndWait();
                                System.out.println("error 2");
                            }

                        } else {
                            //let the user know that the serial number already exists
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(error);
                            alert.setHeaderText(null);
                            alert.setContentText("This serial number already exist! Enter a new serial number!");
                            alert.showAndWait();
                            System.out.println("error 3");
                        }
                    } else {
                        //let user know that the serial is in incorrect format
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(error);
                        alert.setHeaderText(null);
                        alert.setContentText("Serial number must be in the format: A-XXX-XXX-XXX\nA must be a letter and X may either be a letter or a digit!");
                        alert.showAndWait();
                        System.out.println("error 4");
                    }

                } else {
                    //let the user know that the serial is not proper length
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(error);
                    alert.setHeaderText(null);
                    alert.setContentText("Serial number is not the proper length!\nSerial number must be in format: A-XXX-XXX-XXX");
                    alert.showAndWait();
                    System.out.println("error 5");
                }

            }else {
                //let the user know that the name must be between 2 and 256 characters
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(error);
                    alert.setHeaderText(null);
                    alert.setContentText("Name must be between 2 and 256 characters!");
                    alert.showAndWait();
                    System.out.println("error 6");
                }

            } else {
                //let user know the name cannot be left blank
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(error);
                alert.setHeaderText(null);
                alert.setContentText("Please enter a name for the item!");
                alert.showAndWait();
                System.out.println("error 7");
            }
        }

    @FXML
    //removes single item from list
    void remove(ActionEvent event) {

        //creates object and set it to value of the selected item
        Object item = tableView.getSelectionModel().getSelectedItem();

        //remove it from the list
        inventory.removeItem((Item) item);
    }

    @FXML
    //clear all items from list
    void clear(ActionEvent event) {
        inventory.clearList();
    }

    @FXML
    //search through the entire inventory
    void searchInventory(ActionEvent event) {

        //create filtered list
        FilteredList<Item> searchFilter = new FilteredList<>(inventory.getData(), e -> true);
        searchBar.setOnKeyReleased(e -> {
            //whatever key is pressed, search for the corresponding item with that value
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                searchFilter.setPredicate((Predicate<? super Item>) item -> {
                    //if search bar is empty
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

            //sorted list so the search bar can pull sorted data
            SortedList<Item> sortedData = new SortedList<>(searchFilter);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);

        });
    }
    @FXML
    void saveHtml(ActionEvent event) {
        //calls html method in wrapper class
        inventory.saveHtml();
    }
    @FXML
    void saveTsv(ActionEvent event) {
        //calls tsv method in wrapper class
        inventory.saveTsv();
    }
    @FXML
    void saveJson(ActionEvent event) throws IOException {
        //calls json method in wrapper class
        inventory.saveJson();
    }

    @FXML
    void openHtml(ActionEvent event) throws IOException {
        inventory.clearList();
        inventory.loadHtml();
        tableView.setItems(inventory.getData());
    }
    @FXML
    void openTsv(ActionEvent event) throws IOException {
        inventory.clearList();
        inventory.loadTsv();
        tableView.setItems(inventory.getData());
    }
    @FXML
    void openJson(ActionEvent event) {
        inventory.clearList();
        inventory.loadJson();
        tableView.setItems(inventory.getData());
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
    private MenuItem htmlSave;

    @FXML
    private MenuItem tsvSave;

    @FXML
    private MenuItem jsonSave;

    @FXML
    private MenuItem htmlOpen;

    @FXML
    private MenuItem tsvOpen;

    @FXML
    private MenuItem jsonOpen;
}
