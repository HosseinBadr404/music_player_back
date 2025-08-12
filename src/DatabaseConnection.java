import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/Music_Player";
    private static final String USER = "root";
    private static final String PASSWORD = "NPSru5687#";


    public static Connection getConnection() throws SQLException {
        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database!");
            return connection;
        } catch (SQLException e) {

            System.out.println("Database connection error: " + e.getMessage());
            throw e;
        }
    }
}