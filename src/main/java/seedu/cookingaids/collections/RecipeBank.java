package seedu.cookingaids.collections;

import static seedu.cookingaids.collections.DishCalendar.dishCalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        String recipeName = recipe.getRecipeName();
        List<Dish> dishes = DishCalendar.getDishesByName(recipeName);


        if (!dishes.isEmpty()) {
            System.out.println("You have " + recipeName +
                    " scheduled in your calendar for the following times:");

            for (int i = 0; i < dishes.size(); i++) {
                System.out.println((i + 1) + ", Date: " + dishes.get(i).getDishDate().toString());
            }

            System.out.println("Do you still want to delete this recipe? " +
                    "The dishes will remain in your calendar, but will no longer be linked. (Y/N)");

            Scanner scanner = new Scanner(System.in);

            String choice = scanner.next().toUpperCase();
            if (choice.equals("N")) {
                System.out.println("Canceling deletion of" + recipeName);
                return;
            }
        }

        recipeBank.remove(recipe);
        System.out.println(recipe.getName() + " has been deleted from the recipe bank!");
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
