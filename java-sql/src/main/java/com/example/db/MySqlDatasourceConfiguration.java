package com.example.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnectionFactory {

    public  static DataSource getConnection() throws SQLException {
        // In a real application, you would retrieve these values from a configuration file or environment variables
        String url = "jdbc:mysql://localhost:3306/mts_db";
        String username = "root";
        String password = "rootpass123";
        return java.sql.DriverManager.getConnection(url, username, password);
    }

}
