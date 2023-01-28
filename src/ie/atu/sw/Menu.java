package ie.atu.sw;

import java.util.*;

/**
 * Displays the console UI, facilitates the configuring of data and the creation of the output.
 *
 * @author Luke Blunden
 * @version 1.0
 */
public class Menu {
    protected static Scanner scanner = new Scanner(System.in);

    /**
     * Displays the console UI until the user quits
     */
    public void display() {
        Data data = new Data();
        Output output = new Output();
        ConsoleOptions co = new ConsoleOptions();
        boolean running = true;
        while (running){
            int choice = co.showOptions();
            switch (choice) {
                case 1 -> data.textOrURL();
                case 2 -> data.setDictionary();
                case 3 -> data.setStopWords();
                case 4 -> data.setOutputFile();
                case 5 -> output.execute(data);
                case 6 -> running = false;
                default -> System.out.println("Please enter number between 1-6");
            }
        }
    }
}

