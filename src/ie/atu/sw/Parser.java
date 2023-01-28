package ie.atu.sw;

import java.io.*;

/**
 * Defines the behaviour all parsers should implement
 *
 * @author Luke Blunden
 * @version 1.0
 */
public interface Parser {
    /**
     * A parser should be provided with an InputStream which it should attempt to
     * read line by line, and process each of these lines
     *
     * @param inputStream InputStream typically from a File or a URL
     */
    void parse(InputStream inputStream);

    /**
     * A parser should process a line by dividing into parts and processing each part.
     * Contains a default implementation splitting lines on characters not
     * including a-z or '
     *
     * @param line line to be divided into parts
     */
    default void lineProcess(String line) {
        // Time complexity: O(n) process each line n * process each word n = n²
        // Default implementation splits line on all characters not including a-z or '
        // ' are retained to preserve punctuation
        String[] words = line.toLowerCase().split("[^a-z']+");
        subLineProcess(words);
    }

    /**
     * A parser should process a String[] of parts of a line
     *
     * @param parts to be processed in some way
     */
    void subLineProcess(String[] parts);
}
