package com.bank.model;

/**
 * POJO representing a bank transaction (deposit or withdrawal).
 */
public class Transaction {

    private int transactionId;
    private long accountNumber;
    private String transactionType;    // "DEPOSIT" or "WITHDRAW"
    private double amount;
    private String transactionDate;

    // Default constructor (required by Jackson for JSON deserialization)
    public Transaction() {
    }

    // Parameterized constructor
    public Transaction(int transactionId, long accountNumber, String transactionType,
                       double amount, String transactionDate) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
