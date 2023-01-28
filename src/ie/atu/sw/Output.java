package ie.atu.sw;

import java.io.*;

/**
 * Takes data and validates that each part necessary for indexing is present.
 * If data is not present it will either use a default or request it from user.
 * Once data is validated it will create an Indexer based on the data type,
 * index the data and output the index.
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class Output {
    /**
     * Takes and validates data and outputs the index. The index is only passed the
     * data from the data objects (Book/Url, Csv, StopWords)
     *
     * @param data Data object containing Book/Url, Csv and StopWords
     */
    public void execute(Data data){
        // Validates that all necessary components have been added
        // (Input file/URL, dictionary, stop wordsm output file)
        if(data.getInputType() == null){
            System.out.println("Please specify input");
            data.textOrURL();
        }
        while(data.getDictionary().isEmpty()){
            // Will attempt to use default dictionary file path to parse dictionary
            try {
                data.setDictionary(new FileInputStream("./dictionary.csv"));
            } catch (FileNotFoundException e) {
                System.out.println("Default file ./dictionary.csv not found.");
                data.setDictionary();
            }
        }
        while(data.getStopWords().isEmpty()){
            // Will attempt to use default dictionary file path to parse stop words
            try{
                data.setStopWords(new FileInputStream("./google-1000.txt"));
            } catch (FileNotFoundException e) {
                System.out.println("Default file ./google-1000.txt not found.");
                data.setStopWords();
            }
        }
        if (data.getOutputFile() == null) data.setOutputFile();

        Indexer indexer;
        // Creates parser based on whether input type was text or url
        if (data.getInputType().equals("text")){
            indexer = new BookIndexer(data.getBook(), data.getDictionary(), data.getStopWords());
        } else {
            indexer = new UrlIndexer(data.getUrl(), data.getDictionary(), data.getStopWords());
        }
        indexer.index();
        indexer.printIndex(data.getOutputFile(), data.getOutputOrder());
    }
}
