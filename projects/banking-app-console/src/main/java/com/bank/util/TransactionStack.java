package com.bank.util;

import com.bank.model.Account;
import com.bank.model.Transaction;

import java.util.Stack;

/**
 * TransactionStack wraps java.util.Stack to provide undo capability.
 *
 * Key concepts demonstrated:
 * - Stack (LIFO — Last In, First Out): undo reverses the MOST RECENT action first
 * - Wrapper pattern: encapsulates Stack and adds banking-specific behavior
 * - Undo logic: restores account balance to its pre-transaction state
 *
 * Day 5: Created for transaction undo feature
 *
 * How undo works:
 * 1. Every deposit/withdraw pushes a Transaction onto the stack
 *    (Transaction stores balanceBefore and balanceAfter)
 * 2. When "undo" is called:
 *    - Pop the most recent transaction from the stack
 *    - Restore the account balance to balanceBefore
 *    - The transaction is effectively reversed
 *
 * Example:
 *   Deposit Rs.5000 (balance: 50000 -> 55000)  => stack: [DEP 5000]
 *   Withdraw Rs.2000 (balance: 55000 -> 53000) => stack: [DEP 5000, WDR 2000]
 *   UNDO => pop WDR 2000, restore balance to 55000 => stack: [DEP 5000]
 *   UNDO => pop DEP 5000, restore balance to 50000 => stack: []
 */
public class TransactionStack {

    // Internal Stack data structure
    private Stack<Transaction> stack;

    /**
     * Create an empty transaction stack.
     */
    public TransactionStack() {
        this.stack = new Stack<>();
    }

    /**
     * Push a transaction onto the stack (called after every successful deposit/withdraw).
     *
     * @param transaction The transaction to record
     */
    public void push(Transaction transaction) {
        stack.push(transaction);
    }

    /**
     * Undo the last transaction by restoring the account's previous balance.
     * Pops the most recent transaction from the stack and reverses it.
     *
     * @param account The account whose balance should be restored
     * @return The transaction that was undone, or null if stack is empty
     */
    public Transaction undoLast(Account account) {
        if (stack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return null;
        }

        // Pop the most recent transaction
        Transaction lastTxn = stack.pop();

        // Restore the account balance to what it was BEFORE the transaction
        account.restoreBalance(lastTxn.getBalanceBefore());

        System.out.println("UNDO: Reversed " + lastTxn.getType()
                + " of Rs." + lastTxn.getAmount()
                + " on Account #" + lastTxn.getAccountNumber());
        System.out.println("Balance restored to Rs." + account.getBalance());

        return lastTxn;
    }

    /**
     * Peek at the most recent transaction without removing it.
     *
     * @return The most recent transaction, or null if empty
     */
    public Transaction peek() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    /**
     * Get the complete transaction history (all items still on the stack).
     * Iterating over the stack does NOT remove items.
     */
    public void getHistory() {
        if (stack.isEmpty()) {
            System.out.println("No transaction history.");
            return;
        }
        System.out.println("\n--- Transaction History (" + stack.size() + " transactions) ---");
        for (Transaction txn : stack) {
            txn.display();
        }
    }

    /**
     * Clear all transactions from the stack.
     */
    public void clear() {
        stack.clear();
        System.out.println("Transaction history cleared.");
    }

    /**
     * Check if the stack is empty.
     *
     * @return true if no transactions are recorded
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Get the number of transactions on the stack.
     *
     * @return Number of transactions
     */
    public int size() {
        return stack.size();
    }
}
