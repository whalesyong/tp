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

public class IngredientStorage {
    private static final HashMap<String, List<Ingredient>> ingredients = new HashMap<>();

    public static void addToStorage(Ingredient newIngredient) {
        String name = newIngredient.getName();
        List<Ingredient> ingredientList = ingredients.getOrDefault(name, new ArrayList<>());

        // Check if an ingredient with the same expiry date exists, then add quantity
        boolean isMerged = false;
        for (Ingredient ing : ingredientList) {
            if (Objects.equals(newIngredient.getExpiryDate().getDateLocalDate(),
                    ing.getExpiryDate().getDateLocalDate())) {
                ing.addQuantity(newIngredient.getQuantity());
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

    private static void checkExpiringSoon(String name) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Ingredient> ingredientList = ingredients.get(name);

        for (Ingredient ing : ingredientList) {
            LocalDate expiryDate = ing.getExpiryDate().getDateLocalDate();
            if (expiryDate != null && expiryDate.equals(tomorrow)) {
                ing.setExpiringSoon(true);
            }
        }
    }

    private static void removeExpiredIngredients(String name) {
        List<Ingredient> ingredientList = ingredients.getOrDefault(name, new ArrayList<>());

        LocalDate today = LocalDate.now();
        ingredientList.removeIf(ing -> {
            LocalDate expiryDate = ing.getExpiryDate().getDateLocalDate();
            return expiryDate != null && expiryDate.isBefore(today);
        });

        if (ingredientList.isEmpty()) {
            ingredients.remove(name);
        }
    }

    public static void useIngredients(String name, int amount) {
        if (!ingredients.containsKey(name)) {
            System.out.println("Ingredient not found: " + name);
            return;
        }

        List<Ingredient> ingredientList = ingredients.get(name);

        // Remove expired ingredients
        removeExpiredIngredients(name);

        // Reduce quantity starting from the earliest expiry date
        for (Iterator<Ingredient> iterator = ingredientList.iterator(); iterator.hasNext() && amount > 0; ) {
            Ingredient ing = iterator.next();
            int currentQuantity = ing.getQuantity();

            if (currentQuantity <= amount) {
                amount -= currentQuantity;
                iterator.remove(); // Remove if quantity reaches 0
            } else {
                ing.removeQuantity(amount);
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
}
