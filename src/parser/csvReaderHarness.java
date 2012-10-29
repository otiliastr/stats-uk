package parser;

import java.util.ArrayList;

/**
 * Simple harness for the csv parser.
 */
public class csvReaderHarness {

    public static void main(String[] args) {
        String test = "'123,45',the,quick,'brown,fox',jumped,'over,the,lazy',dog";
        csvReader reader = new csvReader();
        ArrayList<String> result = reader.parse(test);
        for (String value : result)
            System.out.println(value);
    }
}
