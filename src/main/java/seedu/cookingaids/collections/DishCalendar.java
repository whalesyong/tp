package seedu.cookingaids.collections;

import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.DishDate;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;

import java.util.ArrayList;
import java.util.List;


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
        if (RecipeBank.contains(dish.getName())) {
            List<Ingredient> ingredientList = getIngredientList(dish);

            assert ingredientList != null;
            for (Ingredient ingredient : ingredientList) {
                System.out.println(ingredient.getName());
                int requiredQuantity = ingredient.getQuantity();
                if (IngredientStorage.contains(ingredient.getName())) {
                    int totalQuantity = getTotalQuantity(ingredient);
                    if (totalQuantity > requiredQuantity) {
                        System.out.println("case 1" + ingredient.getName());
                        IngredientStorage.useIngredients(ingredient.getName(), requiredQuantity);
                    }
                    if (totalQuantity < requiredQuantity) {
                        System.out.println("case 2" + ingredient.getName());
                        IngredientStorage.removeIngredient(ingredient.getName());
                        ingredient.setQuantity(requiredQuantity - totalQuantity);
                        ShoppingList.addToShoppingList(ingredient);

                    }
                    if (totalQuantity == requiredQuantity) {
                        System.out.println("case 3" + ingredient.getName());
                        IngredientStorage.removeIngredient(ingredient.getName());
                    }
                }
            }

        }

        dishCalendar.add(dish);


    }

    private static int getTotalQuantity(Ingredient ingredient) {
        List<Ingredient> storedIngredients = IngredientStorage.getIngredients(ingredient.getName());
        int totalQuantity = 0;
        System.out.println(ingredient.getName());
        for (Ingredient storedIngredient : storedIngredients) {

            totalQuantity += storedIngredient.getQuantity();

        }
        return totalQuantity;
    }

    public static List<Ingredient> getIngredientList(Dish dish) {
        ArrayList<Recipe> recipeBank = RecipeBank.getRecipeBank();
        for (Recipe item : recipeBank) {
            if (item.getName().equals(dish.getName())) {
                return item.getIngredients();
            }

        }
        return null;
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
