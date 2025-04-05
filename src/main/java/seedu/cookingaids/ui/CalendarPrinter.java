package seedu.cookingaids.ui;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import seedu.cookingaids.items.Dish;

public class CalendarPrinter {
    public static final String BLANK_SPACE = "          ";

    public static void printMonthCalendar(int year, Month month, ArrayList<Dish> list) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate today = LocalDate.now();
        int daysInMonth = firstDay.getMonth().length(firstDay.isLeapYear());
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue();
        int secondRowFirstDate = 9 - firstDayOfWeek;
        int thirdRowFirstDate = secondRowFirstDate + 7;
        int fourthRowFirstDate = thirdRowFirstDate + 7;
        int fifthRowFirstDate = fourthRowFirstDate + 7;
        int sixthRowFirstDate = fifthRowFirstDate + 7;


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

        printFirstCalendarRow(year, month, list, firstDayOfWeek, today);


        printCalendarRow(year, month, secondRowFirstDate, daysInMonth, list);
        printCalendarRow(year, month, thirdRowFirstDate, daysInMonth, list);
        printCalendarRow(year, month, fourthRowFirstDate, daysInMonth, list);
        printCalendarRow(year, month, fifthRowFirstDate, daysInMonth, list);
        printCalendarRow(year, month, sixthRowFirstDate, daysInMonth, list);


        System.out.println();
    }

    private static void printCalendarRow(int year, Month month, int startDate, int daysInMonth, ArrayList<Dish> list) {

        for (int day = startDate; day <= startDate + 6; day++) {
            if (day > daysInMonth) {
                break;
            }
            LocalDate currentDate = LocalDate.of(year, month, day);
            LocalDate today = LocalDate.now();

            if (currentDate.equals(today)) {
                System.out.printf("\u001B[1;31m%10d\u001B[0m", day);
            } else {
                System.out.printf("%10d", day);
            }
        }
        System.out.println();
        printFoodRows(year, month, startDate, daysInMonth, list);
    }

    private static void printFoodRows(int year, Month month, int startDate, int daysInMonth, ArrayList<Dish> list) {
        for (int row = 0; row < 2; row++) {
            for (int day = startDate; day <= startDate + 6; day++) {
                if (day > daysInMonth) {
                    break;
                }
                LocalDate currentDate = LocalDate.of(year, month, day);
                Optional<Dish> firstDish = list.stream()
                        .filter(dish -> dish.getDishDate().getDateLocalDate().equals(currentDate)).findFirst();
                Dish dish = firstDish.orElse(null); // Returns null if no dish found
                if (dish != null) {
                    System.out.printf("%10.9s", dish.getName());
                    list.remove(dish);
                } else {
                    System.out.printf(BLANK_SPACE);
                }
            }
            System.out.println();
        }

        for (int day = startDate; day <= startDate + 6; day++) {
            if (day > daysInMonth) {
                break;
            }
            LocalDate currentDate = LocalDate.of(year, month, day);
            List<Dish> listDish = list.stream()
                    .filter(dish -> dish.getDishDate().getDateLocalDate().equals(currentDate)).toList();

            if (listDish.size() == 1) {
                System.out.printf("%10.9s", listDish.get(0).getName());
                list.remove(listDish.get(0));
            } else if (listDish.size() > 1) {
                System.out.printf("%10.9s", "+" + listDish.size() + " more");
            } else {
                System.out.printf(BLANK_SPACE);
            }
        }
        System.out.println();
    }


    private static void printFirstCalendarRow(
            int year
            , Month month
            , ArrayList<Dish> list
            , int firstDayOfWeek
            , LocalDate today) {
        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print(BLANK_SPACE);
        }
        for (int day = 1; day <= 8 - firstDayOfWeek; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            if (currentDate.equals(today)) {
                System.out.printf("\u001B[1;31m%10d\u001B[0m", day);
            } else {
                System.out.printf("%10d", day);
            }
        }
        System.out.println();
        printFirstFoodRows(year, month, list, firstDayOfWeek);
    }

    private static void printFirstFoodRows(int year, Month month, ArrayList<Dish> list, int firstDayOfWeek) {
        for (int row = 0; row < 2; row++) {
            for (int i = 1; i < firstDayOfWeek; i++) {
                System.out.print(BLANK_SPACE);
            }
            for (int day = 1; day <= 7 - firstDayOfWeek; day++) {
                LocalDate currentDate = LocalDate.of(year, month, day);
                Optional<Dish> firstDish = list.stream()
                        .filter(dish -> dish.getDishDate().getDateLocalDate().equals(currentDate)).findFirst();
                Dish dish = firstDish.orElse(null); // Returns null if no dish found
                if (dish != null) {
                    System.out.printf("%10.9s", dish.getName());
                    list.remove(dish);
                } else {
                    System.out.printf(BLANK_SPACE);
                }
            }
            System.out.println();
        }
        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print(BLANK_SPACE);
        }
        for (int day = 1; day <= 7 - firstDayOfWeek; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);
            List<Dish> listDish = list.stream()
                    .filter(dish -> dish.getDishDate().getDateLocalDate().equals(currentDate)).toList();

            if (listDish.size() == 1) {
                System.out.printf("%10.9s", listDish.get(0).getName());
                list.remove(listDish.get(0));
            } else if (listDish.size() > 1) {
                System.out.printf("%10.9s", "+" + listDish.size() + " more");
            } else {
                System.out.printf(BLANK_SPACE);
            }
        }
        System.out.println();
    }


}
