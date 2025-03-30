package seedu.cookingaids.collections;

import seedu.cookingaids.items.Ingredient;

import java.util.ArrayList;


public class ShoppingList {
    private static ArrayList<Ingredient> shoppingList = new ArrayList<>();

    public static ArrayList<Ingredient> getShoppingList() {
        return shoppingList;
    }


    public static void addToShoppingList(Ingredient ingredient) {
        for (Ingredient item : shoppingList) {
            if (item.getName().equals(ingredient.getName())) {
                item.addQuantity(ingredient.getQuantity()); // Update quantity
                return;
            }
        }
        System.out.println("added");
        shoppingList.add(ingredient);
    }

    public static Ingredient removeFromShoppingList(Ingredient ingredient) {
        for (Ingredient item : shoppingList) {
            if (item.getName().equals(ingredient.getName())) {
                if (item.getQuantity() > ingredient.getQuantity()) {
                    item.removeQuantity(ingredient.getQuantity()); // Update quantity
                    return null;

                }
                if (item.getQuantity() == ingredient.getQuantity()) {
                    shoppingList.remove(item);
                    return null;
                }
                shoppingList.remove(item);
                ingredient.removeQuantity(item.getQuantity());

            }

        }
        return ingredient;

    }
}

