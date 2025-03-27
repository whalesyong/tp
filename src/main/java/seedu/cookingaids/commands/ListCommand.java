package seedu.cookingaids.commands;

import com.sun.net.httpserver.Headers;
import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.ui.CalendarPrinter;
import seedu.cookingaids.ui.Ui;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCommand {
    public static final String COMMAND_WORD = "list";

    public static void displayDishList(String receivedString) {
        if (receivedString.contains("-month")){
            displayDishMonth();
        }
        else {
            ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
            Ui.printDishListView(listOfDish);
        }
    }
    public static void displayDishMonth() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        LocalDate startOfMonth = LocalDate.of(year,month,1);
        int lengthOfMonth = LocalDate.now().lengthOfMonth();
        LocalDate endOfMonth = startOfMonth.plusDays(lengthOfMonth);

        ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
        ArrayList<Dish> filteredList = new ArrayList<>();
        for (Dish dish: listOfDish){
            LocalDate dishDate = dish.getDishDate().getDateLocalDate();
            if(dishDate != null && dishDate.isAfter(startOfMonth) && dishDate.isBefore(endOfMonth)){
                filteredList.add(dish);
            }
        }
        CalendarPrinter.printMonthCalendar(2025, Month.MARCH, filteredList);
        Ui.printDishListView(filteredList);

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
}

