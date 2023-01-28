package ie.atu.sw;

import java.util.*;

/**
 * Extends WordDetail to include a Set of page numbers
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class BookWordDetail extends WordDetail {
    private final Set<Integer> pages;

    /**
     * Constructor taking in the pages data from a Book object and
     * converting to an ordered set
     *
     * @param definitions contains the List object from a Csv
     * @param pages contains a set of pages from a Book
     */
    public BookWordDetail(List<String> definitions, Set<Integer> pages){
        super(definitions);
        // Time complexity: O(n) to copy Set
        this.pages = new TreeSet<>(pages);
    }

    /**
     * Returns a copy of the pages
     *
     * @return pages a copy of the pages a word occurs on
     */
    protected Set<Integer> getPages(){
        // Time complexity: O(n) to copy Set
        return new TreeSet<>(pages);
    }
}
