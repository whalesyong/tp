package seedu.cookingaids.commands;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.parser.Parser;

import java.util.List;
import java.util.Scanner;

public class DeleteCommand {
    public static final String COMMAND_WORD = "delete";
    static final int SPACE = 1;

    public static void deleteDish(String receivedText) {
        String[] parsedDish = Parser.parseDish(receivedText);

        String dishId = parsedDish[0];        // dish ID
        String dishName = parsedDish[1];
        String dishDate = parsedDish[2];      // date

        List<Dish> dishes = DishCalendar.getDishesByName(dishName);

        if (dishes.isEmpty()) {
            System.out.println("No scheduled dishes found for: " + dishName);
            return;
        }

        // If dishDate is provided, filter by it
        if (!dishDate.equals("none")) {
            dishes.removeIf(dish -> dish.getDishDate().toString().equals(dishDate));
            if (dishes.isEmpty()) {
                System.out.println("No dishes found with name: " + dishName + " on " + dishDate);
                return;
            }
        }

        // If only one dish matches, delete it
        if (dishes.size() == 1) {
            DishCalendar.removeDishInCalendar(dishes.get(0));
            System.out.println(dishes.get(0).getDishDate().toString() + " - " + dishName + " Successfully deleted!");
        } else {
            System.out.println("Multiple dishes found:");
            for (int i = 0; i < dishes.size(); i++) {
                System.out.println((i + 1) + ". ID: " + dishes.get(i).getId() + ", Date: " +
                        dishes.get(i).getDishDate().toString() + " - " + dishName);
            }
            System.out.println("Which would you like to delete? Input a number.");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= dishes.size()) {
                Dish selectedDish = dishes.get(choice - 1);
                DishCalendar.removeDishInCalendar(selectedDish);
                System.out.println(selectedDish.getDishDate().toString() + " - " + dishName + " Successfully deleted!");
            } else {
                System.out.println("Invalid choice. No dish deleted.");
            }
        }
    }

    public static void deleteDishWithWhen(String receivedText) {
        String[] parsedDish = Parser.parseDish(receivedText);

        String dishName = parsedDish[1];
        String date = parsedDish[2];

        if (date.equals("none")) {
            System.out.println("Invalid format. Use: delete dish_name -when=YYYY-MM-DD");
            return;
        }

        List<Dish> dishes = DishCalendar.getDishesByName(dishName);
        if (dishes.isEmpty()) {
            System.out.println("No scheduled dishes found for: " + dishName);
            return;
        }

        for (Dish dish : dishes) {
            if (dish.getDishDate().toString().equals(date)) {
                DishCalendar.removeDishInCalendar(dish);
                System.out.println(date + " - " + dishName + " successfully deleted!");
                return;
            }
        }

        System.out.println("No dish found with name: " + dishName + " on " + date);
    }

    public static void deleteDishByWhen(String receivedText) {
        String date = receivedText.trim();

        List<Dish> dishesToRemove = DishCalendar.getDishesByDate(date);

        if (dishesToRemove.isEmpty()) {
            System.out.println("No dishes found on " + date);
            return;
        }

        for (Dish dish : dishesToRemove) {
            DishCalendar.removeDishInCalendar(dish);
        }

        System.out.println("Deleted all dishes scheduled for " + date);
    }

    public static void deleteIngredient(String receivedText) {
        String ingredientName = Parser.parseIngredientForDeletion(receivedText);

        IngredientStorage.removeIngredient(ingredientName);
        System.out.println("Deleted " + ingredientName + " from the list of available ingredients.");
    }

    public static void deleteRecipe(String receivedText) {
        String recipeName = Parser.parseRecipeForDeletion(receivedText);

        RecipeBank.removeRecipeFromRecipeBank(recipeName);
        System.out.println(recipeName + " has been deleted from the recipe bank!");
    }
}
