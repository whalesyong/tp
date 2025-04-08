package seedu.cookingaids.logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    private static final String LOG_FOLDER = "./data/log";
    private File logDir;

    @BeforeEach
    void setUp() {
        logDir = new File(LOG_FOLDER);
        if (!logDir.exists()) {
            assertTrue(logDir.mkdirs(), "Failed to create log directory");
        }

        // Clean up existing log files
        for (File file : logDir.listFiles()) {
            file.delete();
        }
    }

    @AfterEach
    void tearDown() {
        for (File file : logDir.listFiles()) {
            file.delete();
        }
    }

    private File[] getLogFiles() {
        return logDir.listFiles((dir, name) -> name.startsWith("cookingaids") && name.endsWith(".log"));
    }

    private void flushHandlers(Logger logger) {
        for (Handler handler : logger.getHandlers()) {
            handler.flush();
        }
    }

    private void waitForLogFile() {
        int retries = 10;
        while (retries-- > 0) {
            File[] files = getLogFiles();
            if (files != null && files.length > 0 && files[0].exists()) return;
            try {
                Thread.sleep(50);  // Allow time for async file I/O
            } catch (InterruptedException ignored) {}
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
        logger.severe("Test severe message");
        flushHandlers(logger);
        waitForLogFile();

        File[] files = getLogFiles();
        assertNotNull(files, "Log files array should not be null");
        assertTrue(files.length > 0, "At least one log file should exist");
        System.out.println("Log file created at: " + files[0].getAbsolutePath());
    }

    @Test
    void getLogger_logLevelsAreCorrect() {
        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        assertEquals(Level.FINE, logger.getLevel(), "Logger should have FINE level");

        Handler[] handlers = logger.getHandlers();
        assertTrue(handlers.length > 0, "Logger should have at least one handler");
        Handler consoleHandler = handlers[0];
        assertTrue(consoleHandler instanceof ConsoleHandler, "Handler should be a ConsoleHandler");
        assertEquals(Level.WARNING, consoleHandler.getLevel(), "Console handler should have WARNING level");

        logger.severe("Test severe message");
        logger.warning("Test warning message");
        logger.info("Test info message");
        logger.fine("Test fine message");

        flushHandlers(logger);
        waitForLogFile();

        File[] files = getLogFiles();
        assertTrue(files.length > 0, "At least one log file should exist");
        assertTrue(files[0].length() > 0, "Log file should contain logged messages");
    }

    @Test
    void getLogger_singletonFileHandler() {
        Logger logger1 = LoggerFactory.getLogger(LoggerTest.class);
        Logger logger2 = LoggerFactory.getLogger(String.class);

        logger1.severe("Log from logger1");
        logger2.severe("Log from logger2");

        flushHandlers(logger1);
        flushHandlers(logger2);
        waitForLogFile();

        File[] files = getLogFiles();
        assertEquals(1, files.length, "Should only create one log file for multiple loggers");
    }
}
