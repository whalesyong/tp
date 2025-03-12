package seedu.cookingaids.Parser;

import seedu.cookingaids.Commands.AddCommand;
import seedu.cookingaids.Commands.DisplayCommand;
import seedu.cookingaids.Commands.HelpCommand;
import seedu.cookingaids.Ui.Ui;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Holds methods that manipulate user input into useful data
 * that can be understood by other methods.
 */
public class Parser {
    private static final String UNKNOWN_COMMAND_STR = "Unknown command: %s";
    private static final String RECIPE_FLAG = "-recipe=";
    private static final String CALLS_FOR_FLAG = "-callsfor=";
    private static final String DISH_FLAG = "-dish=";
    private static final String INGREDIENT_FLAG = "-ingredient=";

    /**
     * Deciphers the user's input for commands and executes the corresponding method.
     * If the command is not recognized, an error message is displayed.
     *
     * @param receivedText Entire command input received from the user.
     */
    public static void decipherCommand(String receivedText) {
        String command = receivedText.strip().split(" ")[0];

        switch (command) {
        case DisplayCommand.COMMAND_WORD -> handleDisplayCommand(receivedText);
        case AddCommand.COMMAND_WORD -> handleAddCommand(receivedText);
        case HelpCommand.COMMAND_WORD -> HelpCommand.showHelp();
        default -> {
            System.out.println(String.format(UNKNOWN_COMMAND_STR, receivedText));
            System.out.println("Type \"help\" to see available commands.");
        }
        }
        Ui.printLineDivider();
    }
  
    private static void handleDisplayCommand(String receivedText) {
        if (receivedText.contains("-recipe")) {
            DisplayCommand.displayRecipeBank();
        } else if (receivedText.contains("-dish")) {
            DisplayCommand.displayDishCalendar();
        } else if (receivedText.contains("-ingredient")) {
            DisplayCommand.displayIngredients();
        } else {
            System.out.printf((UNKNOWN_COMMAND_STR) + "%n", receivedText);
        }
    }

    private static void handleAddCommand(String receivedText) {
        if (receivedText.contains(RECIPE_FLAG)) {
            AddCommand.addRecipe(receivedText);
        } else if (receivedText.contains(DISH_FLAG)) {
            AddCommand.addDish(receivedText);
        } else if (receivedText.contains(INGREDIENT_FLAG)) {
            AddCommand.addIngredient(receivedText);
        } else {
            System.out.println("I DO NOT UNDERSTAND " + receivedText);
        }
    }

    public static String[] parseDish(String receivedText) {
        String[] returnedArray = {"1", "none", "none"};
        String[] parts = receivedText.split(" ");

        if (parts.length > 0) {
            returnedArray[1] = parts[0];
        }
        if (parts.length > 1) {
            returnedArray[2] = parts[1];
        }
        return returnedArray;
    }

    /**
     * Parses a recipe input string and extracts the recipe name and ingredients.
     *
     * @param receivedText Input string containing recipe details.
     * @return An array with two elements: the recipe name and ingredients string.
     */
    public static String[] parseRecipe(String receivedText) {
        String[] returnedArray = {"none", ""};

        try {
            if (receivedText.contains(RECIPE_FLAG)) {
                int recipeStartIndex = receivedText.indexOf(RECIPE_FLAG) + RECIPE_FLAG.length();
                int recipeEndIndex = receivedText.contains(CALLS_FOR_FLAG)
                        ? receivedText.indexOf(CALLS_FOR_FLAG)
                        : receivedText.length();
                returnedArray[0] = receivedText.substring(recipeStartIndex, recipeEndIndex).trim();
            }
        } catch (Exception ignored) {}

        try {
            if (receivedText.contains(CALLS_FOR_FLAG)) {
                int ingredientsStartIndex = receivedText.indexOf(CALLS_FOR_FLAG) + CALLS_FOR_FLAG.length();
                if (ingredientsStartIndex < receivedText.length()) {
                    returnedArray[1] = receivedText.substring(ingredientsStartIndex).trim();
                }
            }
        } catch (Exception ignored) {}

        return returnedArray;
    }

    /**
     * Parses an ingredient command into a HashMap containing quantity, expiry date, and ingredient name.
     *
     * @param command The ingredient command string.
     * @return A HashMap with keys "quantity", "expiry_date", and "ingredient".
     */
    public static HashMap<String, String> parseIngredient(String command) {
        HashMap<String, String> data = new HashMap<>();
        String[] parts = command.split(" ");
        Pattern quantityPattern = Pattern.compile("\\d+"); // Matches positive numbers
        for (String part : parts) {
            if (part.startsWith("-ingredient=")) {
                data.put("ingredient", part.substring(11).trim());
            } else if (part.startsWith("-quantity=")) {
                String quantity = part.substring(9).trim();
                if (quantityPattern.matcher(quantity).matches()) {
                    data.put("quantity", quantity);
                } else {
                    System.out.println("Invalid quantity format.");
                    return null; // Exit early if quantity is invalid
                }
            } else if (part.startsWith("-expiry=")) {
                data.put("expiry_date", part.substring(8).trim());
            }
        }
        // Default values
        data.putIfAbsent("quantity", "1");
        data.putIfAbsent("expiry_date", "None");
        return data;
    }




}
