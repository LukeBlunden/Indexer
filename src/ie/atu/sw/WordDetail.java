package ie.atu.sw;

import java.util.*;

/**
 * Contains the List of definitions for a word from a dictionary csv
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class WordDetail {
    private final List<String> definitions;

    /**
     * Constructor taking in the definitions data from a Csv object and
     * converting to an ArrayList
     *
     * @param definitions contains the List object from a Csv
     */
    public WordDetail(List<String> definitions){
        // Time complexity: O(n) to copy List
        this.definitions = new ArrayList<>(definitions);
    }

    /**
     * Returns a copy of the definitions
     *
     * @return definitions a copy of the definitions for a word
     */
    protected List<String> getDefinitions() {
        // Time complexity: O(n) to copy List
        return new ArrayList<>(definitions);
    }
}
