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

import sun.net.www.http.HttpClient;











/**
 *
 * @author Dilshan
 */

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
            Registry reg =LocateRegistry.createRegistry(2000);
            reg.rebind("sensorServer", new JavaRMIServer());
             System.out.println ("Service started....");
        } catch (Exception e) {
             System.err.println ("err"+e);
        }
        
       
    
    }

    @Override
    public String addSensor(String name, String floor, String room, double d) throws RemoteException {
         System.out.println("Call Funtion"); //To change body of generated methods, choose Tools | Templates.
         
        
        try {
            
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
            System.out.println(output);
            return "Successfull add";
            
        } catch (Exception e) {
            System.err.println("err "+e);
           return "error "+e;
          }
          
         
         
         
    }

    @Override
    public String getSernsors() throws Exception {
        
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add( new Sensor("name", "floor", "room", 10));
          try {

             
              URL url = new URL("http://localhost:3000/getSensorData");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                System.out.println("Output from Server .... \n");
                String output = br.readLine();
              
               conn.disconnect();
                System.out.println("abc"+sensors.get(0).getName());
               return output;
	  } catch (Exception e) {
            System.err.println("err "+e);
             return "err "+e;
          }
         
    }

    @Override
    public String updateSernsorLevel(int id, double lavel) throws Exception {
       //To change body of generated methods, choose Tools | Templates.
     try {
            
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("lavel",lavel );
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
    
    @Override
    public String login(String password) throws Exception {
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
    public String editSensor(int id, String name, String room, String floor) throws Exception {
        try {
            JSONObject jSONObject = new JSONObject();
            
            jSONObject.put("name",name);
            jSONObject.put("room",room);
            jSONObject.put("floor",floor);
            jSONObject.put("id",id );
            
            System.out.println(id + name + room + floor);
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

  

   
    
}
