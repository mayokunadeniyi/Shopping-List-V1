package Model;

public class Item {

    private int id;
    private String itemName;
    private String itemQuantity;
    private String itemDateCreated;

    public Item() {
    }

    public Item(int id, String itemName, String itemQuantity, String itemDateCreated) {
        this.id = id;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemDateCreated = itemDateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemDateCreated() {
        return itemDateCreated;
    }

    public void setItemDateCreated(String itemDateCreated) {
        this.itemDateCreated = itemDateCreated;
    }
}
