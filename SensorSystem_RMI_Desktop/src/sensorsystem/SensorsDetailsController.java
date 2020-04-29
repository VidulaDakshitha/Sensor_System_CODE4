/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.awt.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.management.Notification;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PropertySheet.Item;
import org.json.JSONArray;
import org.json.JSONObject;



public class SensorsDetailsController implements Initializable{

    private int editID;
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
    ArrayList<Integer> sensorId;
    @FXML
    private TableColumn<?, ?> smokelevel;
    @FXML
    private TableColumn<?, ?> status;
    
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        sensorId= new ArrayList<>();
         
         System.setProperty("java.security.policy", "file:allowall.policy");
       try {
            Registry reg =LocateRegistry.getRegistry("127.0.0.1",2000);
            sensorService = (SensorService) reg.lookup("sensorServer");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
            room.setCellValueFactory(new PropertyValueFactory<>("room"));
            colavel.setCellValueFactory(new PropertyValueFactory<>("colevel"));
            
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            smokelevel.setCellValueFactory(new PropertyValueFactory<>("smoke"));
            
            sensorTable.setItems(observableList);
           

            
        sensorTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
                if (event.getButton()==MouseButton.PRIMARY&&event.getClickCount()==2) {
                    try{
                    System.out.println("Mouse clicked");
                    
                    Sensor pos = sensorTable.getSelectionModel().getSelectedItem();
                    
                    editID = pos.getID();
                    String tempName = pos.getName();
                    String tempFloor = pos.getFloor();
                    String tempRoom = pos.getRoom();
                    String status = pos.getStatus();
                    //System.out.println("This statt: " + status);
                    
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editSensor.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
        
                    EditSensorController controller = fxmlLoader.getController();
                    controller.transferData(editID, tempName, tempFloor, tempRoom, status);
        
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));  
                    stage.show();
                    
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else if(event.getButton()==MouseButton.SECONDARY){
                          
                }   
           }
        });
           
            String data = sensorService.getSernsors();
            JSONArray jSONArray = new JSONArray(data);
                
            System.out.println(jSONArray.length());
               
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                System.out.println(jSONObject);
                sensorId.add(Integer.parseInt(jSONObject.get("id").toString().trim()));
                Sensor sensor =new Sensor(Integer.parseInt(jSONObject.get("id").toString().trim()),jSONObject.get("name").toString().trim(), jSONObject.get("floor").toString().trim(), jSONObject.get("room").toString().trim(),Double.parseDouble(jSONObject.get("colevel").toString()), jSONObject.get("status").toString().trim(),Double.parseDouble(jSONObject.get("smokelevel").toString()) );
                sensorTable.getItems().add(sensor);
               /* System.out.println("smoke level "+sensor.getSmokeLevel());
                System.out.println("status "+sensor.getStatus());
                System.out.println("fact status"+status.getCellData(i));
                System.out.println("fact "+smokelevel.getCellData(i));
                */
                
                  if (sensor.getLevel()>=70) {
                  System.out.println("co2 wadi "+sensor.getName());
                    Notifications.create()
                    .title("CO2 Lovel is High ")
                    .text("Floor"+sensor.getFloor()+" Room"+sensor.getRoom()+" co2 wadi ")
                    .showWarning();
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
    

    
    public void updatetable(){
        
       try {
             String data = sensorService.getSernsors();
            JSONArray jSONArray = new JSONArray(data);
                
            System.out.println(jSONArray.length());
                 sensorTable.getItems().clear();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                //System.out.println(jSONObject);
                Sensor sensor =new Sensor(Integer.parseInt(jSONObject.get("id").toString().trim()),jSONObject.get("name").toString().trim(), jSONObject.get("floor").toString().trim(), jSONObject.get("room").toString().trim(),Double.parseDouble(jSONObject.get("colevel").toString()), jSONObject.get("status").toString().trim(),Double.parseDouble(jSONObject.get("smokelevel").toString()) );
                sensorTable.getItems().add(sensor);
              //  System.out.println("smoke level "+sensor.getSmokeLevel());
              //  System.out.println("status "+sensor.getStatus());
                if (sensor.getLevel()>=70) {
                  /*  System.out.println("co2 wadi "+sensor.getName());
                    Notifications.create()
                    .title("CO2 Lovel is Heigh ")
                    .text("Floor"+sensor.getFloor()+" Room"+sensor.getRoom()+" co2 wadi ")
                    ;*/
                }
            }
        } catch (Exception e) {
            System.err.println("error "+e);
        }
    }

 
    
    
}
