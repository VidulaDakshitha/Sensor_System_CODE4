/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;






import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SensorSyaytem_RMI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
       
         launch(args);
         
   
         
         
   /*  try {

            URL url = new URL("http://localhost:3001/getSensorData");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           
            
		
		System.out.println("Output from Server .... \n");
                String output = br.readLine();
                
              
         
    System.out.println(output); 
    
    
		

		conn.disconnect();

	  } catch (Exception e) {

		        System.err.println("err "+e);

	  }

      */ 
      
   
   
    }
    
}
