package seedu.cookingaids.collections;

import java.util.ArrayList;
import java.util.List;

import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

public class RecipeBank {

    static ArrayList<Recipe> recipeBank = new ArrayList<>();
    //    public static int index = 0;

    public static void initializeRecipeBank(List<Recipe> recipesList) {
        recipeBank.addAll(recipesList);
    }

    public static void addRecipeToRecipeBank(Recipe recipe) {
        recipeBank.add(recipe);
        //        index += 1;
    }

    public static ArrayList<Recipe> getRecipeBank() {
        return recipeBank;
    }

    public static Integer getRecipeBankSize() {
        return recipeBank.size();
    }

    public static void removeRecipeFromRecipeBank(Recipe recipe) {
        recipeBank.remove(recipe);
    }

    public static List<Recipe> getRecipeByName(String recipeName) {
        List<Recipe> recipeList = new ArrayList<>();

        for (Recipe recipe : recipeBank) {
            if (recipe.getRecipeName().equalsIgnoreCase(recipeName)) {
                recipeList.add(recipe);
            }
        }

        return recipeList;
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
