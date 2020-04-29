/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;



public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private SensorService sensorService = null;
    @FXML
    private TextField password;
    @FXML
    private Button loginBTN;
         

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.setProperty("java.security.policy", "file:allowall.policy");
       try {
             Registry reg =LocateRegistry.getRegistry("127.0.0.1",2000);
            sensorService = (SensorService) reg.lookup("sensorServer");
           
 
        } catch (Exception e) {
            
            System.err.println("error "+e);
        } 
    }
    

    @FXML
    private void loginMethod(MouseEvent event){
        
        String pass;
        
        pass=password.getText().toString();
        
        try{
            String logMsg = sensorService.login(pass);
            System.out.println(logMsg);
        if(logMsg.startsWith("Logged"))
        {          
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Sensor System");
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("logoNew-removebg-preview.png")));
            stage.setScene(new Scene(root));  
            stage.show();
            Stage stageClose=(Stage)loginBTN.getScene().getWindow();
            stageClose.close();
        }
        else if(logMsg.startsWith("Wrong"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unauthorized");
            alert.setHeaderText("Wrong password");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
               // System.out.println("Pressed OK.");
            }
            }); 
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Oopzz!! Something went wrong!!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
              //  System.out.println("Pressed OK.");
            }
            }); 
        }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       
        
        
        
        
    }
        
    
}
