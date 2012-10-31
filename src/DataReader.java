import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import parser.csvReader;

public class DataReader {
    // Singleton since users will never need more than one
    // instance of this class
    private static DataReader uniqueReader;

    private DataReader() {}

    public DataReader getInstance() {
        if (uniqueReader == null)
            uniqueReader = new DataReader();
        return uniqueReader;
    }

    public void read(String filePath) {
        BufferedReader buf = null;
        try {
            buf = new BufferedReader(new FileReader(filePath));
        } catch(FileNotFoundException fne) {
            System.out.println("Bad file path given as input.");
            fne.printStackTrace();
        }

        csvReader reader = new csvReader();
        String line = null;
        do {
            try {
                line = buf.readLine();
            } catch(IOException ioe) {
                System.out.println("Can't read a line from the file.");
                ioe.printStackTrace();
            }
            ArrayList<String> values = reader.parse(line);
            String regionName = values.get(0);
            String cityName = values.get(1);
            // dealing with data for the entire region
            if (cityName.equals("Total")) 
                CrimeData.setRegionData(values);
            else
                CrimeData.setCityData(values);
        } while (line != null);
    }
}
