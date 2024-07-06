package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    //private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // проверить

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    public static Connection connection = null;

    public static Connection openConnection()  {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            throw new RuntimeException(e);
        }
        return connection;
    }
}
