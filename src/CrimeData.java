import parser.csvReader;

import java.util.ArrayList;

public class CrimeData implements StatisticData {
    // Stores the tags associated with crime rates
    private static ArrayList<String> tags;
    // Stores data corresponding to one region
    private ArrayList<String> data;

    // rating for this data set
    private double rating;

    // private constructor to prevent instantiating except from
    // static methods
    private CrimeData() { }

    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * @param line Line in csv file containing tags of the data
     */
    public void setTags(String line) {
        csvReader reader = new csvReader();
        tags = reader.parse(line);
    }

    /*
     * Sets the crime data for a region. Region is always 
     * first element of the row.
     * @param line Line in csv file containing a row of data
     */
    public static void setRegionData(String line) {
        csvReader reader = new csvReader();
        CrimeData crimeData = new CrimeData();
        crimeData.data = reader.parse(line);
        if (!crimeData.data.get(1).equals("Total"))
            throw new IllegalArgumentException();

        String regionName = crimeData.data.get(0);
        // throw out first two elements as all data stored in them is
        // mirrored in the region and city classes
        crimeData.data.remove(0);
        crimeData.data.remove(1);
        Country.getCountry().getRegion(regionName).setCrimeData(crimeData);
    }

    public static void setCityData(String line) {
        csvReader reader = new csvReader();
        CrimeData crimeData = new CrimeData();
        crimeData.data = reader.parse(line);
        String regionName = crimeData.data.get(0);
        String cityName = crimeData.data.get(1);
        // throw out first two elements as all data stored in them is
        // mirrored in the region and city classes
        crimeData.data.remove(0);
        crimeData.data.remove(1);
        Country.getCountry().getRegion(regionName).getZone(cityName).setCrimeData(crimeData);
    }

    /**
     * Compute the rating for this set of data; used for colouring the map
     */
    public void computeRating() {
        // returns the mean of the data right now
        rating = 0.0;    
        // first element is population of region; second is household population
        for (int i = 2; i < data.size(); ++i) {
            String strValue = data.get(i);
            // process the string so that it has no commas
            strValue = strValue.replace(",", "");
            double value = Double.parseDouble(strValue);
            rating += value;
        }
    }

    
    /**
     * Returns a string of all values separated by spaces
     */
    public String toString() {
        String returnString = "";
        for (String value : data) {
            returnString.concat(" " + value);
        }

        return returnString;
    }
}
