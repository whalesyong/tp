package seedu.cookingaids.Items;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Dish extends Food {
    private DishDate dishDate;

    // constructor for jackson
    @JsonCreator
    public Dish(@JsonProperty("id") int id,
                @JsonProperty("name") String name,
                @JsonProperty("date") String dishDate) {
        super(id, name);
        this.dishDate = (dishDate != null) ? new DishDate(dishDate) : new DishDate("None");
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

