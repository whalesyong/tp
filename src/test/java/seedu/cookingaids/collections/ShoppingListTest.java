package seedu.cookingaids.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.items.Ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



class ShoppingListTest {

    @BeforeEach
    void setUp() {
        ShoppingList.getShoppingList().clear();
    }

    @Test
    void addToShoppingList_newIngredient_addedSuccessfully() {
        Ingredient ingredient = new Ingredient("tomato", 2);
        ShoppingList.addToShoppingList(ingredient);
        assertTrue(ShoppingList.contains("tomato"));
        assertEquals(2, ShoppingList.getIngredientQuantity("tomato"));
    }

    @Test
    void addToShoppingList_existingIngredient_quantityUpdated() {
        Ingredient ingredient1 = new Ingredient("tomato", 2);
        Ingredient ingredient2 = new Ingredient("tomato", 3);
        ShoppingList.addToShoppingList(ingredient1);
        ShoppingList.addToShoppingList(ingredient2);
        assertEquals(5, ShoppingList.getIngredientQuantity("tomato"));
    }

    @Test
    void getIngredientQuantity_existingIngredient_correctQuantityReturned() {
        Ingredient ingredient = new Ingredient("onion", 4);
        ShoppingList.addToShoppingList(ingredient);
        assertEquals(4, ShoppingList.getIngredientQuantity("onion"));
    }

    @Test
    void getIngredientQuantity_nonExistingIngredient_returnsZero() {
        assertEquals(0, ShoppingList.getIngredientQuantity("Garlic"));
    }



    @Test
    void removeFromShoppingList_partialQuantity_quantityReduced() {
        Ingredient ingredient = new Ingredient("pepper", 6);
        ShoppingList.addToShoppingList(ingredient);
        ShoppingList.removeFromShoppingList(new Ingredient("pepper", 4));
        assertEquals(2, ShoppingList.getIngredientQuantity("pepper"));
    }

    @Test
    void removeFromShoppingList_exactQuantity_ingredientRemoved() {
        Ingredient ingredient = new Ingredient("salt", 5);
        ShoppingList.addToShoppingList(ingredient);
        ShoppingList.removeFromShoppingList(new Ingredient("salt", 5));
        assertFalse(ShoppingList.contains("salt"));
    }


}
