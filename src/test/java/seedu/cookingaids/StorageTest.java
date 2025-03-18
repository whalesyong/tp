package seedu.cookingaids;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.Items.Dish;
import seedu.cookingaids.Items.Recipe;
import seedu.cookingaids.Storage.Storage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StorageTest {

    private ArrayList<Dish> testDishes;
    private ArrayList<Recipe> testRecipes;

    @BeforeEach
    public void setUp() {
        // Create test data
        testDishes = new ArrayList<>();
        testDishes.add(new Dish(1, "Pasta Carbonara", "2025-03-20"));
        testDishes.add(new Dish(2, "Chicken Curry", "2025-03-22"));

        testRecipes = new ArrayList<>();
        ArrayList<String> pastaIngredients = new ArrayList<>();
        pastaIngredients.add("Spaghetti");
        pastaIngredients.add("Eggs");
        pastaIngredients.add("Bacon");
        pastaIngredients.add("Parmesan");

        ArrayList<String> curryIngredients = new ArrayList<>();
        curryIngredients.add("Chicken");
        curryIngredients.add("Curry Powder");
        curryIngredients.add("Coconut Milk");

        testRecipes.add(new Recipe("Pasta Carbonara", pastaIngredients));
        testRecipes.add(new Recipe("Chicken Curry", curryIngredients));
    }

    @Test
    public void testDataWrapperConstructors() {
        // Test empty constructor
        Storage.DataWrapper emptyWrapper = new Storage.DataWrapper();
        assertNull(emptyWrapper.dishes, "Dishes should be null with empty constructor");
        assertNull(emptyWrapper.recipes, "Recipes should be null with empty constructor");

        // Test parameterized constructor
        Storage.DataWrapper paramWrapper = new Storage.DataWrapper(testDishes, testRecipes);
        assertEquals(testDishes, paramWrapper.dishes, "Dishes should match input");
        assertEquals(testRecipes, paramWrapper.recipes, "Recipes should match input");
    }


    /**
     * This test uses a package-level approach to test the functionality without
     * directly testing the file I/O operations, which we can't easily control
     * without modifying the final field.
     */
    @Test
    public void testDataWrapperSerialization() {
        // Test that the DataWrapper class can be correctly used to wrap our data
        Storage.DataWrapper wrapper = new Storage.DataWrapper(testDishes, testRecipes);

        // Verify the content of the wrapper
        assertEquals(2, wrapper.dishes.size(), "DataWrapper should contain 2 dishes");
        assertEquals(2, wrapper.recipes.size(), "DataWrapper should contain 2 recipes");
        assertEquals("Pasta Carbonara", wrapper.dishes.get(0).getName(), "First dish should be Pasta Carbonara");
        assertEquals("Chicken Curry", wrapper.recipes.get(1).getRecipeName(), "Second recipe should be Chicken Curry");
    }

    /**
     * This test would be better with dependency injection, but we'll test what we can
     * with the current design.
     */
    @Test
    public void testLoadDataHandlesEmptyOrMissingFile() {
        // When loading from a non-existent file, it should return empty lists
        Storage.DataWrapper result = Storage.loadData();

        // We can't guarantee the file doesn't exist, but we can test that the method returns
        // a valid DataWrapper object in any case
        assertNotNull(result, "LoadData should always return a non-null DataWrapper");
        assertNotNull(result.dishes, "Dishes list should not be null");
        assertNotNull(result.recipes, "Recipes list should not be null");
    }
}
