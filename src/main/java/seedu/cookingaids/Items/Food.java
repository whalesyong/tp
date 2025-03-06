package seedu.cookingaids.Items;

import java.util.HashMap;
import java.util.Map;

public abstract class Food {
    protected static Map<Integer, Food> foodDatabase = new HashMap<>();
    protected int id;
    protected String name;

    public Food(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void createFood(Food food) {
        foodDatabase.put(food.id, food);
    }

    public static Food getFood(int id) {
        return foodDatabase.get(id);
    }

    public static void deleteFood(int id) {
        foodDatabase.remove(id);
    }

    public abstract void displayInfo();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Dish ID: " + id + ", Name: " + name ;
    }
}