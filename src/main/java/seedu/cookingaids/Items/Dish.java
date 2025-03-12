package seedu.cookingaids.Items;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


public class Dish extends Food {
    private DishDate dishDate;


    public Dish(@JsonProperty("id") int id,
                @JsonProperty("name") String name) {
        super(id, name);
        dishDate = new DishDate("None");
    }
    public Dish(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("date") String date) {
        super(id, name);
        dishDate = new DishDate(date);
    }
    public DishDate getDishDate() {
        return dishDate;
    }


    @Override
    public void displayInfo() {

        System.out.println("Dish ID: " + id + ", Name: " + name +", Scheduled for:" + dishDate.toString());
    }

    @Override
    public String toString() {
        return super.toString() + ", Scheduled for:" + dishDate.toString();
    }
}

