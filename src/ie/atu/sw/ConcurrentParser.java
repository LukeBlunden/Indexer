package ie.atu.sw;

import java.io.*;
import java.util.concurrent.Executors;

/**
 * A ConcurrentParser implements the method to process each line of the provided
 * InputStream concurrently
 *
 * @author Luke Blunden
 * @version 1.0
 */
public abstract class ConcurrentParser implements Parser {

    /**
     * Implements the parse method by attempting to read in lines from provided
     * InputStream and process each line as an individual virtual thread using
     * an executor service.
     *
     * @param inputStream InputStream typically from a File or a URL
     */
    public final void parse(InputStream inputStream) {
        // Time complexity: O(n²) process each line n * process each word n = n²
        try(
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                var executorService = Executors.newVirtualThreadPerTaskExecutor()
        ){
            String line;
            while((line = bufferedReader.readLine()) != null){
                var l = line;
                executorService.execute(() -> lineProcess(l));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
