/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.net.www.http.HttpClient;



public class JavaRMIServer extends UnicastRemoteObject implements SensorService{

    public JavaRMIServer() throws RemoteException{
        super();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.setProperty("java.security.policy", "file:allowall.policy");
        try {
            final JavaRMIServer jrmi=new JavaRMIServer();
            Registry reg =LocateRegistry.createRegistry(2000); // create rmi registry
            reg.rebind("sensorServer", new JavaRMIServer()); // bind JavaRMIServer class 
            System.out.println ("Service started....");
             
          
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run()  {
                    try {
                        jrmi.SendMail(); 
                    } catch (Exception ex) {
                        Logger.getLogger(JavaRMIServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, 0, 15000);
        } catch (Exception e) {
             System.err.println ("err"+e);
        }
        
       
    
    }

    @Override
    public String addSensor(String name, String floor, String room, double d) throws RemoteException {
       /*
        This Method uses to add new sensor. After reciveing sensor details, details pass as a json object
        via api.
        after adding new sensor details, if it is successfull then return message called "Successfull"
        otherwise return error 
        */
         
        
        try {
            // creat a json object
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name",name );
            jSONObject.put("floorid",floor );
            jSONObject.put("room",room );
            jSONObject.put("colevel",d );
            
            
            
            
            
            byte[] postData =jSONObject.toString().getBytes(StandardCharsets.UTF_8);
            int length=postData.length;
            URL url = new URL("http://localhost:3000/addSensorData");
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
          
            return "Successfull add";
            
        } catch (Exception e) {
            System.err.println("err "+e);
           return "error "+e;
          }
          
         
         
         
    }

    @Override
    public String getSernsors() throws Exception {
        
        /*
        This method use to get sensor details via api.
        after receveing sensor details return that all data as a string  
        */
          try {

             
              URL url = new URL("http://localhost:3000/getSensorData");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
               
                String output = br.readLine();
              
               conn.disconnect();
               return output;
	  } catch (Exception e) {
            System.err.println("err "+e);
             return "err "+e;
          }
         
    }

   
    @Override
    public String login(String password) throws Exception {
        /*
        This method use to check login credentials. when you try to login desktop application this method 
        method will be  triggered.
        */
        JSONArray jSONArray;
        JSONObject jSONObject;
          try {
              URL url = new URL("http://localhost:3000/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output = br.readLine();
                jSONArray = new JSONArray(output);
                jSONObject = jSONArray.getJSONObject(0);
                int gotPass = Integer.parseInt(jSONObject.get("password").toString().trim());
                String passToString = String.valueOf(gotPass);
                
                if(passToString.equals(password)){
                    return "Logged in";
                }
                else{
                    return "Wrong pass";
                }
               
	  } catch (Exception e) {
            System.err.println("err "+e);
             return "err "+e;
          }
         
    }

    @Override
    public String editSensor(int id, String name, String room, String floor, String status) throws Exception {
        /*
        this method use to update existing sensor details.
        */
        
        
        try {
            JSONObject jSONObject = new JSONObject();
            
            jSONObject.put("name",name);
            jSONObject.put("room",room);
            jSONObject.put("floor",floor);
            jSONObject.put("status",status);
            jSONObject.put("id",id );
            
            byte[] postData =jSONObject.toString().getBytes(StandardCharsets.UTF_8);
            int length=postData.length;
             URL url = new URL("http://localhost:3000/editSensor");
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
            return "Edited Successfull";
            
        } catch (Exception e) {
            System.err.println("err "+e);
           return "error "+e;
          }
    
    }
    
    
    
       @Override
    public void SendMail() throws Exception {
        /*
        this method use to send send mails. this methode check co2 level and  smoke level.
        if co2 level or somoke level is grater than 5 then it will send warning to user via email.
        this methode calls every 15 seconds
        */
        
        int counter=0;
        String room=null;
        ArrayList<Sensor> sensors = new ArrayList<>();
        ArrayList<Sensor> email=new ArrayList<Sensor>();
        ArrayList<String> rooms = new ArrayList<>();
        sensors.add( new Sensor("name", "floor", "room", 10));
        
          try {

             
              URL url = new URL("http://localhost:3000/getSensorData");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                System.out.println("abc"+sensors.get(0).getName());
                //System.out.println(Double.parseDouble(jSONObject.get("colevel").toString()));
                System.out.println("Output from Server .... \n");
                String output = br.readLine();
                JSONArray jSONArray = new JSONArray(output);
             
              for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                double val=Double.parseDouble(jSONObject.get("colevel").toString());
                double val2=Double.parseDouble(jSONObject.get("smokelevel").toString());
                room=jSONObject.get("room").toString();
                if(val>5||val2>5)
                {
                    rooms.add(room);
                    counter=counter+1;
                        System.out.println("sesnor details"+jSONObject);
                 }
              
              }
              if(counter>0){
                  
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("room",rooms );



                    byte[] postData =jSONObject.toString().getBytes(StandardCharsets.UTF_8);
                    int length=postData.length;
                    URL url1 = new URL("http://localhost:3000/sendmail");
                    HttpURLConnection conn1;
                    conn1 = (HttpURLConnection) url1.openConnection();
                    conn1.setRequestMethod("POST");
                    conn1.setRequestProperty("userid", "123464");
                    conn1.setRequestProperty("Content-Type", "application/json");
                    conn1.setDoOutput(true);
                    DataOutputStream dataOutputStream1 = new DataOutputStream(conn1.getOutputStream());
                    dataOutputStream1.write(postData);  
                    dataOutputStream1.flush();
                    dataOutputStream1.close();
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
              
            }
             
            //System.out.println(jSONArray.getJSONObject(0));
            conn.disconnect();
            //System.out.println("abc"+sensors.get(0).getName());
              
               
                
	  } catch (Exception e) {
            System.err.println("err "+e);
             
          }
         
    }

  

   
    
}
