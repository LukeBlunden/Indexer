package ie.atu.sw;

/**
 * Console interface to get a user specified text file or Url, a dictionary
 * and a list of common words and output them to an ordered index with
 * definitions
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class Runner {
    public static void main(String[] args) {
        new Menu().display();
    }
}
