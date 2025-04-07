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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCommandTest {

    private RecipeBank recipeBank;
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
        Dish spaghetti = new Dish( "spaghetti", "20/03/2025");
        DishCalendar.addDishToCalendar(spaghetti);
    }

    @AfterEach
    void tearDown() {
        DishCalendar.clear();
        RecipeBank.clear();
    }

    @Test
    void execute_addDishToEmptyDishCalendar_addsSuccessfully() {
        DishCalendar emptyDishCalendar = new DishCalendar();
        String dishName = "pizza";

        AddCommand.addDish("add -dish=" + dishName + " -when=2025/11/22");

        assertAddDishSuccessful("pizza", emptyDishCalendar);
    }

    @Test
    void execute_addIngredientToEmptyStorage_addsSuccessfully() {
        IngredientStorage.clear();
        String ingredientName = "tomato";

        AddCommand.addIngredient("add -ingredient=" + ingredientName);

        assertAddIngredientSuccessful("tomato");
        assertEquals(1, IngredientStorage.getStorage().size());
    }

    @Test
    void execute_addRecipeToEmptyRecipeBank_addsSuccessfully() {
        RecipeBank.clear();

        AddCommand.addRecipe("add -recipe=pizza -needs=tomato,1,cheese,1");

        assertAddRecipeSuccessful("pizza");

        List<Recipe> pizzaRecipes = RecipeBank.getRecipeByName("pizza");
        assertEquals(1, pizzaRecipes.size());
        Recipe pizzaRecipe = pizzaRecipes.get(0);

        assertEquals(2, pizzaRecipe.getIngredients().size());
        assertTrue(pizzaRecipe.getIngredients().stream()
                .anyMatch(i -> i.getName().equals("tomato") && i.getQuantity() == 1));
        assertTrue(pizzaRecipe.getIngredients().stream()
                .anyMatch(i -> i.getName().equals("cheese") && i.getQuantity() == 1));
    }

    @Test
    void execute_addRecipeThatExists_succeeds() {
        RecipeBank.clear();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("tomato", 1));
        ingredients.add(new Ingredient("bread", 2));
        Recipe sandwichRecipe = new Recipe("sandwich", ingredients);
        RecipeBank.addRecipeToRecipeBank(sandwichRecipe);

        assertTrue(RecipeBank.contains("sandwich"));
        assertEquals(1, RecipeBank.getRecipeBankSize());

        AddCommand.addRecipe("add -recipe=sandwich -needs=mayo,1,lettuce,1,turkey,2");

        assertAddRecipeSuccessful("sandwich");
        assertEquals(1, RecipeBank.getRecipeBankSize());

        List<Recipe> sandwiches = RecipeBank.getRecipeByName("sandwich");
        assertEquals(1, sandwiches.size());
    }

    @Test
    void execute_addRecipeWithMissingIngredientsFormat_returnsErrorMessage() {
        RecipeBank.clear();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            AddCommand.addRecipe("add -recipe=toast -needs=bread,1,butter");

            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid format"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void execute_addRecipeWithEmptyIngredients_returnsErrorMessage() {
        RecipeBank.clear();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Try to add recipe with no ingredients
            AddCommand.addRecipe("add -recipe=toast -needs=");

            String output = outputStream.toString().trim();
            assertTrue(output.contains("Invalid format"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void execute_addEmptyDishName_returnsInvalidFormatMessage() {
        String invalidDishName = "";  // Invalid because dish name is empty
        AddCommand.addDish("add -dish=" + invalidDishName + " -when=2025-03-23");


        String commandOutput = outputStream.toString().trim();
        assertEquals("Invalid format. Use: add -dish=dish_name -when=YYYY/MM/DD " +
                "\ndish name should be in lower_snake_case" +"\nonly dates in the future are accepted", commandOutput);
    }

    @Test
    void execute_addDishWithNoDate_returnsInvalidFormatMessage() {
        String invalidDate = "";  // Invalid date
        AddCommand.addDish("add -dish=lasagna -when=" + invalidDate);

        String commandOutput = outputStream.toString().trim();
        assertEquals("Invalid format. Use: add -dish=dish_name -when=YYYY/MM/DD " +
                "\ndish name should be in lower_snake_case" +"\nonly dates in the future are accepted", commandOutput);
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
