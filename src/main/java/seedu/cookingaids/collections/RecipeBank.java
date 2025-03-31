package seedu.cookingaids.collections;

import java.util.ArrayList;
import java.util.List;

import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

public class RecipeBank {

    static ArrayList<Recipe> recipeBank = new ArrayList<>();

    public static void initializeRecipeBank(List<Recipe> recipesList) {
        recipeBank.addAll(recipesList);
    }

    public static void addRecipeToRecipeBank(Recipe recipe) {
        recipeBank.add(recipe);
    }

    public static ArrayList<Recipe> getRecipeBank() {
        return recipeBank;
    }

    public static void removeRecipeFromRecipeBank(String command) {
        String recipeName = command.replace("delete -recipe=", "").trim();  // Extract name
        recipeBank.removeIf(recipe -> recipe.getRecipeName().equalsIgnoreCase(recipeName));
    }

    public static boolean contains(String recipeName) {
        return recipeBank.stream().anyMatch(recipe -> recipe.getRecipeName().equalsIgnoreCase(recipeName));
    }
    public static List<Ingredient> getIngredientList(Dish dish) {
        ArrayList<Recipe> recipeBank = RecipeBank.getRecipeBank();
        for (Recipe item : recipeBank) {
            if (item.getName().equals(dish.getName())) {
                return item.getIngredients();
            }

        }
        return null;
    }

    public static void clear() {
        recipeBank.clear();
    }
}
