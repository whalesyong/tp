package seedu.cookingaids.Commands;

import seedu.cookingaids.Collections.DishCalendar;
import seedu.cookingaids.Items.Dish;
import seedu.cookingaids.Parser.Parser;

public class AddCommand {
    public final static String COMMAND_WORD = "add";
    final static int SPACE = 1;

    static public void addDish(String receivedText){
        receivedText = receivedText.substring(COMMAND_WORD.length() + SPACE);
        String[] dishFields = Parser.parseDish(receivedText);
        Dish dish = new Dish(Integer.parseInt(dishFields[0]),dishFields[1],dishFields[2]);
        DishCalendar.addDishToCalendar(dish);
        System.out.println("Added Dish: "+dish.getName() +", Scheduled for: "+ dish.getDishDate().toString());
    }
}
