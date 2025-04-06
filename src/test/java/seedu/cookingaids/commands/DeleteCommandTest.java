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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteCommandTest {

    private DishCalendar dishCalendar;
    private RecipeBank recipeBank;

    @BeforeEach
    void setUp() {
        dishCalendar = new DishCalendar();
        Dish spaghetti = new Dish( "spaghetti", "20/03/2025");
        DishCalendar.addDishToCalendar(spaghetti);
        recipeBank = new RecipeBank();
    }

    @AfterEach
    void tearDown() {
        dishCalendar.clear();
        recipeBank.clear();
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
        Ingredient tomato = new Ingredient("tomato");
        IngredientStorage.addToStorage(tomato);
        assertDeletionSuccessfulIngredient("tomato");
    }

    @Test
    void execute_nonExistentIngredient_returnsIngredientNotFoundMessage() {
        assertDeletionFailsDueToNoSuchIngredient("garlic");
    }

    @Test
    void execute_validRecipe_deletesSuccessfully() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("bread", 2));
        ingredients.add(new Ingredient("tomato", 1));
        ingredients.add(new Ingredient("egg", 1));
        Recipe sandwich = new Recipe("sandwich", ingredients);
        RecipeBank.addRecipeToRecipeBank(sandwich);

        assertTrue(RecipeBank.contains("sandwich"));
        assertEquals(1, RecipeBank.getRecipeBankSize());

        DeleteCommand.removeRecipe(sandwich);

        assertFalse(RecipeBank.contains("sandwich"));
        assertEquals(0, RecipeBank.getRecipeBankSize());

    }

    @Test
    void execute_nonExistentRecipe_printsNoRecipeFoundMessage() {
        DeleteCommand.deleteRecipe("delete -recipe=NonExistentRecipe");
    }

    @Test
    void execute_deleteFromEmptyRecipeBank_returnsRecipeNotFoundMessage() {
        recipeBank.clear();
        DeleteCommand.deleteRecipe("delete -recipe=AnyRecipe");

    }

    @Test
    void execute_multipleRecipesWithSameName_promptsUserToChoose() {
        // Add two recipes with the same name but different ingredients
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("bread", 2));
        ingredients1.add(new Ingredient("tomato", 1));
        Recipe sandwich1 = new Recipe("sandwich", ingredients1);

        ArrayList<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("bread", 2));
        ingredients2.add(new Ingredient("ham", 1));
        ingredients2.add(new Ingredient("cheese", 1));
        Recipe sandwich2 = new Recipe("sandwich", ingredients2);

        RecipeBank.addRecipeToRecipeBank(sandwich1);
        RecipeBank.addRecipeToRecipeBank(sandwich2);

        assertEquals(2, RecipeBank.getRecipeByName("sandwich").size());
    }

    @Test
    void execute_nonExistentRecipe_returnsRecipeNotFoundMessage() {
        assertDeletionFailsDueToNoSuchRecipe("30");
    }

    @Test
    void execute_afterDishCalendarCleared_returnsDishNotFoundMessage() {
        dishCalendar.clear();
        assertDeletionFailsDueToNoSuchDish("2", dishCalendar);
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
        Ingredient ingredient = new Ingredient( "lettuce");
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
        DeleteCommand.deleteRecipe("delete -recipe=3");
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
