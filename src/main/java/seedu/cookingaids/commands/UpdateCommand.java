package seedu.cookingaids.commands;

import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.parser.Parser;

import seedu.cookingaids.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateCommand {

    public static final String COMMAND_WORD = "update";
    private static final int SPACE = 1;

    /**
     * Updates a recipe in the recipe bank.
     *
     * @param receivedText The user input containing the recipe index and update details.
     */
    public static void updateRecipe(String receivedText) {
        String recipeIndex = Parser.parseRecipeIndexForUpdate(receivedText);

        try {
            int index = Integer.parseInt(recipeIndex) - 1;
            ArrayList<Recipe> recipeBank = RecipeBank.getRecipeBank();

            if (index < 0 || index >= recipeBank.size()) {
                System.out.println("Invalid recipe index! Please provide a valid index.");
                return;
            }

            Recipe recipe = recipeBank.get(index);

            // Check what needs to be updated
            if (receivedText.contains(Parser.NEW_NAME_FLAG)) {
                String newName = Parser.parseNewNameForUpdate(receivedText);
                recipe.setRecipeName(newName);
                System.out.println("Recipe name updated to: " + newName);
            }

            if (receivedText.contains(Parser.NEW_INGREDIENTS_FLAG)) {
                try {
                    String ingredientsStr = Parser.parseNewIngredientsForUpdate(receivedText);
                    ArrayList<Ingredient> ingredients = parseIngredientsString(ingredientsStr);
                    recipe.setIngredients(ingredients);
                    System.out.println("Recipe ingredients updated successfully!");
                } catch (InvalidInputException e) {
                    System.out.println("Invalid input!");
                }
            }

            if (!receivedText.contains(Parser.NEW_NAME_FLAG) && !receivedText.contains(Parser.NEW_INGREDIENTS_FLAG)) {
                System.out.println("No updates specified. Use -newname= or -newingredients= flags.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid recipe index number!");
        }
    }

    /**
     * Parses a string of ingredients into a list of Ingredient objects.
     * Format: ingredient1,quantity1,ingredient2,quantity2,...
     *
     * @param ingredientsStr The string containing ingredients in format (name,quantity) without spaces.
     * @return An ArrayList of Ingredient objects.
     */
    private static ArrayList<Ingredient> parseIngredientsString(String ingredientsStr) throws InvalidInputException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        String[] parts = ingredientsStr.split(",");

        // Check if there's an even number of elements
        if (parts.length % 2 != 0) {
            throw new InvalidInputException();
        }

        // Process pairs of name and quantity
        for (int i = 0; i < parts.length; i += 2) {
            String name = parts[i];
            String quantity = parts[i + 1];
            try {
                int quantityValue = Integer.parseInt(quantity);
                ingredients.add(new Ingredient(name, quantityValue));
            } catch (NumberFormatException e) {
                // If quantity is not a valid number, throw an exception
                throw new InvalidInputException();
            }
        }

        return ingredients;
    }

    /**
     * Removes the command word from the received input text.
     *
     * @param receivedText The full command input from the user.
     * @return The input text without the command word.
     */
    public static String removeCommandWord(String receivedText) {
        return receivedText.substring(COMMAND_WORD.length() + SPACE);
    }
}