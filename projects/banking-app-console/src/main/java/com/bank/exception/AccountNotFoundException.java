package com.bank.exception;

/**
 * Custom checked exception thrown when an account cannot be found by its account number.
 *
 * Key concepts demonstrated:
 * - Custom exceptions with domain-specific fields
 * - Extends Exception (checked): caller must handle
 * - Self-documenting: "AccountNotFoundException" is more meaningful than a generic message
 *
 * Usage:
 *   throw new AccountNotFoundException(9999L);
 *   // Message: "Account #9999 not found."
 */
public class AccountNotFoundException extends Exception {

    // The account number that was searched for but not found
    private long accountNumber;

    /**
     * Constructor with the account number that was not found.
     * Automatically generates a descriptive message.
     *
     * @param accountNumber The account number that was not found
     */
    public AccountNotFoundException(long accountNumber) {
        super("Account #" + accountNumber + " not found.");
        this.accountNumber = accountNumber;
    }

    /**
     * Constructor with a custom message and the account number.
     *
     * @param message       Custom error message
     * @param accountNumber The account number that was not found
     */
    public AccountNotFoundException(String message, long accountNumber) {
        super(message);
        this.accountNumber = accountNumber;
    }

    /**
     * Get the account number that was not found.
     */
    public long getAccountNumber() {
        return accountNumber;
    }
}
