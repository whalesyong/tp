package seedu.cookingaids.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpiryDate extends DishDate {

    @JsonCreator
    public ExpiryDate(@JsonProperty("dateString") String date) {
        super(date);
    }
}
