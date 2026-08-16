import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 * ------------------
 * This class is responsible for creating and returning a connection
 * to the MySQL database. All database settings are kept here ONLY,
 * so no other class needs to hardcode the URL, username, or password.
 */
public class DBConnection {

    // Database connection details (change here if your setup is different)
    private static final String URL = "jdbc:mysql://localhost:3306/farmer_market";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Fully qualified name of the MySQL JDBC driver class
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Creates and returns a new Connection object to the MySQL database.
     * Returns null if the connection could not be created.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName(DRIVER);

            // Create the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: MySQL JDBC Driver not found.");
            System.out.println("Make sure mysql-connector-j.jar is added to your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the database.");
            System.out.println("Please check that MySQL is running and your credentials are correct.");
            e.printStackTrace();
        }
        return connection;
    }
}
