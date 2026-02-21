package com.bank.model;

import com.bank.exception.InsufficientFundsException;

/**
 * CurrentAccount extends Account — demonstrates inheritance with different behavior.
 *
 * Key concepts demonstrated:
 * - Inheritance: extends Account, gets all common fields/methods
 * - Method overriding: withdraw() allows overdraft up to a limit
 * - Polymorphism: same withdraw() call, different behavior than SavingsAccount
 *
 * Business rules:
 * - Overdraft limit defaults to Rs.10,000
 * - No interest on current accounts (calculateInterest returns 0)
 * - Can withdraw beyond balance up to the overdraft limit
 */
public class CurrentAccount extends Account {

    private static final long serialVersionUID = 1L;

    // Maximum amount the account can go negative
    private double overdraftLimit;

    /**
     * Constructor with explicit overdraft limit.
     */
    public CurrentAccount(long accountNumber, String holderName, double balance, double overdraftLimit) {
        super(accountNumber, holderName, balance, "CURRENT");
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Convenience constructor with default overdraft limit of Rs.10,000.
     */
    public CurrentAccount(long accountNumber, String holderName, double balance) {
        this(accountNumber, holderName, balance, 10000.0);
    }

    /**
     * Current accounts earn no interest.
     * We MUST implement this abstract method, even though it returns 0.
     */
    @Override
    public double calculateInterest() {
        return 0.0;
    }

    /**
     * Override withdraw to allow overdraft.
     * Unlike SavingsAccount, CurrentAccount can go negative up to overdraftLimit.
     *
     * Example: balance=30000, overdraftLimit=10000
     *   - Can withdraw up to 40000 (30000 + 10000)
     *   - Withdrawing 35000 leaves balance at -5000 (overdraft warning)
     *
     * @throws InsufficientFundsException if amount exceeds balance + overdraft limit
     */
    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (!isActive()) {
            throw new IllegalStateException("Account #" + getAccountNumber() + " is inactive.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive. Got: " + amount);
        }
        // Current account rule: allow overdraft up to limit
        if (amount > getBalance() + overdraftLimit) {
            throw new InsufficientFundsException(
                    "Exceeds overdraft limit of Rs." + overdraftLimit
                            + ". Max withdrawal: Rs." + (getBalance() + overdraftLimit),
                    amount,
                    getBalance());
        }
        setBalance(getBalance() - amount);
        System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
        // Warn if account is now in overdraft (negative balance)
        if (getBalance() < 0) {
            System.out.println("WARNING: Account in overdraft by Rs." + Math.abs(getBalance()));
        }
    }

    /**
     * Override displayInfo to show current-account-specific details.
     */
    @Override
    public void displayInfo() {
        System.out.print("[CURRENT] ");
        super.displayInfo();
        System.out.println("  Overdraft Limit: Rs." + overdraftLimit);
    }

    // ==================== GETTERS ====================

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
