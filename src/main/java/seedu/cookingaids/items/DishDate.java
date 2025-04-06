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
            dateLocalDate = parseDate(date);
            dateString = dateLocalDate == null ? "None" : //None set here to match format
                    dateLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            assert dateString != null : "Date string should not be null";
            assert (dateLocalDate == null || dateLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .equals(dateString))
                    : "dateLocalDate and dateString should be consistent";

        } catch (DateTimeParseException e) {

            tryDateFormats();
        }
    }

    private void tryDateFormats() {
        LocalDate formattedDate = parseOtherDateFormat(dateString);

        if (formattedDate != null) {

            dateLocalDate = formattedDate;
        } else {

            LocalDate today = LocalDate.now();
            switch (dateString) {
            case "today", "td","tdy":
                dateLocalDate = today;
                break;
            case "tomorrow", "tmr":
                dateLocalDate = today.plusDays(1);
                break;
            case "nextweek", "nxt wk":
                dateLocalDate = today.plusWeeks(1);
                break;
            default:
                dateLocalDate = null;
            }
        }
        dateString = dateLocalDate == null ? "None" : //None set here to match format
                dateLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    /**
     * Parses a string into a LocalDate object.
     * If the string is "none", it returns null.
     *
     * @param receivedText The date string to be parsed.
     * @return The LocalDate corresponding to the parsed string, or null if the string is "none" or invalid.
     */
    private LocalDate parseDate(String receivedText) {
        if (receivedText == null || receivedText.equalsIgnoreCase("none")) {
            return null;
        }
        // Expecting the pattern dd/MM/yyyy as per your formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(receivedText, formatter);
    }

    private LocalDate parseOtherDateFormat(String dateString) {

        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        };

        LocalDate date;
        for (DateTimeFormatter formatter : formatters) {
            try {
                date = LocalDate.parse(dateString, formatter);
                return date;
            } catch (DateTimeParseException e) {
                //intentionally left empty for logic purposes

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




}
