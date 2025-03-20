package seedu.cookingaids.commands;

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

    public static void addDish(String receivedText) {
        receivedText = receivedText.substring(COMMAND_WORD.length() + SPACE);
        String[] dishFields = Parser.parseDish(receivedText);
        Dish dish = new Dish(Integer.parseInt(dishFields[0]), dishFields[1], dishFields[2]);
        DishCalendar.addDishToCalendar(dish);
        System.out.println("Added Dish: " + dish.getName() + ", Scheduled for: " + dish.getDishDate().toString());
    }

    public static void addDishWithWhen(String receivedText) {
        String[] parsedDish = Parser.parseDish(receivedText);

        String dishName = parsedDish[1];
        String date = parsedDish[2];

        if (date.equals("none")) {
            System.out.println("Invalid format. Use: add dish_name -when=YYYY-MM-DD");
            return;
        }

        int newId = DishCalendar.generateNewDishId();
        Dish newDish = new Dish(newId, dishName, date);
        DishCalendar.addDishToCalendar(newDish);

        System.out.println(date + " - " + dishName + " successfully added!");
    }

    public static void addRecipe(String receivedText) {
        receivedText = receivedText.substring(COMMAND_WORD.length() + SPACE);
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
        String inputs = receivedText.substring(COMMAND_WORD.length() + SPACE);
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
