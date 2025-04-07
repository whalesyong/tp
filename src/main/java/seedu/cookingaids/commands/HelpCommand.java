package seedu.cookingaids.commands;

import java.util.logging.Logger;
import seedu.cookingaids.logger.LoggerFactory;

public class HelpCommand {
    public static final String COMMAND_WORD = "help";
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCommand.class);

    /**
     * Displays the help menu with available commands and their usage.
     * This function prints command categories such as add, list, view, update, delete, suggest, bye, and help,
     * and explains how to use each of them.
     */
    public static void showHelp() {
        LOGGER.info("Displaying help menu");
        System.out.println("Available commands:\n");

        // Add command
        System.out.println("  add - Adds a new recipe, dish or ingredient");
        System.out.println("    Usage: add -recipe=<recipe_name>   (Adds a new recipe)");
        System.out.println("           add -recipe=<recipe_name> -needs=ingredient1,quantity1,ingredient2,quantity2... "
                + "(Adds a new recipe with required ingredients)");
        System.out.println("           add -dish=<dish_name> -when=<date>      (Adds a new dish to the calendar)");
        System.out.println("           add -ingredient=<ingredient_name> -expiry=<expiryDate> -quantity=<quantity>   "
                + "(Adds a new ingredient to inventory)\n");

        // List command
        System.out.println("  list - Displays all stored recipes, scheduled dishes and available ingredients");
        System.out.println("    Usage: list -recipe       (Displays a list of available recipes)");
        System.out.println("           list -dish         (Displays a list of scheduled dishes sorted by month)");
        System.out.println("           list -ingredient   (Displays all available ingredients)\n");

        // View command
        System.out.println("  view - Displays dishes scheduled and shopping list");
        System.out.println("    Usage: view -month=<month_index>   (Displays a calendar showing your scheduled dishes)"
        );
        System.out.println("           view -shopping              " +
                "(Displays Shopping List showing ingredients to obtain for scheduled dishes)\n");

        // Update command
        System.out.println("  update - Edits a recipe or ingredient quantity");
        System.out.println("    Usage: update -recipe=<recipe_name> -name=<new_name> " +
                "-ingredients=<new_ingredient_1,quantity_1,new_ingredient_2,quantity_2> "
                + "(Updates available recipes and/or their required ingredients)");
        System.out.println("           update -ingredient=<ingredient_name> -quantity=<new_quantity> "
                + "(Updates available quantity of selected ingredient)\n");

        // Delete command
        System.out.println("  delete - Deletes a recipe, dish or ingredient");
        System.out.println("    Usage: delete -recipe=<recipe_name>      (Deletes a recipe)");
        System.out.println("           delete -dish=<dish_name>          (Removes a dish from the calendar)");
        System.out.println("           delete -ingredient=<ingredient_name>   " +
                "(Removes an ingredient and all its quantities from the storage)\n");

        // Suggest command
        System.out.println("  suggest - Suggests dishes based on available ingredients");
        System.out.println("    Usage: suggest\n");

        // Bye command
        System.out.println("  bye - Exits the program");
        System.out.println("    Usage: bye\n");

        // Help command
        System.out.println("  help - Shows this help message");
        System.out.println("    Usage: help\n");

        // User guide
        System.out.println("For more details, visit our User Guide at:");
        System.out.println("https://ay2425s2-cs2113-t11b-1.github.io/tp/UserGuide.html");
    }
}
