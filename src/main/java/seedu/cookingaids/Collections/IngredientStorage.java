package seedu.cookingaids.Collections;

import seedu.cookingaids.Items.ExpiryDate;
import seedu.cookingaids.Items.Ingredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class IngredientStorage {
    private static final HashMap<String, List<Ingredient>> ingredients = new HashMap<>();

    public static void addToStorage(Ingredient newIngredient) {
        String name = newIngredient.getName();
        List<Ingredient> ingredientList = ingredients.getOrDefault(name, new ArrayList<>());

        // Check if an ingredient with the same expiry date exists, then add quantity
        for (Ingredient ing : ingredientList) {
            if ((newIngredient.getExpiryDate().getDateLocalDate() == null && ing.getExpiryDate().getDateLocalDate() == null) ||
                    (newIngredient.getExpiryDate().getDateLocalDate() != null && newIngredient.getExpiryDate().getDateLocalDate()
                            .equals(ing.getExpiryDate().getDateLocalDate()))) {
                ing.addQuantity(newIngredient.getQuantity());
                return;
            }
        }

        // Otherwise, add a new ingredient entry
        ingredientList.add(newIngredient);

        // Sort ingredients by expiry date (null = last)
        ingredientList.sort(Comparator.comparing(
                Ingredient::getExpiryDate,
                Comparator.comparing(ExpiryDate::getDateLocalDate, Comparator.nullsLast(Comparator.naturalOrder()))
        ));


        ingredients.put(name, ingredientList);
    }

    public static void removeIngredient(String ingredientName) {
        ingredients.remove(ingredientName);
    }

    public void displayStorage() {
        for (String name : ingredients.keySet()) {
            System.out.println(name + ":");
            for (Ingredient ing : ingredients.get(name)) {
                System.out.println("  " + ing);
            }
        }
    }

    public List<Ingredient> getIngredients(String name) {
        return ingredients.getOrDefault(name, new ArrayList<>());
    }
}
