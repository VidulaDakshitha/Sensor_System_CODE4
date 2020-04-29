/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;


import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Locale;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class MainWindowController implements Initializable {
    
    
   private SensorService sensorService = null;
   private Boolean clcikShowAdd=false;
   

    @FXML
    private Label addbtn;
    @FXML
    private Label sensorbtn;
    @FXML
    private VBox addNewBox;
    @FXML
    private VBox addNewVbox;
   
    @FXML
    private Button addSensorBtn;
    @FXML
    private TextField sName;
    
    @FXML
    private TextField sRoom;
    @FXML
    private TextField sFloor;
    
    
    @FXML
    private AnchorPane mainAnchor_main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
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
    private void showAddNew(MouseEvent event) throws IOException {
       /*
        This methode use to show add new sensor window.
        when click on add new button this method will be triggered
        */
        if (clcikShowAdd) {
           Parent root =FXMLLoader.load(getClass().getResource("AddNewSensor.fxml"));
            Scene scene =  addbtn.getScene();
            root.translateXProperty().set(scene.getWidth());
            addNewBox.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
            timeline.getKeyFrames().add(keyFrame);

            timeline.play();
            addNewBox.getChildren().remove(0);

            sensorbtn.getStyleClass().remove("active");
            sensorbtn.getStyleClass().add("box");
            addbtn.getStyleClass().remove("box");
            addbtn.getStyleClass().add("active"); 
            clcikShowAdd=false;
        }
        
    }

    @FXML
    private void showSensors(MouseEvent event) throws IOException {
        /*
        This methode use to show all sensor details table.
        when click on sensor details button this method will be triggered
        */
        if (!clcikShowAdd) {
            Parent root =FXMLLoader.load(getClass().getResource("SensorsDetails.fxml"));
            Scene scene =  addbtn.getScene();
            root.translateYProperty().set(scene.getHeight());
            addNewBox.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
            timeline.getKeyFrames().add(keyFrame);

            timeline.play();
            addNewBox.getChildren().remove(0);
            // addbtn.setTextFill(Color.GRAY);
            addbtn.getStyleClass().remove("active");
            addbtn.getStyleClass().add("box");
            sensorbtn.getStyleClass().remove("box");
            sensorbtn.getStyleClass().add("active");
            clcikShowAdd=true;
        }
        
    }
  

    @FXML
    private void addNewSensor(MouseEvent event) {
      // check mouse is clicked
        addNew();
        
    }
    
    @FXML
    private void addNewKeyPress(KeyEvent event) {
        // check enter key is pressed
        if (event.getCode() == KeyCode.ENTER){
            addNew();
        }
    }
        private void addNew(){
            /*
            this methode use to add new sensor to system
            */
        String name,floor,room;
        name=sName.getText().toString();
        floor=sFloor.getText().toString();
        room=sRoom.getText().toString();
        
        if (name.equals("") || floor.equals("") || room.equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warining");
                alert.setHeaderText("All fields are mandatory!!!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        //System.out.println("Pressed OK.");
                    }
                 }); 
        }else{
                sName.setText("");
                sFloor.setText("");
                sRoom.setText("");
             try {
                  
                    String newMess= sensorService.addSensor(name , floor,room,0);
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
                                    System.out.println("Pressed OK.");
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
