package eu.senla.course.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
final public class ConnectionUtil {

    private static String user;
    private static String password;
    private static String url;
    private static String driver;
    @Value("${db.user}")
    public void setUser(String user) {
        ConnectionUtil.user = user;
    }
    @Value("${db.password}")
    public void setPassword(String password) {
        ConnectionUtil.password = password;
    }
    @Value("${db.url}")
    public void setUrl(String url) {
        ConnectionUtil.url = url;
    }
    @Value("${db.driver}")
    public void setDriver(String driver) {
        ConnectionUtil.driver = driver;
    }

    private static ConnectionUtil instance = new ConnectionUtil();
    private static Connection connection = null;

    private ConnectionUtil() {

    }

    public static ConnectionUtil getInstance() {
        return instance;
    }

    public Connection connect() {
        if (connection == null) {
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

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error close connection " + e.getMessage());
            }
        }
    }
}
