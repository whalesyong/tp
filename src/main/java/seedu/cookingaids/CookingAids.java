package main.java.seedu.cookingaids;

public class CookingAids {
    public static void main(String[] args) {
        // Test subclass
        class Dish extends Food {
            public Dish(int id, String name) {
                super(id, name);
            }

            @Override
            public void displayInfo() {
                System.out.println("Dish ID: " + id + ", Name: " + name);
            }
        }

        Food dish1 = new Dish(1, "Pasta");
        Food dish2 = new Dish(2, "Pizza");

        // Test Create
        Food.createFood(dish1);
        Food.createFood(dish2);

        // Test Read
        System.out.println("Retrieving dish with ID 1:");
        Food retrievedDish = Food.getFood(1);
        System.out.println(retrievedDish.getName());
        System.out.println(retrievedDish.getId());

        if (retrievedDish != null) {
            retrievedDish.displayInfo();
        } else {
            System.out.println("Dish not found.");
        }

        System.out.println();

        // Test Delete
        System.out.println("Deleting dish with ID 1.");
        Food.deleteFood(1);

        System.out.println("Trying to retrieve deleted dish:");
        Food deletedDish = Food.getFood(1);
        if (deletedDish != null) {
            deletedDish.displayInfo();
        } else {
            System.out.println("Dish not found.");
        }
    }
}
