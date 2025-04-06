package seedu.cookingaids.search;

import java.util.ArrayList;
import java.util.List;

import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.commands.UpdateCommand;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.commands.UpdateCommand.*;

public class Search {

    public static ArrayList<Recipe> searchRecipes(String receivedText) {
        System.out.println("Searching recipes");

        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            ArrayList<String> tags = parseTagsString(receivedText);
//            System.out.println("tags are" + tags);
            recipes = RecipeBank.getRecipeByTags(tags);
        } catch (InvalidInputException e) {
            System.out.println("Invalid input");
        }

        return recipes;
    }

    private static ArrayList<String> parseTagsString(String tagsStr) throws InvalidInputException {
        ArrayList<String> tags = new ArrayList<>();
        String[] parts = tagsStr.split(",");
        for (String p : parts) {
            tags.add(p.toLowerCase()); // Optional: trim whitespace
        }
        return tags;
    }
}