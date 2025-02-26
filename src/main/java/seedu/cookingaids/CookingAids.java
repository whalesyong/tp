package main.java.seedu.cookingaids;

import java.util.Scanner;

import static main.java.seedu.cookingaids.Food.*;

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
        createFood(dish1);
        createFood(dish2);

        // Test Read
        System.out.println("Retrieving dish with ID 1:");
        Food retrievedDish = getFood(1);
        System.out.println(retrievedDish.getName());
        System.out.println(retrievedDish.getId());

        if (retrievedDish != null) {
            retrievedDish.displayInfo();
            System.out.println();
        } else {
            System.out.println("Dish not found.");
        }

        // Test Delete
        System.out.println("Deleting dish with ID 1.");
        deleteFood(1);

        System.out.println("Trying to retrieve deleted dish:");
        Food deletedDish = getFood(1);
        if (deletedDish != null) {
            deletedDish.displayInfo();
        } else {
            System.out.println("Dish not found.");
            System.out.println();
        }

        Scanner scanner = new Scanner(System.in);
        int foodIdCounter = 1;

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);

            String command = parts[0].toLowerCase();
            String foodName = parts.length > 1 ? parts[1] : "";

            switch (command) {
            case "add":
                Food newDish = new Dish(foodIdCounter++, foodName);
                createFood(newDish);
                System.out.println("Added: " + foodName);
                break;
            case "list":
                System.out.println("Food List:");
                if (foodDatabase.isEmpty()) {
                    System.out.println("No items found");
                } else {
                    for (Food food : foodDatabase.values()) {
                        food.displayInfo();
                    }
                }
                break;
            case "delete":
                int idToDelete = -1;
                for (Food food : foodDatabase.values()) {
                    if (food.getName().equalsIgnoreCase(foodName)) {
                        idToDelete = food.getId();
                        break;
                    }
                }
                if (idToDelete != -1) {
                    deleteFood(idToDelete);
                    System.out.println("Deleted: " + foodName);
                } else {
                    System.out.println("Item not found");
                }
                break;
            default:
                System.out.println("Unknown command. Use add <food>, list, or delete <food>");
            }
        }
    }
}
