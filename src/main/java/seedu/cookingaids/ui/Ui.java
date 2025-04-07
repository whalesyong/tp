package seedu.cookingaids.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.cookingaids.collections.DishCalendar;
import seedu.cookingaids.collections.IngredientStorage;
import seedu.cookingaids.collections.RecipeBank;
import seedu.cookingaids.collections.ShoppingList;
import seedu.cookingaids.items.Dish;
import seedu.cookingaids.items.Ingredient;
import seedu.cookingaids.items.Recipe;
import seedu.cookingaids.logger.LoggerFactory;
import seedu.cookingaids.parser.Parser;
import seedu.cookingaids.storage.Storage;

/**
 * Ui class holds methods relating to the User interface of the program
 * including accepting commands and printing outputs
 */
public class Ui {

    /**
     * Offset required to convert between 1-indexing and 0-indexing.
     */
    public static final int DISPLAYED_INDEX_OFFSET = 1;



    public static final String LINE_DIVIDER =
            "______________________________________________________________________________";

    public static final String USER_MARKER = ">>> ";
    public static final String WELCOME_MESSAGE = "welcome to cooking";
    public static final String DATA_CORRUPTED_WARNING_MESSAGE = "In order to preserve your data, please perform " +
            "Ctrl+C (Win) or Cmd+C (MacOS) and look through cookingaids.json for any potential corrupted fields, " +
            "and delete them. Sending the 'bye' command will permanently wipe all data.";
    public static final String ASCII_MESSAGE = """ 
             ░▒▓██████▓▒░░▒▓█▓▒░▒▓███████▓▒░ ░▒▓███████▓▒░\s
            ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░       \s
            ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░       \s
            ░▒▓████████▓▒░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓██████▓▒░ \s
            ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░\s
            ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░\s
            ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░▒▓███████▓▒░░▒▓███████▓▒░ \s""";
    public static Runnable waitForCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(Ui.class);
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";
    private static final String MESSAGE_STORE_SUCCESS = "Stored Dish List successfully in: ";
    private static final String FILE_PATH= "./data/cookingaids.json";


    public static void printLineDivider() {
        System.out.println(LINE_DIVIDER);
    }

    public static void showWelcomeMessage() {
        LOGGER.info("Showing welcome message");
        System.out.println(WELCOME_MESSAGE + '\n' + ASCII_MESSAGE);
    }

    public static void printItems(String... messages) { //print items to console
        LOGGER.log(Level.FINE, "Printing {0} message(s)", messages.length);
        for (String s : messages) {
            System.out.println(s.replace("\n", System.lineSeparator()));
        }
    }

    /**
     * outputs taskList on console
     *
     * @param dishes a list of dishes to be displayed in the console
     */
    public static void printDishListView(List<Dish> dishes) {
        LOGGER.log(Level.FINE, "Displaying {0} dishes", dishes.size());
        //prints TaskList on console
        final List<String> formattedItems = new ArrayList<>();
        for (Dish d : dishes) {
            formattedItems.add(d.toString());
        }
        printAsIndexedList(formattedItems);
    }
    public static void printShoppingListView(ArrayList<Ingredient> shoppingList) {
        LOGGER.log(Level.FINE, "Displaying shopping list with {0} items", shoppingList.size());
        //prints TaskList on console
        final List<String> formattedItems = new ArrayList<>();
        for (Ingredient ingredients : shoppingList) {
            formattedItems.add(formatIngredientForList(ingredients));
        }
        printAsIndexedList(formattedItems);
    }

    private static String formatIngredientForList(Ingredient ingredients) {
        return String.format("%-15s", ingredients.getName()) + " qty: " + ingredients.getQuantity();
    }

    /**
     * Outputs a list of recipes to the console in a formatted view.
     *
     * @param recipes A list of recipes to be displayed in the console.
     */
    public static void printRecipeListView(ArrayList<Recipe> recipes) {
        //prints TaskList on console
        final List<String> formattedItems = new ArrayList<>();
        for (Recipe r : recipes) {
            formattedItems.add(r.toString());
        }
        printAsIndexedList(formattedItems);
    }

    /**
     * Outputs a list of recipes to the console in a formatted view.
     *
     * @param ingredients A list of ingredients to be displayed in the console.
     */
    public static void printIngredientListView(HashMap<String, List<Ingredient>> ingredients) {
        if (ingredients.isEmpty()) {
            System.out.println("There are no ingredients");
            return;
        }
        for (String name : ingredients.keySet()) {
            System.out.println(name + ":");
            for (Ingredient ingredient : ingredients.get(name)) {
                System.out.println("  " + ingredient);
            }
        }
    }


    /**
     * print items as an indexed list
     */
    private static void printAsIndexedList(List<String> list) {
        printItems(getIndexedListForViewing(list));

    }

    /**
     * Formats a list of items into a string where each item is indexed, starting from 1.
     *
     * @param listItems A list of items to be formatted as an indexed list.
     * @return A string representing the indexed list of items.
     */
    private static String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = DISPLAYED_INDEX_OFFSET;
        //sets offset so it starts from 1
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * extracts items from each list entry
     *
     * @return a formatted version of items
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    /**
     * Waits for user input and processes commands until the user types "bye".
     * It initializes the required data structures, processes commands, and then stores the data before terminating.
     */
    public static void waitForCommand() {
        LOGGER.info("Starting command input loop");

        try {
            //initialize list
            Storage.DataWrapper wrapper = Storage.loadData();
            LOGGER.info("Data loaded successfully");

            DishCalendar.initializeDishCalendar(wrapper.dishes);
            RecipeBank.initializeRecipeBank(wrapper.recipes);
            IngredientStorage.initializeIngredientStorage(wrapper.ingredients);
            ShoppingList.initializeShoppingList(wrapper.shopping);

            Scanner scanner = new Scanner(System.in);
            String scannedText;
            System.out.print(USER_MARKER);
            while (!(scannedText = scanner.nextLine()).equals("bye")) {     //bye breaks the while loop
                LOGGER.log(Level.FINE, "Processing command: {0}", scannedText);
                Parser.decipherCommand(scannedText);
                System.out.print(USER_MARKER);

            }

            LOGGER.info("Saving data before exit");
            Storage.storeData(DishCalendar.getDishCalendar(),
                    RecipeBank.getRecipeBank(), IngredientStorage.getStorage(),
                    ShoppingList.getShoppingList());
            printItems(MESSAGE_STORE_SUCCESS + FILE_PATH);


        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in command processing: {0}", e.getMessage());
            throw e;
        }
    }


}
