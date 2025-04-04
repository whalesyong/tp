package seedu.cookingaids.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class UnitConfig {
    private static final String CONFIG_PATH = "./data/units.config";
    private static HashMap<String, List<String>> unitMap = new HashMap<>();

    static {
        loadUnits();
    }

    private static void loadUnits() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(CONFIG_PATH);
            if (file.exists()) {
                // Use TypeReference to specify the expected type
                unitMap = objectMapper.readValue(file, new TypeReference<HashMap<String, List<String>>>() {});
            }
        } catch (IOException e) {
            System.err.println("Failed to load units config: " + e.getMessage());
        }
    }

    public static String getUnit(String ingredientName) {
        // Check each unit list to find the matching ingredient
        for (String unit : unitMap.keySet()) {
            List<String> ingredients = unitMap.get(unit);
            if (ingredients.contains(ingredientName.toLowerCase())) {
                return unit;
            }
        }
        // Default to "pcs" if no matching unit is found
        return "pcs";
    }
}
