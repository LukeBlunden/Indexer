package ie.atu.sw;

import java.util.Set;

/**
 * A SinglePageParser implements the processing of the words in a line to
 * add them to a data structure without keeping track of line or page numbers
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class SinglePageParser extends SequentialParser {
    private final Set<String> data;

    /**
     * Implements the subLineProcess method to add words to the data structure
     *
     * @param data words on a line of a file
     */
    public SinglePageParser(Set<String> data){
        this.data = data;
    }
    @Override
    public void subLineProcess(String[] parts){
        // Time complexity: O(n log(n)) to process each word in line O(n)
        // and potentially O(log(n)) for Set.add and Map.put
        for (String word : parts) {
            // Removes leading and trailing ' which are present from preserving them
            // Which are present from preserving punctuation in default lineProcess method
            word = word.replaceAll("^'|'$", "");
            if(word.equals("")) continue;
            data.add(word);
        }
    }
}
