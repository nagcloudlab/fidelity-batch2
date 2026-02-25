package com.example;

// App: ticket booking app

// module-1 : transfer module
// module-2 : ticket-booking module

//-------------------------------------------------------
// Imp Notes
//-------------------------------------------------------
/*

2 types of throwable in Java:

1. Error: represents serious problems that a reasonable application should not try to catch. (e.g., OutOfMemoryError, StackOverflowError, etc.)
2. Exception: represents conditions that a reasonable application might want to catch. (e.g., IOException, SQLException, etc.)


2 types of exceptions in Java:
1. Checked Exception: must be either caught or declared in the method signature using 'throws' keyword. (e.g., IOException, SQLException, etc.) 
2. Unchecked Exception: does not need to be declared or caught. (e.g., NullPointerException, ArrayIndexOutOfBoundsException, etc.)



*/

//-------------------------------------------------------
// Error
//-------------------------------------------------------

class OutOfMemoryError extends Error {
    private String message;

    public OutOfMemoryError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

// -------------------------------------------------------
// Exception
// -------------------------------------------------------

class AccountNotFoundException extends RuntimeException {
    private String message;
    private String account;

    public AccountNotFoundException(String message, String account) {
        this.message = message;
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public String getAccount() {
        return account;
    }
}

class InsufficientBalanceException extends RuntimeException {
    private String message;
    private String account;
    private double balance;
    private double attemptedAmount;

    public InsufficientBalanceException(String message, String account, double balance, double attemptedAmount) {
        this.message = message;
        this.account = account;
        this.balance = balance;
        this.attemptedAmount = attemptedAmount;
    }

    public String getMessage() {
        return message;
    }

    public String getAccount() {
        return account;
    }

    public double getBalance() {
        return balance;
    }

    public double getAttemptedAmount() {
        return attemptedAmount;
    }

}

// -------------------------------------------------------
// transfer module
// -------------------------------------------------------

class TransferService {
    // public void transferMoney(double amount, String fromAccount, String
    // toAccount) throws Exception {
    public void transferMoney(double amount, String fromAccount, String toAccount) {
        // load 'fromAccount' details from database
        System.out.println("Loading account details for " + fromAccount);
        boolean fromAccountExists = true; // Assume we checked this in database
        if (!fromAccountExists) {
            System.out.println("Account not found: " + fromAccount);
            AccountNotFoundException exception = new AccountNotFoundException(
                    "Account not found: " + fromAccount,
                    fromAccount);
            throw exception;
        }
        // load 'toAccount' details from database
        System.out.println("Loading account details for " + toAccount);
        boolean toAccountExists = true; // Assume we checked this in database
        if (!toAccountExists) {
            System.out.println("Account not found: " + toAccount);
            AccountNotFoundException exception = new AccountNotFoundException(
                    "Account not found: " + toAccount,
                    toAccount);
            throw exception;
        }
        // Validate 'fromAccount' has sufficient balance
        System.out.println("Validating sufficient balance in " + fromAccount);
        double fromAccountBalance = 500.0; // Assume we fetched this from database
        if (fromAccountBalance < amount) {
            System.out.println("Insufficient balance in " + fromAccount);
            InsufficientBalanceException exception = new InsufficientBalanceException(
                    "Insufficient balance in account: " + fromAccount,
                    fromAccount,
                    fromAccountBalance,
                    amount);
            throw exception;
        }
        // Debit 'fromAccount'
        System.out.println("Debiting " + amount + " from " + fromAccount);
        // Credit 'toAccount'
        System.out.println("Crediting " + amount + " to " + toAccount);
        // Update both accounts in database
        System.out.println("Updating account details in database for " + fromAccount + " and " + toAccount);
        System.out.println("Transfer successful!");

    }
}

// -------------------------------------------------------
// ticket booking module
// -------------------------------------------------------

class TicketBooking {
    TransferService transferService = new TransferService();

    public void bookTicket() {
        System.out.println("Booking ticket...");
        double amount = 10000.0;
        // transfer money from user account to ticket booking account
        try {
            transferService.transferMoney(amount, "user-account-123", "ticket-booking-account-456");
            System.out.println("Ticket booked successfully!");
        }
        // catch (InsufficientBalanceException e) {

        // System.out.println("Failed to book ticket: " + e.getMessage());
        // System.out.println("Account: " + e.getAccount());
        // System.out.println("Current Balance: " + e.getBalance());
        // System.out.println("Attempted Amount: " + e.getAttemptedAmount());

        // // in real application. how exceptions are handled?
        // // - log the exception details in a file or monitoring system
        // // - show user-friendly error message to the user
        // // - execute plan-B if applicable (e.g., offer alternative payment methods,
        // // retry the transaction, etc.)
        // // - notify support team if the issue is critical or recurring
        // // - perform any necessary cleanup or rollback operations to maintain system
        // // integrity

        // } catch (AccountNotFoundException e) {

        // System.out.println("Failed to book ticket: " + e.getMessage());
        // System.out.println("Account: " + e.getAccount());

        // // in real application. how exceptions are handled?
        // // - log the exception details in a file or monitoring system
        // // - show user-friendly error message to the user
        // // - execute plan-B if applicable (e.g., offer alternative payment methods,
        // // retry the transaction, etc.)
        // // - notify support team if the issue is critical or recurring
        // // - perform any necessary cleanup or rollback operations to maintain system
        // // integrity

        // }
        catch (AccountNotFoundException | InsufficientBalanceException e) {
            System.out.println("Failed to book ticket: " + e.getMessage());
            if (e instanceof AccountNotFoundException) {
                AccountNotFoundException ex = (AccountNotFoundException) e;
                System.out.println("Account: " + ex.getAccount());
            } else if (e instanceof InsufficientBalanceException) {
                InsufficientBalanceException ex = (InsufficientBalanceException) e;
                System.out.println("Account: " + ex.getAccount());
                System.out.println("Current Balance: " + ex.getBalance());
                System.out.println("Attempted Amount: " + ex.getAttemptedAmount());
            }

            // in real application. how exceptions are handled?
            // - log the exception details in a file or monitoring system
            // - show user-friendly error message to the user
            // - execute plan-B if applicable (e.g., offer alternative payment methods,
            // retry the transaction, etc.)
            // - notify support team if the issue is critical or recurring
            // - perform any necessary cleanup or rollback operations to maintain system
            // integrity
        } finally {
            System.out.println("Executing finally block to perform cleanup operations if necessary.");
            // perform any necessary cleanup operations here (e.g., close database
            // connections, release resources, etc.)
        }
    }
}

public class Main {
    public static void main(String[] args) {

        TicketBooking ticketBooking = new TicketBooking();
        ticketBooking.bookTicket();

    }
}