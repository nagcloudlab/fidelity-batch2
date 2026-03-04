package com.example.entity;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account implements Comparable<Account>, Serializable {

    private static Logger logger = LoggerFactory.getLogger(Account.class);

    @Id
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;
    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;
    private double balance;
    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public Account(String accountNumber, String accountHolderName, double balance, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.accountType = accountType;
    }

    public Account() {
    }

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

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            logger.info("Deposited {} to account {}, new balance: {}", amount, accountNumber, balance);
        }else{
            throw new IllegalArgumentException("Invalid deposit amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            logger.info("Withdrew {} from account {}, new balance: {}", amount, accountNumber, balance);
        }else{
            throw new IllegalArgumentException("Insufficient balance or invalid amount");
        }
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

    //accountNumber -> equals and hashCode methods for proper functioning in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }


    @Override    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

}
