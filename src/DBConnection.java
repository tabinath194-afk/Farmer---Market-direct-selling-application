import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/farmer_market";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);

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
