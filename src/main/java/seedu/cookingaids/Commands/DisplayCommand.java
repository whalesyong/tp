package seedu.cookingaids.Commands;

import seedu.cookingaids.Collections.DishCalendar;
import seedu.cookingaids.Collections.IngredientStorage;
import seedu.cookingaids.Items.Dish;
import seedu.cookingaids.Items.Ingredient;
import seedu.cookingaids.Ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayCommand {
    public final static String COMMAND_WORD = "list";

    public static void displayDishCalendar(){
        ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
        Ui.printDishListView(listOfDish);

    }

    public static void displayIngredients() {
        HashMap<String, List<Ingredient>> ingredients = IngredientStorage.getStorage();
        Ui.printIngredientListView(ingredients);
    }

}

