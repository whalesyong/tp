package seedu.cookingaids.collections;

import seedu.cookingaids.items.ExpiryDate;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import java.util.Iterator;
import java.util.Comparator;

/***
 * This class represents the ingredient inventory of the user.
 * This class contains the ingredients in a hashmap with the key as the name of the ingredient
 * and the value as list of ingredient objects of that name with different expiry dates
 * This class contains methods to work on the inventory, given commands from the user
 */
public class IngredientStorage {
    private static HashMap<String, List<Ingredient>> ingredients = new HashMap<>();

    public static void initializeIngredientStorage(HashMap<String, List<Ingredient>> newIngredients) {
        ingredients = newIngredients;
    }
    /**
     * takes ingredient input to be added from shopping list
     * checks if the ingredient of the same expiry date is in the hashmap, if it is, adding the quantity to it
     * else creating a new entry to the list of Ingredient objects under that name and adding to it
     * Ingredients under that name are also checked if they are expiring soon or expired
     *
     * @param newIngredient is the ingredient to be added
     */
    public static void addToStorage(Ingredient newIngredient) {
        newIngredient = ShoppingList.removeFromShoppingList(newIngredient);
        if(newIngredient == null){
            return;
        }
        
        String name = newIngredient.getName();
        List<Ingredient> ingredientList = ingredients.getOrDefault(name, new ArrayList<>());

        // Check if an ingredient with the same expiry date exists, then add quantity
        boolean isMerged = false;
        for (Ingredient ingredient : ingredientList) {
            if (Objects.equals(newIngredient.getExpiryDate().getDateLocalDate(),
                    ingredient.getExpiryDate().getDateLocalDate())) {
                ingredient.addQuantity(newIngredient.getQuantity());
                isMerged = true;
                break; // Exit once merged
            }
        }

        // If no matching expiry date, add the new ingredient as a separate entry
        if (!isMerged) {
            ingredientList.add(newIngredient);
        }
        // Sort ingredients by expiry date (null = last)
        ingredientList.sort(Comparator.comparing(
                Ingredient::getExpiryDate,
                Comparator.comparing(ExpiryDate::getDateLocalDate, Comparator.nullsLast(Comparator.naturalOrder()))
        ));


        ingredients.put(name, ingredientList);

        checkExpiringSoon(name);
        checkExpiredIngredients(name);
    }

    public static void removeIngredient(String ingredientName) {
        ingredients.remove(ingredientName);
    }

    public static HashMap<String, List<Ingredient>> getStorage() {
        return ingredients;
    }

    public static boolean contains(String ingredientName) {
        return ingredients.containsKey(ingredientName);
    }

    public static void clear() {
        ingredients.clear();
    }

    public void displayStorage() {
        HashMap<String, List<Ingredient>> ingredients = getStorage();
        Ui.printIngredientListView(ingredients);
    }

    public static List<Ingredient> getIngredients(String name) {
        return ingredients.getOrDefault(name, new ArrayList<>());
    }
    /**
     * Goes through the ingredients with same name as input
     * and checks if their expiry date matches date to be deemed expiring soon
     * Currently, expiring soon date is set to the next day upon method call
     *
     * @param name is the name of ingredient to be checked
     */
    private static void checkExpiringSoon(String name){
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Ingredient> ingredientList = ingredients.get(name);

        for (Ingredient ingredient : ingredientList) {
            ExpiryDate expiryDate = ingredient.getExpiryDate();
            if(expiryDate == null){
                break;
            }
            LocalDate expiryLocalDate =  expiryDate.getDateLocalDate();

            if (expiryLocalDate != null) {
                if (expiryLocalDate.equals(tomorrow)) {
                    ingredient.setExpiringSoon(true);
                }
            }
        }
    }

    /**
     * Goes through the ingredients with the same name as input
     * and checks if their expiry date has passed upon method call
     *
     * @param name is the name of ingredient to be checked
     */
    private static void checkExpiredIngredients(String name) {
        LocalDate today = LocalDate.now();
        List<Ingredient> ingredientList = ingredients.get(name);

        for (Ingredient ingredient : ingredientList) {
            ExpiryDate expiryDate = ingredient.getExpiryDate();
            if(expiryDate == null){
                break;
            }
            LocalDate expiryLocalDate = expiryDate.getDateLocalDate();
            if (expiryLocalDate != null) {
                if (expiryLocalDate.isBefore(today)) {
                    ingredient.setExpired(true);
                }
            }
        }
    }

    /**
     * Goes through list of Ingredients with the same name as input
     * Removes the ingredients which have expired as of method call
     *
     * @param name is the name of ingredient to be checked
     */
    private static void removeExpiredIngredients(String name) {
        List<Ingredient> ingredientList = ingredients.getOrDefault(name, new ArrayList<>());

        LocalDate today = LocalDate.now();
        ingredientList.removeIf(ingredient -> {
            LocalDate expiryDate = ingredient.getExpiryDate().getDateLocalDate();
            if (expiryDate!=null) {
                return expiryDate.isBefore(today);
            }
            return false;
        });

        if (ingredientList.isEmpty()) {
            ingredients.remove(name);
        }
    }

    /**
     * Goes through the ingredients of the same name as String input
     * Removes expired ingredients
     * Reduces quantity from unexpired Ingredient objects
     *
     * @param name is the name of ingredient to be checked
     * @param amount is the amount of ingredient to be used
     */
    public static void useIngredients(String name, int amount) {
        if (!ingredients.containsKey(name)) {
            System.out.println("Ingredient not found: " + name);
            return;
        }

        List<Ingredient> ingredientList = ingredients.get(name);

        // Remove expired ingredients
        removeExpiredIngredients(name);

        // Reduce quantity starting from the earliest expiry date
        for (Iterator<Ingredient> looker = ingredientList.iterator(); looker.hasNext() && amount > 0; ) {
            Ingredient ingredient = looker.next();
            int currentQuantity = ingredient.getQuantity();

            if (currentQuantity <= amount) {
                amount -= currentQuantity;
                looker.remove(); // Remove if quantity reaches 0
            } else {
                ingredient.removeQuantity(amount);
                amount = 0; // All required quantity is deducted
            }
        }

        // Update or remove if empty
        if (ingredientList.isEmpty()) {
            ingredients.remove(name);
        } else {
            ingredients.put(name, ingredientList);
        }
    }

    /**
     * Goes through the ingredients of the same name as String input
     * returns number of unexpired ingredients
     *
     * @param ingredientName is the name of ingredient to be checked
     */
    public static int getUnexpiredIngredients(String ingredientName) {
        List<Ingredient> ingredientList = ingredients.getOrDefault(ingredientName, new ArrayList<>());
        int unexpiredQuantity = 0;
        LocalDate today = LocalDate.now();
        for (Ingredient ingredient : ingredientList) {
            LocalDate expiryDate = ingredient.getExpiryDate().getDateLocalDate();
            if (expiryDate == null || !expiryDate.isBefore(today)) {
                unexpiredQuantity += ingredient.getQuantity();
            }
        }

        return unexpiredQuantity;
    }

    /**
     * Goes through the ingredients of the same name as String input
     * returns number of ingredients expiring soon
     *
     * @param ingredientName is the name of ingredient to be checked
     */
    public static int getExpiringSoonIngredients(String ingredientName) {
        List<Ingredient> ingredientList = ingredients.getOrDefault(ingredientName, new ArrayList<>());
        int expiringSoonQuantity = 0;
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        for (Ingredient ingredient : ingredientList) {
            LocalDate expiryDate = ingredient.getExpiryDate().getDateLocalDate();
            if (expiryDate != null) {
                if (expiryDate.equals(tomorrow)) {
                    expiringSoonQuantity += ingredient.getQuantity();
                }
            }
        }
        return expiringSoonQuantity;
    }

    /**
     * Goes through the ingredients of the same name as String input
     * returns number of ingredients
     *
     * @param ingredient is the ingredient object to be checked
     */
    public static int getTotalIngredientQuantity(Ingredient ingredient) {
        List<Ingredient> storedIngredients = IngredientStorage.getIngredients(ingredient.getName());
        int totalQuantity = 0;
        for (Ingredient storedIngredient : storedIngredients) {

            totalQuantity += storedIngredient.getQuantity();

        }
        return totalQuantity;
    }

    //FOR DEBUGGING. TODO remove statement
    public static void printMap(){
        System.out.println(ingredients);
    }
}
