package seedu.cookingaids.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.items.Dish;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DishCalendarTest {
    private Dish dish1;
    private Dish dish2;
    private Dish dish3;
    @BeforeEach
    public void setUp() {
        // Clear the static dishCalendar before each test
        DishCalendar.setDishCalendar(new ArrayList<>());

        // Initialize test dishes
        this.dish1 = new Dish(1, "Pasta", "2025-03-20");
        this.dish2 = new Dish(2, "Pizza", "2025-03-21");
        this.dish3 = new Dish(3, "Pasta", "2025-03-20"); // Same name as dish1 and same date
    }
    @Test
    public void testInitializeDishCalendar() {
        // Test initializing the calendar with dishes
        List<Dish> initialDishes = Arrays.asList(dish1, dish2);
        DishCalendar.initializeDishCalendar(initialDishes);

        assertEquals(2, DishCalendar.getDishCalendar().size());
        assertTrue(DishCalendar.getDishCalendar().contains(dish1));
        assertTrue(DishCalendar.getDishCalendar().contains(dish2));
    }
    @Test
    public void testGetAllDishes_expectListOfDishes() {
        // Add dishes to calendar
        DishCalendar.addDishToCalendar(this.dish1);
        DishCalendar.addDishToCalendar(this.dish2);

        // Test getting all dishes (should return a copy of the list)
        List<Dish> allDishes = DishCalendar.getAllDishes();
        assertEquals(2, allDishes.size());
        assertTrue(allDishes.contains(dish1));
        assertTrue(allDishes.contains(dish2));

    }
    @Test
    public void testAddDishToCalendar_expectCalendarContainsAddedDishes() {
        // Test adding dishes one by one
        DishCalendar.addDishToCalendar(this.dish1);
        assertEquals(1, DishCalendar.getDishCalendar().size());

        DishCalendar.addDishToCalendar(this.dish2);
        assertEquals(2, DishCalendar.getDishCalendar().size());

        // Test adding a duplicate dish (should still add it)
        DishCalendar.addDishToCalendar(this.dish3);
        assertEquals(3, DishCalendar.getDishCalendar().size());
    }

    @Test
    public void testGetDishesByName_expectListOfDishWithSameName() {
        // Add dishes to calendar
        DishCalendar.addDishToCalendar(this.dish1); // Pasta
        DishCalendar.addDishToCalendar(this.dish2); // Pizza
        DishCalendar.addDishToCalendar(this.dish3); // Another Pasta

        // Test getting dishes by name
        List<Dish> pastaDishes = DishCalendar.getDishesByName("Pasta");
        assertEquals(2, pastaDishes.size());
        assertTrue(pastaDishes.contains(this.dish1));
        assertTrue(pastaDishes.contains(this.dish3));

        // Test case insensitivity
        List<Dish> pizzaDishes = DishCalendar.getDishesByName("pizza");
        assertEquals(1, pizzaDishes.size());
        assertTrue(pizzaDishes.contains(this.dish2));

        // Test non-existent dish
        List<Dish> nonExistentDishes = DishCalendar.getDishesByName("Burger");
        assertTrue(nonExistentDishes.isEmpty());
    }

    @Test
    public void testRemoveDishInCalendar_expectListWithDishesRemoved() {
        // Add dishes to calendar
        DishCalendar.addDishToCalendar(this.dish1);
        DishCalendar.addDishToCalendar(this.dish2);
        assertEquals(2, DishCalendar.getDishCalendar().size());

        // Test removing a dish
        DishCalendar.removeDishInCalendar(this.dish1);
        assertEquals(1, DishCalendar.getDishCalendar().size());
        assertFalse(DishCalendar.getDishCalendar().contains(this.dish1));
        assertTrue(DishCalendar.getDishCalendar().contains(this.dish2));
    }

}
