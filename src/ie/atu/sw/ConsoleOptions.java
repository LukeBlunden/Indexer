package ie.atu.sw;

/**
 * Display options to the console for user to select from
 *
 * @author Luke Blunden & John Healy
 * @version 1.0
 */
public class ConsoleOptions {
    /**
     * displays options for user to select from
     *
     * @return int corresponding to user choice
     */
    public int showOptions(){
        System.out.println(ConsoleColour.WHITE);
        System.out.println("************************************************************");
        System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
        System.out.println("*                                                          *");
        System.out.println("*              Virtual Threaded Text Indexer               *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        System.out.println("(1) Specify Text or URL");
        System.out.println("(2) Configure Dictionary [Default: ./dictionary.csv]");
        System.out.println("(3) Configure Common Words [Default: ./google-1000.txt]");
        System.out.println("(4) Specify Output File");
        System.out.println("(5) Execute");
        System.out.println("(6) Quit");

        System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
        System.out.print("Select Option [1-6]>");
        System.out.println();

        return getOption();
    }

    /**
     * Gets the desired option from the user
     *
     * @return in corresponding to user choice
     */
    private int getOption(){
        int choice;
        // Validates that input is between 1-6 before returning
        do{
            while (!Menu.scanner.hasNextInt()){
                System.out.println("Please enter number between 1-6");
                Menu.scanner.next();
            }
            choice = Menu.scanner.nextInt();
            if (choice < 1 || choice > 6){
                System.out.println("Please enter number between 1-6");
            }
        } while (choice < 1 || choice > 6);
        return choice;
    }
}
