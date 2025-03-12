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
        } else {
            System.out.printf((UNKNOWN_COMMAND_STR) + "%n", receivedText);
        }
    }

    private static void handleAddCommand(String receivedText) {
        if (receivedText.contains(RECIPE_FLAG)) {
            AddCommand.addRecipe(receivedText);
        } else if (receivedText.contains(DISH_FLAG)) {
            AddCommand.addDish(receivedText);
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
        Pattern quantityPattern = Pattern.compile("-\\d+");

        for (String part : parts) {
            if (quantityPattern.matcher(part).matches()) {
                data.put("quantity", part.substring(1));
            } else if (part.startsWith("-")) {
                String key = isDate(part.substring(1)) ? "expiry_date" : "ingredient";
                data.put(key, part.substring(1));
            }
        }

        data.putIfAbsent("expiry_date", "None");
        return data;
    }

    /**
     * Checks if the input string could represent a date.
     *
     * @param input The string to check.
     * @return True if the string is a potential date, false otherwise.
     */
    public static boolean isDate(String input) {
        return input.matches("\\d{4}-\\d{2}-\\d{2}") || input.matches("\\d{1,2}.*[A-Za-z]+.*");
    }
}
