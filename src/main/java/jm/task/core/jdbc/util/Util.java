package jm.task.core.jdbc.util;// тест


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private Util() {
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            throw new RuntimeException(e);
        }
        return connection;
    }
}
