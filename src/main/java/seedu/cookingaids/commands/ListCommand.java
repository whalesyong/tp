package seedu.cookingaids.commands;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.ShoppingList;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handles the listing of various items such as dishes, ingredients, recipes, and shopping list.
 */
public class ListCommand {
    public static final String COMMAND_WORD = "list";

    /**
     * Displays the list of all scheduled and unscheduled dishes.
     *
     * @param receivedString The command input from the user.
     */
    public static void displayDishList(String receivedString) {
        ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
        List<Dish> validDishes = listOfDish.stream()
                .filter(dish -> dish.getDishDate() != null && dish.getDishDate().getDateLocalDate() != null)
                .toList();

        List<Dish> invalidDishes = listOfDish.stream()
                .filter(dish -> dish.getDishDate() == null || dish.getDishDate().getDateLocalDate() == null)
                .toList();
        if (receivedString.contains("-u")) {
            List<Dish> today = ViewCommand.sortDishesToday(validDishes);
            List<Dish> afterToday = ViewCommand.sortDishesAfterToday(validDishes);
            if (today.isEmpty() && afterToday.isEmpty()) {
                System.out.println("No upcoming dishes planned!");
            }else if(today.isEmpty()){
                System.out.println("Upcoming dishes planned:");
                Ui.printDishListView(afterToday);
            } else if (afterToday.isEmpty()) {
                System.out.println("Today's dishes:");
                Ui.printDishListView(today);
            } else {
                System.out.println("Today's dishes:");
                Ui.printDishListView(today);
                System.out.println("Upcoming dishes planned:");
                Ui.printDishListView(afterToday);
            }


        } else {
            // Separate valid dishes from invalid ones

            System.out.println("All dishes:");
            Ui.printDishListView(ViewCommand.sortDishesByDateStream(validDishes));

            if (!invalidDishes.isEmpty()) {
                System.out.println("Dishes with no scheduled date:");
                Ui.printDishListView(invalidDishes);
            }
        }
    }

    /**
     * Displays the list of all available ingredients in the storage.
     */
    public static void displayIngredients() {
        HashMap<String, List<Ingredient>> ingredients = IngredientStorage.getStorage();
        Ui.printIngredientListView(ingredients);
    }

    /**
     * Displays all recipes stored in the recipe bank.
     */
    public static void displayRecipeBank() {
        ArrayList<Recipe> listOfRecipes = RecipeBank.getRecipeBank();
        Ui.printRecipeListView(listOfRecipes);
    }

    /**
     * Displays all ingredients currently in the shopping list.
     */
    public static void displayShoppingList() {
        ArrayList<Ingredient> shoppingList = ShoppingList.getShoppingList();
        Ui.printShoppingListView(shoppingList);
    }
}
