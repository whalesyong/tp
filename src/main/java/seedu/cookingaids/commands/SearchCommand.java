package seedu.cookingaids.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.search.Search;
import seedu.cookingaids.logger.LoggerFactory;

public class SearchCommand {
    public static final String COMMAND_WORD = "search";
    public static final String COLON_SEPARATOR = ": ";
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCommand.class);

    public static void printSearchResult(String receivedText, String searchType) {
        LOGGER.info("Searching recipes with tags: " + receivedText + ", search type: " + searchType);
        
        if (receivedText == null || receivedText.trim().isEmpty()) {
            LOGGER.warning("Empty or null search tags provided");
            System.out.println("Please provide valid search tags.");
            return;
        }

        try {
            ArrayList<Recipe> taggedRecipes = Search.searchRecipes(receivedText, searchType);

            if (taggedRecipes.isEmpty()) {
                LOGGER.info("No recipes found matching tags");
                System.out.println("No recipes match your tags.");
            } else {
                LOGGER.info("Found " + taggedRecipes.size() + " matching recipes");
                System.out.println("Your tags match these recipes:");
                int i = 1;
                for (Recipe recipe : taggedRecipes) {
                    if (recipe != null) {
                        LOGGER.fine("Matched recipe: " + recipe.getRecipeName());
                        System.out.println(i++ + COLON_SEPARATOR + recipe.getRecipeName());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.severe("Error during recipe search: " + e.getMessage());
            System.out.println("An error occurred while searching: " + e.getMessage());
        }
    }
}
