package seedu.cookingaids.commands;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.ShoppingList;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.logger.LoggerFactory;
import seedu.cookingaids.storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.cookingaids.parser.Parser.RECIPE_FLAG;
import static seedu.cookingaids.parser.Parser.INGREDIENT_FLAG;
import static seedu.cookingaids.parser.Parser.parseDish;
import static seedu.cookingaids.parser.Parser.parseIngredient;
import static seedu.cookingaids.parser.Parser.parseRecipe;

public class AddCommand {
    public static final String COMMAND_WORD = "add";
    private static final int SPACE = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddCommand.class);

    /**
     * Removes the command word from the received input string.
     *
     * @param receivedText The full command input from the user.
     * @return The input string without the command word.
     */
    public static String removeCommandWord(String receivedText) {
        assert receivedText != null : "Received text should not be null";
        assert receivedText.length() > COMMAND_WORD.length() + SPACE : "Received text is too short to process";

        return receivedText.substring(COMMAND_WORD.length() + SPACE);
    }
  
    /**
     * Adds a dish to the DishCalendar.
     *
     * @param receivedText The user input containing dish details.
     */
    public static void addDish(String receivedText) {
        try {
            if (containsOtherFlags(receivedText)) return;

            String[] dishFields = parseDish(removeCommandWord(receivedText));
            validateDishFields(dishFields);

            Dish dish = new Dish(dishFields[0], dishFields[1]);
            DishCalendar.addDishToCalendar(dish);

            printDishConfirmation(dish, dishFields[1]);
            persistData();
        } catch (InvalidInputException e) {
            System.out.println("Invalid format. Use: add -dish=dish_name -when=YYYY/MM/DD " +
                    "\ndish name should be in lower_snake_case");
    }

    /**
     * Adds a recipe to the RecipeBank.
     *
     * @param receivedText The user input containing recipe details.
     */
    public static void addRecipe(String receivedText) {
        try {
            // Check for conflicting flags and ignore the request if they're present
            if (containsOtherFlags(receivedText)) return;

            // Parse the recipe and its ingredients
            String[] recipeFields = parseRecipe(removeCommandWord(receivedText));
            validateRecipeFields(recipeFields);

            // Process ingredients if they exist
            ArrayList<Ingredient> ingredients = parseIngredientPairs(recipeFields[1]);
            Recipe recipe = createRecipe(recipeFields[0], ingredients);\
              
            // Add recipe to the recipe bank and print confirmation
            RecipeBank.addRecipeToRecipeBank(recipe);
            System.out.println("Added Recipe: " + recipeFields[0]);
            System.out.println("Ingredients: " + ingredients);

            // Persist data to storage
            persistData();
        } catch (InvalidInputException e) {
            System.out.println("Invalid format, recipe should have ingredients and quantities in pairs" +
                    " (use -needs=ingredient_1,quantity_1,ingredient_2,quantity_2)");
        }
    }

    /**
     * Adds an ingredient to the IngredientStorage.
     *
     * @param receivedText The user input containing ingredient details.
     */
    public static void addIngredient(String receivedText) {
        try {
            HashMap<String, String> fields = parseIngredient(removeCommandWord(receivedText));
            validateIngredientFields(fields);

            Ingredient ingredient = new Ingredient(fields.get("ingredient"),
                    fields.get("expiry_date"),
                    Integer.parseInt(fields.get("quantity")));

            IngredientStorage.addToStorage(ingredient);
            System.out.println("Added Ingredient: " + ingredient);
            persistData();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Replaces spaces in a string with underscores.
     *
     * @param input The input string.
     * @return The modified string with spaces replaced by underscores.
     */
    private static String replaceSpaceWithUnderscore(String input) {
        return input.replace(" ", "_");
    }

    private static boolean containsOtherFlags(String input) {
        if (input.contains(RECIPE_FLAG) && input.contains(INGREDIENT_FLAG)) {
            System.out.println("Other commands found, I can only process one at a time");
            return true;
        }
        return false;
    }

    private static void validateDishFields(String[] fields) throws InvalidInputException {
        assert fields.length == 2 : "Dish fields must contain 2 elements";
        if (!fields[1].isEmpty() && !isValidDate(fields[1])) throw new InvalidInputException();
    }

    private static void printDishConfirmation(Dish dish, String userInputDate) {
        String date = dish.getDishDate().toString();
        if (date.equals("None") && !userInputDate.isEmpty()) {
            System.out.println("Could not recognise date! Saving without date");
        }
        System.out.println("Added Dish: " + dish.getName() + ", " +
                (date.equals("None") ? "No scheduled date yet" : "Scheduled for: " + date));
    }

    private static void validateRecipeFields(String[] fields) throws InvalidInputException {
        assert fields != null && fields.length == 2 : "Recipe fields must contain 2 elements";
        if (fields[0].isEmpty() || fields[1].isEmpty()) throw new InvalidInputException();
    }

    private static ArrayList<Ingredient> parseIngredientPairs(String input) throws InvalidInputException {
        String[] parts = input.split(",");
        if (parts.length % 2 != 0) throw new InvalidInputException();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < parts.length; i += 2) {
            try {
                ingredients.add(new Ingredient(parts[i].trim(), Integer.parseInt(parts[i + 1].trim())));
            } catch (NumberFormatException e) {
                throw new InvalidInputException();
            }
        }
        return ingredients;
    }

    private static Recipe createRecipe(String name, ArrayList<Ingredient> ingredients) {
        String formattedName = replaceSpaceWithUnderscore(name);
        return ingredients.isEmpty()
                ? new Recipe(formattedName)
                : new Recipe(formattedName, ingredients);
    }

    private static void validateIngredientFields(HashMap<String, String> fields) {
        if (fields == null || !fields.containsKey("ingredient") || !fields.containsKey("expiry_date")
                || !fields.containsKey("quantity")) {
            throw new IllegalArgumentException("Invalid format. Use: add -ingredient=... -expiry=... -quantity=...");
        }

        String name = fields.get("ingredient");
        String expiry = fields.get("expiry_date");
        int quantity;

        if (name.isEmpty()) throw new IllegalArgumentException("Ingredient name cannot be empty");
        if (expiry.isEmpty() || !isValidDate(expiry)) throw new IllegalArgumentException("Expiry date is invalid");

        try {
            quantity = Integer.parseInt(fields.get("quantity"));
            if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a positive integer");
        }
    }

    private static void persistData() {
        LOGGER.info("Saving to file now");
        Storage.storeData(DishCalendar.getDishCalendar(),
                RecipeBank.getRecipeBank(),
                IngredientStorage.getStorage(),
                ShoppingList.getShoppingList());
    }

    public static boolean isValidDate(String dateString) {
        switch (dateString) {
        case "tdy", "td", "today", "tomorrow", "tmr":
            return true;
        default:
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd")
                        .withResolverStyle(ResolverStyle.STRICT);
                LocalDate.parse(dateString, formatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
    }
}
