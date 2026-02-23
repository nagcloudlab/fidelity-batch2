package com.bank.model;

import com.bank.exception.InsufficientFundsException;
import java.io.Serializable;

/**
 * Abstract base class for all account types in the banking system.
 *
 * Key concepts demonstrated:
 * - Abstraction: Account is abstract — cannot be instantiated directly.
 *   Every account must be a specific type (Savings or Current).
 * - Encapsulation: Fields are private, accessed via getters/setters with validation.
 * - Serializable: Implements Serializable so accounts can be saved to binary files.
 * - Abstract method: calculateInterest() — each subclass defines its own interest logic.
 *
 * Day 2: Created as abstract class with inheritance hierarchy
 * Day 3: Added exception throwing in deposit/withdraw
 * Day 7: Added Serializable for file persistence
 */
public abstract class Account implements Serializable {

    // serialVersionUID is used during deserialization to verify that the sender
    // and receiver of a serialized object have loaded classes that are compatible.
    private static final long serialVersionUID = 1L;

    // Private fields — encapsulation ensures data integrity
    private long accountNumber;
    private String holderName;
    private double balance;
    private String accountType;   // "SAVINGS" or "CURRENT"
    private boolean isActive;

    /**
     * Parameterized constructor.
     * Called by subclasses via super(...) — constructor chaining.
     */
    public Account(long accountNumber, String holderName, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.accountType = accountType;
        this.isActive = true;
    }

    /**
     * Abstract method — each account type must define how interest is calculated.
     * SavingsAccount: balance * interestRate / 100
     * CurrentAccount: returns 0 (no interest on current accounts)
     */
    public abstract double calculateInterest();

    /**
     * Deposit money into the account.
     * Validates that the amount is positive and the account is active.
     * Throws IllegalArgumentException for invalid amounts.
     */
    public void deposit(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Account #" + accountNumber + " is inactive. Operation denied.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive. Got: " + amount);
        }
        this.balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    /**
     * Withdraw money from the account.
     * Base implementation checks for active status and positive amount.
     * Subclasses override this to add specific rules (min balance, overdraft).
     *
     * @throws InsufficientFundsException if insufficient funds
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (!isActive) {
            throw new IllegalStateException("Account #" + accountNumber + " is inactive. Operation denied.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive. Got: " + amount);
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Available: Rs." + balance, amount, balance);
        }
        this.balance -= amount;
        System.out.println("Withdrawn Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    /**
     * Display account information.
     * Subclasses override this to add type-specific details.
     */
    public void displayInfo() {
        String status = isActive ? "Active" : "INACTIVE";
        System.out.println("Account #" + accountNumber + " | " + holderName
                + " | Balance: Rs." + balance + " | Type: " + accountType
                + " | " + status);
    }

    // ==================== GETTERS ====================

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return isActive;
    }

    // ==================== SETTERS ====================

    public void setHolderName(String holderName) {
        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.holderName = holderName;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    /**
     * Protected setter — only subclasses can directly modify balance.
     * External code must use deposit() / withdraw() which enforce business rules.
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Restore balance to a previous value — used by undo (TransactionStack).
     * Package-private so only classes in the same package hierarchy can call it.
     */
    public void restoreBalance(double previousBalance) {
        this.balance = previousBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
