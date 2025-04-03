package seedu.cookingaids.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.ui.Ui;

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
    private static final String SHOPPING_LIST_FIELD_NAME = "shopping";
    private static final String FILE_PATH = "./data/cookingaids.json";

    private static final String MESSAGE_STORE_SUCCESS = "Stored Dish List successfully in: ";
    private static final String MESSAGE_STORE_FAILURE = "Failed to store Dish List in: ";
    private static final String MESSAGE_NO_DATA_FOUND = "No data found, creating new lists.";
    private static final String MESSAGE_LOAD_FAILURE = "Failed to load Dish list from: %s, loading new list.";


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
            Ui.printItems(MESSAGE_STORE_SUCCESS + FILE_PATH);
        } catch (IOException e) {
            Ui.printItems(MESSAGE_STORE_FAILURE + FILE_PATH);
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
            Ui.printItems(MESSAGE_NO_DATA_FOUND);
            IngredientStorage.clear();
            return new DataWrapper(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        }

        try {
            return mapper.readValue(file, DataWrapper.class);
        } catch (IOException e) {
            Ui.printItems(String.format(MESSAGE_LOAD_FAILURE, FILE_PATH));
            Ui.printItems(e.getMessage());
            return new DataWrapper(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        }
    }

    /**
     * DataWrapper class to handle serialization and deserialization of application data.
     * This class contains lists of dishes and recipes and methods to manipulate them.
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
            Ui.printItems("All data obtained: ");
        }
    }
}