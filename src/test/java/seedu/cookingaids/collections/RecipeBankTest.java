package seedu.cookingaids.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.cookingaids.items.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RecipeBankTest {

    private Recipe testRecipe1;
    private Recipe testRecipe2;
    private List<Recipe> testRecipes;

    @BeforeEach
    void setUp() {
        // Clear the recipe bank before each test to ensure independence
        RecipeBank.getRecipeBank().clear();

        // Create test recipes
        testRecipe1 = new Recipe("Spaghetti Carbonara");
        testRecipe1.setIngredients(new ArrayList<>(Arrays.asList("Pasta", "Eggs", "Pancetta", "Cheese")));

        testRecipe2 = new Recipe("Chocolate Cake");
        testRecipe2.setIngredients(new ArrayList<>(Arrays.asList("Flour", "Sugar", "Cocoa", "Eggs", "Butter")));

        testRecipes = Arrays.asList(testRecipe1, testRecipe2);
    }

    @Test
    void initializeRecipeBank() {
        // Given an empty recipe bank
        assertTrue(RecipeBank.getRecipeBank().isEmpty());

        // When initializing with test recipes
        RecipeBank.initializeRecipeBank(testRecipes);

        // Then the recipe bank should contain those recipes
        assertEquals(2, RecipeBank.getRecipeBank().size());
        assertTrue(RecipeBank.getRecipeBank().contains(testRecipe1));
        assertTrue(RecipeBank.getRecipeBank().contains(testRecipe2));
    }

    @Test
    void addRecipeToRecipeBank() {
        // Given an empty recipe bank
        assertTrue(RecipeBank.getRecipeBank().isEmpty());

        // When adding a recipe
        RecipeBank.addRecipeToRecipeBank(testRecipe1);

        // Then the recipe bank should contain that recipe
        assertEquals(1, RecipeBank.getRecipeBank().size());
        assertTrue(RecipeBank.getRecipeBank().contains(testRecipe1));

        // When adding another recipe
        RecipeBank.addRecipeToRecipeBank(testRecipe2);

        // Then the recipe bank should contain both recipes
        assertEquals(2, RecipeBank.getRecipeBank().size());
        assertTrue(RecipeBank.getRecipeBank().contains(testRecipe2));
    }

    @Test
    void getRecipeBank() {
        // Given a recipe bank with test recipes
        RecipeBank.addRecipeToRecipeBank(testRecipe1);
        RecipeBank.addRecipeToRecipeBank(testRecipe2);

        // When getting the recipe bank
        ArrayList<Recipe> recipeBank = RecipeBank.getRecipeBank();

        // Then it should contain the added recipes
        assertEquals(2, recipeBank.size());
        assertTrue(recipeBank.contains(testRecipe1));
        assertTrue(recipeBank.contains(testRecipe2));
    }

    @Test
    void removeRecipeFromRecipeBank() {
        // Given a recipe bank with test recipes
        RecipeBank.addRecipeToRecipeBank(testRecipe1);
        RecipeBank.addRecipeToRecipeBank(testRecipe2);
        assertEquals(2, RecipeBank.getRecipeBank().size());

        // When removing a recipe
        RecipeBank.removeRecipeFromRecipeBank("delete -recipe=Spaghetti Carbonara");

        // Then the recipe bank should no longer contain that recipe
        assertEquals(1, RecipeBank.getRecipeBank().size());
        assertFalse(RecipeBank.getRecipeBank().contains(testRecipe1));
        assertTrue(RecipeBank.getRecipeBank().contains(testRecipe2));

        // When removing a recipe that doesn't exist
        RecipeBank.removeRecipeFromRecipeBank("delete -recipe=Nonexistent Recipe");

        // Then the recipe bank should remain unchanged
        assertEquals(1, RecipeBank.getRecipeBank().size());
        assertTrue(RecipeBank.getRecipeBank().contains(testRecipe2));
    }

    @Test
    void contains() {
        // Given a recipe bank with test recipes
        RecipeBank.addRecipeToRecipeBank(testRecipe1);

        // When checking if it contains a recipe by name
        boolean containsExisting = RecipeBank.contains("Spaghetti Carbonara");
        boolean containsNonexistent = RecipeBank.contains("Nonexistent Recipe");

        // Then it should return true for existing recipes and false for nonexistent ones
        assertTrue(containsExisting);
        assertFalse(containsNonexistent);

        // Test case insensitivity
        boolean containsCaseInsensitive = RecipeBank.contains("spaghetti carbonara");
        assertTrue(containsCaseInsensitive);
    }
}
