package seedu.cookingaids.items;

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

    /**
     * Displays detailed information about the food item.
     * This method must be implemented by subclasses of Food to provide specific details about the food.
     */
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
