package seedu.cookingaids.logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;

class LoggerTest {
    private static final String LOG_FOLDER = "./data/log";
    private File logDir;

    @BeforeEach
    void setUp() {
        logDir = new File(LOG_FOLDER);
        // Clean up any existing log files
        if (logDir.exists()) {
            for (File file : logDir.listFiles()) {
                file.delete();
            }
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up test log files
        if (logDir.exists()) {
            for (File file : logDir.listFiles()) {
                file.delete();
            }
        }
    }

    @Test
    void getLogger_createsLogDirectory() {
        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        assertTrue(logDir.exists(), "Log directory should be created");
        assertTrue(logDir.isDirectory(), "Log directory path should be a directory");
    }

    @Test
    void getLogger_createsLogFile() {
        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        
        // Log something to ensure file is created
        logger.severe("Test severe message");
        
        // Check that log files exist
        File[] files = logDir.listFiles((dir, name) -> 
            name.startsWith("cookingaids") && name.endsWith(".log"));
        assertNotNull(files, "Log files array should not be null");
        assertTrue(files.length > 0, "At least one log file should exist");
    }

    @Test
    void getLogger_logLevelsAreCorrect() {
        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        assertEquals(Level.FINE, logger.getLevel(), "Logger should have FINE level");
        
        // Get console handler and verify its level
        ConsoleHandler consoleHandler = (ConsoleHandler) logger.getHandlers()[0];
        assertEquals(Level.WARNING, consoleHandler.getLevel(), 
            "Console handler should have WARNING level");
        
        // Log messages at different levels
        logger.severe("Test severe message");
        logger.warning("Test warning message");
        logger.info("Test info message");
        logger.fine("Test fine message");
        
        // Verify log file exists and has content
        File[] files = logDir.listFiles((dir, name) -> 
            name.startsWith("cookingaids") && name.endsWith(".log"));
        assertTrue(files[0].length() > 0, "Log file should contain logged messages");
    }

    @Test
    void getLogger_singletonFileHandler() {
        Logger logger1 = LoggerFactory.getLogger(LoggerTest.class);
        Logger logger2 = LoggerFactory.getLogger(String.class);
        
        // Log with both loggers
        logger1.severe("Test logger1");
        logger2.severe("Test logger2");
        
        // Should only create one log file
        File[] files = logDir.listFiles((dir, name) -> 
            name.startsWith("cookingaids") && name.endsWith(".log"));
        assertEquals(1, files.length, "Should only create one log file for multiple loggers");
    }
}
