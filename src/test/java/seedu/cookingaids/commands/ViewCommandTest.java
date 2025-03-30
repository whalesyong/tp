package seedu.cookingaids.commands;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.DishDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewCommandTest {

    private Dish dish1, dish2, dish3;

    @BeforeEach
    void setUp() {
        dish1 = mock(Dish.class);
        dish2 = mock(Dish.class);
        dish3 = mock(Dish.class);

        DishDate date1 = new DishDate("10/03/2025");
        DishDate date2 = new DishDate("20/03/2025");
        DishDate date3 = new DishDate("05/04/2025");

        when(dish1.getDishDate()).thenReturn(date1);
        when(dish2.getDishDate()).thenReturn(date2);
        when(dish3.getDishDate()).thenReturn(date3);
    }

    @Test
    void sortDishesByDateStream_validInput_sortedList() {
        ArrayList<Dish> dishes = new ArrayList<>(List.of(dish2, dish1));
        List<Dish> sortedDishes = ViewCommand.sortDishesByDateStream(dishes);
        assertEquals(dish1, sortedDishes.get(0));
        assertEquals(dish2, sortedDishes.get(1));
    }

    @Test
    void sortDishesByDateStream_invalidInput_unsortedList() {
        ArrayList<Dish> dishes = new ArrayList<>(List.of(dish2, dish1));
        List<Dish> sortedDishes = ViewCommand.sortDishesByDateStream(dishes);
        assertNotEquals(dish2, sortedDishes.get(0));
    }

}
