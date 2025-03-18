package seedu.cookingaids.items;

public class Ingredient extends Food{
    static ExpiryDate expiryDate;
    private int quantity;

    public Ingredient(int id, String name) {
        super(id, name);
    }

    public Ingredient(int id, String name, int quantity ) {
        super(id, name);
        this.quantity = quantity;
        expiryDate = new ExpiryDate("None");
    }

    public Ingredient(int id, String name, String expiry_date, int quantity ) {
        super(id, name);
        expiryDate = new ExpiryDate(expiry_date);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public void displayInfo() {
        System.out.println("Ingredient ID: " + id + ", Name: " + name +", Scheduled for:" + expiryDate.toString());
    }

    @Override
    public String toString() {
        return name + " (" + quantity + (expiryDate != null? ", Expiry: " + expiryDate : "") + ")";
    }
}
