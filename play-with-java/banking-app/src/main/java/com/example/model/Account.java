
package com.example.model;

import org.slf4j.Logger;

import com.example.BankApp;

public abstract class Account {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(Account.class);

    private String holderName;
    private long accountNumber;
    private double balance;
    private boolean isActive;

    // Constructor
    public Account(String holderName, long accountNumber, double initialBalance) {
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isActive = true;
    }

    // Concrete method — same for all account types
    public void deposit(double amount) {
        if (amount <= 0) {
            logger.warn("Invalid deposit amount: Rs." + amount);
            return;
        }
        this.balance += amount;
        logger.info("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    // Abstract method — each account type MUST implement its own rules
    public abstract void withdraw(double amount);

    // Concrete display — children can override if needed
    public void displayInfo() {
        logger.info("Account #" + accountNumber + " | " + holderName
                + " | Balance: Rs." + balance + " | Active: " + isActive);
    }

    // Getters
    public String getHolderName() {
        return holderName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setHolderName(String holderName) {
        if (holderName == null || holderName.trim().isEmpty()) {
            logger.warn("Name cannot be empty.");
            return;
        }
        this.holderName = holderName;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // Protected — only children can use this to modify balance
    protected void setBalance(double balance) {
        this.balance = balance;
    }
}