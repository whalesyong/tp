package seedu.cookingaids.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {

    @Test
    void constructor_inputNameQuantity_expectReceived() {
        Ingredient ingredient = new Ingredient(1, "Tomato", 5);
        assertEquals(1, ingredient.getId());
        assertEquals("Tomato", ingredient.getName());
        assertEquals(5, ingredient.getQuantity());
        assertEquals("None", ingredient.getExpiryDate().toString());
    }

    @Test
    void constructor_inputNameExpiryQuantity_expectReceived() {
        Ingredient ingredient = new Ingredient(2, "Milk", "2025-04-15", 10);
        assertEquals(2, ingredient.getId());
        assertEquals("Milk", ingredient.getName());
        assertEquals(10, ingredient.getQuantity());
        assertEquals("15/04/2025", ingredient.getExpiryDate().toString());
    }

    @Test
    void addQuantity_positiveValue_expectIncreasedQuantity() {
        Ingredient ingredient = new Ingredient(3, "Eggs", 12);
        ingredient.addQuantity(8);
        assertEquals(20, ingredient.getQuantity());
    }

    @Test
    void removeQuantity_positiveValue_expectDecreasedQuantity() {
        Ingredient ingredient = new Ingredient(4, "Butter", 15);
        ingredient.removeQuantity(5);
        assertEquals(10, ingredient.getQuantity());
    }

    @Test
    void removeQuantity_exceedingAmount_expectNegativeQuantity() {
        Ingredient ingredient = new Ingredient(5, "Flour", 3);
        ingredient.removeQuantity(10);
        assertEquals(-7, ingredient.getQuantity());
    }

    @Test
    void toString_withExpiry_expectCorrectFormat() {
        Ingredient ingredient = new Ingredient(6, "Sugar", "2025-06-30", 25);
        assertEquals("Sugar (25, Expiry: 30/06/2025)", ingredient.toString());
    }

    @Test
    void toString_withoutExpiry_expectCorrectFormat() {
        Ingredient ingredient = new Ingredient(7, "Salt", 50);
        assertEquals("Salt (50, Expiry: None)", ingredient.toString());
    }

    @Test
    void displayInfo_expectCorrectOutput() {
        Ingredient ingredient = new Ingredient(8, "Cheese", "2025-12-01", 7);
        ingredient.displayInfo();
        // This will print to console, no assertion needed for manual checking
    }
}
