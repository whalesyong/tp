package seedu.cookingaids.logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;

public class LoggerFactory {
    private static final int BUFFER_SIZE = 1024;
    private static final Level DEFAULT_LEVEL = Level.FINE;
    private static final Level MEMORY_FLUSH_LEVEL = Level.INFO;
    private static final Level CONSOLE_LEVEL = Level.WARNING; // Only show severe errors to users
    private static final String LOG_FILE_PREFIX = "cookingaids";
    private static final String LOG_FILE_SUFFIX = ".log";
    private static final DateTimeFormatter DATE_FORMAT = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    private static FileHandler fileHandler = null;

    public static void closeFileHandler() {
        if (fileHandler != null) {
            fileHandler.flush();
            fileHandler.close();
            fileHandler = null;
        }
    }
    public static void flushAndCloseFileHandler() {
        if (fileHandler != null) {
            fileHandler.flush();
            fileHandler.close();
            fileHandler = null;
            System.err.println("FileHandler flushed and closed.");
        }
    }


    private static String getLogFolder(){
        return System.getProperty("cookingaids.logdir", "./data/log");
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(DEFAULT_LEVEL);

        // Add console handler with restricted level
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(CONSOLE_LEVEL); // Only severe errors shown in console
        logger.addHandler(consoleHandler);

        // Set up log file only if not already initialized
        if (fileHandler == null) {
            try {
                File logDir = new File(getLogFolder());
                if (!logDir.exists()) {
                    boolean created = logDir.mkdirs();
                    System.out.println("Created log dir: " + created + ", path: " + logDir.getAbsolutePath());
                }

                String timestamp = LocalDateTime.now().format(DATE_FORMAT);
                String logFileName = String.format("%s_%s%s", LOG_FILE_PREFIX, timestamp, LOG_FILE_SUFFIX);
                String currentLogPath = getLogFolder() + File.separator + logFileName;

                fileHandler = new FileHandler(currentLogPath, true);


                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.ALL);

                System.out.println("Log file path: " + currentLogPath);
            } catch (IOException e) {
                System.err.println("Failed to set up log file handler: " + e.getMessage());

                return logger;
            }
        }


        // Add memory handler with existing file handler
        try {
            MemoryHandler memoryHandler = new MemoryHandler(
                    fileHandler, BUFFER_SIZE, MEMORY_FLUSH_LEVEL);
            memoryHandler.setLevel(Level.ALL); // Keep full logging in file
            logger.addHandler(memoryHandler);
        } catch (Exception e) {
            System.err.println("Failed to set up memory handler: " + e.getMessage());
        }

        return logger;
    }
}
