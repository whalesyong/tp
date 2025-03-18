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
    public void addRecipeToRecipeBank(Recipe recipe) {
        recipeBank.add(recipe);
    }

    public static ArrayList<Recipe> getRecipeBank() {
        return recipeBank;
    }

    // d

    public static void removeRecipeFromRecipeBank(String recipeName) {
        for (int i = 0; i < recipeBank.size(); i++) {
            if (Objects.equals(recipeBank.get(i).getRecipeName(), recipeName)) {
                recipeBank.remove(i);
                return;
            }
        }

    }
}
