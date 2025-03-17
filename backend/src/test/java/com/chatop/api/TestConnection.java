package com.chatop.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class TestConnection {

    @Test
    public void testConnection() {

        //String url = "jdbc:mysql://3306-idx-oc3-chatop-1737992916895.cluster-qtqwjj3wgzff6uxtk26wj7fzq6.cloudworkstations.dev/my_database?connectTimeout=5000&socketTimeout=5000";

        // String url = "jdbc:mysql://db:3306/my_database?connectTimeout=5000&socketTimeout=5000";

        String url = System.getenv("DATABASE_URL"); // Get from environment variable
        String user = System.getenv("DATABASE_USER");
        String password = System.getenv("DATABASE_PASSWORD");

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
