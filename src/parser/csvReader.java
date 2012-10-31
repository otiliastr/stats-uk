/*
 * Consider using XML parsers
 */
package parser;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * General purpose parser for csv files. 
 */
public class csvReader {
    // regex to split csv line by
    final Pattern pattern = Pattern.compile("'[^']*'|[^,]+");

    /**
     * Takes a line given as comma-separated values and returns
     * an array of strings containing the values
     * @param line List of comma-separated values with ' as entry delimiter 
     * @return ArrayList with each value as a separate entry
     */
    public ArrayList<String> parse(String line) {
        ArrayList<String> values = new ArrayList<String>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String value = matcher.group();
            if (value.charAt(0) == '\'')
                value = value.substring(1, value.length() - 1);
            values.add(value);
        }

        return values;
    } 
}
