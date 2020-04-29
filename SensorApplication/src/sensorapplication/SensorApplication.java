/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorapplication;

import com.sun.glass.ui.SystemClipboard;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


public class SensorApplication {

    ArrayList<Integer> sensorId;
    /**
     * @param args the command line arguments
     */
    
     public String updateSernsorLevel(int id, double lavel, double smokeLevel) throws Exception {
       //To change body of generated methods, choose Tools | Templates.
     try {
            
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("lavel",lavel );
            jSONObject.put("smokeLevel",smokeLevel);
            jSONObject.put("id",id );
            
           
            
            
            byte[] postData =jSONObject.toString().getBytes(StandardCharsets.UTF_8);
            int length=postData.length;
             URL url = new URL("http://localhost:3000/updateSensorLavel");
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("userid", "123464");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.write(postData);  
            dataOutputStream.flush();
            dataOutputStream.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                
                String output = br.readLine();
            System.out.println(output);
            return "Successfull Update";
            
        } catch (Exception e) {
            System.err.println("err "+e);
           return "error "+e;
          }
    
    
    }
     
     public void getSensorId()
     {
         int count=0;
         
        
        sensorId= new ArrayList<>();
        
          try {

             
              URL url = new URL("http://localhost:3000/getSensorData");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
               
                //System.out.println(Double.parseDouble(jSONObject.get("colevel").toString()));
                System.out.println("Output from Server .... \n");
                String output = br.readLine();
             JSONArray jSONArray = new JSONArray(output);
             
              for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                int val=Integer.parseInt(jSONObject.get("id").toString());
                String Senstatus=jSONObject.get("status").toString();
                  System.out.println("Senstatus "+Senstatus);
                if(Senstatus.equals("Active"))
                {
                    count=count+1;
                    sensorId.add(val);
                }
                
               
              }
              System.out.println("count"+count);
          }catch(Exception e)
          {
               System.err.println("err "+e);
          }
         
     }
     
     
 
     
       private void updateLavel(){
      DecimalFormat f = new DecimalFormat("##.00");
      DecimalFormat f2 = new DecimalFormat("##.00");
      try {
         for (Integer id : sensorId) {
             Random r= new Random();
             Random r2= new Random();
             int rangeMin=0,rangeMax=10;
             
             
          double level = Double.parseDouble( f.format(rangeMin + (rangeMax - rangeMin) * r.nextDouble()));
          double smoke = Double.parseDouble( f2.format(rangeMin + (rangeMax - rangeMin) * r2.nextDouble()));
          String data = updateSernsorLevel(id,level,smoke);
          
      } 
         
      } catch (Exception e) {
           System.err.println("error "+e);
      }
      
      
  }
     
    public static void main(String[] args) {
        // TODO code application logic here
        
       
        
       try{
           final SensorApplication sensorApp=new SensorApplication();
           sensorApp.getSensorId();
           
           Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run()  {
                    try {
                        sensorApp.getSensorId();
                        sensorApp.updateLavel();
                    } catch (Exception ex) {
                        Logger.getLogger(SensorApplication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, 0, 10000);
       }catch(Exception e)
       {
           
       }
        
  
    }
    
}
