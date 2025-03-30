package seedu.cookingaids.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.collections.IngredientStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class Storage {
    private static final String RECIPE_LIST_FIELD_NAME = "recipes";
    private static final String DISH_LIST_FIELD_NAME = "dishes";
    private static final String INGREDIENT_STORAGE_FIELD_NAME = "ingredients";
    private static final String SHOPPING_LIST_FIELD_NAME = "shopping cart";
    private static final String FILE_PATH = "./data/cookingaids.json";


    /**
     * Stores the list of dishes and recipes into a JSON file.
     * This method serializes the dish and recipe lists into a JSON format and writes them to the specified file path.
     *
     * @param dishList   The list of dishes to be stored.
     * @param recipeList The list of recipes to be stored.
     */
    public static void storeData(
            ArrayList<Dish> dishList,
            ArrayList<Recipe> recipeList,
            HashMap<String,List<Ingredient>> ingredientStorage,
            ArrayList<Ingredient> shoppingList) {

        //check if any inputs are null:\
        assert dishList != null : "Dish list cannot be null";
        assert recipeList != null : "Recipe list cannot be null";
        assert ingredientStorage != null : "Ingredient storage cannot be null";

        //get dishList array, store into json.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put(DISH_LIST_FIELD_NAME, dishList);
        dataMap.put(RECIPE_LIST_FIELD_NAME, recipeList);
        dataMap.put(INGREDIENT_STORAGE_FIELD_NAME, ingredientStorage);
        dataMap.put(SHOPPING_LIST_FIELD_NAME, shoppingList);

        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();


        try {
            mapper.writeValue(file, dataMap);
            System.out.println("Stored Dish List successfully in: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to store Dish List in: " + FILE_PATH);
        }
    }

    /**
     * Loads the list of dishes and recipes from a JSON file.
     * This method reads a JSON file containing dishes and recipes and deserializes the data into appropriate objects.
     * If the file doesn't exist or fails to load, it returns empty lists of dishes and recipes.
     *
     * @return A DataWrapper containing the lists of dishes and recipes.
     */
    public static DataWrapper loadData() {


        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No data found, creating new lists.");
            IngredientStorage.clear();
            return new DataWrapper(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        }

        try {
            return mapper.readValue(file, DataWrapper.class);
        } catch (IOException e) {
            System.err.println("Failed to load Dish list from: " + FILE_PATH + ", loading new list.");
            System.err.println(e.getMessage());
            return new DataWrapper(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        }


    }

    /**
     * Loads the list of dishes and recipes from a JSON file.
     * This method reads a JSON file containing dishes and recipes and deserializes the data into appropriate objects.
     * If the file doesn't exist or fails to load, it returns empty lists of dishes and recipes.
     *
     * @return A DataWrapper containing the lists of dishes and recipes.
     */
    public static class DataWrapper {
        public List<Dish> dishes;
        public List<Recipe> recipes;
        public HashMap<String, List<Ingredient>> ingredients;
        public List<Ingredient> shopping;

        public DataWrapper() {
        }

        public DataWrapper(List<Dish> dishes, List<Recipe> recipes,
                           HashMap<String, List<Ingredient>> ingredients,
                           List<Ingredient> shopping) {
            this.dishes = dishes;
            this.recipes = recipes;
            this.ingredients = ingredients;
            this.shopping = shopping;
        }

        //for debugging
        public static void printData() {
            System.out.println("All data obtained: ");
            // System.out.println("All dishes: " + dishes);
            // System.out.println("All recipes: " + recipes);
            //System.out.println("All ingredients: " + ingredients);
        }
    }

}
