import java.util.HashMap;

class Country{
    private static Country uniqueCountry;
    private String name;
    private HashMap<String, Region> regions; 

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

    public void addRegion(Region region){
        regions.put(region.getName(), region);
    }

    public Region getRegion(String regionName){
        // Add a region if the region isn't found in the table
        // Assumes the input is correct (might be good to do some
        // sort of checking)
        if (!regions.containsKey(regionName))
            regions.put(regionName, new Region(regionName));
        return regions.get(regionName); 
    }
}
