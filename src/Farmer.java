/**
 * Farmer.java
 * -----------
 * Model class representing a Farmer.
 * A "model" class simply stores data (fields) and provides
 * getters/setters to read and update that data.
 */
public class Farmer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String location;

    // Constructor used when creating a NEW farmer (no id yet, since MySQL will auto-generate it)
    public Farmer(String name, String phone, String email, String password, String location) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.location = location;
    }

    // Constructor used when reading an EXISTING farmer from the database (id is known)
    public Farmer(int id, String name, String phone, String email, String password, String location) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.location = location;
    }

    // ---------- Getters and Setters ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
