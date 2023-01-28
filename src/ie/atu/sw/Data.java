package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * Provides methods for getting user input from the console, as well as being
 * an access layer to the Book, Url, StopWords and Csv data classes.
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class Data {
    private String outputFile;
    private boolean outputOrdered = true;
    private String inputType;

    private final Book book = new Book();
    private final Url url = new Url();
    private final StopWords stopWords = new StopWords();
    private final Csv dictionary = new Csv();

    /**
     * Gets and validates user input for specifying the type of file being parsed.
     * Based on input it passes responsibility to either the Book or Url class
     */
    public void textOrURL(){
        System.out.println("Index text or URL? [text/url]");
        String input;
        do {
            input = Menu.scanner.next().toLowerCase();
            switch (input) {
                case "text" -> book.specifyTextFile();
                case "url" -> url.specifyURL();
                default -> System.out.println("Invalid input. Please enter [text/url]");
            }
        } while(!(input.equals("text") || input.equals("url")));
        inputType = input;
    }

    /**
     * Sets and validates the users input .txt output file
     */
    public void setOutputFile(){
        // Requires the user to specify a .txt file to ensure correct output and formatting
        do {
            System.out.println("Enter output file [e.g. Index.txt]");
            outputFile = Menu.scanner.next();
        } while (outputFile.isEmpty() || !outputFile.endsWith(".txt"));
        System.out.println("Output file added");
        setOutputOrder();
    }

    /**
     * Gets the output file which has been validated to end in .txt
     *
     * @return outputFile ending in .txt
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * Sets the users desired ordering of the index (alphabetical or reversed)
     */
    private void setOutputOrder(){
        String input;
        do {
            System.out.println("Index in reverse alphabetical order? [y/n]");
            input = Menu.scanner.next().toLowerCase();
        } while (!(input.equals("y") || input.equals("n")));
        if (input.equals("y")) outputOrdered = false;
    }

    /**
     * Gets the index output ordering
     *
     * @return outputOrdered true if in alphabetical order
     */
    public boolean getOutputOrder() {
        return outputOrdered;
    }

    /**
     * Gets the input type which was set in the textOrUrl method
     *
     * @see #textOrURL()
     * @return inputType either text or url depending on user choice
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * Receives filepath as InputStream and forwards to dictionary to be parsed
     *
     * @param inputStream based on either user input or default dictionary
     */
    public void setDictionary(InputStream inputStream) {
        dictionary.parseDictionary(inputStream);
    }

    /**
     * Sets user specified dictionary filepath if not using default dictionary
     */
    public void setDictionary() {
        System.out.println("Enter dictionary file path");
        InputStream inputStream = getPath();
        setDictionary(inputStream);
    }

    /**
     * Gets the data from the Dictionary class
     *
     * @return Map of the parsed dictionary
     */
    public Map<String, List<String>> getDictionary() {
        return dictionary.getDictionary();
    }

    /**
     * Receives filepath as InputStream and forwards to stopWords to be parsed
     *
     * @param inputStream based on either user input or default stopWords
     */
    public void setStopWords(InputStream inputStream){
        stopWords.parseStopWords(inputStream);
    }

    /**
     * Sets user specified stopWords filepath if not using default dictionary
     */
    public void setStopWords() {
        System.out.println("Enter common words file path");
        InputStream inputStream = getPath();
        setStopWords(inputStream);
    }

    /**
     * Gets the data from the StopWords class
     *
     * @return Set of the parsed stopWords
     */
    public Set<String> getStopWords() {
        return stopWords.getStopWords();
    }

    /**
     * Gets the data from the Book class
     *
     * @return Map of the parsed book
     */
    public Map<String, Set<Integer>> getBook() {
        return book.getBook();
    }

    /**
     * Gets the data from the Url class
     *
     * @return Set of the parsed url
     */
    public Set<String> getUrl(){
        return url.getUrl();
    }

    /**
     * Helper class for getting input from user and converting to InputStream
     *
     * @return inputStream based on user input
     */
    // Helper class to get validate files input by user
    private InputStream getPath() {
        InputStream inputStream = null;
        while (inputStream == null){
            String temp = Menu.scanner.next();
            try {
                inputStream = new FileInputStream(temp);
            } catch (FileNotFoundException e) {
                System.out.println("Error: File not found");
            }
        }
        return inputStream;
    }
}
