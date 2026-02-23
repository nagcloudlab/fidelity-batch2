package com.bank.model;

import com.bank.exception.InsufficientFundsException;

/**
 * SavingsAccount extends Account — demonstrates inheritance.
 *
 * Key concepts demonstrated:
 * - Inheritance: extends Account, inherits all fields and methods
 * - Method overriding: withdraw() enforces minimum balance rule
 * - Polymorphism: can be referenced as Account type
 * - super keyword: calls parent constructor and methods
 *
 * Business rules:
 * - Interest rate defaults to 4.0%
 * - Minimum balance of Rs.1000 must be maintained
 * - Interest is calculated as: balance * interestRate / 100
 */
public class SavingsAccount extends Account {

    private static final long serialVersionUID = 1L;

    // Default interest rate for savings accounts
    private double interestRate;

    // Minimum balance that must be maintained in a savings account
    private static final double MINIMUM_BALANCE = 1000.0;

    /**
     * Constructor — calls parent constructor via super().
     * This is constructor chaining: SavingsAccount -> Account.
     */
    public SavingsAccount(long accountNumber, String holderName, double balance, double interestRate) {
        super(accountNumber, holderName, balance, "SAVINGS");
        this.interestRate = interestRate;
    }

    /**
     * Convenience constructor with default interest rate of 4.0%.
     */
    public SavingsAccount(long accountNumber, String holderName, double balance) {
        this(accountNumber, holderName, balance, 4.0);
    }

    /**
     * Calculate interest for savings account.
     * This is the abstract method from Account — we MUST implement it.
     *
     * @return interest amount based on current balance and interest rate
     */
    @Override
    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }

    /**
     * Override withdraw to enforce minimum balance rule.
     *
     * @Override annotation tells the compiler we're intentionally overriding
     * a parent method. If we misspell the method name, the compiler catches it.
     *
     * @throws InsufficientFundsException if withdrawal would breach minimum balance
     */
    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (!isActive()) {
            throw new IllegalStateException("Account #" + getAccountNumber() + " is inactive.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive. Got: " + amount);
        }
        // Savings account rule: must maintain minimum balance
        if (amount > getBalance() - MINIMUM_BALANCE) {
            throw new InsufficientFundsException(
                    "Must maintain minimum balance of Rs." + MINIMUM_BALANCE
                            + ". Max withdrawal: Rs." + (getBalance() - MINIMUM_BALANCE),
                    amount,
                    getBalance());
        }
        setBalance(getBalance() - amount);
        System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
    }

    /**
     * Add calculated interest to the account balance.
     * Reuses the inherited deposit() method — code reuse through inheritance.
     */
    public void addInterest() {
        double interest = calculateInterest();
        deposit(interest);
        System.out.println("Interest of Rs." + interest + " added at " + interestRate + "% rate");
    }

    /**
     * Override displayInfo to show savings-specific details.
     * Calls super.displayInfo() first for common fields, then adds extras.
     */
    @Override
    public void displayInfo() {
        System.out.print("[SAVINGS] ");
        super.displayInfo();
        System.out.println("  Interest Rate: " + interestRate + "% | Min Balance: Rs." + MINIMUM_BALANCE);
    }

    // ==================== GETTERS ====================

    public double getInterestRate() {
        return interestRate;
    }

    public static double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}
