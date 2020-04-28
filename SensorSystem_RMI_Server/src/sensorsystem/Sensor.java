package sensorsystem;



public class Sensor {

    private int id;
    private String name,floor,room,colevel,status,smokelevel;
    private double level,smoke;
    
    
    public Sensor(String name, String floor, String room, double level) {
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = level;
        this.colevel= new Double(level).toString();
    }
    public Sensor(String name, String floor, String room, String colevel) {
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = level;
        this.level = Double.parseDouble(colevel);
    }

    public Sensor(int id, String name, String floor, String room, double level, String status,double smoke) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = level;
        this.smoke=smoke;
        this.colevel= new Double(level).toString();
        this.smokelevel=new Double(smoke).toString();
        this.status = status;
    }
    public Sensor(int id, String name, String floor, String room, String colevel, String status,String smokelevel) {
        this.id = id;
        this.colevel = colevel;
        this.smokelevel=smokelevel;
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = Double.parseDouble(colevel);
        this.smoke=Double.parseDouble(smokelevel);
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom() {
        return room;
    }

    public String getColevel() {
        return colevel;
    }
    
    public String getSmokeLevel(){
        return smokelevel;
    }

    public double getLevel() {
        return level;
    }
    
    public double getSmoke(){
        return smoke;
    }

    public int getID()
    {
        return id;
    }
    
    public String getStatus()
    {
        return status;
    }
   
}
