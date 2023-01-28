package ie.atu.sw;

import java.util.*;

/**
 * A CsvParser overrides the processing of lines from a file to
 * instead separate them by , as well as implementing the processing of
 * halves of a csv line
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class CsvParser extends ConcurrentParser {
    private final Map<String, List<String>> csv;

    /**
     * Constructor receiving the implemented Map to update from a Csv object
     *
     * @param csv data in a Csv object
     */
    public CsvParser(Map<String, List<String>> csv) {
        this.csv = csv;
    }

    /**
     * Overrides the default implementation by splitting lines on , character
     *
     * @param line line to be divided into parts
     */
    @Override
    public void lineProcess(String line){
        // Time complexity: O(n²) process each line n * process each word n = n²
        String[] words = line.split(",");
        subLineProcess(words);
    }

    /**
     * Implements the subLineProcess method to process each half of the csv line
     * and add to the csv data
     *
     * @param parts 2 halves of a csv line
     */
    @Override
    public void subLineProcess(String[] parts) {
        // Time complexity: O(n) to process each word in line which is larger
        // than worst case O(log(n)) for Map.put
        // First half of parts in csv is word
        String word = parts[0].trim().toLowerCase();
        // Second half of parts in csv is all definitions
        // Regex uses positive look behinds to split along : and ; while keeping delimiter
        // This will contain the definition type as well as the definitions
        String[] splitDefinitions = parts[1].split("(?<=:|;)");
        List<String> definitions = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        String wordType = null;
        // Time complexity: O(n)
        for(String definition : splitDefinitions){
            // Checks if a definition type and not the first one
            // if yes then adds previous definition and resets
            if (definition.contains(":") && !sb.isEmpty()) {
                // Time complexity: O(1)
                definitions.add(wordType + " " +sb);
                sb.setLength(0);
            }
            // Checks if a definition type, if so saves it in wordType
            if(definition.contains(":")) {
                wordType = definition.trim();
            // Otherwise must be part of definition and adds to StringBuilder
            } else {
                sb.append(definition.trim()).append(" ");
            }
        }
        // Time complexity: O(1)
        definitions.add(wordType + " " + sb);
        // Time complexity: Worst case O(log(n))
        csv.put(word,definitions);
    }
}
