package seedu.cookingaids.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.logger.LoggerFactory;
import seedu.cookingaids.ui.Ui;

public class Storage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Storage.class);
    private static final String RECIPE_LIST_FIELD_NAME = "recipes";
    private static final String DISH_LIST_FIELD_NAME = "dishes";
    private static final String INGREDIENT_STORAGE_FIELD_NAME = "ingredients";
    private static final String SHOPPING_LIST_FIELD_NAME = "shopping";
    private static final String FILE_PATH = "./data/cookingaids.json";

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
            HashMap<String, List<Ingredient>> ingredientStorage,
            ArrayList<Ingredient> shoppingList) {

        LOGGER.info("Starting data serialization process");

        //check if any inputs are null:
        if (dishList == null || recipeList == null || ingredientStorage == null) {
            LOGGER.severe("Null input detected during data storage");
            throw new AssertionError("Input lists cannot be null");
        }

        LOGGER.fine(String.format("Storing %d dishes, %d recipes, %d ingredient categories",
                dishList.size(), recipeList.size(), ingredientStorage.size()));

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(DISH_LIST_FIELD_NAME, dishList);
        dataMap.put(RECIPE_LIST_FIELD_NAME, recipeList);
        dataMap.put(INGREDIENT_STORAGE_FIELD_NAME, ingredientStorage);
        dataMap.put(SHOPPING_LIST_FIELD_NAME, shoppingList);

        File file = new File(FILE_PATH);
        if (!file.getParentFile().exists()) {
            LOGGER.warning("Data directory not found, creating new directory");
            file.getParentFile().mkdirs();
        }

        try {
            mapper.writeValue(file, dataMap);
            LOGGER.info("Data successfully stored to " + FILE_PATH);
        } catch (IOException e) {
            LOGGER.severe("Failed to store data: " + e.getMessage());
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
        LOGGER.info("Starting data loading process");

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            LOGGER.info("No data found, creating new lists at " + FILE_PATH);
            Ui.printItems(MESSAGE_NO_DATA_FOUND);
            IngredientStorage.clear();
            return new DataWrapper(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        }

        try {
            LOGGER.fine("Reading data file: " + FILE_PATH);
            DataWrapper data = mapper.readValue(file, DataWrapper.class);

            // Validate loaded data
            if (data.dishes == null || data.recipes == null ||
                    data.ingredients == null || data.shopping == null) {
                LOGGER.warning("Corrupted data detected - missing required fields. Your data will be discarded.");
                throw new IOException("Corrupted data format");
            }

            LOGGER.info(String.format("Successfully loaded %d dishes, %d recipes",
                    data.dishes.size(), data.recipes.size()));
            return data;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load data: {0}", e.getMessage());
            Ui.printItems(String.format(MESSAGE_LOAD_FAILURE, FILE_PATH));
            Ui.printItems("Corrupted data detected, discarding corrupted data.");
            Ui.printItems(Ui.DATA_CORRUPTED_WARNING_MESSAGE);
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
