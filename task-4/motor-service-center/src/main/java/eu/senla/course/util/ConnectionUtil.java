package eu.senla.course.util;

import eu.senla.course.annotation.property.ConfigProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    @ConfigProperty(key = "db.user")
    private static String user;
    @ConfigProperty(key = "db.password")
    private static String password;
    @ConfigProperty(key = "db.url")
    private static String url;
    @ConfigProperty(key = "db.driver")
    private static String driver;

    private static ConnectionUtil instance = new ConnectionUtil();
    private static Connection connection = null;

    private ConnectionUtil() {

    }

    public static ConnectionUtil getInstance(){
        return instance;
    }

    public Connection connect() {
        if (connection == null){
            try {
                if (driver != null) {
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Exception " + e.getMessage());
            }
        }
        return connection;
    }

    public void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error close connection " + e.getMessage());
            }
        }
    }
}
