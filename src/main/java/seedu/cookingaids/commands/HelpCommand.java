package seedu.cookingaids.commands;

public class HelpCommand {

    public static final String COMMAND_WORD = "help";

    /**
     * Displays a list of available commands along with their descriptions.
     */
    public static void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  list - Displays all stored recipes, scheduled dishes and available ingredients");
        System.out.println("    Usage: list -recipe  (Displays all recipes)");
        System.out.println("           list -dish    (Displays all scheduled dishes)");
        System.out.println("           list -ingredient  (Displays all available ingredients)");
        System.out.println();
        System.out.println("  add - Adds a new recipe, dish or ingredient");
        System.out.println("    Usage: add -recipe=<recipe_name>   (Adds a new recipe)");
        System.out.println("           add -recipe=<recipe_name> -needs=ingredient1,ingredient2... (Adds a new recipe with ingredients)");
        System.out.println("           add -dish=<dish_name>       (Adds a new dish to the schedule)");
        System.out.println("           add -ingredient=<ingredient_name>   (Adds a new ingredient)");
        System.out.println();
        System.out.println("  remove - Removes a recipe, dish or ingredient");
        System.out.println("    Usage: remove -recipe=<recipe_name>   (Removes a recipe)");
        System.out.println("           remove -dish=<dish_name>       (Removes a recipe)");
        System.out.println("           remove -ingredient=<ingredient_name>   (Removes a recipe)");
        System.out.println();
        System.out.println("  help - Shows this help message");
        System.out.println("    Usage: help");
    }
}
