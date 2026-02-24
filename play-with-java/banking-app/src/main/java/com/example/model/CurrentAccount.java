package com.example.model;

import org.slf4j.Logger;

public class CurrentAccount extends Account {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(CurrentAccount.class);

    private double overdraftLimit;

    public CurrentAccount(String holderName, long accountNumber, double initialBalance, double overdraftLimit) {
        super(holderName, accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        if (amount > getBalance() + overdraftLimit) {
            System.out.println("Exceeds overdraft limit.");
            System.out.println("Max withdrawal: Rs." + (getBalance() + overdraftLimit));
            return;
        }
        setBalance(getBalance() - amount);
        System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
        if (getBalance() < 0) {
            System.out.println("WARNING: Account in overdraft by Rs." + Math.abs(getBalance()));
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void displayInfo() {
        logger.info("[CURRENT] ");
        super.displayInfo();
        logger.info("  Overdraft Limit: Rs." + overdraftLimit);
    }
}