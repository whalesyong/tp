package seedu.cookingaids.commands;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.parser.Parser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;

import static seedu.cookingaids.parser.Parser.*;

public class AddCommand {
    public static final String COMMAND_WORD = "add";
    private static final int SPACE = 1;
    private static final String INGREDIENT_SEPARATOR = ",";

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

    public static boolean isValidDate(String dateString) {
        switch(dateString){
        case "tdy","td","today","tomorrow","tmr","nxtwk":
            return true;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Adds a dish to the DishCalendar.
     *
     * @param receivedText The user input containing dish details.
     */
    public static void addDish(String receivedText) {
        try {
            if(receivedText.contains(RECIPE_FLAG) || receivedText.contains(INGREDIENT_FLAG)){
                System.out.println("Other commands found, I can only process one at a time");
                return;
            }
            receivedText = removeCommandWord(receivedText);
            String[] dishFields = Parser.parseDish(receivedText);

            assert dishFields != null : "Dish fields should not be null";
            assert dishFields.length == 2 : "Dish fields should contain exactly two elements";

            if (!dishFields[1].isEmpty() && !isValidDate(dishFields[1])) {
                throw new InvalidInputException();
            }

            Dish dish = new Dish(dishFields[0], dishFields[1]);
            DishCalendar.addDishToCalendar(dish);

            String date = dish.getDishDate().toString();
            assert date != null : "Dish date should not be null";

            if (date.equals("None") && !dishFields[1].isEmpty()) {
                System.out.println("Could not recognise date! Saving without date");
                System.out.println("Added Dish: " + dish.getName() + ", No scheduled date yet");
            } else if (date.equals("None")) {
                System.out.println("Added Dish: " + dish.getName() + ", No scheduled date yet");
            } else {
                System.out.println("Added Dish: " + dish.getName() + ", Scheduled for: " + date);
            }
        } catch (InvalidInputException e) {
            System.out.println("Invalid format. Use: add -dish=dish_name -when=YYYY/MM/DD " +
                    "\ndish name should be in lower_snake_case");

        }
    }

    /**
     * Adds a recipe to the RecipeBank.
     *
     * @param receivedText The user input containing recipe details.
     */
    public static void addRecipe(String receivedText) {

        try {
            if(receivedText.contains(DISH_FLAG) || receivedText.contains(INGREDIENT_FLAG)){
                System.out.println("Other commands found, I can only process one at a time");
                return;
            }
            receivedText = removeCommandWord(receivedText);
            String[] recipeFields = Parser.parseRecipe(receivedText);

            assert recipeFields != null : "Recipe fields should not be null";
            assert recipeFields.length == 2 : "Recipe fields should contain exactly two elements";

            String recipeName = recipeFields[0];
            String ingredientsString = recipeFields[1];

            ArrayList<Ingredient> ingredients = new ArrayList<>();

            if (!ingredientsString.isEmpty()) {
                String[] pairsArray = ingredientsString.split(",");

                // Check if we have an even number of elements (pairs of ingredient and quantity)
                if (pairsArray.length % 2 != 0) {
                    throw new InvalidInputException();
                }

                // Process pairs of ingredient name and quantity
                for (int i = 0; i < pairsArray.length; i += 2) {
                    String ingredientName = pairsArray[i].trim();
                    String quantityStr = pairsArray[i + 1].trim();

                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        // Assuming a static counter or method to generate IDs
                        Ingredient ingredient = new Ingredient(ingredientName, quantity);
                        ingredients.add(ingredient);
                    } catch (NumberFormatException e) {
                        throw new InvalidInputException();
                    }
                }
            } else {
                throw new InvalidInputException();
            }

            assert recipeName != null && !recipeName.isEmpty() : "Recipe name should not be empty";
            Recipe recipe = ingredients.isEmpty()
                    ? new Recipe(replaceSpaceWithUnderscore(recipeName))
                    : new Recipe(replaceSpaceWithUnderscore(recipeName), ingredients);

            RecipeBank.addRecipeToRecipeBank(recipe);

            System.out.println("Added Recipe: " + recipe.getName());
            System.out.println("Ingredients: " + recipe.getIngredientsString());
        } catch (InvalidInputException e) {

            System.out.println("Invalid format, recipe should have ingredients and quantities in pairs" +
                    " (use -needs=ingredient_1,quantity_1,ingredient_2,quantity_2)");
        }
    }

    /**
     * Parses the ingredients string into an ArrayList.
     *
     * @param ingredientsString The string containing ingredient details.
     * @return A list of parsed ingredient names.
     */
    private static ArrayList<String> parseIngredients(String ingredientsString) {
        ArrayList<String> ingredients = new ArrayList<>();
        if (!ingredientsString.isEmpty()) {
            String[] ingredientArray = ingredientsString.split(INGREDIENT_SEPARATOR);
            for (String ingredient : ingredientArray) {
                ingredients.add(ingredient.trim());
            }
        }
        return ingredients;
    }

    /**
     * Adds an ingredient to the IngredientStorage.
     *
     * @param receivedText The user input containing ingredient details.
     */
    public static void addIngredient(String receivedText) {
        try {
            String inputs = removeCommandWord(receivedText);
            HashMap<String, String> ingredientFields = Parser.parseIngredient(inputs);
            if (ingredientFields == null) {
                System.out.println("Invalid format. Use: add -ingredient=ingredient_name -expiry=YYYY/MM/DD " +
                        "-quantity=quantity, the only dashes should be for the flags");
                return;
            }
            assert ingredientFields.containsKey("ingredient") : "Missing 'ingredient' key";
            assert ingredientFields.containsKey("expiry_date") : "Missing 'expiry_date' key";
            assert ingredientFields.containsKey("quantity") : "Missing 'quantity' key";
            String ingredientName = ingredientFields.get("ingredient");
            if (ingredientName.isEmpty()) {
                throw new IllegalArgumentException("Ingredient name cannot be empty");
            }
            String expiryDate = ingredientFields.get("expiry_date");
            if (expiryDate.isEmpty()) {
                throw new IllegalArgumentException("Expiry date cannot be empty");
            }
            int quantity;
            try {
                quantity = Integer.parseInt(ingredientFields.get("quantity"));
                if (quantity < 0) {
                    throw new IllegalArgumentException("Quantity must be a positive integer");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantity must be a positive integer");
            }
            assert quantity > 0 : "Quantity should be greater than zero";
            Ingredient ingredient = new Ingredient(ingredientName, expiryDate, quantity);
            IngredientStorage.addToStorage(ingredient);
            System.out.println("Added Ingredient: " + ingredient);
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
}
