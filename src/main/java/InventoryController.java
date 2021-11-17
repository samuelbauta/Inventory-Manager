import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //these skeletal code was basically run down in the wrapper class

        //im going to follow the same approach I did for the last design but
        //improve in areas that I fell short on previously

    }

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button editButton;

    @FXML
    private Menu openButton;

    @FXML
    private Button removeButton;

    @FXML
    private Menu saveButton;

    @FXML
    private MenuItem sortName;

    @FXML
    private MenuItem sortSerialNo;

    @FXML
    private MenuItem sortValue;

    @FXML
    private Menu viewButton;

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void clear(ActionEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {

    }

    @FXML
    void open(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

}
