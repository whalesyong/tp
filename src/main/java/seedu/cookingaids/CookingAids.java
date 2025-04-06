package seedu.cookingaids;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.cookingaids.logger.LoggerFactory;
import seedu.cookingaids.ui.Ui;

public class CookingAids {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookingAids.class);
    private static final String VERSION = "v2.1";

    public static void main(String[] args) {
        LOGGER.info("Starting CookingAids " + VERSION);
        
        try {
            Ui.showWelcomeMessage();
            System.out.println("CookingAIDS " + VERSION + ". Type \"help\" to see available commands");
            LOGGER.info("Application initialized successfully");
            
            Ui.waitForCommand();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Application crashed: {0;'q" +
                    "}", e.getMessage());
            throw e;
        }
    }
}

