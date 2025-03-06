package seedu.cookingaids;

import java.util.ArrayList;
import java.util.List;

public class DisplayCommand {
    final static String COMMAND_WORD = "list";

    public static void diplayDishCalendar(){
        ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
        Ui.printItemsListView(listOfDish);

    }

}

