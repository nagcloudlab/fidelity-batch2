package com.example.repository;

import com.example.entity.Account;
import com.example.entity.AccountType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * authors: foo
 */

//@Component("jdbcAccountRepository")
public class JdbcAccountRepository_Without_Spring_Support implements AccountRepository {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(JdbcAccountRepository_Without_Spring_Support.class);

    private DataSource dataSource;

//    @Autowired
    public JdbcAccountRepository_Without_Spring_Support(DataSource dataSource) {
       this.dataSource = dataSource;
       logger.info("JdbcAccountRepository initialized with DataSource: {}", dataSource);
    }

    @Override
    public Account findById(String accountNumber) {
        logger.info("JdbcAccountRepository findById called with accountNumber: {}", accountNumber);
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            logger.info("Successfully obtained connection from DataSource: {}", connection);
            String sql= "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
               String accountNum = resultSet.getString("account_number");
               String accountHolderName = resultSet.getString("account_holder_name");
               double balance = resultSet.getDouble("balance");
               String accountTypeStr = resultSet.getString("account_type");
               AccountType accountType = AccountType.valueOf(accountTypeStr);
               Account account = new Account(accountNum, accountHolderName, balance, accountType);
               logger.info("Account found: {}", account);
                return account;
            } else {
                logger.warn("No account found with accountNumber: {}", accountNumber);
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error obtaining connection from DataSource", e);
            throw new RuntimeException("Database error", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("Connection closed successfully");
                } catch (Exception e) {
                    logger.error("Error closing connection", e);
                }
            }
        }
    }

    @Override
    public void save(Account account) {
        logger.info("JdbcAccountRepository save called with account: {}", account);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            logger.info("Successfully obtained connection from DataSource: {}", connection);
            String sql = "UPDATE accounts SET account_holder_name = ?, balance = ?, account_type = ? WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getAccountHolderName());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setString(3, account.getAccountType().name());
            preparedStatement.setString(4, account.getAccountNumber());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Account updated successfully: {}", account);
            } else {
                logger.warn("No account found to update with accountNumber: {}", account.getAccountNumber());
            }
        }catch (Exception e) {
            logger.error("Error obtaining connection from DataSource", e);
            throw new RuntimeException("Database error", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("Connection closed successfully");
                } catch (Exception e) {
                    logger.error("Error closing connection", e);
                }
            }
        }
    }

}
