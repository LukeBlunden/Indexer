package ie.atu.sw;

import java.util.*;
import java.util.concurrent.*;

/**
 * A BookParser implements the method to process the words of a line and
 * add them to a data structure as well as keeping track of line and page
 * numbers of the FileInputStream
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class BookParser extends ConcurrentParser{
    private final Map<String,Set<Integer>> book;
    private int lineNo = 0;
    private int pageNo = 1;

    /**
     * Constructor receiving the implemented Map to update from a Book object
     *
     * @param book data in a Book object
     */
    public BookParser(Map<String,Set<Integer>> book) {
        this.book = book;
    }

    /**
     * Implements the subLineProcess method to add words and page numbers to
     * the book data structure as well as updating the line number
     *
     * @param parts each word in the line of a book
     */
    @Override
    public void subLineProcess(String[] parts){
        // Time complexity: Worst case O(n log(n)) to process each word in line O(n)
        // and potentially O(log(n)) for Set.add and Map.put
        lineNo++;
        // Assumes 1 page = 40 lines
        if (lineNo % 40 == 0) pageNo++;
        // Time complexity: O(n)
        for (String word : parts) {
            // Removes leading and trailing ' which are present from preserving them
            // Which are present from preserving punctuation in default lineProcess method
            word = word.replaceAll("^'|'$", "");
            if(word.equals("")) continue;
            Set<Integer> pages = new ConcurrentSkipListSet<>();
            if(book.containsKey(word)) pages = book.get(word);
            // Time complexity: Worst case O(log(n))
            pages.add(pageNo);
            // Time complexity: Worst case O(log(n))
            book.put(word, pages);
        }
    }
}
