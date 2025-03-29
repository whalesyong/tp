package seedu.cookingaids.collections;

import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.DishDate;

import java.util.ArrayList;
import java.util.List;


public class DishCalendar {
    public static ArrayList<Dish> dishCalendar = new ArrayList<>();
    private static int currentId = 1; // Keeps track of the latest assigned ID

    public static synchronized int generateNewDishId() {
        return currentId++; // Increment and return the next ID
    }
    public static void initializeDishCalendar(List<Dish> dishes) {
        dishCalendar.addAll(dishes);
    }

    public static ArrayList<Dish> getDishCalendar() {
        return dishCalendar;
    }
    public static List<Dish> getAllDishes() {
        return new ArrayList<>(dishCalendar);
    }
    public static void setDishCalendar(ArrayList<Dish> dishCalendar) {
        DishCalendar.dishCalendar = dishCalendar;
    }

    public static void addDishToCalendar(Dish dish) {

        dishCalendar.add(dish);

    }

    public static List<Dish> getDishesByName(String dishName) {
        List<Dish> matchingDishes = new ArrayList<>();
        for (Dish dish : dishCalendar) {
            if (dish.getName().equalsIgnoreCase(dishName)) {
                matchingDishes.add(dish);
            }
        }
        return matchingDishes;
    }
    public static List<Dish> getDishesByDate(String date) {
        List<Dish> matchingDishes = new ArrayList<>();
        for (Dish dish : dishCalendar) {
            if (dish.getDishDate().equals(new DishDate(date))) {
                matchingDishes.add(dish);
            }
        }
        return matchingDishes;
    }

    public static void removeDishInCalendar(Dish dish) {

        dishCalendar.remove(dish);

    }

    public void removeDishInCalendarByDate(String date) {
        date = date.trim().toLowerCase();
        ArrayList<Dish> dishesToBeRemoved = new ArrayList<Dish>();
        for(Dish d : dishCalendar){
            DishDate dishDate = d.getDishDate();
            if (date.equals(dishDate.toString().toLowerCase())){
                dishesToBeRemoved.add(d);
            }
        }
        dishCalendar.remove(dishesToBeRemoved);
    }

    public boolean containsDish(String dishName) {
        for (Dish dish : dishCalendar) {
            if (dish.getName().equalsIgnoreCase(dishName)) {
                return true;
            }
        }
        return false;
    }

    public static void clear() {
        dishCalendar.clear();
    }
}
