package seedu.cookingaids.commands;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.ShoppingList;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.logger.LoggerFactory;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.parser.Parser;
import seedu.cookingaids.storage.Storage;

public class DeleteCommand {
    public static final String COMMAND_WORD = "delete";
    static final int SPACE = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCommand.class);

    /**
     * Removes the command word from the received input text.
     *
     * @param receivedText The full command input from the user.
     * @return The input text without the command word.
     */
    public static String removeCommandWord(String receivedText) {
        return receivedText.substring(COMMAND_WORD.length() + SPACE);
    }

    /**
     * Deletes a dish from the dish calendar.
     *
     * @param receivedText The user input containing the dish name and optional date.
     */
    public static void deleteDish(String receivedText) {
        try {
            receivedText = removeCommandWord(receivedText);
            String[] parsedDish = Parser.parseDish(receivedText);

            String dishName = parsedDish[0];
            String dishDate = parsedDish[1];
            List<Dish> dishes = DishCalendar.getDishesByName(dishName);

            if (dishes.isEmpty()) {
                System.out.println("No scheduled dishes found for: " + dishName);
                return;
            }

            dishes.removeIf(dish -> !dishDate.isEmpty() && dish.getDishDate().toString().equals(dishDate));
            if (dishes.isEmpty()) {
                System.out.println("No dishes found with name: " + dishName + " on " + dishDate);
                return;
            }

            if (dishes.size() == 1) {
                removeDish(dishes.get(0), dishName);
            } else {
                promptUserForDishDeletion(dishes, dishName);
            }
        } catch (InvalidInputException e) {
            System.out.println("Invalid input");
        }
    }

    private static void removeDish(Dish dish, String dishName) {
        DishCalendar.removeDishInCalendar(dish);
        System.out.println(dish.getDishDate().toString() + " - " + dishName + " Successfully deleted!");

        LOGGER.info("Saving to file after dish deletion");
        Storage.storeData(DishCalendar.getDishCalendar(),
                RecipeBank.getRecipeBank(), IngredientStorage.getStorage(),
                ShoppingList.getShoppingList());
    }

    private static void promptUserForDishDeletion(List<Dish> dishes, String dishName) {
        System.out.println("Multiple dishes found:");
        for (int i = 0; i < dishes.size(); i++) {
            System.out.println((i + 1) + ", Date: " + dishes.get(i).getDishDate().toString() + " - " + dishName);
        }
        System.out.println("Which would you like to delete? Input a number.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= dishes.size()) {
            removeDish(dishes.get(choice - 1), dishName);
        } else {
            System.out.println("Invalid choice. No dish deleted.");
        }
    }

    /**
     * Deletes a dish based on its date.
     *
     * @param receivedText The user input containing the dish name and date.
     */
    public static void deleteDishWithWhen(String receivedText) {
        try {
            String[] parsedDish = Parser.parseDish(receivedText);
            String dishName = parsedDish[0];
            String date = parsedDish[1];
            if (date.equals("none")) {
                System.out.println("Invalid format. Use: delete dish_name -when=YYYY/MM/DD");
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
        } catch (InvalidInputException e) {
            System.out.println("no dish found");
        }
    }

    /**
     * Deletes all dishes scheduled for a given date.
     *
     * @param receivedText The date for which all dishes should be deleted.
     */
    public static void deleteDishByWhen(String receivedText) {
        String parsedDate = Parser.parseWhenForDeletion(receivedText);

        List<Dish> dishesToRemove = DishCalendar.getDishesByDate(parsedDate);

        if (dishesToRemove.isEmpty()) {
            System.out.println("No dishes found on " + parsedDate);
            return;
        }

        for (Dish dish : dishesToRemove) {
            DishCalendar.removeDishInCalendar(dish);
        }

        System.out.println("Deleted all dishes scheduled for " + parsedDate);

        LOGGER.info("Saving to file after bulk dish deletion");
        Storage.storeData(DishCalendar.getDishCalendar(),
                RecipeBank.getRecipeBank(), IngredientStorage.getStorage(),
                ShoppingList.getShoppingList());
    }

    /**
     * Deletes an ingredient from the ingredient storage.
     *
     * @param receivedText The name of the ingredient to be deleted.
     */
    public static void deleteIngredient(String receivedText) {
        String ingredientName = Parser.parseIngredientForDeletion(receivedText);

        if (IngredientStorage.contains(ingredientName)) {
            IngredientStorage.removeIngredient(ingredientName);
            System.out.println("Deleted " + ingredientName + " from the list of available ingredients.");
            LOGGER.info("Saving to file after ingredient deletion");
            Storage.storeData(DishCalendar.getDishCalendar(),
                RecipeBank.getRecipeBank(), IngredientStorage.getStorage(),
                ShoppingList.getShoppingList());
        } else {
            System.out.println("Please provide a valid ingredient name");
        }
    }

    public static void removeRecipe(Recipe recipe) {
        RecipeBank.removeRecipeFromRecipeBank(recipe);
    }

    public static void deleteRecipe(String receivedText) {
        String recipeName = Parser.parseRecipeForDeletion(receivedText);

        List<Recipe> recipes = RecipeBank.getRecipeByName(recipeName);

        if (recipes.isEmpty()) {
            System.out.println("No recipe found: " + recipeName);
        } else if (recipes.size() == 1) {
            removeRecipe(recipes.get(0));
            LOGGER.info("Saving to file after recipe deletion");
            Storage.storeData(DishCalendar.getDishCalendar(),
                    RecipeBank.getRecipeBank(), IngredientStorage.getStorage(),
                    ShoppingList.getShoppingList());
        } else {
            promptUserForRecipeDeletion(recipes, recipeName);
        }
    }

    public static void promptUserForRecipeDeletion(List<Recipe> recipes, String recipeName) {
        System.out.println("Multiple recipes found:");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i+1) + ", Ingredients: " + recipes.get(i).getIngredientsString());
        }

        System.out.println("Which recipe would you like to delete? Input a number.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= recipes.size()) {
            removeRecipe(recipes.get(choice-1));
            LOGGER.info("Saving to file after recipe deletion");
            Storage.storeData(DishCalendar.getDishCalendar(),
                    RecipeBank.getRecipeBank(), IngredientStorage.getStorage(),
                    ShoppingList.getShoppingList());
        } else {
            System.out.println("Invalid choice. No recipe deleted.");
        }
    }
}
