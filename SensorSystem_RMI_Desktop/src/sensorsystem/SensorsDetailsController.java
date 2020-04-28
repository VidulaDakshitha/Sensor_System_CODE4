/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;


import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.management.Notification;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Dilshan
 */
public class SensorsDetailsController implements Initializable, Runnable {

    private SensorService sensorService = null;
    @FXML
    private TableView<Sensor> sensorTable;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> floor;
    @FXML
    private TableColumn<?, ?> room;
    @FXML
    private TableColumn<?, ?> colavel;

    /**
     * Initializes the controller class.
     */
    ObservableList<Sensor> observableList = FXCollections.observableArrayList(
 
    
    );
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         System.setProperty("java.security.policy", "file:allowall.policy");
       try {
            Registry reg =LocateRegistry.getRegistry("127.0.0.1",2001);
            sensorService = (SensorService) reg.lookup("sensorServer");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
            room.setCellValueFactory(new PropertyValueFactory<>("room"));
            colavel.setCellValueFactory(new PropertyValueFactory<>("colevel"));
            sensorTable.setItems(observableList);
      
           
            String data = sensorService.getSernsors();
            JSONArray jSONArray = new JSONArray(data);
                
            System.out.println(jSONArray.length());
                
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                System.out.println(jSONObject);
                Sensor sensor =new Sensor(jSONObject.get("name").toString().trim(), jSONObject.get("floor").toString().trim(), jSONObject.get("room").toString().trim(),Double.parseDouble(jSONObject.get("colevel").toString()) );
                sensorTable.getItems().add(sensor);
                System.out.println(sensor.getName());
                if (sensor.getLevel()>=75) {
                   System.out.println("co2 wadi "+sensor.getName());
                }
            }
           
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    updatetable();
                }
            }, 0, 15000);
           
 
        } catch (Exception e) {
            
            System.err.println("error "+e);
        }
    }    
    
    private void updatetable(){
        
       try {
           
             String data = sensorService.getSernsors();
            JSONArray jSONArray = new JSONArray(data);
                
            System.out.println(jSONArray.length());
                 sensorTable.getItems().clear();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                //System.out.println(jSONObject);
                Sensor sensor =new Sensor(jSONObject.get("name").toString().trim(), jSONObject.get("floor").toString().trim(), jSONObject.get("room").toString().trim(),Double.parseDouble(jSONObject.get("colevel").toString()) );
                sensorTable.getItems().add(sensor);
                //System.out.println(sensor.getName());
                if (sensor.getLevel()>=75) {
                   System.out.println("co2 wadi "+sensor.getName());
            
                }
            }
        } catch (Exception e) {
            System.err.println("error "+e);
        }
      
        
    }

    @Override
    public void run() {
        while (true) {   
            try {
                 updatetable();
            Thread.sleep(1000*15);
            } catch (Exception e) {
                System.err.println("error "+e);
            }
           
        }
        
    }
    
    
    
}
