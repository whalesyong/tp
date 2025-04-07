package seedu.cookingaids.suggest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.logger.LoggerFactory;
import seedu.cookingaids.ui.Ui;

public class Suggest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Suggest.class);
    private static final String MESSAGE_NO_AVAILABLE_RECIPES = "There are no available recipes.";
    private static final String MESSAGE_MISSING_INGREDIENTS_PREFIX = "Not enough ingredients for ";
    private static final String MESSAGE_MISSING_INGREDIENTS_SUFFIX = "! Here's what you're missing: ";

    /**
     * suggests recipes based on user's available ingredients
     *
     * @return List of available recipes
     */
    public static List<Recipe> suggestRecipes() {
        LOGGER.info("Starting recipe suggestion process");
        HashMap<String, List<Ingredient>> availableIngredientsMap = IngredientStorage.getStorage();
        ArrayList<Recipe> availableRecipes = RecipeBank.getRecipeBank();

        if (availableRecipes.isEmpty()) {
            LOGGER.warning("No recipes available in RecipeBank");
            Ui.printItems(MESSAGE_NO_AVAILABLE_RECIPES);
            return new ArrayList<>();
        }

        LOGGER.log(Level.FINE, "Found {0} available ingredients", availableIngredientsMap.size());
        return findRecipesWithAvailableIngredients(availableRecipes, availableIngredientsMap);
    }

    /**
     * Finds recipes that can be made with the available ingredients.
     *
     * @param availableRecipes List of all available recipes
     * @param availableIngredientsMap Map of available ingredients with their quantities
     * @return List of recipes that can be made with available ingredients
     */
    private static List<Recipe> findRecipesWithAvailableIngredients(
            ArrayList<Recipe> availableRecipes,
            HashMap<String, List<Ingredient>> availableIngredientsMap) {

        List<Recipe> suggestedRecipes = new ArrayList<>();
        LOGGER.log(Level.FINE, "Checking {0} recipes for matches", availableRecipes.size());

        for (Recipe recipe : availableRecipes) {
            checkRecipeIngredients(recipe, availableIngredientsMap, suggestedRecipes);
        }

        LOGGER.log(Level.INFO, "Found {0} suggested recipes", suggestedRecipes.size());
        return suggestedRecipes;
    }

    /**
     * Checks if a recipe can be made with available ingredients and their quantities.
     * If all ingredients are available in sufficient quantities, adds the recipe to suggested recipes.
     * If not, prints the missing ingredients or insufficient quantities.
     *
     * @param recipe The recipe to check
     * @param availableIngredientsMap Map of available ingredients with their quantities
     * @param suggestedRecipes List to add recipes that can be made
     */
    private static void checkRecipeIngredients(
            Recipe recipe,
            HashMap<String, List<Ingredient>> availableIngredientsMap,
            List<Recipe> suggestedRecipes) {

        LOGGER.log(Level.FINE, "Checking ingredients for recipe: {0}", recipe.getRecipeName());
        ArrayList<Ingredient> neededIngredients = recipe.getIngredients();
        ArrayList<Ingredient> missingIngredients = new ArrayList<>();
        boolean canMakeRecipe = true;

        for (Ingredient neededIngredient : neededIngredients) {
            String ingredientName = neededIngredient.getName().toLowerCase();

            // Look for the ingredient in availableIngredientsMap (case-insensitive)
            List<Ingredient> availableIngredientsList = null;
            for (String key : availableIngredientsMap.keySet()) {
                if (key.toLowerCase().equals(ingredientName)) {
                    availableIngredientsList = availableIngredientsMap.get(key);
                    break;
                }
            }

            if (availableIngredientsList == null || availableIngredientsList.isEmpty()) {
                // Ingredient not available at all
                missingIngredients.add(neededIngredient);
                canMakeRecipe = false;
            } else if (neededIngredient.getQuantity() > 0) {
                // Check if we have enough quantity (only if the recipe specifies a quantity)
                int totalAvailableQuantity = 0;
                for (Ingredient availableIngredient : availableIngredientsList) {
                    totalAvailableQuantity += availableIngredient.getQuantity();
                }

                if (totalAvailableQuantity < neededIngredient.getQuantity()) {
                    // Not enough quantity, create a new ingredient with the missing amount
                    Ingredient insufficientIngredient = new Ingredient(
                            neededIngredient.getName(),
                            neededIngredient.getQuantity() - totalAvailableQuantity
                    );
                    missingIngredients.add(insufficientIngredient);
                    canMakeRecipe = false;
                }
            }
        }

        if (canMakeRecipe) {
            LOGGER.log(Level.INFO, "Recipe {0} can be made with available ingredients", recipe.getRecipeName());
            suggestedRecipes.add(recipe);
        } else {
            LOGGER.log(
                    Level.FINE,
                    "Recipe {0} is missing {1} ingredients or has insufficient quantities",
                    new Object[]{recipe.getRecipeName(), missingIngredients.size()});
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
        StringBuilder message = new StringBuilder(MESSAGE_MISSING_INGREDIENTS_PREFIX
                + recipe.getRecipeName() + MESSAGE_MISSING_INGREDIENTS_SUFFIX);

        for (Ingredient ingredient : missingIngredients) {
            message.append("\n")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getName().toLowerCase());
        }

        Ui.printItems(message.toString());
    }
}
