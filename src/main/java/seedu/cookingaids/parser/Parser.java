package seedu.cookingaids.parser;


import seedu.cookingaids.commands.ViewCommand;
import seedu.cookingaids.commands.AddCommand;
import seedu.cookingaids.commands.DeleteCommand;
import seedu.cookingaids.commands.ListCommand;
import seedu.cookingaids.commands.SuggestCommand;
import seedu.cookingaids.commands.UpdateCommand;
import seedu.cookingaids.commands.HelpCommand;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.ui.Ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds methods that manipulate user input into useful data
 * that can be understood by other methods.
 */
public class Parser {
    public static final String NEW_INGREDIENTS_FLAG = "-newingredients=";
    public static final String NEW_NAME_FLAG = "-newname=";
    private static final String UNKNOWN_COMMAND_STR = "Unknown command: %s";
    private static final String RECIPE_FLAG = "-recipe=";
    private static final String NEEDS_FLAG = "-needs=";
    private static final String DISH_FLAG = "-dish=";
    private static final String WHEN_FLAG = "-when=";
    private static final String INGREDIENT_FLAG = "-ingredient=";
    private static final String MONTH_FLAG = "-month=";
    private static final String YEAR_FLAG = "-year=";
    private static final int LENGTH_INGREDIENT_FLAG = 11;
    private static final int LENGTH_QUANTITY_FLAG = 9;
    private static final int LENGTH_EXPIRY_FLAG = 7;
    private static final int LENGTH_NEW_EXPIRY_FLAG = 11;

    /**
     * Deciphers the user's input for commands and executes the corresponding method.
     * If the command is not recognized, an error message is displayed.
     *
     * @param receivedText Entire command input received from the user.
     */
    public static void decipherCommand(String receivedText) {
        String command = receivedText.strip().split(" ")[0];

        switch (command) {
        case ListCommand.COMMAND_WORD -> handleDisplayCommand(receivedText);
        case AddCommand.COMMAND_WORD -> handleAddCommand(receivedText);
        case DeleteCommand.COMMAND_WORD -> handleDeleteCommand(receivedText);
        case UpdateCommand.COMMAND_WORD -> handleUpdateCommand(receivedText);
        case HelpCommand.COMMAND_WORD -> HelpCommand.showHelp();
        case SuggestCommand.COMMAND_WORD -> SuggestCommand.printSuggestions();
        case ViewCommand.COMMAND_WORD -> handleViewCommand(receivedText);
        default -> {
            System.out.println(String.format(UNKNOWN_COMMAND_STR, receivedText));
            System.out.println("Type \"help\" to see available commands.");
        }
        }
        Ui.printLineDivider();
    }

    private static void handleViewCommand(String receivedText) {
        if (!receivedText.contains(MONTH_FLAG)) {
            System.out.println("Missing month flag, try \"view -month=\"");
            return;

        }
        if (receivedText.contains(YEAR_FLAG)) {

            try {
                int year = extractYear(receivedText);
                int month = extractMonth(receivedText);
                ViewCommand.displayDishMonth(month, year);

            } catch (InvalidInputException e) {
                System.out.println(
                        "Invalid month or year input. Use: view -year={2015-2035} or leave blank for the current year."
                );
                System.out.println(
                        "Use: view -month={1-12} or leave blank for the current month.");

            }
        } else {


            try {
                int month = extractMonth(receivedText);
                ViewCommand.displayDishMonth(month); // Assuming ViewCommand has an overloaded method
            } catch (InvalidInputException e) {
                System.out.println(
                        "Invalid month input. Use: view -month={1-12} or leave blank for the current month.");
            }
        }

    }

    private static Integer extractMonth(String receivedText) throws InvalidInputException {
        Pattern pattern = Pattern.compile("-month=([\\w\\s]+)");
        Matcher matcher = pattern.matcher(receivedText);

        int month;
        if (matcher.find()) {
            if (matcher.group(1).isBlank()) {
                month = LocalDate.now().getMonthValue(); // Default to current month
            } else {

                try {
                    month = Integer.parseInt(matcher.group(1).trim());
                } catch (NumberFormatException e) {

                    throw new InvalidInputException();

                }
            }

        } else {
            month = LocalDate.now().getMonthValue(); // Default to current month
        }
        return month;
    }

    private static int extractYear(String receivedText) throws InvalidInputException {
        Pattern pattern = Pattern.compile("-year=([\\w\\s]+)");
        Matcher matcher = pattern.matcher(receivedText); //TODO

        int year;
        if (matcher.find()) {
            if (matcher.group(1).isBlank()) {
                year = LocalDate.now().getYear(); // Default to current year
            } else {

                try {
                    year = Integer.parseInt(matcher.group(1).trim());
                    if (year > 2035 || year < 2015) {
                        throw new InvalidInputException();
                    }
                } catch (NumberFormatException e) {

                    throw new InvalidInputException();
                }
            }

        } else {
            year = LocalDate.now().getYear(); // Default to current year
        }
        return year;
    }


    private static void handleDisplayCommand(String receivedText) {
        if (receivedText.contains("-recipe")) {
            ListCommand.displayRecipeBank();
        } else if (receivedText.contains("-dish")) {
            ListCommand.displayDishList(receivedText);
        } else if (receivedText.contains("-ingredient")) {
            ListCommand.displayIngredients();
        } else if (receivedText.contains("-shopping")) {
            ListCommand.displayShoppingList();
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

    private static void handleDeleteCommand(String receivedText) {
        if (receivedText.contains(RECIPE_FLAG)) {
            DeleteCommand.deleteRecipe(receivedText);
        } else if (receivedText.contains(DISH_FLAG) && receivedText.contains(WHEN_FLAG)) {
            DeleteCommand.deleteDishWithWhen(receivedText);
        } else if (receivedText.contains(DISH_FLAG)) {
            DeleteCommand.deleteDish(receivedText);
        } else if (receivedText.contains(WHEN_FLAG)) {
            DeleteCommand.deleteDishByWhen(receivedText);
        } else if (receivedText.contains(INGREDIENT_FLAG)) {
            DeleteCommand.deleteIngredient(receivedText);
        } else {
            System.out.println("Invalid delete command: " + receivedText);
        }
    }

    /**
     * Deciphers the user's input for commands and executes the corresponding method.
     * If the command is not recognized, an error message is displayed.
     *
     * @param receivedText Entire command input received from the user.
     */

    private static void handleUpdateCommand(String receivedText) {
        if (receivedText.contains(RECIPE_FLAG)) {
            UpdateCommand.updateRecipe(receivedText);
        } else if (receivedText.contains(INGREDIENT_FLAG)) {
            UpdateCommand.updateIngredient(receivedText);
        } else {
            System.out.println("Invalid update command: " + receivedText);
            System.out.println("Use 'update -recipe=INDEX -newname=NAME -newingredients=INGREDIENTS'");
        }
    }

    // Other existing methods...

    /**
     * Parses the recipe index from an update command.
     *
     * @param receivedText The update command string.
     * @return The recipe index as a string.
     */
    public static String parseRecipeIndexForUpdate(String receivedText) {
        if (!receivedText.contains(RECIPE_FLAG)) {
            return "";
        }
        int startIndex = receivedText.indexOf(RECIPE_FLAG) + RECIPE_FLAG.length();
        int endIndex = receivedText.indexOf(" ", startIndex);
        if (endIndex == -1) {
            endIndex = receivedText.length();
        }

        return receivedText.substring(startIndex, endIndex).trim();
    }

    /**
     * Parses the new name for a recipe from an update command.
     *
     * @param receivedText The update command string.
     * @return The new recipe name.
     */
    public static String parseNewNameForUpdate(String receivedText) {
        if (!receivedText.contains(NEW_NAME_FLAG)) {
            return "";
        }
        int startIndex = receivedText.indexOf(NEW_NAME_FLAG) + NEW_NAME_FLAG.length();
        int endIndex;

        if (receivedText.contains(NEW_INGREDIENTS_FLAG) &&
                receivedText.indexOf(NEW_INGREDIENTS_FLAG) > startIndex) {
            endIndex = receivedText.indexOf(NEW_INGREDIENTS_FLAG);
        } else {
            endIndex = receivedText.length();
        }

        return receivedText.substring(startIndex, endIndex).trim();
    }

    /**
     * Parses the new ingredients for a recipe from an update command.
     *
     * @param receivedText The update command string.
     * @return The new ingredients
     */
    public static String parseNewIngredientsForUpdate(String receivedText) {
        if (!receivedText.contains(NEW_INGREDIENTS_FLAG)) {
            return "";
        }
        int startIndex = receivedText.indexOf(NEW_INGREDIENTS_FLAG) + NEW_INGREDIENTS_FLAG.length();
        int endIndex;

        if (receivedText.contains(NEW_NAME_FLAG) &&
                receivedText.indexOf(NEW_NAME_FLAG) > startIndex) {
            endIndex = receivedText.indexOf(NEW_NAME_FLAG);
        } else {
            endIndex = receivedText.length();
        }

        // Return the raw string without trimming to preserve exact format
        return receivedText.substring(startIndex, endIndex);
    }

    public static String parseDishNameForDeletion(String receivedText) {
        if (!receivedText.contains(DISH_FLAG)) {
            return "";
        }
        int startIndex = receivedText.indexOf(DISH_FLAG) + DISH_FLAG.length();
        int endIndex = receivedText.indexOf(" ", startIndex);
        if (endIndex == -1) {
            endIndex = receivedText.length();
        }

        return receivedText.substring(startIndex, endIndex).trim();
    }

    public static String parseWhenForDeletion(String receivedText) {
        if (!receivedText.contains(WHEN_FLAG)) {
            return "";
        }
        int startIndex = receivedText.indexOf(WHEN_FLAG) + WHEN_FLAG.length();
        int endIndex = receivedText.indexOf(" ", startIndex);
        if (endIndex == -1) {
            endIndex = receivedText.length();
        }

        return receivedText.substring(startIndex, endIndex).trim();
    }

    public static String[] parseDishAndWhenForDeletion(String receivedText) {
        String dishName = parseDishNameForDeletion(receivedText);
        String when = parseWhenForDeletion(receivedText);
        return new String[]{dishName, when};
    }

    public static String parseIngredientForDeletion(String receivedText) {
        if (!receivedText.contains(INGREDIENT_FLAG)) {
            return "";
        }
        int startIndex = receivedText.indexOf(INGREDIENT_FLAG) + INGREDIENT_FLAG.length();
        int endIndex = receivedText.indexOf(" ", startIndex);
        if (endIndex == -1) {
            endIndex = receivedText.length();
        }

        return receivedText.substring(startIndex, endIndex).trim();
    }

    public static String parseRecipeForDeletion(String receivedText) {
        if (!receivedText.contains(RECIPE_FLAG)) {
            return "";
        }
        int startIndex = receivedText.indexOf(RECIPE_FLAG) + RECIPE_FLAG.length();
        int endIndex = receivedText.indexOf(" ", startIndex);
        if (endIndex == -1) {
            endIndex = receivedText.length();
        }

        return receivedText.substring(startIndex, endIndex).trim();
    }

    public static String[] parseDish(String input) throws InvalidInputException {
        boolean containsWhenFlag = input.contains("-when=");
        Pattern pattern = Pattern.compile("-dish=(\\S+)");
        if (containsWhenFlag) {
            pattern = Pattern.compile("-dish=(\\S+)\\s+-when=(\\S+)");
        }

        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            if (containsWhenFlag) {
                return new String[]{matcher.group(1), matcher.group(2)}; // Maintain index alignment
            }
            return new String[]{matcher.group(1), ""};
        } else {
            throw new InvalidInputException();
        }
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
                int recipeEndIndex = receivedText.contains(NEEDS_FLAG)
                        ? receivedText.indexOf(NEEDS_FLAG)
                        : receivedText.length();
                returnedArray[0] = receivedText.substring(recipeStartIndex, recipeEndIndex).trim();
            }
        } catch (Exception ignored) {
            System.out.println("exception encountered");
        }

        try {
            if (receivedText.contains(NEEDS_FLAG)) {
                int ingredientsStartIndex = receivedText.indexOf(NEEDS_FLAG) + NEEDS_FLAG.length();
                if (ingredientsStartIndex < receivedText.length()) {
                    returnedArray[1] = receivedText.substring(ingredientsStartIndex).trim();
                }
            }
        } catch (Exception ignored) {
            System.out.println("exception encountered");
        }

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
        // Split by "-" but keep the first part (command) intact
        String[] parts = command.split("-");
        for (String part : parts) {
            if (part.startsWith("ingredient=")) {
                String ingredient = part.substring(LENGTH_INGREDIENT_FLAG).trim();
                data.put("ingredient", ingredient);
            } else if (part.startsWith("quantity=")) {
                String quantity = part.substring(LENGTH_QUANTITY_FLAG).trim();
                data.put("quantity", quantity);
            } else if (part.startsWith("expiry=")) {
                String expiry = part.substring(LENGTH_EXPIRY_FLAG).trim();
                data.put("expiry_date", expiry);
            } else if (!part.isEmpty()) {
                return null;
            }
        }
        data.putIfAbsent("quantity", "1");
        data.putIfAbsent("expiry_date", "None");
        return data;
    }

    public static HashMap<String, String> parseIngredientUpdate(String command) {
        HashMap<String, String> data = new HashMap<>();
        // Split by "-" but keep the first part (command) intact
        String[] parts = command.split("-");
        for (String part : parts) {
            if (part.startsWith("ingredient=")) {
                String ingredient = part.substring(LENGTH_INGREDIENT_FLAG).trim();
                data.put("ingredient", ingredient);
            } else if (part.startsWith("quantity=")) {
                String quantity = part.substring(LENGTH_QUANTITY_FLAG).trim();
                data.put("quantity", quantity);
            } else if (part.startsWith("expiry=")) {
                String expiry = part.substring(LENGTH_EXPIRY_FLAG).trim();
                data.put("expiry_date", expiry);
            } else if (part.startsWith("new_expiry=")) {
                String newExpiry = part.substring(LENGTH_NEW_EXPIRY_FLAG).trim();
                data.put("new_expiry", newExpiry);
            } else if (!part.isEmpty()) {
                return null;
            }
        }
        data.putIfAbsent("quantity", "1");
        data.putIfAbsent("expiry_date", "None");
        data.putIfAbsent("new_expiry", "None");
        return data;
    }

    public static boolean isDate(String input) {
        return input.matches("\\d{4}-\\d{2}-\\d{2}") || input.matches("\\d{1,2}.*[A-Za-z]+.*");
    }
}
