package seedu.cookingaids.items;

public abstract class Food {
    protected String name;

    public Food( String name) {

        this.name = name;
    }

    /**
     * Displays detailed information about the food item.
     * This method must be implemented by subclasses of Food to provide specific details about the food.
     */
    public abstract void displayInfo();


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  " Name: " + name ;
    }
}
