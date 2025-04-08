package seedu.cookingaids.commands;

import java.util.ArrayList;

import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.search.Search;

public class SearchCommand {
    public static final String COMMAND_WORD = "search";
    public static final String COLON_SEPARATOR = ": ";

    public static void printSearchResult(String receivedText, String searchType) {
        if (receivedText == null || receivedText.trim().isEmpty()) {
            System.out.println("Please provide valid search tags.");
            return;
        }

        try {
            ArrayList<Recipe> taggedRecipes = Search.searchRecipes(receivedText, searchType);

            if (taggedRecipes.isEmpty()) {
                System.out.println("No recipes match your tags.");
            } else {
                System.out.println("Your tags match these recipes:");
                int i = 1;
                for (Recipe recipe : taggedRecipes) {
                    if (recipe != null) {
                        System.out.println(i++ + COLON_SEPARATOR + recipe.getRecipeName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while searching: " + e.getMessage());
        }
    }
}
