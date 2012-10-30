import java.util.HashMap;

class Region extends Zone{
    private HashMap<String, Zone> zones;

    public Region(String name){
        this(name,0);
    }

    public Region(String name,int populationSize){
        super(name,populationSize);
        zones =  new HashMap<String, Zone>();
    }

    public void addZone(Zone z){
        zones.put(z.getName(), z);
    }

    public Zone getZone(String zoneName){
        return zones.get(zoneName); //trebuie tratata exceptia
    }
}
