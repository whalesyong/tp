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
                new Ingredient("Spaghetti", 2),
                new Ingredient("Egg", 3),
                new Ingredient("Parmesan", 1),
                new Ingredient("Black Pepper", 1)
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
    void getName() {
        assertEquals(recipeName, testRecipe.getName());
    }

    @Test
    void setRecipeName() {
        String newName = "Spaghetti_Carbonara";
        testRecipe.setRecipeName(newName);
        assertEquals(newName, testRecipe.getRecipeName());
        assertEquals(newName, testRecipe.getName());
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
    void getIngredientsString() {
        String expected = "Spaghetti (2), Egg (3), Parmesan (1), Black Pepper (1)";
        assertEquals(expected, testRecipe.getIngredientsString());
    }

    @Test
    void setIngredients() {
        ArrayList<Ingredient> newIngredients = new ArrayList<>(Arrays.asList(
                new Ingredient("Flour", 2),
                new Ingredient("Sugar", 1),
                new Ingredient("Egg", 4),
                new Ingredient("Butter", 1)
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
        String expected = "Recipe named 'Pasta Carbonara' needs ingredients Spaghetti (2), " +
                "Egg (3), Parmesan (1), Black Pepper (1)";
        assertEquals(expected, testRecipe.toString());
    }
}
