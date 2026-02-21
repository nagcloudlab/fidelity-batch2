package com.bank.repository;

import com.bank.config.DatabaseManager;
import com.bank.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC repository for Account CRUD operations.
 * All methods use PreparedStatement with ? placeholders and try-with-resources.
 */
public class AccountRepository {

    // ──── CREATE ────

    public Account insert(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, holder_name, account_type, balance) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, account.getAccountNumber());
            ps.setString(2, account.getHolderName());
            ps.setString(3, account.getAccountType());
            ps.setDouble(4, account.getBalance());
            ps.executeUpdate();
            return account;
        }
    }

    // ──── READ (all) ────

    public List<Account> findAll() throws SQLException {
        String sql = "SELECT * FROM accounts ORDER BY account_number";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    // ──── READ (by account number) ────

    public Account findByAccountNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccount(rs);
                }
                return null;    // not found
            }
        }
    }

    // ──── UPDATE (balance only) ────

    public void updateBalance(long accountNumber, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setLong(2, accountNumber);
            ps.executeUpdate();
        }
    }

    // ──── UPDATE (full — holder name and account type) ────

    public Account update(long accountNumber, Account updated) throws SQLException {
        String sql = "UPDATE accounts SET holder_name = ?, account_type = ? WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, updated.getHolderName());
            ps.setString(2, updated.getAccountType());
            ps.setLong(3, accountNumber);

            int rows = ps.executeUpdate();
            if (rows == 0) return null;    // not found
            return findByAccountNumber(accountNumber);
        }
    }

    // ──── DELETE ────

    public boolean delete(long accountNumber) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            return ps.executeUpdate() > 0;    // true if a row was deleted
        }
    }

    // ──── SEARCH (by name — case insensitive LIKE) ────

    public List<Account> searchByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE LOWER(holder_name) LIKE ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapRowToAccount(rs));
                }
            }
        }
        return accounts;
    }

    // ──── FILTER (by account type) ────

    public List<Account> filterByType(String type) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE UPPER(account_type) = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapRowToAccount(rs));
                }
            }
        }
        return accounts;
    }

    // ──── HELPER: map a ResultSet row to an Account object ────

    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        return new Account(
                rs.getLong("account_number"),
                rs.getString("holder_name"),
                rs.getString("account_type"),
                rs.getDouble("balance")
        );
    }
}
