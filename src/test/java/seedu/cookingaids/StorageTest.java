package seedu.cookingaids;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.storage.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StorageTest {

    private ArrayList<Dish> testDishes;
    private ArrayList<Recipe> testRecipes;
    private HashMap<String, List<Ingredient>> testIngredients;

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

        // Create test ingredients
        testIngredients = new HashMap<>();
        ArrayList<Ingredient> pastaStock = new ArrayList<>();
        pastaStock.add(new Ingredient(1,"Spaghetti", "2025-04-15",2));
        pastaStock.add(new Ingredient(1,"Eggs", "2025-03-30", 4));
        testIngredients.put("Pasta Carbonara", pastaStock);

        ArrayList<Ingredient> curryStock = new ArrayList<>();
        curryStock.add(new Ingredient(1,"Chicken","2025-04-10", 1));
        curryStock.add(new Ingredient(1,"Curry Powder","2025-06-01", 1));
        testIngredients.put("Chicken Curry", curryStock);
    }

    @Test
    public void testDataWrapperConstructors() {
        // Test empty constructor
        Storage.DataWrapper emptyWrapper = new Storage.DataWrapper();
        assertNull(emptyWrapper.dishes, "Dishes should be null with empty constructor");
        assertNull(emptyWrapper.recipes, "Recipes should be null with empty constructor");
        assertNull(emptyWrapper.ingredients, "Ingredients should be null with empty constructor");
        // Test parameterized constructor
        Storage.DataWrapper paramWrapper = new Storage.DataWrapper(testDishes, testRecipes, testIngredients);
        assertEquals(testDishes, paramWrapper.dishes, "Dishes should match input");
        assertEquals(testRecipes, paramWrapper.recipes, "Recipes should match input");
        assertEquals(testIngredients, paramWrapper.ingredients, "Ingredients should match input");
    }


    /**
     * This test uses a package-level approach to test the functionality without
     * directly testing the file I/O operations, which we can't easily control
     * without modifying the final field.
     */
    @Test
    public void testDataWrapperSerialization() {
        // Test that the DataWrapper class can be correctly used to wrap our data
        Storage.DataWrapper wrapper = new Storage.DataWrapper(testDishes, testRecipes, testIngredients);

        // Verify the content of the wrapper
        assertEquals(2, wrapper.dishes.size(), "DataWrapper should contain 2 dishes");
        assertEquals(2, wrapper.recipes.size(), "DataWrapper should contain 2 recipes");
        assertEquals(2, wrapper.ingredients.size(), "DataWrapper should contain 2 ingredient lists");

        assertEquals("Pasta Carbonara", wrapper.dishes.get(0).getName(), "First dish should be Pasta Carbonara");
        assertEquals("Chicken Curry", wrapper.recipes.get(1).getRecipeName(), "Second recipe should be Chicken Curry");

        List<Ingredient> pastaStock = wrapper.ingredients.get("Pasta Carbonara");
        assertNotNull(pastaStock, "Pasta Carbonara ingredients should exist");
        assertEquals(2, pastaStock.size(), "Pasta Carbonara should have 2 ingredients");

        List<Ingredient> curryStock = wrapper.ingredients.get("Chicken Curry");
        assertNotNull(curryStock, "Chicken Curry ingredients should exist");
        assertEquals(2, curryStock.size(), "Chicken Curry should have 2 ingredients");
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
        assertEquals(testIngredients.size(), result.ingredients.size(),
                "Loaded ingredients should match stored ingredients");
    }
}
