package com.example.repository;

import com.example.db.MySqlDatasourceConfiguration;
import com.example.entity.Account;
import com.example.entity.AccountType;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcAccountRepository implements AccountRepository {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(JdbcAccountRepository.class);

    @Override
    public List<Account> findAll() {
        logger.info("Loading all accounts from the database");
        // step-1: Load jdbc driver ( automatically done by having the driver in classpath )
        // step-2: Establish connection to database
        Connection connection = null;
        List<Account> accounts = new ArrayList<>();
        try {
            connection = MySqlDatasourceConfiguration.getDataSource().getConnection();
            logger.info("Successfully connected to the database");
            // step-3: Execute SQL query to load account data
            String sql = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // step-4: Map the result set to Account object and return it
            ResultSet resultSet = preparedStatement.executeQuery(); // query hits the database
            while (resultSet.next()) {
                logger.info("Successfully loaded account with account number: {}", resultSet.getString("account_number"));
                String accNumber = resultSet.getString("account_number");
                String accHolderName = resultSet.getString("account_holder_name");
                double balance = resultSet.getDouble("balance");
                String accType = resultSet.getString("account_type");
                Account account = new Account(accNumber, accHolderName, balance, AccountType.valueOf(accType.toUpperCase()));
                accounts.add(account);
            }
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // return the connection back to the pool
                    logger.info("Database connection closed");
                } catch (SQLException e) {
                    logger.error("Failed to close the database connection", e);
                    e.printStackTrace();
                }
            }
        }
        return accounts;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        logger.info("Loading account with account number: {}", accountNumber);
        // step-1: Load jdbc driver ( automatically done by having the driver in classpath )
        // step-2: Establish connection to database
        Connection connection = null;
        Account account = null;
        try {
            connection = MySqlDatasourceConfiguration.getDataSource().getConnection();
            logger.info("Successfully connected to the database");
            // step-3: Execute SQL query to load account data
            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountNumber);
            // step-4: Map the result set to Account object and return it
            ResultSet resultSet = preparedStatement.executeQuery(); // query hits the database
            if (resultSet.next()) {
                logger.info("Successfully loaded account with account number: {}", accountNumber);
                String accNumber = resultSet.getString("account_number");
                String accHolderName = resultSet.getString("account_holder_name");
                double balance = resultSet.getDouble("balance");
                String accType = resultSet.getString("account_type");
                account = new Account(accNumber, accHolderName, balance, AccountType.valueOf(accType.toUpperCase()));
            } else {
                logger.warn("No account found with account number: {}", accountNumber);
            }
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("Database connection closed");
                } catch (SQLException e) {
                    logger.error("Failed to close the database connection", e);
                    e.printStackTrace();
                }
            }
        }
        return account;
    }

    @Override
    public void updateAccount(Account account) {
        logger.info("Updating account with account number: {}", account.getAccountNumber());
        // step-1: Load jdbc driver ( automatically done by having the driver in classpath )
        // step-2: Establish connection to database
        Connection connection = null;
        try {
            connection = MySqlDatasourceConfiguration.getDataSource().getConnection();
            logger.info("Successfully connected to the database");
            // step-3: Execute SQL query to load account data
            String sql = "UPDATE accounts SET account_holder_name = ?, balance = ?, account_type = ? WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getAccountHolderName());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setString(3, account.getAccountType().name());
            preparedStatement.setString(4, account.getAccountNumber());
            // step-4: Map the result set to Account object and return it
            int rowsUpdated = preparedStatement.executeUpdate(); // query hits the database
            if (rowsUpdated > 0) {
                logger.info("Successfully updated account with account number: {}", account.getAccountNumber());
            } else {
                logger.warn("No account found with account number: {}", account.getAccountNumber());
            }
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("Database connection closed");
                } catch (SQLException e) {
                    logger.error("Failed to close the database connection", e);
                    e.printStackTrace();
                }
            }
        }
    }
}
