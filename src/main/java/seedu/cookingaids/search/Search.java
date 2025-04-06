package seedu.cookingaids.search;

import java.util.ArrayList;

import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.exception.InvalidInputException;
import seedu.cookingaids.items.Recipe;

public class Search {

    public static ArrayList<Recipe> searchRecipes(String receivedText, String searchType) {
        System.out.println("Searching recipes");

        ArrayList<Recipe> recipes = new ArrayList<>();

        if (receivedText == null || receivedText.trim().isEmpty()) {
            System.out.println("Empty search query. Please provide tags to search for.");
            return recipes;
        }

        try {
            ArrayList<String> tags = parseTagsString(receivedText);
            if (tags.isEmpty()) {
                System.out.println("No valid tags found in input.");
                return recipes;
            }

            System.out.println("Searching for tags: " + String.join(", ", tags) +
                    " using " + searchType.toUpperCase() + " matching");

            // Use the appropriate search method based on searchType
            if ("and".equalsIgnoreCase(searchType)) {
                recipes = RecipeBank.getRecipeByANDTags(tags);
            } else {
                // Default to OR search
                recipes = RecipeBank.getRecipeByORTags(tags);
            }
        } catch (InvalidInputException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

        return recipes;
    }

    private static ArrayList<String> parseTagsString(String tagsStr) throws InvalidInputException {
        if (tagsStr == null || tagsStr.trim().isEmpty()) {
            throw new InvalidInputException();
        }

        ArrayList<String> tags = new ArrayList<>();
        String[] parts = tagsStr.split(",");

        if (parts.length == 0) {
            throw new InvalidInputException();
        }

        for (String p : parts) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) {
                tags.add(trimmed.toLowerCase());
            }
        }

        if (tags.isEmpty()) {
            throw new InvalidInputException();
        }

        return tags;
    }
}
