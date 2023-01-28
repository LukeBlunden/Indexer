package ie.atu.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Contains the data structure of the book and provides methods for updating and
 * receiving back this data
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class Book {
    private final Map<String, Set<Integer>> book;

    /**
     * Default constructor used when no filepath has been provided
     */
    public Book(){
        book = new ConcurrentHashMap<>();
    }

    /**
     * Constructor used in case of filepath being provided, attempts to parse book
     * at filepath.
     *
     * @param bookPath user provided text filepath
     * @throws FileNotFoundException if filepath does not point to a file
     */
    public Book(String bookPath) throws FileNotFoundException {
        book = new ConcurrentHashMap<>();
        parseBook(new FileInputStream(bookPath));
    }

    /**
     * Gets user input of text filepath, will keep requesting input until an actual
     * file is input.
     * Will parse text once file has been correctly input.
     */
    public void specifyTextFile() {
        File file;
        String textFile;
        do {
            System.out.println("Enter text file with directory [e.g. ./Book.txt]");
            textFile = Menu.scanner.next();
            file = new File(textFile);
            if(!file.exists()) System.out.println("File not found");
        } while (!file.exists());
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Text file found. Parsing data...");
        parseBook(inputStream);
    }

    /**
     * Parses the text based off of user specified filepath
     *
     * @param inputStream based off of user input
     */
    private void parseBook(InputStream inputStream){
        new BookParser(book).parse(inputStream);
    }

    /**
     * Returns the text data as a new Hashmap
     *
     * @return Map of the book data
     */
    public Map<String, Set<Integer>> getBook(){
        // Time complexity: O(n)
        return new HashMap<>(book);
    }
}
