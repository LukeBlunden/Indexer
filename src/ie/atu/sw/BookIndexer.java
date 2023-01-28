package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * Implements the methods of an indexer in relation to indexing a book
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class BookIndexer implements Indexer{
    // Implemented as TreeMap to allow for Map.descendingKeySet()
    private final TreeMap<String, BookWordDetail> index = new TreeMap<>();

    private Map<String, Set<Integer>> book;
    private Map<String, List<String>> dictionary;
    private Set<String> stopWords;

    /**
     * Constructor which takes in the data provided by output from the Book, Csv
     * and StopWords objects
     *
     * @param book Map from Book object
     * @param dictionary Map from Csv object
     * @param stopWords Set from StopWords object
     */
    public BookIndexer(Map<String, Set<Integer>> book, Map<String, List<String>> dictionary, Set<String> stopWords){
        this.book = book;
        this.dictionary = dictionary;
        this.stopWords = stopWords;
    }

    /**
     * Constructor which provides ability to instantiate a BookIndexer using String
     * filepaths
     *
     * @param bookPath filepath to a text file
     * @param dictionaryPath filepath to a csv file
     * @param stopWordsPath filepath to a common words text file
     */
    public BookIndexer(String bookPath, String dictionaryPath, String stopWordsPath){
        try {
            book = new Book(bookPath).getBook();
            dictionary = new Csv(dictionaryPath).getDictionary();
            stopWords = new StopWords(stopWordsPath).getStopWords();
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect file path/name, please check and try again.");
            System.out.println("Usage: new BookIndexer(bookPath, dictionaryPath, commonWordsPath");
        }
    }

    /**
     * Implements the index method to combine data from the book and dictionary
     * data (ignoring stop words) into an ordered index
     */
    @Override
    public void index(){
        System.out.println("Indexing started");
        // Time complexity: O(1) to retrieve keyset
        Set<String> words = book.keySet();
        // Time complexity: O(n log(n)), O(n) to iterate over Map, O(log(n)) to add to Map,
        // worst case O(log(n)) to get from Map (extra log(n)'s discarded)
        for (String word : words){
            // Check word against stop words
            if (stopWords.contains(word)) { continue; }
            // Check dictionary contains word
            if (!dictionary.containsKey(word)) { continue; }
            // Time complexity: worst case O(log(n)) (in this case O(1) as
            // dictionary and book are Hashmaps
            BookWordDetail bookWordDetail = new BookWordDetail(dictionary.get(word), book.get(word));
            // Time complexity: worst case O(log(n))
            index.put(word, bookWordDetail);
        }
        System.out.println("Indexing finished");
    }

    /**
     * Implements the printIndex method to output the ordered index to an
     * output file, including total unique word count, definitions for
     * words and pages they occur on.
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
                printWriter.printf("%-20s%s\n", "", "Pages");
                printWriter.printf("%-20s%s\n\n", "", index.get(key).getPages());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
