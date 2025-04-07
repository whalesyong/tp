package seedu.cookingaids.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UpdateCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        // Create test recipe
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("tomato", 2));
        ingredients.add(new Ingredient("cheese", 100));

        ArrayList<String> tags = new ArrayList<>(Arrays.asList("italian", "dinner"));
        testRecipe = new Recipe("Pasta", ingredients, tags);

        // Add recipe to the recipe bank
        RecipeBank.addRecipeToRecipeBank(testRecipe);

        // Reset output content
        outContent.reset();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        RecipeBank.clear();
    }

    @Test
    void updateRecipe_nonexistentRecipe_printsErrorMessage() {
        UpdateCommand.updateRecipe("-recipe=NonexistentRecipe -newname=NewName");
        assertTrue(outContent.toString().contains("No such recipe: NonexistentRecipe"));
    }

    @Test
    void updateRecipe_updateNameOnly_successfullyUpdatesName() {
        UpdateCommand.updateRecipe("-recipe=Pasta -newname=ItalianPasta");
        assertTrue(outContent.toString().contains("Recipe name updated to: ItalianPasta"));

        // Verify recipe name was updated
        List<Recipe> updatedRecipes = RecipeBank.getRecipeByName("ItalianPasta");
        assertFalse(updatedRecipes.isEmpty());
        assertEquals("ItalianPasta", updatedRecipes.get(0).getRecipeName());
    }

    @Test
    void updateRecipe_updateIngredientsOnly_successfullyUpdatesIngredients() {
        UpdateCommand.updateRecipe("-recipe=Pasta -newingredients=tomato,3,basil,10");
        assertTrue(outContent.toString().contains("Recipe ingredients updated successfully!"));

        // Verify ingredients were updated
        List<Recipe> updatedRecipes = RecipeBank.getRecipeByName("Pasta");
        assertFalse(updatedRecipes.isEmpty());
        assertEquals(2, updatedRecipes.get(0).getIngredients().size());
        assertEquals("tomato", updatedRecipes.get(0).getIngredients().get(0).getName());
        assertEquals(3, updatedRecipes.get(0).getIngredients().get(0).getQuantity());
    }

    @Test
    void updateRecipe_updateTagsOnly_successfullyUpdatesTags() {
        UpdateCommand.updateRecipe("-recipe=Pasta -newtags=easy,quick,vegetarian");
        assertTrue(outContent.toString().contains("Recipe tags updated successfully!"));

        // Verify tags were updated
        List<Recipe> updatedRecipes = RecipeBank.getRecipeByName("Pasta");
        assertFalse(updatedRecipes.isEmpty());
        assertEquals(3, updatedRecipes.get(0).getTags().size());
        assertTrue(updatedRecipes.get(0).getTags().contains("easy"));
        assertTrue(updatedRecipes.get(0).getTags().contains("quick"));
        assertTrue(updatedRecipes.get(0).getTags().contains("vegetarian"));
    }

    @Test
    void updateRecipe_updateMultipleFields_successfullyUpdatesAllFields() {
        UpdateCommand.updateRecipe("-recipe=Pasta -newname=PastaDeluxe " +
                "-newingredients=tomato,5,cheese,200 -newtags=gourmet,special");

        // Verify all fields were updated
        List<Recipe> updatedRecipes = RecipeBank.getRecipeByName("PastaDeluxe");
        assertFalse(updatedRecipes.isEmpty());
        Recipe updatedRecipe = updatedRecipes.get(0);

        assertEquals("PastaDeluxe", updatedRecipe.getRecipeName());
        assertEquals(2, updatedRecipe.getIngredients().size());
        assertEquals(2, updatedRecipe.getTags().size());
        assertTrue(updatedRecipe.getTags().contains("gourmet"));
    }

    @Test
    void updateRecipe_noUpdateFlags_printsErrorMessage() {
        UpdateCommand.updateRecipe("-recipe=Pasta");
        assertTrue(outContent.toString().contains("No updates specified"));
    }

    @Test
    void updateRecipe_invalidIngredientFormat_printsErrorMessage() {
        UpdateCommand.updateRecipe("-recipe=Pasta -newingredients=invalidFormat");
        assertTrue(outContent.toString().contains("Invalid ingredient input"));
    }

    @Test
    void promptUserForRecipeUpdate_singleInput_returnsCorrectRecipe() {
        // Add a second pasta recipe to create ambiguity
        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("tomato", 3));
        ingredients2.add(new Ingredient("bacon", 50));

        Recipe testRecipe2 = new Recipe("Pasta", ingredients2);
        RecipeBank.addRecipeToRecipeBank(testRecipe2);

        // Create list of recipes
        List<Recipe> recipes = RecipeBank.getRecipeByName("Pasta");

        // Simulate user input
        String input = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Recipe selectedRecipe = UpdateCommand.promptUserForRecipeUpdate(recipes);

        // Verify the correct recipe was selected
        assertEquals(testRecipe, selectedRecipe);
    }

    @Test
    void updateIngredient_invalidFormat_printsErrorMessage() {
        UpdateCommand.updateIngredient("update invalid input");
        assertTrue(outContent.toString().contains("Invalid format"));
    }

    @Test
    void updateDish_nonexistentDish_printsErrorMessage() {
        UpdateCommand.updateDish("update -dish=NonexistentDish");
        assertTrue(outContent.toString().contains("No scheduled dishes found for: NonexistentDish"));
    }

    // Additional tests for updateDish would require mocking DishCalendar and user input
    // These tests would be similar to the updateRecipe tests but with dish-specific logic
}
