import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class InventoryManagementApplication extends Application {
        @Override
        //initiates application screen
        public void start(Stage primaryStage) throws Exception {
            Parent root =
                    FXMLLoader.load((getClass().getResource("inventory.fxml")));
            Scene mainScene = new Scene(root); // attach scene graph to scene
            primaryStage.setTitle("Inventory Manager"); // displayed in window's title bar
            primaryStage.setResizable(false);
            primaryStage.setScene(mainScene); // attach scene to stage
            primaryStage.show(); // display the stage
        }
        public static void main(String[] args) {
            launch(args);
        }
    }
/*

COMPLETE-------------
The monetary value of an item shall be greater than or equal to 0
Each inventory item shall have a serial number in the format of A-XXX-XXX-XXX, where A must be a letter and X can be either a letter or digit
The serial number of an item shall be unique within the current set of inventory items
Each inventory item shall have a name
The name of an item shall be between 2 and 256 characters in length (inclusive)
The GUI shall have a control that allows the user to add a new inventory item

The GUI shall have a control that allows the user to remove a single existing inventory item
The GUI shall have a control that allows the user to remove all existing inventory items


INCOMPLETE-----------
The user shall be able to store at least 1024 inventory items
Each inventory item shall have a monetary value in US dollars


The application shall display an error message if the user enters an existing serial number for the new item
The application shall display an error message if the user enters an invalid item value
The application shall display an error message if the user enters an invalid item name
The GUI shall have a control that allows the user to edit the value of an existing inventory item
The application shall display an error message if the user enters an invalid item value
The GUI shall have a control that allows the user to edit the serial number of an existing inventory item
The application shall prevent the user from duplicating the serial number
The GUI shall have a control that allows the user to edit the name of an existing inventory item
The application shall display an error message if the user enters an invalid item name

The GUI shall have a control that allows the user to sort the inventory items by value
The GUI shall have a control that allows the user to sort inventory items by serial number
The GUI shall have a control that allows the user to sort inventory items by name

The GUI shall have a control that allows the user to search for an inventory item by serial number
The GUI shall have a control that allows the user to search for an inventory item by name

 */