/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.awt.Toolkit;
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
import javafx.scene.control.Alert;
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
    @FXML
    private TableColumn<?, ?> smokelevel;
    @FXML
    private TableColumn<?, ?> status;
    

    /**
     * Initializes the controller class.
     */
    ObservableList<Sensor> observableList = FXCollections.observableArrayList();
    ArrayList<Integer> sensorId;
   
    
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        sensorId= new ArrayList<>();
        String mess="";
         /*
        this method use to connect rmi server 
        */
         System.setProperty("java.security.policy", "file:allowall.policy");
       try {
            Registry reg =LocateRegistry.getRegistry("127.0.0.1",2000);
            sensorService = (SensorService) reg.lookup("sensorServer");
            
            // set Table View Columns to display detials
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
            room.setCellValueFactory(new PropertyValueFactory<>("room"));
            colavel.setCellValueFactory(new PropertyValueFactory<>("colevel"));
            
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            smokelevel.setCellValueFactory(new PropertyValueFactory<>("smoke"));
            
            sensorTable.setItems(observableList);
           

            // handele sensor update
            // when user double click on the row edit detials window will be shown
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

                            // show edit details window
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editSensor.fxml"));
                            Parent root = (Parent) fxmlLoader.load();

                            EditSensorController controller = fxmlLoader.getController();
                            // send data to EditSensorController class
                            controller.transferData(editID, tempName, tempFloor, tempRoom, status); 

                            Stage stage = new Stage();
                            stage.setTitle("Sensor System");
                            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("logoNew-removebg-preview.png")));
                            stage.setScene(new Scene(root));  
                            stage.show();

                        }catch(Exception e){
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
            });
           

            /*
            The sensor details should be updated every 15 seconds 
            */
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                   Platform.runLater(()->{
                       
                    updatetable();
                   });
                        
                }
            }, 0, 15000);
           
            
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
    
    private void showNotification(String mess){
        Toolkit.getDefaultToolkit().beep();// Notification sound
         Notifications.create()
                            .title("CO2 Lovel is High ")
                            .text(mess)
                            .showWarning();
    }

    
    public void updatetable(){
       /*
        This methode use to display sensor details
        
        */
        String mess="";
       try {
             String data = sensorService.getSernsors();
             JSONArray jSONArray = new JSONArray(data);
                
           System.out.println(jSONArray.length());
                 sensorTable.getItems().clear();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
               // System.out.println(jSONObject);
                Sensor sensor =new Sensor(Integer.parseInt(jSONObject.get("id").toString().trim()),jSONObject.get("name").toString().trim(), jSONObject.get("floor").toString().trim(), jSONObject.get("room").toString().trim(),Double.parseDouble(jSONObject.get("colevel").toString()), jSONObject.get("status").toString().trim(),Double.parseDouble(jSONObject.get("smokelevel").toString()) );
                sensorTable.getItems().add(sensor);
                
                
                  if (sensor.getLevel()>=5) {
                      mess+="Floor: "+sensor.getFloor()+" Room: "+sensor.getRoom()+"\n";
                     
                }
            }
            if (!mess.equals("")) {
                  showNotification(mess);
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
