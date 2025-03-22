package seedu.cookingaids.collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.items.Ingredient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IngredientStorageTest {

    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Ingredient ingredient3;

    @BeforeEach
    public void setUp() {
        // Clear the storage before each test
        IngredientStorage.getStorage().clear();

        // Initialize test ingredients
        this.ingredient1 = new Ingredient(1,"Sugar", "2025-12-31", 500);
        this.ingredient2 = new Ingredient(2,"Salt",  "2025-06-30", 300);
        this.ingredient3 = new Ingredient(3,"Sugar", "2025-12-31", 200); // Same name & expiry as ingredient1
    }

    @Test
    public void testAddToStorage_newIngredient() {
        IngredientStorage.addToStorage(ingredient1);

        assertTrue(IngredientStorage.contains("Sugar"));
        List<Ingredient> sugarList = IngredientStorage.getIngredients("Sugar");
        assertEquals(1, sugarList.size());
        assertEquals(500, sugarList.get(0).getQuantity());
    }

    @Test
    public void testAddToStorage_mergeQuantity() {
        IngredientStorage.addToStorage(ingredient1);
        IngredientStorage.addToStorage(ingredient3);

        List<Ingredient> sugarList = IngredientStorage.getIngredients("Sugar");
        assertEquals(1, sugarList.size());
        assertEquals(700, sugarList.get(0).getQuantity());
    }

    @Test
    public void testAddToStorage_differentExpiryDates() {
        Ingredient differentExpiry = new Ingredient(1,"Sugar", "2026-01-01", 200);

        IngredientStorage.addToStorage(ingredient1);
        IngredientStorage.addToStorage(differentExpiry);

        List<Ingredient> sugarList = IngredientStorage.getIngredients("Sugar");
        assertEquals(2, sugarList.size());

        assertEquals("2025-12-31", sugarList.get(0).getExpiryDate().toString());
        assertEquals("2026-01-01", sugarList.get(1).getExpiryDate().toString());
    }

    @Test
    public void testRemoveIngredient() {
        IngredientStorage.addToStorage(ingredient1);
        assertTrue(IngredientStorage.contains("Sugar"));

        IngredientStorage.removeIngredient("Sugar");
        assertFalse(IngredientStorage.contains("Sugar"));
    }

    @Test
    public void testGetIngredients_nonExistent() {
        List<Ingredient> nonExistent = IngredientStorage.getIngredients("Flour");
        assertTrue(nonExistent.isEmpty());
    }

    @Test
    public void testGetStorage_returnsCopy() {
        IngredientStorage.addToStorage(ingredient1);
        assertEquals(1, IngredientStorage.getStorage().size());
    }
}
