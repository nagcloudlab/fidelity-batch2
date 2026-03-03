package com.example.entity;


import jakarta.persistence.*;
import org.slf4j.Logger;

@Entity
@Table(name = "accounts")
public class Account {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(Account.class);

    @Id
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;
    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;
    private double balance;
    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    // Constructor
    public Account(){}
    public Account(String accountNumber, String accountHolderName, double balance, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            logger.warn("Invalid deposit amount: Rs." + amount);
            return;
        }
        this.balance += amount;
        logger.info("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    public  void withdraw(double amount){
        if (amount <= 0) {
            logger.warn("Invalid withdrawal amount: Rs." + amount);
            return;
        }
        if (amount > balance) {
            logger.warn("Insufficient funds for withdrawal of Rs." + amount);
            return;
        }
        this.balance -= amount;
        logger.info("Withdrew Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public double getBalance() {
        return balance;
    }
    public AccountType getAccountType() {
        return accountType;
    }


    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                '}';
    }
}