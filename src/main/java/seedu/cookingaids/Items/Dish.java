package seedu.cookingaids.Items;

public class Dish extends Food {
    static DishDate dishDate;


    public Dish(int id, String name) {
        super(id, name);
        dishDate = new DishDate("None");
    }
    public Dish(int id, String name,String date) {
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

