package com.bank.repository;

import com.bank.config.DatabaseManager;
import com.bank.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC repository for Transaction operations.
 * All methods use PreparedStatement with ? placeholders and try-with-resources.
 */
public class TransactionRepository {

    // ──── INSERT a transaction ────

    public void insert(Transaction txn) throws SQLException {
        String sql = """
            INSERT INTO transactions (account_number, transaction_type, amount)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, txn.getAccountNumber());
            ps.setString(2, txn.getTransactionType());
            ps.setDouble(3, txn.getAmount());
            ps.executeUpdate();
        }
    }

    // ──── FIND transactions by account number ────

    public List<Transaction> findByAccountNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }
        }
        return transactions;
    }

    // ──── FIND all transactions ────

    public List<Transaction> findAll() throws SQLException {
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }
        }
        return transactions;
    }

    // ──── GET total amount by transaction type (SUM query) ────

    public double getTotalByType(String transactionType) throws SQLException {
        String sql = "SELECT COALESCE(SUM(amount), 0) AS total FROM transactions WHERE transaction_type = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, transactionType);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
                return 0;
            }
        }
    }

    // ──── DELETE transactions by account number (used before deleting an account) ────

    public void deleteByAccountNumber(long accountNumber) throws SQLException {
        String sql = "DELETE FROM transactions WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            ps.executeUpdate();
        }
    }

    // ──── HELPER: map a ResultSet row to a Transaction object ────

    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("transaction_id"),
                rs.getLong("account_number"),
                rs.getString("transaction_type"),
                rs.getDouble("amount"),
                rs.getTimestamp("transaction_date").toString()
        );
    }
}
