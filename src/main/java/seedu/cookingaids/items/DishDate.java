package seedu.cookingaids.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * represents date of a task
 * holds date as string and as LocalDate type
 * hold methods necessary to manipulate task Date
 */
public class DishDate {

    @JsonIgnore
    public LocalDate dateLocalDate;
    public String dateString;

    /**
     * creates a new instance of dishDate
     * saves string input as dateString
     * tries to parse String input into LocalDate type
     * else saves dateLocalDate as null
     *
     * @param date is the date that is to be instantiated
     */
    @JsonCreator
    public DishDate(@JsonProperty("dateString") String date) {
        this.dateString = date;
        try {
            dateLocalDate = parseDateFormat(date);
            dateString = dateLocalDate == null ? "None" : //None set here to match format
                    dateLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        } catch (DateTimeParseException e) {
            dateLocalDate = null;
        }
    }

    public LocalDate parseDateFormat(String dateString) {
        LocalDate today = LocalDate.now();

        switch (dateString) {
        case "today", "td":
            return today;
        case "tomorrow", "tmr":
            return today.plusDays(1);
        case "next week", "nxt wk":
            return today.plusWeeks(1);
        default:
            break;
        }

        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        };

        // Try each format until one works
        LocalDate date = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                date = LocalDate.parse(dateString, formatter);
                return date;

            } catch (DateTimeParseException e) {
                // Tries next format
            }
        }
        return null;

    }


    /**
     * sets a new date for taskDate
     *
     * @param date is the date that is to be instantiated
     */
    public void setDate(String date) {
        this.dateString = date;
        try {
            dateLocalDate = parseDate(date);
        } catch (DateTimeParseException e) {
            dateLocalDate = null;
        }
    }

    public LocalDate getDateLocalDate() {
        return this.dateLocalDate;
    }

    @Override
    public String toString() {
        return dateString;
    }

    /**
     * Parses a string into a LocalDate object.
     * If the string is "none", it returns null.
     *
     * @param receivedText The date string to be parsed.
     * @return The LocalDate corresponding to the parsed string, or null if the string is "none" or invalid.
     */
    public static LocalDate parseDate(String receivedText) {
        if (receivedText == null || receivedText.equalsIgnoreCase("none")) {
            return null;
        }
        // Expecting the pattern dd/MM/yyyy as per your formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(receivedText, formatter);
    }


}
