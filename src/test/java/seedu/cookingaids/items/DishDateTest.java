package seedu.cookingaids.items;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DishDateTest {

    @Test
    void constructor_addLocalDateFormat_expectLocalDateSaved(){
        DishDate dishDate = new DishDate("2002-11-26");
        assertEquals(LocalDate.parse("2002-11-26"),dishDate.getDateLocalDate());
        assertEquals("26/11/2002",dishDate.toString());

    }
    @Test
    void constructor_addStringFormat_expectLocalDateNull_expectString(){
        DishDate dishDate = new DishDate("tomorrow");
        assertEquals("tomorrow",dishDate.toString());
        assertNull(dishDate.getDateLocalDate());
    }
    
}
