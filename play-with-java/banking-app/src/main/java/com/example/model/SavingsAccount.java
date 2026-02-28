package com.example.model;

import org.slf4j.Logger;

public class SavingsAccount extends Account {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SavingsAccount.class);

    private double interestRate;
    private static final double MINIMUM_BALANCE = 1000.0;

    public SavingsAccount(String holderName, long accountNumber, double initialBalance, double interestRate) {
        super(holderName, accountNumber, initialBalance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            logger.warn("Invalid withdrawal amount: Rs." + amount);
            return;
        }
        if (amount > getBalance() - MINIMUM_BALANCE) {
            logger.warn("Cannot withdraw. Must maintain minimum balance of Rs." + MINIMUM_BALANCE);
            logger.warn("Max withdrawal: Rs." + (getBalance() - MINIMUM_BALANCE));
            return;
        }
        setBalance(getBalance() - amount);
        logger.info("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
    }

    public void addInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
        logger.info("Interest of Rs." + interest + " added at " + interestRate + "% rate");
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void displayInfo() {
        logger.info("[SAVINGS] ");
        super.displayInfo();
        logger.info("  Interest Rate: " + interestRate + "% | Min Balance: Rs." + MINIMUM_BALANCE);
    }
}