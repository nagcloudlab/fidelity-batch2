package com.bank.config;

import java.sql.*;

/**
 * Static helper class for H2 database connection and initialization.
 * Uses DriverManager for in-memory H2 database.
 * DB_CLOSE_DELAY=-1 keeps the database alive as long as the JVM runs.
 */
public class DatabaseManager {

    private static final String URL = "jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    /**
     * Returns a new JDBC connection to the H2 in-memory database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Creates the accounts and transactions tables and inserts sample data.
     * Called once at application startup.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Create accounts table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    account_number BIGINT PRIMARY KEY,
                    holder_name VARCHAR(100) NOT NULL,
                    account_type VARCHAR(20) NOT NULL,
                    balance DOUBLE DEFAULT 0
                )
            """);

            // Create transactions table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                    account_number BIGINT NOT NULL,
                    transaction_type VARCHAR(20) NOT NULL,
                    amount DOUBLE NOT NULL,
                    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
                )
            """);

            // Insert sample data if tables are empty
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM accounts");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.executeUpdate("""
                    INSERT INTO accounts (account_number, holder_name, account_type, balance) VALUES
                    (1001, 'Ravi Kumar', 'SAVINGS', 50000),
                    (1002, 'Priya Shah', 'CURRENT', 120000),
                    (1003, 'Amit Verma', 'SAVINGS', 30000),
                    (1004, 'Neha Gupta', 'SAVINGS', 75000)
                """);
                System.out.println("Sample data loaded: 4 accounts inserted.");
            }

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
