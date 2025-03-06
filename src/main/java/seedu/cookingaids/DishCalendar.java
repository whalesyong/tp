package seedu.cookingaids;

import java.util.ArrayList;


public class DishCalendar {
    ArrayList<Dish> dishCalendar = new ArrayList<>();

    public static ArrayList<Dish> getDishCalendar() {
        return dishCalendar;
    }


    public void addDishToCalendar(Dish dish) {

        dishCalendar.add(dish);

    }
    public void removeDishInCalendar(Dish dish) {

        dishCalendar.remove(dish);

    }
    public void removeDishInCalendar(int dishIndex) {
        dishCalendar.remove(dishIndex);

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

}
