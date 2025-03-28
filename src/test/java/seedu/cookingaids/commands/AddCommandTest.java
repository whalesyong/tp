package seedu.cookingaids.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCommandTest {

    private DishCalendar dishCalendar;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @BeforeEach
    void setUp() {
        dishCalendar = new DishCalendar();
        Dish spaghetti = new Dish( "Spaghetti", "20/03/2025");
        DishCalendar.addDishToCalendar(spaghetti);
    }

    @AfterEach
    void tearDown() {
        dishCalendar.clear();
    }

    @Test
    void execute_addDishToEmptyDishCalendar_addsSuccessfully() {
        DishCalendar emptyDishCalendar = new DishCalendar();
        String dishName = "Pizza";
        Dish newDish = new Dish(dishName, "22/03/2025");

        AddCommand.addDish("add -dish=" + dishName + " -when=22/03/2025");

        assertTrue(emptyDishCalendar.containsDish(dishName));
    }

    @Test
    void execute_addIngredientToEmptyStorage_addsSuccessfully() {
        IngredientStorage.clear();
        String ingredientName = "Tomato";
        Ingredient newIngredient = new Ingredient(1, ingredientName);

        AddCommand.addIngredient("add -ingredient=" + ingredientName);

        assertTrue(IngredientStorage.contains(ingredientName));
    }

    @Test
    void execute_addRecipeToEmptyRecipeBank_addsSuccessfully() {
        RecipeBank.clear();
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Tomato");
        ingredients.add("Cheese");
        Recipe newRecipe = new Recipe("Pizza", ingredients);

        AddCommand.addRecipe("add -recipe=Pizza -needs=Tomato,Cheese");

        assertTrue(RecipeBank.contains("Pizza"));
    }

    @Test
    void execute_addRecipeThatExists_returnsRecipeAlreadyExistsMessage() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Bread");
        ingredients.add("Tomato");
        RecipeBank.addRecipeToRecipeBank(new Recipe("Sandwich", ingredients));

        AddCommand.addRecipe("add -recipe=Sandwich -needs=Bread,Tomato");

        assertTrue(RecipeBank.contains("Sandwich"));
    }

    @Test
    void execute_addEmptyDishName_returnsInvalidFormatMessage() {
        String invalidDishName = "";  // Invalid because dish name is empty
        AddCommand.addDish("add -dish=" + invalidDishName + " -when=2025-03-23");

        String commandOutput = outputStream.toString().trim();
        assertEquals("Invalid format. Use: add -dish=dish_name -when=YYYY-MM-DD", commandOutput);
    }

    @Test
    void execute_addDishWithNoDate_returnsInvalidFormatMessage() {
        String invalidDate = "";  // Invalid date
        AddCommand.addDish("add -dish=Lasagna -when=" + invalidDate);

        String commandOutput = outputStream.toString().trim();
        assertEquals("Invalid format. Use: add -dish=dish_name -when=YYYY-MM-DD", commandOutput);
    }

    @Test
    void execute_addRecipeWithMissingIngredients_returnsMissingIngredientsMessage() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Bread");
        Recipe incompleteRecipe = new Recipe("Toast", ingredients);

        AddCommand.addRecipe("add -recipe=Toast -needs=Bread");

        assertAddRecipeSuccessful("Toast");
    }

    private void assertAddDishSuccessful(String dishName, DishCalendar dishCalendar) {
        assertTrue(dishCalendar.containsDish(dishName));
    }

    private void assertAddIngredientSuccessful(String ingredientName) {
        assertTrue(IngredientStorage.contains(ingredientName));
    }

    private void assertAddRecipeSuccessful(String recipeName) {
        assertTrue(RecipeBank.contains(recipeName));
    }
}
