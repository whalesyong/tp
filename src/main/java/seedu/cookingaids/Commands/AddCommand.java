package seedu.cookingaids.Commands;

import seedu.cookingaids.Collections.DishCalendar;
import seedu.cookingaids.Collections.IngredientStorage;
import seedu.cookingaids.Items.Dish;
import seedu.cookingaids.Items.Ingredient;
import seedu.cookingaids.Parser.Parser;

import java.util.HashMap;

public class AddCommand {
    public final static String COMMAND_WORD = "add";
    final static int SPACE = 1;

    public static void addDish(String receivedText){
        receivedText = receivedText.substring(COMMAND_WORD.length() + SPACE);
        String[] dishFields = Parser.parseDish(receivedText);
        Dish dish = new Dish(Integer.parseInt(dishFields[0]),dishFields[1],dishFields[2]);
        DishCalendar.addDishToCalendar(dish);
        System.out.println("Added Dish: "+dish.getName() +", Scheduled for: "+ dish.getDishDate().toString());
    }

    public static void addIngredient(String receivedText) {
        String inputs = receivedText.substring(COMMAND_WORD.length() + SPACE);
        HashMap<String, String> ingredientFields = Parser.parseIngredient(inputs);
        if (ingredientFields == null) {
            return;
        }
        Ingredient ingredient = new Ingredient(1,ingredientFields.get("ingredient"),
                ingredientFields.get("expiry_date"),
                Integer.parseInt(ingredientFields.get("quantity")));
        IngredientStorage.addToStorage(ingredient);
        System.out.println("Added Ingredient: " + ingredient);
    }
}
