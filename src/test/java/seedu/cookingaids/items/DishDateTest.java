package seedu.cookingaids.items;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishDateTest {

    @Test
    void constructor_addLocalDateFormat_expectLocalDateSaved(){
        DishDate dishDate = new DishDate("2002-11-26");
        assertEquals(LocalDate.parse("2002-11-26"),dishDate.getDateLocalDate());
        assertEquals("26/11/2002",dishDate.toString());

    }
    @Test
    void constructor_addStringFormat_expectLocalDate(){
        DishDate dishDate = new DishDate("tomorrow");
        String dishString =  dishDate.toString();
        assert dishString != null : "Dish fields should not be null";
        assertEquals(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),dishString);

    }
    
}
