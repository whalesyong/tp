package seedu.cookingaids.commands;


import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.ShoppingList;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.ui.CalendarPrinter;
import seedu.cookingaids.ui.Ui;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class ListCommand {
    public static final String COMMAND_WORD = "list";

    public static void displayDishList(String receivedString) {

        ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
        Ui.printDishListView(listOfDish);

    }

    public static List<Dish> sortDishesByDateStream(ArrayList<Dish> dishes) {
        return dishes.stream()
                .sorted(Comparator.comparing(dish -> dish.getDishDate().getDateLocalDate()))
                .toList();
    }


    public static void displayIngredients() {
        HashMap<String, List<Ingredient>> ingredients = IngredientStorage.getStorage();
        Ui.printIngredientListView(ingredients);
    }

    public static void displayRecipeBank() {
        // This should ideally be a singleton or accessed through a proper controller
        ArrayList<Recipe> listOfRecipes = RecipeBank.getRecipeBank();
        Ui.printRecipeListView(listOfRecipes);
    }
    public static void displayShoppingList() {

        ArrayList<Ingredient> shoppingList = ShoppingList.getShoppingList();
        Ui.printShoppingListView(shoppingList);
    }
}

