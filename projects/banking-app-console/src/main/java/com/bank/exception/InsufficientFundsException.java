package com.bank.exception;

/**
 * Custom checked exception thrown when a withdrawal or transfer cannot be completed
 * due to insufficient funds in the account.
 *
 * Key concepts demonstrated:
 * - Custom exceptions: more descriptive than generic IllegalArgumentException
 * - Extends Exception (checked): forces callers to handle it with try-catch or throws
 * - Domain-specific fields: carries requestedAmount and currentBalance for detailed error reporting
 *
 * Day 3: Created to replace generic exception handling with business-specific exceptions
 *
 * Usage:
 *   throw new InsufficientFundsException("Must maintain min balance", 48000, 50000);
 *
 * Catching:
 *   catch (InsufficientFundsException e) {
 *       System.out.println("Requested: " + e.getRequestedAmount());
 *       System.out.println("Available: " + e.getCurrentBalance());
 *   }
 */
public class InsufficientFundsException extends Exception {

    // The amount the user tried to withdraw
    private double requestedAmount;

    // The actual balance available in the account
    private double currentBalance;

    /**
     * Constructor with message, requested amount, and current balance.
     *
     * @param message         Human-readable error description
     * @param requestedAmount The amount that was attempted
     * @param currentBalance  The current balance of the account
     */
    public InsufficientFundsException(String message, double requestedAmount, double currentBalance) {
        super(message);   // pass message to parent Exception class
        this.requestedAmount = requestedAmount;
        this.currentBalance = currentBalance;
    }

    /**
     * Get the amount that was attempted for withdrawal.
     */
    public double getRequestedAmount() {
        return requestedAmount;
    }

    /**
     * Get the current balance of the account at the time of the exception.
     */
    public double getCurrentBalance() {
        return currentBalance;
    }
}
