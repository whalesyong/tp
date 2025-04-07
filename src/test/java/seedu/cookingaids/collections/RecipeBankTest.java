package seedu.cookingaids.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
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
        RecipeBank.clear();

        // Create test recipes
        ArrayList<Ingredient> ingredients1 = new ArrayList<>(Arrays.asList(
                new Ingredient("Pasta", 2),
                new Ingredient("Eggs", 3),
                new Ingredient("Cheese", 1)
        ));
        testRecipe1 = new Recipe("Spaghetti Carbonara", ingredients1);

        ArrayList<Ingredient> ingredients2 = new ArrayList<>(Arrays.asList(
                new Ingredient("Flour", 2),
                new Ingredient("Sugar", 1),
                new Ingredient("Cocoa", 1),
                new Ingredient("Eggs", 3),
                new Ingredient("Butter", 1)
        ));
        testRecipe2 = new Recipe("Chocolate Cake", ingredients2);

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
        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe1);
            RecipeBank.addRecipeToRecipeBank(testRecipe2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // When getting the recipe bank
        ArrayList<Recipe> recipeBank = RecipeBank.getRecipeBank();

        // Then it should contain the added recipes
        assertEquals(2, recipeBank.size());
        assertTrue(recipeBank.contains(testRecipe1));
        assertTrue(recipeBank.contains(testRecipe2));
    }

    @Test
    void getRecipeBankSize() {
        // Given an empty recipe bank
        assertEquals(0, RecipeBank.getRecipeBankSize());

        // When adding recipes
        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, RecipeBank.getRecipeBankSize());

        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(2, RecipeBank.getRecipeBankSize());
    }

    @Test
    void removeRecipeFromRecipeBank() {
        // Given a recipe bank with test recipes
        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe1);
            RecipeBank.addRecipeToRecipeBank(testRecipe2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(2, RecipeBank.getRecipeBank().size());

        // When removing a recipe
        RecipeBank.removeRecipeFromRecipeBank(testRecipe1);

        // Then the recipe bank should no longer contain that recipe
        assertEquals(1, RecipeBank.getRecipeBank().size());
        assertFalse(RecipeBank.getRecipeBank().contains(testRecipe1));
        assertTrue(RecipeBank.getRecipeBank().contains(testRecipe2));
    }

    @Test
    void getRecipeByName() {
        // Given a recipe bank with test recipes
        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe1);
            RecipeBank.addRecipeToRecipeBank(testRecipe2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // When getting recipes by name
        List<Recipe> foundRecipes = RecipeBank.getRecipeByName("Spaghetti Carbonara");

        // Then it should return the correct recipes
        assertEquals(1, foundRecipes.size());
        assertEquals(testRecipe1, foundRecipes.get(0));

        // Test case insensitivity
        List<Recipe> caseInsensitiveRecipes = RecipeBank.getRecipeByName("spaghetti carbonara");
        assertEquals(1, caseInsensitiveRecipes.size());
        assertEquals(testRecipe1, caseInsensitiveRecipes.get(0));

        // Test non-existent recipe
        List<Recipe> nonExistentRecipes = RecipeBank.getRecipeByName("Nonexistent Recipe");
        assertTrue(nonExistentRecipes.isEmpty());
    }

    @Test
    void contains() {
        // Given a recipe bank with test recipes
        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe1);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    //    @Test
    //    void getIngredientList() {
    //        // Given a recipe bank with a recipe
    //        RecipeBank.addRecipeToRecipeBank(testRecipe1);
    //
    //        // When getting ingredients for a dish with matching recipe
    //        Dish dish = new Dish("Spaghetti Carbonara", "2025-04-05");
    //        List<Ingredient> ingredients = RecipeBank.getIngredientList(dish);
    //
    //        // Then it should return the correct ingredients
    //        assertNotNull(ingredients);
    //        assertEquals(3, ingredients.size());
    //        assertTrue(ingredients.stream().anyMatch(i -> i.getName().equals("Pasta")));
    //
    //        // Test with non-existent dish
    //        Dish nonExistentDish = new Dish("Nonexistent Dish", "2025-04-05");
    //        List<Ingredient> nonExistentIngredients = RecipeBank.getIngredientList(nonExistentDish);
    //        assertNull(nonExistentIngredients);
    //    }

    @Test
    void clear() {
        // Given a recipe bank with test recipes
        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe1);
            RecipeBank.addRecipeToRecipeBank(testRecipe2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(2, RecipeBank.getRecipeBank().size());

        // When clearing the recipe bank
        RecipeBank.clear();

        // Then it should be empty
        assertEquals(0, RecipeBank.getRecipeBank().size());
        assertTrue(RecipeBank.getRecipeBank().isEmpty());
    }
}
