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

    /**
     * Creates a new food entry in the food database.
     *
     * @param food The food item to be added to the database.
     */
    public static void createFood(Food food) {
        foodDatabase.put(food.id, food);
    }

    /**
     * Retrieves a food item from the food database by its ID.
     *
     * @param id The ID of the food item to be retrieved.
     * @return The food item corresponding to the given ID, or null if no such item exists.
     */
    public static Food getFood(int id) {
        return foodDatabase.get(id);
    }

    /**
     * Deletes a food item from the food database by its ID.
     *
     * @param id The ID of the food item to be removed from the database.
     */
    public static void deleteFood(int id) {
        foodDatabase.remove(id);
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