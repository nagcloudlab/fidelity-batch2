package com.bank.model;

/**
 * Flat POJO representing a bank account.
 * Not abstract — needed for Jackson JSON serialization.
 * Jackson uses the default constructor + getters/setters for JSON conversion.
 */
public class Account {

    private long accountNumber;
    private String holderName;
    private String accountType;    // "SAVINGS" or "CURRENT"
    private double balance;

    // Default constructor (required by Jackson for JSON deserialization)
    public Account() {
    }

    // Parameterized constructor
    public Account(long accountNumber, String holderName, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Getters and Setters (ALL needed for JSON conversion)

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", holderName='" + holderName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                '}';
    }
}
