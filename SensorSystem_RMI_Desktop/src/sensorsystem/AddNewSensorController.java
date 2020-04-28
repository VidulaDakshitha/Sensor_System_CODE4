/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Dilshan
 */
public class AddNewSensorController implements Initializable {

    @FXML
    private VBox addNewBox;
    @FXML
    private VBox addNewVbox;
    @FXML
    private Label message;
    @FXML
    private TextField sName;
    @FXML
    private TextField sFloor;
    @FXML
    private TextField sRoom;
    @FXML
    private Button addSensorBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addNewSensor(MouseEvent event) {
    }
    
}
