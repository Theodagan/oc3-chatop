package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/my_database?connectTimeout=5000&socketTimeout=5000";
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
