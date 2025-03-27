package seedu.cookingaids.ui;

import seedu.cookingaids.items.Dish;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarPrinter {


    public static void printMonthCalendar(int year, Month month, ArrayList<Dish> list) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate today = LocalDate.now();
        int daysInMonth = firstDay.getMonth().length(firstDay.isLeapYear());
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue();

        // Print header
        System.out.println("\n" + " ".repeat(10) +
                month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase() +
                " " + year + "\n");

        // Print Days of Week
        String[] weekDays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        for (String day : weekDays) {
            System.out.printf("%10s", day);
        }
        System.out.println("\n" + "-".repeat(70));

        // Adjust for first day of the week
        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print("          ");
        }

        // Print days
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            // Highlight today's date
            if (currentDate.equals(today)) {
                System.out.printf("\u001B[1;31m%10d\u001B[0m", day);
            } else {
                System.out.printf("%10d", day);
            }

            // Move to next line after Sunday
            if ((day + firstDayOfWeek - 1) % 7 == 0) {
                printDishOnCalendar(dayOfWeek, "kdhb k jks s");
                System.out.println();
                System.out.println();
            }
        }
        System.out.println();
    }

    private static void printDishOnCalendar(DayOfWeek day, String name) {
        DayOfWeek[] weekDays = {DayOfWeek.of(1), DayOfWeek.of(2), DayOfWeek.of(3),
                DayOfWeek.of(4), DayOfWeek.of(5), DayOfWeek.of(6), DayOfWeek.of(7)};
        for (DayOfWeek d : weekDays) {
            if (d.equals(day)) {
                System.out.printf("%10.10s", name);
            } else {
                System.out.printf("%10s", " ");
            }
        }
        System.out.println();
    }

}
