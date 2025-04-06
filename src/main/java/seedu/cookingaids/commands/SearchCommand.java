package seedu.cookingaids.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.search.Search;

public class SearchCommand {
    public static final String COMMAND_WORD = "search";
    public static final String COLON_SEPARATOR = ": ";

    public static void printSearchResult(String receivedText) {
        ArrayList<Recipe> taggedRecipes = Search.searchRecipes(receivedText);

        if (taggedRecipes.isEmpty()) {
            System.out.println("No recipes match your tags.");
        } else {
            System.out.println("Your tags match these recipes:");
            int i = 1;
            for (Recipe recipe : taggedRecipes) {
                System.out.println(i++ + COLON_SEPARATOR + recipe.getRecipeName());
            }
        }
    }
}
