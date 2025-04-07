package seedu.cookingaids;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class CookingAidsTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setProperty("test.env", "true"); // Skip waitForCommand during tests
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.clearProperty("test.env"); // Clear property after the test
    }

    @Test
    void testMainOutput() {
        CookingAids.main(new String[]{});

        String output = outputStream.toString().trim();
        assertTrue(output.contains("CookingAIDS v2.1. Type \"help\" to see available commands"));
    }
}
