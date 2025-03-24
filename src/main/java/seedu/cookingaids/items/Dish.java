package seedu.cookingaids.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Dish {
    private DishDate dishDate;
    private Recipe recipe;
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

    public void displayInfo() {
        if (dishDate.toString().isEmpty()) {
            System.out.println("Name: " + dishName + "No scheduled date yet");
        } else {
            System.out.println("Name: " + dishName + ", Scheduled for:" + dishDate.toString());
        }
    }

    @Override
    public String toString() {
        if (dishDate.toString().isEmpty()) {
            return  dishName + "No scheduled date yet";
        } else {
            return dishName + ", Scheduled for:" + dishDate.toString();
        }
    }

}

