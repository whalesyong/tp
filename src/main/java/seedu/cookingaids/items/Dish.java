package seedu.cookingaids.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Dish {
    private DishDate dishDate;

    private String dishName;

    // constructor for jackson
    @JsonCreator
    public Dish(
            @JsonProperty("name") String dishName,
            @JsonProperty("date") String dishDate) {

        this.dishName = dishName.toLowerCase();
        this.dishDate = (dishDate != null) ? new DishDate(dishDate.toLowerCase()) : new DishDate("");
    }

    public String getName() {
        return dishName;
    }


    public DishDate getDishDate() {
        return dishDate;
    }

    public void setDishDate(DishDate dishDate) {
        this.dishDate = dishDate;
    }

    @Override
    public String toString() {
        if (dishDate.toString().equals("None")) {
            return  dishName ;
        } else {
            return String.format("%-15s", dishName) + ", Scheduled for:" + dishDate.toString();
        }
    }

}

