import java.util.ArrayList;

public class csvReaderHarness {

    public static void main(String[] args) {
        String test = "the,quick,'brown,fox',jumped,'over,the,lazy',dog";
        csvReader reader = new csvReader();
        ArrayList<String> result = reader.parse(test);
        for (String value : result)
            System.out.println(value);
    }
}
