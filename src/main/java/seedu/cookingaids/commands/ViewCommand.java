package seedu.cookingaids.commands;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.ui.CalendarPrinter;
import seedu.cookingaids.ui.Ui;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ViewCommand {
    public static final String COMMAND_WORD = "view";

    public static void displayDishMonth(int month) throws InvalidInputException {
        if (month <1 || month >12){

            throw new InvalidInputException();
        }
        int year = LocalDate.now().getYear();
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        int lengthOfMonth = LocalDate.now().lengthOfMonth();
        LocalDate endOfMonth = startOfMonth.plusDays(lengthOfMonth);

        ArrayList<Dish> listOfDish = DishCalendar.getDishCalendar();
        ArrayList<Dish> filteredList = new ArrayList<>();
        for (Dish dish : listOfDish) {
            LocalDate dishDate = dish.getDishDate().getDateLocalDate();
            if (dishDate != null && dishDate.isAfter(startOfMonth.minusDays(1)) && dishDate.isBefore(endOfMonth)) {
                filteredList.add(dish);
            }
        }
        CalendarPrinter.printMonthCalendar(year, Month.of(month), filteredList);
        Ui.printDishListView(sortDishesByDateStream(filteredList));

    }
    public static List<Dish> sortDishesByDateStream(ArrayList<Dish> dishes) {
        return dishes.stream()
                .sorted(Comparator.comparing(dish -> dish.getDishDate().getDateLocalDate()))
                .toList();
    }

}
