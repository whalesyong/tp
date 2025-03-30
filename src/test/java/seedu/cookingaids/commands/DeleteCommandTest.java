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
        Dish spaghetti = new Dish( "spaghetti", "20/03/2025");
        DishCalendar.addDishToCalendar(spaghetti);
    }

    @AfterEach
    void tearDown() {
        dishCalendar.clear();
    }

    @Test
    void execute_emptyDishCalendar_returnsDishNotFoundMessage() {
        assertDeletionFailsDueToNoSuchDish("pizza", new DishCalendar());
    }

    @Test
    void execute_invalidDish_returnsInvalidIndexMessage() {
        assertDeletionFailsDueToInvalidDish("NonExistentDish", dishCalendar);
    }

    @Test
    void execute_validDish_deletesSuccessfully() {
        assertDeletionSuccessful("spaghetti", dishCalendar);
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
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "Bread"));
        ingredients.add( new Ingredient(2,"Tomato"));
        ingredients.add(new Ingredient(3, "Egg"));
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
        assertDeletionFailsDueToNoSuchDish("spaghetti", dishCalendar);
    }

    @Test
    void execute_deleteFromEmptyRecipeBank_returnsRecipeNotFoundMessage() {
        RecipeBank.clear();
        assertDeletionFailsDueToNoSuchRecipe("salad");
    }

    @Test
    void execute_longDishName_deletesSuccessfully() {
        String longDishName =
                "a_very_long_dish_name_that_exceeds_normal_expectations_and_is_meant_to_test_the_limits_of_the_system";
        Dish longDish = new Dish( longDishName, "21/03/2025");
        DishCalendar.addDishToCalendar(longDish);
        assertDeletionSuccessful(longDishName, dishCalendar);
    }

    @Test
    void execute_deleteIngredientWhenFull_returnsIngredientNotFoundMessage() throws NullPointerException{
        IngredientStorage.clear();  // Simulate the full storage by clearing it first.
        Ingredient ingredient = new Ingredient(3, "lettuce");
        IngredientStorage.addToStorage(ingredient);
        assertDeletionSuccessfulIngredient("lettuce");
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
