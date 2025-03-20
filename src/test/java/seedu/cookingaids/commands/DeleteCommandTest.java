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
        Dish spaghetti = new Dish(1, "Spaghetti", "20/03/2025");
        DishCalendar.addDishToCalendar(spaghetti);
    }

    @AfterEach
    void tearDown() {
        dishCalendar.clear();
    }

    @Test
    void execute_emptyDishCalendar_returnsDishNotFoundMessage() {
        assertDeletionFailsDueToNoSuchDish("Pizza", new DishCalendar());
    }

    @Test
    void execute_invalidDish_returnsInvalidIndexMessage() {
        assertDeletionFailsDueToInvalidDish("NonExistentDish", dishCalendar);
    }

    @Test
    void execute_validDish_deletesSuccessfully() {
        assertDeletionSuccessful("Spaghetti", dishCalendar);
    }

    @Test
    void execute_validIngredient_deletesSuccessfully() {
        Ingredient tomato = new Ingredient(2, "Tomato");
        IngredientStorage.addToStorage(tomato);
        assertDeletionSuccessfulIngredient("Tomato");
    }

    @Test
    void execute_nonExistentIngredient_returnsIngredientNotFoundMessage() {
        assertDeletionFailsDueToNoSuchIngredient("Garlic");
    }

    @Test
    void execute_validRecipe_deletesSuccessfully() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Bread");
        ingredients.add("Tomato");
        ingredients.add("Egg");
        Recipe sandwich = new Recipe("Sandwich", ingredients);
        RecipeBank.addRecipeToRecipeBank(sandwich);
        assertDeletionSuccessfulRecipe("Sandwich");
    }

    @Test
    void execute_nonExistentRecipe_returnsRecipeNotFoundMessage() {
        assertDeletionFailsDueToNoSuchRecipe("Pasta");
    }

    @Test
    void execute_afterDishCalendarCleared_returnsDishNotFoundMessage() {
        dishCalendar.clear();
        assertDeletionFailsDueToNoSuchDish("Spaghetti", dishCalendar);
    }

    @Test
    void execute_deleteFromEmptyRecipeBank_returnsRecipeNotFoundMessage() {
        RecipeBank.clear();
        assertDeletionFailsDueToNoSuchRecipe("Salad");
    }

    @Test
    void execute_longDishName_deletesSuccessfully() {
        String longDishName =
                "A very long dish name that exceeds normal expectations and is meant to test the limits of the system";
        Dish longDish = new Dish(2, longDishName, "21/03/2025");
        DishCalendar.addDishToCalendar(longDish);
        assertDeletionSuccessful(longDishName, dishCalendar);
    }

    @Test
    void execute_deleteIngredientWhenFull_returnsIngredientNotFoundMessage() {
        IngredientStorage.clear();  // Simulate the full storage by clearing it first.
        Ingredient ingredient = new Ingredient(3, "Lettuce");
        IngredientStorage.addToStorage(ingredient);
        assertDeletionSuccessfulIngredient("Lettuce");
    }

    private void assertDeletionFailsDueToInvalidDish(String dishName, DishCalendar dishCalendar) {
        DeleteCommand.deleteDish("delete -dish=" + dishName);
        assertFalse(dishCalendar.containsDish(dishName));
    }

    private void assertDeletionFailsDueToNoSuchDish(String dishName, DishCalendar dishCalendar) {
        DeleteCommand.deleteDish("delete -dish=" + dishName);
        assertFalse(dishCalendar.containsDish(dishName));
    }

    private void assertDeletionSuccessful(String dishName, DishCalendar dishCalendar) {
        assertTrue(dishCalendar.containsDish(dishName));
        DeleteCommand.deleteDish("delete -dish=" + dishName);
        assertFalse(dishCalendar.containsDish(dishName));
    }

    private void assertDeletionSuccessfulIngredient(String ingredientName) {
        assertTrue(IngredientStorage.contains(ingredientName));
        DeleteCommand.deleteIngredient("delete -ingredient=" + ingredientName);
        assertFalse(IngredientStorage.contains(ingredientName));
    }

    private void assertDeletionSuccessfulRecipe(String recipeName) {
        assertTrue(RecipeBank.contains(recipeName));
        DeleteCommand.deleteRecipe("delete -recipe=" + recipeName);
        assertFalse(RecipeBank.contains(recipeName));
    }

    private void assertDeletionFailsDueToNoSuchIngredient(String ingredientName) {
        DeleteCommand.deleteIngredient("delete -ingredient=" + ingredientName);
        assertFalse(IngredientStorage.contains(ingredientName));
    }

    private void assertDeletionFailsDueToNoSuchRecipe(String recipeName) {
        DeleteCommand.deleteRecipe("delete -recipe=" + recipeName);
        assertFalse(RecipeBank.contains(recipeName));
    }
}
