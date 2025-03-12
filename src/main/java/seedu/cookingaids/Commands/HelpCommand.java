package seedu.cookingaids.Commands;

public class HelpCommand {

    public final static String COMMAND_WORD = "help";

    /**
     * Displays a list of available commands along with their descriptions.
     */
    public static void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  list - Displays all stored recipes and scheduled dishes");
        System.out.println("    Usage: list -recipe  (Displays all recipes)");
        System.out.println("           list -dish    (Displays all scheduled dishes)");
        System.out.println();
        System.out.println("  add - Adds a new recipe or dish");
        System.out.println("    Usage: add -recipe=<recipe_name>   (Adds a new recipe)");
        System.out.println("           add -dish=<dish_name>       (Adds a new dish to the schedule)");
        System.out.println();
        System.out.println("  help - Shows this help message");
        System.out.println("    Usage: help");
    }
}
