package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * Implements the methods of an indexer in relation to indexing a Url
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class UrlIndexer implements Indexer {
    // Implemented as TreeMap to allow for Map.descendingKeySet()
    private final TreeMap<String, WordDetail> index = new TreeMap<>();

    private Set<String> url;
    private Map<String, List<String>> dictionary;
    private Set<String> stopWords;

    /**
     * Constructor which takes in the data provided by output from the Url, Csv
     * and StopWords objects
     *
     * @param url Map from Url object
     * @param dictionary Map from Csv object
     * @param stopWords Set from StopWords object
     */
    public UrlIndexer(Set<String> url, Map<String, List<String>> dictionary, Set<String> stopWords){
        this.url = url;
        this.dictionary = dictionary;
        this.stopWords = stopWords;
    }

    /**
     * Constructor which provides ability to instantiate a UrlIndexer using String
     * filepaths and a url
     *
     * @param urlPath link to a url
     * @param dictionaryPath filepath to a csv file
     * @param stopWordsPath filepath to a common words text file
     */
    public UrlIndexer(String urlPath, String dictionaryPath, String stopWordsPath){
        try {
            url = new Url(urlPath).getUrl();
            dictionary = new Csv(dictionaryPath).getDictionary();
            stopWords = new StopWords(stopWordsPath).getStopWords();
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect file path/name, please check and try again.");
            System.out.println("Usage: new UrlIndexer(urlPath, dictionaryPath, commonWordsPath)");
        }
    }

    /**
     * Implements the index method to combine data from the url and dictionary
     * data (ignoring stop words) into an ordered index
     */
    @Override
    public void index() {
        System.out.println("Indexing started");
        // Time complexity: O(n log(n)), O(n) to iterate over Map, O(log(n)) to add to Map,
        // worst case O(log(n)) to get from Map (extra log(n)'s discarded)
        for (String word : url){
            // Check word against stop words
            if (stopWords.contains(word)) { continue; }
            // Check dictionary contains word
            if (!dictionary.containsKey(word)) { continue; }
            // Time complexity: worst case O(log(n)) (in this case O(1) as dictionary is a Hashmap
            WordDetail wordDetail = new WordDetail(dictionary.get(word));
            // Time complexity: worst case O(log(n))
            index.put(word, wordDetail);
        }
        System.out.println("Indexing finished");
    }

    /**
     * Implements the printIndex method to output the ordered index to an
     * output file, including total unique word count and definitions for words
     *
     * @param output name of the output file to print to
     * @param outputOrder the order in which the index should be printed
     */
    @Override
    public void printIndex(String output, boolean outputOrder) {
        try(PrintWriter printWriter = new PrintWriter(output)){
            Set<String> keys;
            if (outputOrder){
                // Time complexity: O(1) to retrieve keyset
                keys = index.keySet();
            } else {
                // Time complexity: O(1) to retrieve keyset
                keys = index.descendingKeySet();
            }
            printWriter.printf("%-20s%s\n\n", "Word (" + keys.size() + " unique)", "Word Detail");
            // Time complexity: O(n²) to iterate over keys and iterate over definitions in WordDetails
            for (String key : keys) {
                printWriter.printf("%-20s%s\n", key, "Definitions");
                for (String definition : index.get(key).getDefinitions()){
                    printWriter.printf("%-20s%s\n", "", definition);
                }
                printWriter.println();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
