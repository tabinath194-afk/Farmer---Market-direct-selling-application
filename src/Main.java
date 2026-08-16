import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerFarmer();
                    break;
                case 2:
                    registerCustomer();
                    break;
                case 3:
                    addProduct();
                    break;
                case 4:
                    viewProducts();
                    break;
                case 5:
                    searchProduct();
                    break;
                case 6:
                    placeOrder();
                    break;
                case 7:
                    viewOrders();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using Farmer to Market Platform. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("     FARMER TO MARKET PLATFORM");
        System.out.println("========================================");
        System.out.println("1. Farmer Registration");
        System.out.println("2. Customer Registration");
        System.out.println("3. Add Product");
        System.out.println("4. View Products");
        System.out.println("5. Search Product");
        System.out.println("6. Place Order");
        System.out.println("7. View Orders");
        System.out.println("8. Exit");
        System.out.println("========================================");
    }

    private static void registerFarmer() {
        System.out.println("\n--- Farmer Registration ---");
        String name = readString("Enter name: ");
        String phone = readString("Enter phone: ");
        String email = readString("Enter email: ");
        String password = readString("Enter password: ");
        String location = readString("Enter location: ");

        String sql = "INSERT INTO farmers (name, phone, email, password, location) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) return;

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, location);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        System.out.println("Farmer registered successfully! Farmer ID = " + keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Could not register farmer.");
            e.printStackTrace();
        }
    }

    private static void registerCustomer() {
        System.out.println("\n--- Customer Registration ---");
        String name = readString("Enter name: ");
        String phone = readString("Enter phone: ");
        String email = readString("Enter email: ");
        String password = readString("Enter password: ");
        String address = readString("Enter address: ");

        String sql = "INSERT INTO customers (name, phone, email, password, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) return;

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, address);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        System.out.println("Customer registered successfully! Customer ID = " + keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Could not register customer.");
            e.printStackTrace();
        }
    }

  
    private static void addProduct() {
        System.out.println("\n--- Add Product ---");
        int farmerId = readInt("Enter your Farmer ID: ");
        String name = readString("Enter product name: ");
        String category = readString("Enter category: ");
        String description = readString("Enter description: ");
        int quantity = readInt("Enter quantity: ");
        double price = readDouble("Enter price per unit: ");

        String sql = "INSERT INTO products (farmer_id, name, category, description, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return;

            stmt.setInt(1, farmerId);
            stmt.setString(2, name);
            stmt.setString(3, category);
            stmt.setString(4, description);
            stmt.setInt(5, quantity);
            stmt.setDouble(6, price);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Product added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Could not add product. Make sure the Farmer ID exists.");
            e.printStackTrace();
        }
    }

  
    private static void viewProducts() {
        System.out.println("\n--- Available Products ---");

        String sql = "SELECT id, farmer_id, name, category, description, quantity, price FROM products";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (conn == null) return;

            boolean found = false;
            while (rs.next()) {
                found = true;
                printProductRow(rs);
            }

            if (!found) {
                System.out.println("No products found.");
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Could not fetch products.");
            e.printStackTrace();
        }
    }

  
    private static void searchProduct() {
        System.out.println("\n--- Search Product ---");
        String keyword = readString("Enter product name or category to search: ");

        String sql = "SELECT id, farmer_id, name, category, description, quantity, price " +
                "FROM products WHERE name LIKE ? OR category LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return;

            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    printProductRow(rs);
                }
                if (!found) {
                    System.out.println("No matching products found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Could not search products.");
            e.printStackTrace();
        }
    }

    private static void printProductRow(ResultSet rs) throws SQLException {
        System.out.println("----------------------------------------");
        System.out.println("Product ID : " + rs.getInt("id"));
        System.out.println("Farmer ID  : " + rs.getInt("farmer_id"));
        System.out.println("Name       : " + rs.getString("name"));
        System.out.println("Category   : " + rs.getString("category"));
        System.out.println("Description: " + rs.getString("description"));
        System.out.println("Quantity   : " + rs.getInt("quantity"));
        System.out.println("Price      : " + rs.getDouble("price"));
    }

    
    private static void placeOrder() {
        System.out.println("\n--- Place Order ---");
        int customerId = readInt("Enter your Customer ID: ");
        int productId = readInt("Enter Product ID to order: ");
        int quantity = readInt("Enter quantity to order: ");

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) return;

            // Step 1: Fetch product price and available quantity
            String checkSql = "SELECT price, quantity FROM products WHERE id = ?";
            double price;
            int availableQty;

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, productId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Product not found.");
                        return;
                    }
                    price = rs.getDouble("price");
                    availableQty = rs.getInt("quantity");
                }
            }

            if (quantity > availableQty) {
                System.out.println("Not enough stock available. Available quantity: " + availableQty);
                return;
            }

            double totalPrice = price * quantity;
            String orderDate = LocalDate.now().toString();
            String status = "PENDING";

            // Step 2: Insert the order
            String insertSql = "INSERT INTO orders (customer_id, product_id, quantity, total_price, order_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, customerId);
                insertStmt.setInt(2, productId);
                insertStmt.setInt(3, quantity);
                insertStmt.setDouble(4, totalPrice);
                insertStmt.setString(5, orderDate);
                insertStmt.setString(6, status);

                int rows = insertStmt.executeUpdate();

                if (rows > 0) {
                    // Step 3: Reduce product stock
                    String updateSql = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setInt(2, productId);
                        updateStmt.executeUpdate();
                    }

                    try (ResultSet keys = insertStmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            System.out.println("Order placed successfully! Order ID = " + keys.getInt(1));
                            System.out.println("Total Price = " + totalPrice);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Could not place order. Make sure Customer ID and Product ID exist.");
            e.printStackTrace();
        }
    }

    
    private static void viewOrders() {
        System.out.println("\n--- View Orders ---");
        System.out.println("1. View all orders");
        System.out.println("2. View orders by Customer ID");
        int subChoice = readInt("Enter your choice: ");

        String sql;
        Integer customerId = null;

        if (subChoice == 2) {
            customerId = readInt("Enter Customer ID: ");
            sql = "SELECT id, customer_id, product_id, quantity, total_price, order_date, status " +
                    "FROM orders WHERE customer_id = ?";
        } else {
            sql = "SELECT id, customer_id, product_id, quantity, total_price, order_date, status FROM orders";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return;

            if (customerId != null) {
                stmt.setInt(1, customerId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println("----------------------------------------");
                    System.out.println("Order ID    : " + rs.getInt("id"));
                    System.out.println("Customer ID : " + rs.getInt("customer_id"));
                    System.out.println("Product ID  : " + rs.getInt("product_id"));
                    System.out.println("Quantity    : " + rs.getInt("quantity"));
                    System.out.println("Total Price : " + rs.getDouble("total_price"));
                    System.out.println("Order Date  : " + rs.getString("order_date"));
                    System.out.println("Status      : " + rs.getString("status"));
                }
                if (!found) {
                    System.out.println("No orders found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Could not fetch orders.");
            e.printStackTrace();
        }
    }

  
    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
