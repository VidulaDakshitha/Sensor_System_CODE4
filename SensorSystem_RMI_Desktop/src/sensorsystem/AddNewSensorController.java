/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class AddNewSensorController implements Initializable {

    @FXML
    private VBox addNewBox;
    @FXML
    private VBox addNewVbox;
    @FXML
    private TextField sName;
    @FXML
    private TextField sFloor;
    @FXML
    private TextField sRoom;
    @FXML
    private Button addSensorBtn;
    
    private SensorService sensorService = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         /*
        this method use to connect rmi server 
        */
        System.setProperty("java.security.policy", "file:allowall.policy");
       try {
             Registry reg =LocateRegistry.getRegistry("127.0.0.1",2000);
             sensorService = (SensorService) reg.lookup("sensorServer");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.toString());
               
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                       // System.out.println("Pressed OK.");
                    }
                 }); 
        } 
    }    

    @FXML
    private void addNewSensor(MouseEvent event) {
       // check mouse is clicked
        addNew();
    }
    
    @FXML
    private void addNewKeyPress(KeyEvent event) {
        // check enter key is presssed
        if (event.getCode() == KeyCode.ENTER){
             addNew();
             System.out.println("addNewKeyPress(KeyEvent event)");
        }
    }
    
    private void addNew(){
        /*
        this method use tp add a new senseor
        */
        String name,floor,room;
        name=sName.getText().toString(); // get sensor name
        floor=sFloor.getText().toString(); // get sensor floor details
        room=sRoom.getText().toString(); // get sensor room details
        if (name.equals("") || floor.equals("") || room.equals("")) {
            // check whether the fields are empty or not  
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warining");
                alert.setHeaderText("All fields are mandatory!!!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                       // System.out.println("Pressed OK.");
                    }
                 }); 
        }else{
            // if required fileds are not empty then send all data to rmi server
              sName.setText("");
              sFloor.setText("");
              sRoom.setText("");
             try {
                  
                String newMess= sensorService.addSensor(name , floor,room,0); // send data to rmi server and get response
                if (newMess.startsWith("Successfull")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successfull");
                    alert.setHeaderText(name+" added successfully");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                           // System.out.println("Pressed OK.");
                        }
                     }); 
                 }else{
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Error");
                      alert.setHeaderText(newMess);
               
                        alert.showAndWait().ifPresent(rs -> {
                            if (rs == ButtonType.OK) {
                               // System.out.println("Pressed OK.");
                            }
                         });    
                  }
                  
           
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.toString());
               
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                       // System.out.println("Pressed OK.");
                    }
                 }); 
                
            }
        } 
    }
    
}
