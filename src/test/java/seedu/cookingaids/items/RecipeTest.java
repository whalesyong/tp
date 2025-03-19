package seedu.cookingaids.items;

//import seedu.cookingaids.items.Recipe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;

class RecipeTest {

    private Recipe testRecipe;
    private String recipeName;
    private ArrayList<String> ingredients;

//    @Test
//    public void sampleTest() {
//        assertTrue(true);
//    }

    @BeforeEach
    void setUp() {
        // Create test data before each test
        recipeName = "Pasta Carbonara";
        ingredients = new ArrayList<>(Arrays.asList("Spaghetti", "Eggs", "Pancetta", "Parmesan", "Black Pepper"));
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
        String newName = "Spaghetti Carbonara";
        testRecipe.setRecipeName(newName);
        assertEquals(newName, testRecipe.getRecipeName());
    }

    @Test
    void getIngredients() {
        assertEquals(ingredients, testRecipe.getIngredients());
        assertEquals(5, testRecipe.getIngredients().size());
        assertTrue(testRecipe.getIngredients().contains("Eggs"));
    }

    @Test
    void setIngredients() {
        ArrayList<String> newIngredients = new ArrayList<>(Arrays.asList("Flour", "Sugar", "Eggs", "Butter"));
        testRecipe.setIngredients(newIngredients);
        assertEquals(newIngredients, testRecipe.getIngredients());
        assertEquals(4, testRecipe.getIngredients().size());
        assertTrue(testRecipe.getIngredients().contains("Flour"));
        assertFalse(testRecipe.getIngredients().contains("Spaghetti"));
    }

    @Test
    void testToString() {
        String expected = "Recipe named 'Pasta Carbonara' calls for ingredients [Spaghetti, Eggs, Pancetta, Parmesan, Black Pepper]";
        assertEquals(expected, testRecipe.toString());
    }
}