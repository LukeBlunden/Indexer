package ie.atu.sw;

/**
 * Defines the behaviour all indexers should implement
 *
 * @author Luke Blunden
 * @version 1.0
 */
public interface Indexer {
    /**
     * An indexer should index something
     */
    void index();

    /**
     * An indexer should be able to print the index to a file
     *
     * @param output name of the output file to print to
     * @param outputOrder the order in which the index should be printed
     */
    void printIndex(String output, boolean outputOrder);
}
