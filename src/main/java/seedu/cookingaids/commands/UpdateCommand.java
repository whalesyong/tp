package seedu.cookingaids.commands;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.DishDate;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.parser.Parser;

import seedu.cookingaids.exception.InvalidInputException;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.cookingaids.commands.AddCommand.isValidDate;
import static seedu.cookingaids.parser.Parser.INGREDIENT_FLAG;
import static seedu.cookingaids.parser.Parser.RECIPE_FLAG;

public class UpdateCommand {

    public static final String COMMAND_WORD = "update";
    private static final int SPACE = 1;

    /**
     * Updates a recipe in the recipe bank.
     *
     * @param receivedText The user input containing the recipe index and update details.
     */
    public static void updateRecipe(String receivedText) {
        String recipeName = Parser.parseRecipeNameForUpdate(receivedText);

        List<Recipe> recipesToUpdate = RecipeBank.getRecipeByName(recipeName);

        Recipe recipeToUpdate;

        if (recipesToUpdate.isEmpty()) {
            System.out.println("No such recipe: " + recipeName);
            return;
        } else if (recipesToUpdate.size() != 1) {
            // prompt
            recipeToUpdate = promptUserForRecipeUpdate(recipesToUpdate);
        } else {
            recipeToUpdate = recipesToUpdate.get(0);
        }

        // Check what needs to be updated
        if (receivedText.contains(Parser.NEW_NAME_FLAG)) {
            String newName = Parser.parseNewNameForUpdate(receivedText);
            recipeToUpdate.setRecipeName(newName);
            System.out.println("Recipe name updated to: " + newName);
        }

        if (receivedText.contains(Parser.NEW_INGREDIENTS_FLAG)) {
            try {
                String ingredientsStr = Parser.parseNewIngredientsForUpdate(receivedText);
                //System.out.println(ingredientsStr);
                ArrayList<Ingredient> ingredients = parseIngredientsString(ingredientsStr);
                recipeToUpdate.setIngredients(ingredients);
                System.out.println("Recipe ingredients updated successfully!");
            } catch (InvalidInputException e) {
                System.out.println("Invalid ingredient input!");
            }
        }

        if (receivedText.contains(Parser.NEW_TAGS_FLAG)) {
            try {
                String tagsStr = Parser.parseNewTagsForUpdate(receivedText);
                ArrayList<String> tags = parseTagsString(tagsStr);
                recipeToUpdate.setTags(tags);
                System.out.println("Recipe tags updated successfully!");
            } catch (InvalidInputException e) {
                System.out.println("Invalid ingredient input!");
            }
        }

        if (!receivedText.contains(Parser.NEW_NAME_FLAG) &&
                !receivedText.contains(Parser.NEW_INGREDIENTS_FLAG) &&
                !receivedText.contains(Parser.NEW_TAGS_FLAG)) {

            System.out.println("No updates specified. Use -newname=, -newingredients= or -newtags= flags.");
        }
    }

    public static Recipe promptUserForRecipeUpdate(List<Recipe> recipes) {
        System.out.println("Multiple recipes found:");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ", Ingredients: " + recipes.get(i).getIngredientsString());
        }

        System.out.println("Which recipe would you like to update? Input a number.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        while (choice <= 0 || choice > recipes.size()) {
            System.out.println("Please enter a valid number.");
        }

        return recipes.get(choice - 1);

    }

    /**
     * Parses a string of ingredients into a list of Ingredient objects.
     * Format: ingredient1,quantity1,ingredient2,quantity2,...
     *
     * @param ingredientsStr The string containing ingredients in format (name,quantity) without spaces.
     * @return An ArrayList of Ingredient objects.
     */
    private static ArrayList<Ingredient> parseIngredientsString(String ingredientsStr) throws InvalidInputException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        String[] parts = ingredientsStr.split(",");

        // Check if there's an even number of elements
        if (parts.length % 2 != 0) {
            throw new InvalidInputException();
        }

        // Process pairs of name and quantity
        for (int i = 0; i < parts.length; i += 2) {
            String name = parts[i];
            String quantity = parts[i + 1];
            try {
                int quantityValue = Integer.parseInt(quantity);
                ingredients.add(new Ingredient(name, quantityValue));
            } catch (NumberFormatException e) {
                // If quantity is not a valid number, throw an exception
                throw new InvalidInputException();
            }
        }

        return ingredients;
    }

    private static ArrayList<String> parseTagsString(String tagsStr) throws InvalidInputException {
        ArrayList<String> tags = new ArrayList<>();
        String[] parts = tagsStr.split(",");
        for (String p : parts) {
            tags.add(p); // Optional: trim whitespace
        }
        return tags;
    }


    /**
     * Removes the command word from the received input text.
     *
     * @param receivedText The full command input from the user.
     * @return The input text without the command word.
     */
    public static String removeCommandWord(String receivedText) {
        return receivedText.substring(COMMAND_WORD.length() + SPACE);
    }

    public static void updateIngredient(String receivedText) {
        try {
            String inputs = removeCommandWord(receivedText);
            HashMap<String, String> ingredientFields = Parser.parseIngredientUpdate(inputs);
            if (ingredientFields == null) {
                System.out.println("Invalid format. Use: update -ingredient=ingredient_name -expiry=YYYY/MM/DD " +
                        "-quantity=quantity, the only dashes should be for the flags");
                return;
            }
            String ingredientName = ingredientFields.get("ingredient");
            if (ingredientName.isEmpty()) {
                throw new IllegalArgumentException("Ingredient name cannot be empty");
            }
            String expiryDate = ingredientFields.get("expiry_date");
            if (expiryDate.isEmpty()) {
                throw new IllegalArgumentException("Expiry date cannot be empty");
            }
            String newExpiry = ingredientFields.get("new_expiry");
            if (newExpiry.isEmpty()) {
                throw new IllegalArgumentException("New expiry date cannot be empty");
            }
            int quantity;
            try {
                quantity = Integer.parseInt(ingredientFields.get("quantity"));
                if (quantity < 0) {
                    throw new IllegalArgumentException("Quantity must be a positive integer");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantity must be a positive integer and less than "
                        + Integer.MAX_VALUE);
            }
            IngredientStorage.updateIngredient(ingredientName, expiryDate, quantity, newExpiry);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void updateDish(String receivedText) {
        try {
            if (receivedText.contains(RECIPE_FLAG) || receivedText.contains(INGREDIENT_FLAG)) {
                System.out.println("Other commands found, I can only process one at a time");
                return;
            }
            receivedText = removeCommandWord(receivedText);
            Pattern pattern = Pattern.compile("-(\\w+)=");
            Matcher matcher = pattern.matcher(receivedText);
            while (matcher.find()) {
                String key = matcher.group(1);

                if (!key.equals("dish")) {
                    throw new IllegalArgumentException("Unexpected key: " + key);
                }
            }
            Pattern pattern1 = Pattern.compile("-dish=(\\S+)");
            Matcher matcher1 = pattern1.matcher(receivedText);

            if (matcher1.find()) {
                String dishName = matcher1.group(1);
                List<Dish> dishes = DishCalendar.getDishesByName(dishName);
                if (dishes.isEmpty()) {
                    System.out.println("No scheduled dishes found for: " + dishName);
                    return;
                }
                if (dishes.size() == 1) {
                    promptNewDate(dishes.get(0));
                } else {
                    promptUserForDishUpdate(dishes, dishName);
                }

            } else {
                throw new InvalidInputException();
            }


        } catch (InvalidInputException e) {
            System.out.println("" +
                    "Format error ensure you do not have additional flags. use update -dish={dishName} only," +
                    " remove additional flags");
            System.out.println("ensure that date is in YYYY/MM/DD format ");
        }catch(InputMismatchException e){
            System.out.println("input an integer");
        }
    }

    private static void promptNewDate(Dish dish) throws InvalidInputException {
        System.out.println("Type New date in YYYY/MM/DD format.");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.next();

        if (!choice.isEmpty() && !isValidDate(choice)) {
            throw new InvalidInputException();
        }
        dish.setDishDate(new DishDate(choice));
        System.out.println("Change successful \n" + dish);

    }

    private static void promptUserForDishUpdate(List<Dish> dishes, String dishName) throws InvalidInputException {
        System.out.println("Multiple dishes found:");
        for (int i = 0; i < dishes.size(); i++) {
            System.out.println((i + 1) + ", Date: " + dishes.get(i).getDishDate().toString() + " - " + dishName);
        }
        System.out.println("Which would dish would you like to reschedule? Input a number.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= dishes.size()) {
            promptNewDate(dishes.get(choice - 1));
        } else {
            System.out.println("Invalid choice. No dish deleted.");
        }
    }
}

