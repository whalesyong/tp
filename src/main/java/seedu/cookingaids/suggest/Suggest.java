package seedu.cookingaids.suggest;

import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Suggest {

    /**
     * suggests recipes based on user's available ingredients
     *
     * @return List of available recipes
     */
    public static List<Recipe> suggestRecipes() {
        HashMap<String, List<Ingredient>> availableIngredientsMap = IngredientStorage.getStorage();
        ArrayList<Recipe> availableRecipes = RecipeBank.getRecipeBank();

        if (availableRecipes.isEmpty()) {
            System.out.println("There are no available recipes.");
            return new ArrayList<>();
        }

        ArrayList<String> availableIngredients = getAvailableIngredientNames(availableIngredientsMap.keySet());
        return findRecipesWithAvailableIngredients(availableRecipes, availableIngredients);

    }

    /**
     * Converts all ingredient names to lowercase for case-insensitive comparison.
     *
     * @param ingredientSet Set of ingredient names from storage
     * @return List of ingredient names in lowercase
     */
    private static ArrayList<String> getAvailableIngredientNames(Set<String> ingredientSet) {
        ArrayList<String> availableIngredients = new ArrayList<>();
        for (String ingredient : ingredientSet) {
            availableIngredients.add(ingredient.toLowerCase());
        }
        return availableIngredients;
    }

    /**
     * Finds recipes that can be made with the available ingredients.
     *
     * @param availableRecipes List of all available recipes
     * @param availableIngredients List of available ingredient names
     * @return List of recipes that can be made with available ingredients
     */
    private static List<Recipe> findRecipesWithAvailableIngredients(
            ArrayList<Recipe> availableRecipes,
            ArrayList<String> availableIngredients) {

        List<Recipe> suggestedRecipes = new ArrayList<>();

        for (Recipe recipe : availableRecipes) {
            checkRecipeIngredients(recipe, availableIngredients, suggestedRecipes);
        }

        return suggestedRecipes;
    }

    /**
     * Checks if a recipe can be made with available ingredients.
     * If all ingredients are available, adds the recipe to suggested recipes.
     * If not, prints the missing ingredients.
     *
     * @param recipe The recipe to check
     * @param availableIngredients List of available ingredient names
     * @param suggestedRecipes List to add recipes that can be made
     */
    private static void checkRecipeIngredients(
            Recipe recipe,
            ArrayList<String> availableIngredients,
            List<Recipe> suggestedRecipes) {

        ArrayList<Ingredient> neededIngredients = recipe.getIngredients();
        ArrayList<Ingredient> missingIngredients = new ArrayList<>();
        int availableCount = 0;

        for (Ingredient neededIngredient : neededIngredients) {
            if (availableIngredients.contains(neededIngredient.getName().toLowerCase())) {
                availableCount++;
            } else {
                missingIngredients.add(neededIngredient);
            }
        }

        if (availableCount == neededIngredients.size()) {
            suggestedRecipes.add(recipe);
        } else if (availableCount < neededIngredients.size()) {
            printMissingIngredients(recipe, missingIngredients);
        }
    }

    /**
     * Prints the missing ingredients for a recipe.
     *
     * @param recipe The recipe that's missing ingredients
     * @param missingIngredients List of missing ingredients
     */
    private static void printMissingIngredients(Recipe recipe, ArrayList<Ingredient> missingIngredients) {
        System.out.println("Not enough ingredients for " + recipe.getRecipeName() + "! Here's what you're missing: ");
        for (Ingredient ingredient : missingIngredients) {
            System.out.println(ingredient.getQuantity() + " " + ingredient.getName().toLowerCase());
        }
    }
}