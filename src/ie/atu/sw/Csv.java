package ie.atu.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Contains the data structure of the dictionary csv and provides methods for updating
 * and receiving back this data
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class Csv {
    private final Map<String, List<String>> dictionary;

    /**
     * Default constructor used when no filepath has been provided
     */
    public Csv() {
        dictionary = new ConcurrentHashMap<>();
    }

    /**
     * Constructor used in case of filepath being provided, attempts to parse dictionary
     * at filepath.
     *
     * @param csvPath user provided dictionary filepath
     * @throws FileNotFoundException if filepath does not point to a file
     */
    public Csv(String csvPath) throws FileNotFoundException {
        dictionary = new ConcurrentHashMap<>();
        parseDictionary(new FileInputStream(csvPath));
    }

    /**
     * Parses the user specified dictionary
     * @param dictionaryPath based off of user input
     */
    protected void parseDictionary(InputStream dictionaryPath){
        System.out.println("Parsing dictionary...");
        new CsvParser(dictionary).parse(dictionaryPath);
    }

    /**
     * Returns the stop words data as a new Hashmap
     *
     * @return Hashmap of the stop words data
     */
    public Map<String, List<String>> getDictionary() {
        // Time complexity: O(n)
        return new HashMap<>(dictionary);
    }
}
