package com.example.exception;

public class InsufficientBalanceException extends RuntimeException {

    private final String accountNumber;
    private final double requestedAmount;
    private final double availableBalance;

    public InsufficientBalanceException(String accountNumber, double requestedAmount, double availableBalance) {
        super(String.format("Insufficient balance in account %s: requested %.2f, available %.2f",
                accountNumber, requestedAmount, availableBalance));
        this.accountNumber = accountNumber;
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }
}
