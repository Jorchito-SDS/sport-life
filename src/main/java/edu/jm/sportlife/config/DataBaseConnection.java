package main.java.edu.jm.sportlife.config;

import java.sql.Connection;
import java.sql.DriverManager;
import main.java.edu.jm.sportlife.config.Credentials;
public class DataBaseConnection {
    private static Connection connection;
    
    private DataBaseConnection() {
    }
    
    public static Connection getConnectionDataBase() throws Exception {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(Credentials.URL_DB, Credentials.USER_DB, Credentials.PASS_DB);
        }
        return connection;
    }
}