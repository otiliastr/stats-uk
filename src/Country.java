import java.util.HashMap;

class Country{
    private static Country uniqueCountry;
    private String name;
    HashMap<String, Region> regions; 

    private Country(){
	regions = new HashMap<String, Region>();
    }

    public static Country getCountry(){
	if (uniqueCountry == null)
	    uniqueCountry = new Country();
	return uniqueCountry;
    }

    public void setName(String name){
	this.name=name;
    }

    public void addRegion(Region reg){
	regions.put(reg.getName(), reg);
    }

    public Region getRegion(String regionName){
	return regions.get(regionName); //trebuie tratata exceptia
    }
}