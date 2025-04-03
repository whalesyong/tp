package seedu.cookingaids.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents expiry date of an ingredient
 * implementation is as per dish date for standardisation
 */
public class ExpiryDate extends DishDate {

    @JsonCreator
    public ExpiryDate(@JsonProperty("dateString") String date) {
        super(date);
    }
}
