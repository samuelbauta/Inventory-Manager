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
