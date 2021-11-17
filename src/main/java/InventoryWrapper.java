import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryWrapper {

ObservableList inventory = FXCollections.observableArrayList();


public ObservableList<Item> inventoryContents()
{
    return inventory;
}

    public void add(){
    Item item = new Item();
    // add new item to the inventory
        //add price, name, serial no.
    }

    public void remove(){
    // find index of the item you want to remove in the inventory
        //remove that item at that index
    }

    public void clear(){
    // clear the inventory of all items using clear method
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

}
