package seedu.cookingaids.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;


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
            dateLocalDate = parseDate(date);
            dateString = dateLocalDate == null ? "none" :
                    dateLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        } catch (DateTimeParseException e) {
            dateLocalDate = null;
        }
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
        if (Objects.equals(receivedText, "none")) {
            return null;
        }
        return LocalDate.parse(receivedText);

    }


}
