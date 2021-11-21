import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;

public class InventoryWrapper {

        //create observable list
        ObservableList<Item> data = FXCollections.observableArrayList();

        //returns the observable list -COMPLETE
        public ObservableList<Item> getData() {
            return data;
        }

        //add item to the list -COMPLETE
        public void addItem(Item newItem){
            getData().addAll(newItem);
        }

        //removes the item from list -COMPLETE
        public void removeItem(Item index){
            getData().remove(index);
        }

        //clear the list -COMPLETE
        public void clearList(){
            getData().clear();
        }

        //TEST
        public void saveList() {
            //method saves list to local storage
            //create new file chooser
            FileChooser fileChooser = new FileChooser();
            //create filter to only save text file
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(filter);
            //open a new window that shows a save dialogue
            File file = fileChooser.showSaveDialog(new Stage());
            String status = null;

            //write a text file with the values from the list
            try {
                BufferedWriter wr = new BufferedWriter(new FileWriter(file));
                for (Item item : data) {
                    wr.write(item.getPrice() + ", " + item.getSerial() + ", " + item.getName());
                    wr.newLine();
                }
                wr.close();
            }catch(Exception e) {
                System.out.println("null");
            }
        }
    }

















/*
ObservableList inventory = FXCollections.observableArrayList();


public ObservableList<Item> inventoryContents()
{
    return inventory;
}

    public void addItem(Item item){
    // add new item to the inventory
        //add price, name, serial no.
    }

    public void removeItem(Item index){
    // find index of the item you want to remove in the inventory
        //remove that item at that index
        inventory.remove(index);
    }

    public void clear(){
    // clear the inventory of all items using clear method
        inventory.removeAll();
    }

    public void open(){
    // open method is used for testing purposes. This scans in a file and
        // writes it into the inventory then checks to make sure that the
        // contents of that file are correct
        // this method also need to handle different types of files including json
        // which im not sure how to do yet
        //basically scan through each line in the file and add it to the inventory

    }

    public void save(){
    // save method will scan content and add it into a file
        // basically run through every item in the inventory and write it
        // into a file

    }

 */


