package ie.atu.sw;

import java.io.*;

/**
 * A SequentialParser implements the method to process each line of the provided
 * InputStream sequentially
 *
 * @author Luke Blunden
 * @version 1.0
 */
public abstract class SequentialParser implements Parser {
    /**
     * Implements the parse method by attempting to read in lines from the provided
     * InputStream and process each one sequentially
     *
     * @param input InputStream typically from a File or a URL
     */
    public final void parse(InputStream input){
        // Time complexity: O(n²) process each line n * process each word n = n²
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while((line = bufferedReader.readLine()) != null){
                lineProcess(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
