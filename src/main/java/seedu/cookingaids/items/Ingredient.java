package seedu.cookingaids.items;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient extends Food{
    public ExpiryDate expiryDate;
    private int quantity;
    private boolean expiringSoon = false;
    protected boolean expired = false;
    private  String name;
    public Ingredient( String name) {
        super( name);
        this.name = name;
    }

    public Ingredient( String name, int quantity ) {
        super( name);
        this.quantity = quantity;
        expiryDate = new ExpiryDate("None");
        this.name = name;
    }
    @JsonCreator
    public Ingredient( @JsonProperty("name") String name,
                      @JsonProperty("expiry") String expiryDate, @JsonProperty("quantity") int quantity ) {
        super(name);

        this.expiryDate = new ExpiryDate(expiryDate);
        this.quantity = quantity;
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    public String getName() {
        return name;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void setExpiringSoon(boolean b) {
        this.expiringSoon = b;
    }
    public void setExpired(boolean b) {
        this.expired = b;
    }


    @Override
    public void displayInfo() {
        System.out.println(" Name: " + name +", Expiring in:" + expiryDate.toString());
    }

    @Override
    public String toString() {
        String expiryFlag = expiringSoon ? "[X]" : "[]";
        return name + " (" + quantity + (expiryDate != null? ", Expiry: "
                + expiryDate : "") + ", Expiring Soon: " + expiryFlag + ")";
    }

}
