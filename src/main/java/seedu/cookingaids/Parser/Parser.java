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
            AddCommand.addIngredient(receivedText);
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
        Pattern quantityPattern = Pattern.compile("\\d+"); // Matches positive numbers

        boolean hasIngredient = false;

        for (String part : parts) {
            if (part.startsWith("-ingredient=")) {
                data.put("ingredient", part.substring(11).trim());
                hasIngredient = true;
            } else if (part.startsWith("-quantity=")) {
                String quantity = part.substring(9).trim();
                if (quantityPattern.matcher(quantity).matches()) {
                    data.put("quantity", quantity);
                } else {
                    System.out.println("Invalid quantity format.");
                    return null; // Exit early if quantity is invalid
                }
            } else if (part.startsWith("-expiry=")) {
                data.put("expiry_date", part.substring(8).trim());
            }
        }

        // Exit early if ingredient is missing
        if (!hasIngredient) {
            return null;
        }

        // Default values
        data.putIfAbsent("quantity", "1");
        data.putIfAbsent("expiry_date", "None");

        return data;
    }




}