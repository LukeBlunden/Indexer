package ie.atu.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Contains the data structure of the stop words and provides methods for updating
 * and receiving back this data
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class StopWords {
    private final Set<String> stopWords;

    /**
     * Default constructor used when no filepath has been provided
     */
    public StopWords(){
        stopWords = new ConcurrentSkipListSet<>();
    }

    /**
     * Constructor used in case of filepath being provided, attempts to parse stop words
     * at filepath.
     *
     * @param stopWordsPath user provided stop words filepath
     * @throws FileNotFoundException if filepath does not point to a file
     */
    public StopWords(String stopWordsPath) throws FileNotFoundException {
        stopWords = new ConcurrentSkipListSet<>();
        new SinglePageParser(stopWords).parse(new FileInputStream(stopWordsPath));
    }

    /**
     * Parses the user specified stop words
     * @param stopWordsPath based off of user input
     */
    protected void parseStopWords(InputStream stopWordsPath){
        System.out.println("Parsing common words...");
        new SinglePageParser(stopWords).parse(stopWordsPath);
    }

    /**
     * Returns the stop words data as a new Hashset
     *
     * @return Hashset of the stop words data
     */
    public Set<String> getStopWords() {
        // Time complexity: O(n)
        return new HashSet<>(stopWords);
    }
}
