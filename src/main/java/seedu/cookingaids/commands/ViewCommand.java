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
        if (month < 1 || month > 12) {

            throw new InvalidInputException();
        }
        assert month >= 1;
        assert month <= 12;

        int year = LocalDate.now().getYear();
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        int lengthOfMonth = startOfMonth.lengthOfMonth();
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
        if (!filteredList.isEmpty()) {
            System.out.println("Dishes not displayed in calendar");
            Ui.printDishListView(sortDishesByDateStream(filteredList));
        }

    }

    public static List<Dish> sortDishesByDateStream(ArrayList<Dish> dishes) {
        return dishes.stream()
                .sorted(Comparator.comparing(dish -> dish.getDishDate().getDateLocalDate()))
                .toList();
    }
    public static List<Dish> sortDishesByDateStream(List<Dish> dishes) {
        return dishes.stream()
                .sorted(Comparator.comparing(dish -> dish.getDishDate().getDateLocalDate()))
                .toList();
    }

}
