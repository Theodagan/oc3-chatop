package com.chatop.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class TestConnection {

    @Test
    public void testConnection() {

        String url = "jdbc:mysql://db:3306/my_database?connectTimeout=5000&socketTimeout=5000";
        String user = "root";
        String password = "root_password";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
