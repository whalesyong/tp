package seedu.cookingaids.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.cookingaids.items.Recipe;

public class RecipeBank {

    static ArrayList<Recipe> recipeBank = new ArrayList<>();

    public static void initializeRecipeBank(List<Recipe> recipeBank) {
        recipeBank.addAll(recipeBank);
    }
    // shld it take in a fully-formed object?
    public static void addRecipeToRecipeBank(Recipe recipe) {
        recipeBank.add(recipe);
    }

    public static ArrayList<Recipe> getRecipeBank() {
        return recipeBank;
    }

    public static void removeRecipeFromRecipeBank(String command) {
        String recipeName = command.replace("delete -recipe=", "").trim();  // Extract name
        recipeBank.removeIf(recipe -> recipe.getName().equalsIgnoreCase(recipeName));
    }

    public static boolean contains(String recipeName) {
        return recipeBank.stream().anyMatch(recipe -> recipe.getName().equalsIgnoreCase(recipeName));
    }
}
