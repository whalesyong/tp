package seedu.cookingaids.collections;

import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.DishDate;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.util.ArrayList;
import java.util.List;

import static seedu.cookingaids.collections.RecipeBank.recipeBank;


public class DishCalendar {
    public static ArrayList<Dish> dishCalendar = new ArrayList<>();

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
//        if(RecipeBank.contains(dish.getName())) {
////            List<String> ingredientList = getIngredientList(dish);
////            for(Ingredient ingredient: ingredientList)
//            dishCalendar.add(dish);
//        }
//        else {
            dishCalendar.add(dish);
//        }

    }
//    public static List<String> getIngredientList(Dish dish) {
//        ArrayList<Recipe> recipeBank = RecipeBank.getRecipeBank();
//        for (Recipe item : recipeBank) {
//            if (item.getName().equals(dish.getName())) {
//                return item.getIngredients();
//            }
//
//        }
//    }

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
