package seedu.cookingaids.Parser;


import seedu.cookingaids.Commands.AddCommand;
import seedu.cookingaids.Commands.DisplayCommand;
import seedu.cookingaids.Ui.Ui;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Holds methods that manipulate input received from user into useful data
 * that can be understood by other methods
 */
public class Parser {
    /**
     * deciphers users input for commands and calls methods to execute
     * if unable to decipher message display instructions that helps user give commands
     *
     * @param receivedText entire command input received
     */
    public static void decipherCommand(String receivedText) {//method to determine what kind of task to call

        String command = receivedText.strip().split(" ")[0];
        switch (command) {
        case DisplayCommand.COMMAND_WORD:
            DisplayCommand.displayDishCalendar(); //create Todo task
            Ui.printLineDivider();
            break;

        case AddCommand.COMMAND_WORD:

            AddCommand.addDish(receivedText); //create Todo task
            Ui.printLineDivider();
            break;

        default:
            System.out.println("I DO NOT UNDERSTAND " + receivedText);
            System.out.println("type \"help\" to see available commands");

        }


    }

    public static String[] parseDish(String receivedText) {
        String[] returnedArray = new String[3];

        returnedArray[0] = "1";
        // saving text in front of /by as description, starting from after "deadline"
        try {

            returnedArray[1] = receivedText.split(" ")[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            returnedArray[1] = "none";
        }
        try {
            returnedArray[2] = receivedText.split(" ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            returnedArray[2] = "none";
        }
        return returnedArray;


    }

    public static HashMap<String, String> parseIngredient(String command) {
        HashMap<String, String> data = new HashMap<>();
        String[] parts = command.split(" ");
        Pattern quantityPattern = Pattern.compile("-\\d+"); // Matches numbers like -5, -10

        for (String part : parts) {
            if (quantityPattern.matcher(part).matches()) {
                data.put("quantity", part.substring(1)); // Remove "-"
            } else if (part.startsWith("-")) {
                if (isDate(part.substring(1))) {
                    data.put("expiry_date", part.substring(1));
                } else {
                    data.put("ingredient", part.substring(1));
                }
            }
        }

        if (!data.containsKey("expiry_date")) {
            data.put("expiry_date", "None");  // Default value if no expiry date is provided
        }

        return data;
    }

    /**
     * Checks if the input could be a date in various formats.
     */
    public static boolean isDate(String input) {
        // Matches YYYY-MM-DD or date formats with alphabets like "31 Dec"
        return input.matches("\\d{4}-\\d{2}-\\d{2}") || input.matches("\\d{1,2}.*[A-Za-z]+.*");
    }



}