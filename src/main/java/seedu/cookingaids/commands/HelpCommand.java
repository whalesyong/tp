package seedu.cookingaids.commands;


public class HelpCommand {

    public static final String COMMAND_WORD = "help";

    /**
     * Displays a list of available commands along with their descriptions.
     */
    public static void showHelp() {
        System.out.println("For help, visit our User Guide at:");
        System.out.println("https://ay2425s2-cs2113-t11b-1.github.io/tp/UserGuide.html");
    }
}
