package seedu.cookingaids.commands;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HelpCommandTest {

    @Test
    void showHelp() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            HelpCommand.showHelp();

            String output = outputStream.toString();

            // Check key phrases to confirm output contains expected help content
            assertTrue(output.contains("Available commands:"));
            assertTrue(output.contains("add - Adds a new recipe, dish or ingredient"));
            assertTrue(output.contains("list - Displays all stored recipes"));
            assertTrue(output.contains("view - Displays dishes scheduled"));
            assertTrue(output.contains("update - Edits a recipe"));
            assertTrue(output.contains("delete - Deletes a recipe"));
            assertTrue(output.contains("suggest - Suggests dishes"));
            assertTrue(output.contains("bye - Exits the program"));
            assertTrue(output.contains("help - Shows this help message"));
            assertTrue(output.contains("User Guide"));

        } finally {
            System.setOut(originalOut);
        }
    }
}
