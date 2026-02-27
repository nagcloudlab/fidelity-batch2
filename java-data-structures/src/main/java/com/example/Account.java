package com.example;

/**
 * author: Nagendra Kumar
 */

public class Account /* extends Object */ implements Comparable<Account> {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public int compareTo(Account other) {
        // Compare by account number
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode(); // hash code based on account number
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // check reference equality
        if (obj == null || getClass() != obj.getClass()) return false; // check type

        Account other = (Account) obj; // cast to Account
        return accountNumber.equals(other.accountNumber); // check logical equality based on account number
    }

}
