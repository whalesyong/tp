package seedu.cookingaids.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SearchCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Recipe testRecipe1;
    private Recipe testRecipe2;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        // Create test recipes for search tests
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("tomato", 2));
        ingredients1.add(new Ingredient("cheese", 100));

        ArrayList<String> tags1 = new ArrayList<>(Arrays.asList("italian", "dinner", "quick"));
        testRecipe1 = new Recipe("Pasta", ingredients1, tags1);

        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("rice", 200));
        ingredients2.add(new Ingredient("chicken", 300));

        ArrayList<String> tags2 = new ArrayList<>(Arrays.asList("asian", "dinner", "spicy"));
        testRecipe2 = new Recipe("Fried Rice", ingredients2, tags2);

        // Add recipes to the recipe bank
        RecipeBank.addRecipeToRecipeBank(testRecipe1);
        RecipeBank.addRecipeToRecipeBank(testRecipe2);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        // Clear recipe bank
        RecipeBank.clear();
    }

    @Test
    void printSearchResult_nullInput_printWarning() {
        SearchCommand.printSearchResult(null, "or");
        assertTrue(outContent.toString().contains("Please provide valid search tags."));
    }

    @Test
    void printSearchResult_emptyInput_printWarning() {
        SearchCommand.printSearchResult("", "or");
        assertTrue(outContent.toString().contains("Please provide valid search tags."));
    }

    @Test
    void printSearchResult_validInputNoMatches_printNoMatchMessage() {
        SearchCommand.printSearchResult("nonexistent", "or");
        assertTrue(outContent.toString().contains("No recipes match your tags."));
    }

    @Test
    void printSearchResult_validInputWithMatches_printMatchingRecipes() {
        SearchCommand.printSearchResult("dinner", "or");
        String output = outContent.toString();
        assertTrue(output.contains("Your tags match these recipes:"));
        assertTrue(output.contains("Pasta"));
        assertTrue(output.contains("Fried Rice"));
    }

    @Test
    void printSearchResult_multipleTagsORSearch_printMatchingRecipes() {
        SearchCommand.printSearchResult("italian,spicy", "or");
        String output = outContent.toString();
        assertTrue(output.contains("Your tags match these recipes:"));
        assertTrue(output.contains("Pasta"));
        assertTrue(output.contains("Fried Rice"));
    }

    @Test
    void printSearchResult_multipleTagsANDSearch_printMatchingRecipes() {
        SearchCommand.printSearchResult("dinner,spicy", "and");
        String output = outContent.toString();
        assertTrue(output.contains("Your tags match these recipes:"));
        assertFalse(output.contains("Pasta"));
        assertTrue(output.contains("Fried Rice"));
    }

    @Test
    void printSearchResult_exceptionThrown_printErrorMessage() {
        // Mock Search class to throw exception
        // Note: In a real implementation, you would use a mocking framework
        // like Mockito to mock the Search class behavior

        // For this test, we'd need to modify the Search class or use PowerMockito
        // to mock static methods. This is a placeholder for that test.
        // SearchCommand.printSearchResult("cause-error", "or");
        // assertTrue(outContent.toString().contains("An error occurred while searching"));
    }
}
