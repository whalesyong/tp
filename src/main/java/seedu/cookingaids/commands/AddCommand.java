package seedu.cookingaids.commands;

import seedu.cookingaids.exceptions.InvalidInputException;
import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCommand {
    public static final String COMMAND_WORD = "add";
    static final int SPACE = 1;

    public static String removeCommandWord(String receivedText) {
        return receivedText.substring(COMMAND_WORD.length() + SPACE);
    }

    public static void addDish(String receivedText) {
        try {
            receivedText = removeCommandWord(receivedText);
            String[] dishFields = Parser.parseDish(receivedText);

            Dish dish = new Dish(dishFields[0], dishFields[1]);
            DishCalendar.addDishToCalendar(dish);

            String date = dish.getDishDate().toString();
            if (date.isEmpty()) {
                System.out.println("Added Dish: " + dish.getName() + ", No scheduled date yet");

            } else {
                System.out.println("Added Dish: "
                        + dish.getName() + ", Scheduled for: "
                        + dish.getDishDate().toString());
            }

        } catch (InvalidInputException e) {

            System.out.println("Invalid format. Use: add -dish=dish_name -when=YYYY-MM-DD");
        }
    }


    public static void addRecipe(String receivedText) {
        receivedText = removeCommandWord(receivedText);
        String[] recipeFields = Parser.parseRecipe(receivedText);

        String recipeName = recipeFields[0];
        String ingredientsString = recipeFields[1];

        // Process ingredients string into ArrayList
        ArrayList<String> ingredients = new ArrayList<>();
        if (!ingredientsString.isEmpty()) {
            String[] ingredientArray = ingredientsString.split(",");
            for (String ingredient : ingredientArray) {
                ingredients.add(ingredient.trim());
            }
        }

        // Create Recipe object
        Recipe recipe;
        if (ingredients.isEmpty()) {
            recipe = new Recipe(recipeName);
        } else {
            recipe = new Recipe(recipeName, ingredients);
        }

        // Add to RecipeBank
        RecipeBank recipeBank = new RecipeBank();
        RecipeBank.addRecipeToRecipeBank(recipe);

        // Print confirmation
        System.out.println("Added Recipe: " + recipeName);
        if (!ingredients.isEmpty()) {
            System.out.println("Ingredients: " + ingredients);
        } else {
            System.out.println("No ingredients specified.");
        }
    }

    public static void addIngredient(String receivedText) {
        String inputs = removeCommandWord(receivedText);
        HashMap<String, String> ingredientFields = Parser.parseIngredient(inputs);
        Ingredient ingredient = new Ingredient(
                1, ingredientFields.get("ingredient"),
                ingredientFields.get("expiry_date"),
                Integer.parseInt(ingredientFields.get("quantity"))
        );
        IngredientStorage.addToStorage(ingredient);
        System.out.println("Added Ingredient: " + ingredient);
    }
}
