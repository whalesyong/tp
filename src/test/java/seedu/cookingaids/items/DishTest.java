package seedu.cookingaids.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishTest {
    @Test
    void constructor_inputNameDate_expectReceived(){

        Dish dish1 = new Dish( "Pasta", "2025/03/20");


        assertEquals("pasta", dish1.getName());
        assertEquals("20/03/2025", dish1.getDishDate().toString());




    }
    @Test
    void constructor_inputMissingDate_expectReceived(){

        // Test dish with null date (should default to "None")
        Dish dish2 = new Dish( "Pizza", null);

        assertEquals("pizza", dish2.getName());
        assertEquals("None", dish2.getDishDate().toString());

    }


}
