package seedu.cookingaids.items;

import java.util.HashMap;
import java.util.Map;

public abstract class Food {
    protected static Map<Integer, Food> foodDatabase = new HashMap<>();
    protected int id;
    protected String name;

    public Food( String name) {

        this.name = name;
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


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  " Name: " + name ;
    }
}
