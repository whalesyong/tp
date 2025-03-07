package seedu.cookingaids.Commands;

import seedu.cookingaids.Collections.DishCalendar;
import seedu.cookingaids.Items.Dish;
import seedu.cookingaids.Ui.Ui;

import java.util.ArrayList;

public class DisplayCommand {
    public final static String COMMAND_WORD = "list";

    public static void displayDishCalendar(){
        ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
        Ui.printDishListView(listOfDish);

    }

}

