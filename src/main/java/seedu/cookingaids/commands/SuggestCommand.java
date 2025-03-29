package seedu.cookingaids.commands;

import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.suggest.Suggest;

import java.util.List;

public class SuggestCommand {

    public static final String COMMAND_WORD = "suggest";
    public static final String COLON_SEPARATOR = ": ";
    public static void printSuggestions() {
        List<Recipe> suggestedRecipes = Suggest.suggestRecipes();
        if (!suggestedRecipes.isEmpty()) {
            System.out.println("You have enough ingredients to make: ");
        }

        int i = 1;
        for (Recipe recipe : suggestedRecipes) {
            System.out.println(i++ + COLON_SEPARATOR + recipe.getRecipeName());
        }

    }
}
