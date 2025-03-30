package seedu.cookingaids.items;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient extends Food{
    public ExpiryDate expiryDate;
    private int quantity;
    private boolean expiringSoon = false;
    private  String name;
    public Ingredient(int id, String name) {
        super(id, name);
        this.name = name;
    }

    public Ingredient(int id, String name, int quantity ) {
        super(id, name);
        this.quantity = quantity;
        expiryDate = new ExpiryDate("None");
        this.name = name;
    }
    @JsonCreator
    public Ingredient(@JsonProperty("id") int id, @JsonProperty("name") String name,
                      @JsonProperty("expiry") String expiryDate, @JsonProperty("quantity") int quantity ) {
        super(id, name);
        this.expiryDate = new ExpiryDate(expiryDate);
        this.quantity = quantity;
        this.name = name;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    public String getName() { return name; }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void setExpiringSoon(boolean b) {
        this.expiringSoon = b;
    }


    @Override
    public void displayInfo() {
        System.out.println("Ingredient ID: " + id + ", Name: " + name +", Scheduled for:" + expiryDate.toString());
    }

    @Override
    public String toString() {
        String expiryFlag = expiringSoon ? "[X]" : "[]";
        return name + " (" + quantity + (expiryDate != null? ", Expiry: "
                + expiryDate : "") + ", Expiring Soon: " + expiryFlag + ")";
    }

}
