/**
 * Product.java
 * ------------
 * Model class representing an agricultural Product
 * that a Farmer has listed for sale.
 */
public class Product {

    private int id;
    private int farmerId;
    private String name;
    private String category;
    private String description;
    private int quantity;
    private double price;

    // Constructor used when creating a NEW product
    public Product(int farmerId, String name, String category, String description, int quantity, double price) {
        this.farmerId = farmerId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // Constructor used when reading an EXISTING product from the database
    public Product(int id, int farmerId, String name, String category, String description, int quantity, double price) {
        this.id = id;
        this.farmerId = farmerId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // ---------- Getters and Setters ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", farmerId=" + farmerId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
