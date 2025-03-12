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

public class Storage {
    private static String DISH_FIELD_NAME = "dishes";
    private static final String FILE_PATH = "./data/cookingaids.json";


    public static void storeList(ArrayList<Dish> dishList){
        //get dishList array, store into json.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, ArrayList<Dish>> dishMap = new HashMap<String, ArrayList<Dish>>();

        dishMap.put(DISH_FIELD_NAME, dishList);

        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try{
            mapper.writeValue(file, dishMap);
            System.out.println("Stored Dish List successfully in: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to store Dish List in: " + FILE_PATH);
        }
    }
}
