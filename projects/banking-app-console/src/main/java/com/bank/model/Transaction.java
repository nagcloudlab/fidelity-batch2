package com.bank.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Transaction records a deposit or withdrawal operation.
 *
 * Key concepts demonstrated:
 * - Serializable: can be saved to binary files
 * - Immutable design: all fields are set at creation, no setters
 * - Storing balanceBefore/balanceAfter enables undo capability
 *
 * Used by:
 * - TransactionStack (Day 5): push/pop for undo
 * - FileManager (Day 7): save/load transaction history
 */
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    // Auto-incrementing transaction ID
    private static long nextId = 1;

    private long transactionId;
    private long accountNumber;
    private String type;              // "DEPOSIT" or "WITHDRAW"
    private double amount;
    private String timestamp;
    private double balanceBefore;     // balance before the transaction — needed for undo!
    private double balanceAfter;      // balance after the transaction

    /**
     * Create a new transaction record.
     * Automatically assigns an incrementing ID and timestamps the creation.
     */
    public Transaction(long accountNumber, String type, double amount,
                       double balanceBefore, double balanceAfter) {
        this.transactionId = nextId++;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now().toString();
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }

    /**
     * Display transaction in a formatted line.
     */
    public void display() {
        System.out.printf("  [#%d] %s | %-10s Rs.%10.2f | Acc#%d | Rs.%.2f -> Rs.%.2f%n",
                transactionId, timestamp, type, amount, accountNumber,
                balanceBefore, balanceAfter);
    }

    // ==================== GETTERS ====================

    public long getTransactionId() {
        return transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + transactionId +
                ", accNo=" + accountNumber +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", before=" + balanceBefore +
                ", after=" + balanceAfter +
                '}';
    }
}
