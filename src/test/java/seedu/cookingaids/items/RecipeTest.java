package seedu.cookingaids.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;

class RecipeTest {

    private Recipe testRecipe;
    private String recipeName;
    private ArrayList<Ingredient> ingredients;

    @BeforeEach
    void setUp() {
        // Create test data before each test
        recipeName = "Pasta Carbonara";
        ingredients = new ArrayList<>(Arrays.asList(
                new Ingredient( "Spaghetti"),
                new Ingredient( "Egg"),
                new Ingredient("Parmesan"),
                new Ingredient( "Black Pepper")
        ));

        testRecipe = new Recipe(recipeName, ingredients);
    }

    @Test
    void constructorWithNameOnly() {
        Recipe nameOnlyRecipe = new Recipe("Simple Recipe");
        assertEquals("Simple Recipe", nameOnlyRecipe.getRecipeName());
        assertTrue(nameOnlyRecipe.getIngredients().isEmpty());
    }

    @Test
    void constructorWithNullIngredients() {
        Recipe nullIngredientsRecipe = new Recipe("Test Recipe", null);
        assertEquals("Test Recipe", nullIngredientsRecipe.getRecipeName());
        assertNotNull(nullIngredientsRecipe.getIngredients());
        assertTrue(nullIngredientsRecipe.getIngredients().isEmpty());
    }

    @Test
    void getRecipeName() {
        assertEquals(recipeName, testRecipe.getRecipeName());
    }

    @Test
    void setRecipeName() {
        String newName = "Spaghetti_Carbonara";
        testRecipe.setRecipeName(newName);
        assertEquals(newName, testRecipe.getRecipeName());
    }

    @Test
    void getIngredients() {
        assertEquals(ingredients, testRecipe.getIngredients());
        assertEquals(4, testRecipe.getIngredients().size());
        assertTrue(testRecipe.getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.getName().equals("Egg")));

    }

    @Test
    void setIngredients() {
        ArrayList<Ingredient> newIngredients = new ArrayList<>(Arrays.asList(
            new Ingredient("Flour"),
            new Ingredient( "Sugar"),
            new Ingredient( "Egg"),
            new Ingredient( "Butter")
        ));

        testRecipe.setIngredients(newIngredients);
        assertEquals(newIngredients, testRecipe.getIngredients());
        assertEquals(4, testRecipe.getIngredients().size());
        assertTrue(testRecipe.getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.getName().equals("Flour")));

        assertFalse(testRecipe.getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.getName().equals("Spaghetti")));

    }

    @Test
    void testToString() {
        String expected = "Recipe named 'Pasta Carbonara' needs ingredients [Spaghetti (0, Expiry: null, " +
                "Expiring Soon: No, Expired: No), Egg (0, Expiry: null, Expiring Soon: No, Expired: No), " +
                "Parmesan (0, Expiry: null, Expiring Soon: No, Expired: No), " +
                "Black Pepper (0, Expiry: null, Expiring Soon: No, Expired: No)]";
        assertEquals(expected, testRecipe.toString());
    }
}
