package seedu.cookingaids.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteCommandTest {

    private DishCalendar dishCalendar;

    @BeforeEach
    void setUp() {
        dishCalendar = new DishCalendar();
        Dish Spaghetti = new Dish(1, "Spaghetti", "20/03/2025");
        DishCalendar.addDishToCalendar(Spaghetti);
    }

    @AfterEach
    void tearDown() {
        dishCalendar.clear();
    }

    @Test
    void assertDeleteDishSuccessful() {
        assertTrue(dishCalendar.containsDish("Spaghetti"));
        DeleteCommand.deleteDish("delete -dish=Spaghetti");
        assertFalse(dishCalendar.containsDish("Spaghetti"));
    }

    @Test
    void assertDeleteIngredientSuccessful() {
        Ingredient tomato = new Ingredient(2, "Tomato");  // Create ingredient
        IngredientStorage.addToStorage(tomato);  // Add to storage
        assertTrue(IngredientStorage.contains("Tomato")); // Verify addition
        DeleteCommand.deleteIngredient("delete -ingredient=Tomato");  // Delete
        assertFalse(IngredientStorage.contains("Tomato")); // Verify deletion
    }

    @Test
    void assertDeleteRecipeSuccessful() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Bread");
        ingredients.add("Tomato");
        ingredients.add("Egg");
        Recipe sandwich = new Recipe("Sandwich", ingredients);  // Create recipe
        RecipeBank.addRecipeToRecipeBank(sandwich);  // Add to recipe bank
        assertTrue(RecipeBank.contains("Sandwich")); // Verify addition
        RecipeBank.removeRecipeFromRecipeBank("delete -recipe=Sandwich");  // Delete
        assertFalse(RecipeBank.contains("Sandwich")); // Verify deletion
    }
}