package seedu.cookingaids.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

class SearchTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Recipe testRecipe1;
    private Recipe testRecipe2;
    private Recipe testRecipe3;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        // Create test recipes
        // Recipe 1: Italian pasta
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("tomato", 2));
        ingredients1.add(new Ingredient("pasta", 200));

        ArrayList<String> tags1 = new ArrayList<>(Arrays.asList("italian", "dinner", "vegetarian"));
        testRecipe1 = new Recipe("Pasta Marinara", ingredients1, tags1);

        // Recipe 2: Asian stir-fry
        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("rice", 200));
        ingredients2.add(new Ingredient("chicken", 300));

        ArrayList<String> tags2 = new ArrayList<>(Arrays.asList("asian", "dinner", "spicy"));
        testRecipe2 = new Recipe("Spicy Chicken Stir-fry", ingredients2, tags2);

        // Recipe 3: Italian dessert
        ArrayList<Ingredient> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredient("mascarpone", 250));
        ingredients3.add(new Ingredient("coffee", 100));

        ArrayList<String> tags3 = new ArrayList<>(Arrays.asList("italian", "dessert", "sweet"));
        testRecipe3 = new Recipe("Tiramisu", ingredients3, tags3);

        // Add recipes to the recipe bank
        try {
            RecipeBank.addRecipeToRecipeBank(testRecipe1);
            RecipeBank.addRecipeToRecipeBank(testRecipe2);
            RecipeBank.addRecipeToRecipeBank(testRecipe3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Reset output content
        outContent.reset();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        RecipeBank.clear();
    }

    @Test
    void searchRecipes_nullInput_returnsEmptyList() {
        ArrayList<Recipe> results = Search.searchRecipes(null, "or");
        assertTrue(results.isEmpty());
        assertTrue(outContent.toString().contains("Empty search query"));
    }

    @Test
    void searchRecipes_emptyInput_returnsEmptyList() {
        ArrayList<Recipe> results = Search.searchRecipes("", "or");
        assertTrue(results.isEmpty());
        assertTrue(outContent.toString().contains("Empty search query"));
    }

    @Test
    void searchRecipes_singleTagOR_returnsMatchingRecipes() {
        ArrayList<Recipe> results = Search.searchRecipes("italian", "or");
        assertEquals(2, results.size());
        assertTrue(results.contains(testRecipe1));
        assertTrue(results.contains(testRecipe3));
        assertFalse(results.contains(testRecipe2));
    }

    @Test
    void searchRecipes_singleTagAND_returnsMatchingRecipes() {
        ArrayList<Recipe> results = Search.searchRecipes("italian", "and");
        assertEquals(2, results.size());
        assertTrue(results.contains(testRecipe1));
        assertTrue(results.contains(testRecipe3));
        assertFalse(results.contains(testRecipe2));
    }

    @Test
    void searchRecipes_multipleTagsOR_returnsMatchingRecipes() {
        ArrayList<Recipe> results = Search.searchRecipes("italian,spicy", "or");
        assertEquals(3, results.size());
        assertTrue(results.contains(testRecipe1));
        assertTrue(results.contains(testRecipe2));
        assertTrue(results.contains(testRecipe3));
    }

    @Test
    void searchRecipes_multipleTagsAND_returnsMatchingRecipes() {
        ArrayList<Recipe> results = Search.searchRecipes("italian,dinner", "and");
        assertEquals(1, results.size());
        assertTrue(results.contains(testRecipe1));
        assertFalse(results.contains(testRecipe2));
        assertFalse(results.contains(testRecipe3));
    }

    @Test
    void searchRecipes_noMatchingTags_returnsEmptyList() {
        ArrayList<Recipe> results = Search.searchRecipes("mexican,breakfast", "or");
        assertTrue(results.isEmpty());
    }

    @Test
    void searchRecipes_mixedCaseTags_performsCaseInsensitiveSearch() {
        ArrayList<Recipe> results = Search.searchRecipes("ITALIAN,DINNER", "and");
        assertEquals(1, results.size());
        assertTrue(results.contains(testRecipe1));
    }

    @Test
    void searchRecipes_tagsWithWhitespace_trimsCorrectly() {
        ArrayList<Recipe> results = Search.searchRecipes(" italian , dinner ", "and");
        assertEquals(1, results.size());
        assertTrue(results.contains(testRecipe1));
    }

    @Test
    void parseTagsString_validInput_returnsTagsList() {
        try {
            // Use reflection to test private method
            java.lang.reflect.Method method = Search.class.getDeclaredMethod("parseTagsString", String.class);
            method.setAccessible(true);

            @SuppressWarnings("unchecked")
            ArrayList<String> tags = (ArrayList<String>) method.invoke(null, "tag1,tag2,tag3");

            assertEquals(3, tags.size());
            assertTrue(tags.contains("tag1"));
            assertTrue(tags.contains("tag2"));
            assertTrue(tags.contains("tag3"));
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    void parseTagsString_nullInput_throwsInvalidInputException() {
        try {
            // Use reflection to test private method
            java.lang.reflect.Method method = Search.class.getDeclaredMethod("parseTagsString", String.class);
            method.setAccessible(true);

            method.invoke(null, (String) null);
            fail("Should have thrown InvalidInputException");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof InvalidInputException);
        }
    }

    @Test
    void parseTagsString_emptyInput_throwsInvalidInputException() {
        try {
            // Use reflection to test private method
            java.lang.reflect.Method method = Search.class.getDeclaredMethod("parseTagsString", String.class);
            method.setAccessible(true);

            method.invoke(null, "");
            fail("Should have thrown InvalidInputException");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof InvalidInputException);
        }
    }
}
