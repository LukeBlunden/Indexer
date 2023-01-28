package ie.atu.sw;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Contains the data structure of the url and provides methods for updating and
 * receiving back this data
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class Url {
    private final Set<String> url;

    /**
     * Default constructor used when no filepath has been provided
     */
    public Url() {
        // Set to concurrent Set in case of SinglePageParser extending ConcurrentParser
        url = new ConcurrentSkipListSet<>();
    }

    /**
     * Constructor used in case of filepath being provided, attempts to parse url
     * at filepath.
     *
     * @param urlPath user provided url filepath
     * @throws FileNotFoundException if filepath does not point to a file
     */
    public Url(String urlPath) throws FileNotFoundException {
        url = new ConcurrentSkipListSet<>();
        parseUrl(new FileInputStream(urlPath));
    }

    /**
     * Gets user input of url, opens a connection and checks response code to ensure
     * data can be read from url, will parse url in case of 200 response.
     * If incorrect url is entered will return to the console options.
     */
    public void specifyURL(){
        try {
            HttpURLConnection connection;
            do {
                System.out.println("Enter URL");
                String getUrl = Menu.scanner.next();
                connection = (HttpURLConnection) new URL(getUrl).openConnection();
                int response = connection.getResponseCode();
                switch (response){
                    // IF any response other than 200 outputs the response message and asks again
                    case -1 -> System.out.println("Not valid HTTP");
                    case 200 -> {
                        InputStream inputStream = new URL(getUrl).openStream();
                        System.out.println("Valid URL. Parsing data...");
                        parseUrl(inputStream);
                    }
                    default -> System.out.println(response + " " + connection.getResponseMessage());
                }
            } while(connection.getResponseCode() != 200);
        } catch (MalformedURLException e){
            System.out.println("Malformed URL. Either no legal protocol could be found in a specification string or the string could not be parsed.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses the user specified url
     *
     * @param inputStream based off of user input
     */
    private void parseUrl(InputStream inputStream){
        new SinglePageParser(url).parse(inputStream);
    }

    /**
     * Returns the url data as a new Hashset
     * @return Set of the url data
     */
    public Set<String> getUrl(){
        // Time complexity: O(n)
        return new HashSet<>(url);
    }
}
