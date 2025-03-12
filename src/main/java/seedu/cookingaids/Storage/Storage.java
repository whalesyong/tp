package seedu.cookingaids.Storage;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import seedu.cookingaids.Items.Dish;
import seedu.cookingaids.Items.Recipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class Storage {
    private static final String RECIPE_LIST_FIELD_NAME = "recipes";
    private static String DISH_LIST_FIELD_NAME = "dishes";
    private static final String FILE_PATH = "./data/cookingaids.json";


    /**
     * Stores the list of dishes and recipes into a JSON file.
     * This method serializes the dish and recipe lists into a JSON format and writes them to the specified file path.
     *
     * @param dishList The list of dishes to be stored.
     * @param recipeList The list of recipes to be stored.
     */
    public static void storeData(ArrayList<Dish> dishList, ArrayList<Recipe> recipeList){
        //get dishList array, store into json.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put(DISH_LIST_FIELD_NAME, dishList);
        dataMap.put(RECIPE_LIST_FIELD_NAME, recipeList);

        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try{
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
    public static DataWrapper loadData(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No data found, creating new lists.");
            return new DataWrapper(new ArrayList<>(), new ArrayList<>());
        }

        try {
            return mapper.readValue(file, DataWrapper.class);
        } catch (IOException e){
            System.err.println("Failed to load Dish list from: " + FILE_PATH + ", loading new list.");
            System.err.println(e.getMessage());
            return new DataWrapper(new ArrayList<>(), new ArrayList<>());
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

        public DataWrapper() {}

        public DataWrapper(List<Dish> dishes, List<Recipe> recipes) {
            this.dishes = dishes;
            this.recipes = recipes;
        }
    }


}
