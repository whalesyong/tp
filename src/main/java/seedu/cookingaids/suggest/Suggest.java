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


    //TODO: extend this method to suggest recipes even if not all ingredients are present
    public static List<Recipe> suggestRecipes(){
        HashMap<String, List<Ingredient>> availableIngredientsMap = IngredientStorage.getStorage();
        ArrayList<Recipe> availableRecipes = RecipeBank.getRecipeBank();

        if (availableRecipes.isEmpty()){
            System.out.println("There are no available recipes.");
            return new ArrayList<>();
        }
        List<Recipe> suggestedRecipes = new ArrayList<>();
        Set<String> foodSet = availableIngredientsMap.keySet();
        List<String> availableIngredients = new ArrayList<>(foodSet);



        // check recipes to see if needed food is present
        for (Recipe recipe : availableRecipes) {
            ArrayList<String> neededIngredients = recipe.getIngredients();
            int availableCount = 0;
            int missingCount = 0;

            //check how many ingredients for the recipe are available
            for (String neededIngredient : neededIngredients) {
                if (availableIngredients.contains(neededIngredient)) {
                    availableCount++;
                } else {
                    missingCount++;
                }
            }
            if (availableCount == neededIngredients.size()) {
                suggestedRecipes.add(recipe);
            }
        }
        return suggestedRecipes;

    }
}
