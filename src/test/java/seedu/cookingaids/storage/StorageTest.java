package seedu.cookingaids.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.items.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StorageTest {
    private ArrayList<Ingredient> testShopping;
    private ArrayList<Dish> testDishes;
    private ArrayList<Recipe> testRecipes;
    private HashMap<String, List<Ingredient>> testIngredients;

    @BeforeEach
    public void setUp() {
        // Create test data
        testDishes = new ArrayList<>();
        testDishes.add(new Dish("pasta_carbonara", "2025-03-20"));
        testDishes.add(new Dish("chicken_curry", "2025-03-22"));

        testRecipes = new ArrayList<>();
        ArrayList<Ingredient> pastaIngredients = new ArrayList<>();
        pastaIngredients.add(new Ingredient("spaghetti"));
        pastaIngredients.add(new Ingredient("egg"));
        pastaIngredients.add(new Ingredient("bacon"));
        pastaIngredients.add(new Ingredient("parmesan"));

        ArrayList<Ingredient> tomatoPastaIngredients = new ArrayList<>();
        pastaIngredients.add(new Ingredient("pasta"));
        pastaIngredients.add(new Ingredient("tomato", 2));


        ArrayList<Ingredient> curryIngredients = new ArrayList<>();
        curryIngredients.add(new Ingredient("chicken"));
        curryIngredients.add(new Ingredient("curry Powder"));
        curryIngredients.add(new Ingredient("coconut Milk"));

        testRecipes.add(new Recipe("pasta_carbonara", pastaIngredients));
        testRecipes.add(new Recipe("chicken_curry", curryIngredients));
        testRecipes.add(new Recipe("tomato_pasta", tomatoPastaIngredients));

        // Create test ingredients
        testIngredients = new HashMap<>();
        ArrayList<Ingredient> pastaStock = new ArrayList<>();
        pastaStock.add(new Ingredient("pasta", "2025-04-15", 2));
        pastaStock.add(new Ingredient("pasta", "2025-03-30", 4));
        testIngredients.put("pasta", pastaStock);

        ArrayList<Ingredient> curryStock = new ArrayList<>();
        curryStock.add(new Ingredient("curry", "2025-04-10", 1));
        curryStock.add(new Ingredient("curry", "2025-06-01", 1));
        testIngredients.put("curry", curryStock);


        testShopping = new ArrayList<>();
        testShopping.add(new Ingredient("tomato", 2));
        testShopping.add(new Ingredient("chicken", 1));


    }

    @Test
    public void testDataWrapperConstructors() {
        // Test empty constructor
        Storage.DataWrapper emptyWrapper = new Storage.DataWrapper();
        assertNull(emptyWrapper.dishes, "Dishes should be null with empty constructor");
        assertNull(emptyWrapper.recipes, "Recipes should be null with empty constructor");
        assertNull(emptyWrapper.ingredients, "Ingredients should be null with empty constructor");
        // Test parameterized constructor
        Storage.DataWrapper paramWrapper = new Storage.DataWrapper(
                testDishes, testRecipes, testIngredients,
                testShopping);
        assertEquals(testDishes, paramWrapper.dishes, "Dishes should match input");
        assertEquals(testRecipes, paramWrapper.recipes, "Recipes should match input");
        assertEquals(testIngredients, paramWrapper.ingredients, "Ingredients should match input");
        assertEquals(testShopping, paramWrapper.shopping, "shopping cart should match input");
    }


    /**
     * This test uses a package-level approach to test the functionality without
     * directly testing the file I/O operations, which we can't easily control
     * without modifying the final field.
     */
    @Test
    public void testDataWrapperSerialization() {
        // Test that the DataWrapper class can be correctly used to wrap our data
        Storage.DataWrapper wrapper = new Storage.DataWrapper(testDishes, testRecipes, testIngredients, testShopping);

        // Verify the content of the wrapper
        assertEquals(2, wrapper.dishes.size(), "DataWrapper should contain 2 dishes");
        assertEquals(3, wrapper.recipes.size(), "DataWrapper should contain 3 recipes");
        assertEquals(2, wrapper.ingredients.size(), "DataWrapper should contain 2 ingredient lists");
        assertEquals(2, wrapper.shopping.size(), "DataWrapper should contain 2 ingredient");

        assertEquals("pasta_carbonara", wrapper.dishes.get(0).getName(), "First dish should be Pasta Carbonara");
        assertEquals("chicken_curry", wrapper.recipes.get(1).getRecipeName(), "Second recipe should be Chicken Curry");

        List<Ingredient> pastaStock = wrapper.ingredients.get("pasta");
        assertNotNull(pastaStock, "Pasta ingredients should exist");
        assertEquals(2, pastaStock.size(), "Pasta should have 2 ingredients");

        List<Ingredient> curryStock = wrapper.ingredients.get("curry");
        assertNotNull(curryStock, "curry ingredients should exist");
        assertEquals(2, curryStock.size(), "Curry should have 2 ingredients");
    }

    //    /**
    //     * This test would be better with dependency injection, but we'll test what we can
    //     * with the current design.
    //     */
    //    @Test
    //    public void testLoadDataHandlesEmptyOrMissingFile() {
    //        // When loading from a non-existent file, it should return empty lists
    //
    //        Storage.DataWrapper result = Storage.loadData();
    //
    //        // We can't guarantee the file doesn't exist, but we can test that the method returns
    //        // a valid DataWrapper object in any case
    //        assertNotNull(result, "LoadData should always return a non-null DataWrapper");
    //        assertNotNull(result.dishes, "Dishes list should not be null");
    //        assertNotNull(result.recipes, "Recipes list should not be null");
    //        assertEquals(testIngredients.size(), result.ingredients.size(),
    //                "Loaded ingredients should match stored ingredients");}

}
