package seedu.cookingaids.Storage;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import seedu.cookingaids.Items.Dish;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class Storage {
    private static String DISH_LIST_FIELD_NAME = "dishes";
    private static final String FILE_PATH = "./data/cookingaids.json";


    public static void storeList(ArrayList<Dish> dishList){
        //get dishList array, store into json.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, ArrayList<Dish>> dishMap = new HashMap<String, ArrayList<Dish>>();

        dishMap.put(DISH_LIST_FIELD_NAME, dishList);

        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try{
            mapper.writeValue(file, dishMap);
            System.out.println("Stored Dish List successfully in: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to store Dish List in: " + FILE_PATH);
        }
    }

    public static ArrayList<Dish> loadList(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No data found, creating new list.");
            return new ArrayList<Dish>();
        }

        try {
            DishListWrapper wrapper = mapper.readValue(file, DishListWrapper.class);
            return new ArrayList<Dish>(wrapper.dishes);
        } catch (IOException e){
            System.err.println("Failed to load Dish list from: " + FILE_PATH + ", loading new list.");
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }


    }

    private static class DishListWrapper {
        public List<Dish> dishes;
        public DishListWrapper() {}
        public DishListWrapper(List<Dish> dishes) { this.dishes = dishes; }
    }


}
