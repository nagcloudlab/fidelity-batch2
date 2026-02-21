package com.training.day05;

import java.util.Stack;

/**
 * Day 5 - Stack Demo (LIFO — Last In, First Out)
 *
 * Demonstrates:
 * - push(), pop(), peek(), isEmpty(), size()
 * - Using a Stack for undo/redo (reversing the last action)
 * - Practical example: bracket matching
 * - Practical example: reversing a string
 *
 * Stack is like a stack of plates — you always add and remove from the TOP.
 */
public class StackDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. BASIC STACK OPERATIONS
        // =============================================
        System.out.println("===== BASIC STACK OPERATIONS =====");

        Stack<String> history = new Stack<>();

        // push(item) — add to the top
        history.push("Opened file");
        history.push("Typed 'Hello'");
        history.push("Deleted line");

        System.out.println("Stack: " + history);
        System.out.println("Size: " + history.size());

        // peek() — look at the top item WITHOUT removing it
        System.out.println("Top (peek): " + history.peek());
        System.out.println("Size after peek: " + history.size() + " (unchanged)");

        // pop() — remove and return the top item
        String popped = history.pop();
        System.out.println("Popped: " + popped);
        System.out.println("Stack after pop: " + history);

        // isEmpty() — check if the stack has any items
        System.out.println("Is empty? " + history.isEmpty());

        // Pop remaining items
        history.pop();
        history.pop();
        System.out.println("After popping all: isEmpty? " + history.isEmpty());

        // =============================================
        // 2. UNDO EXAMPLE — reversing transactions
        // =============================================
        System.out.println("\n===== UNDO EXAMPLE (BANKING) =====");

        Stack<String> transactionStack = new Stack<>();
        double balance = 50000;

        // Perform some transactions
        balance = performTransaction(transactionStack, "DEPOSIT", 5000, balance);
        balance = performTransaction(transactionStack, "WITHDRAW", 2000, balance);
        balance = performTransaction(transactionStack, "DEPOSIT", 10000, balance);

        System.out.println("\nTransaction history (bottom to top): " + transactionStack);
        System.out.println("Current balance: Rs." + balance);

        // Undo the last transaction
        System.out.println("\n--- Undo last transaction ---");
        if (!transactionStack.isEmpty()) {
            String lastAction = transactionStack.pop();
            System.out.println("Undone: " + lastAction);
            // In a real app, you'd reverse the balance change here
        }
        System.out.println("Stack after undo: " + transactionStack);

        // =============================================
        // 3. REVERSING A STRING with Stack
        // =============================================
        System.out.println("\n===== REVERSING A STRING =====");

        String original = "BANK";
        String reversed = reverseString(original);
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);

        // =============================================
        // 4. BRACKET MATCHING with Stack
        // =============================================
        System.out.println("\n===== BRACKET MATCHING =====");

        // A common interview question — check if brackets are balanced
        testBrackets("()");         // true
        testBrackets("({[]})");     // true
        testBrackets("(]");         // false
        testBrackets("([)]");       // false
        testBrackets("");           // true
        testBrackets("{[()]}");     // true
    }

    /**
     * Simulates a banking transaction and pushes a record onto the undo stack.
     */
    public static double performTransaction(Stack<String> stack, String type, double amount, double balance) {
        if (type.equals("DEPOSIT")) {
            balance += amount;
            stack.push("DEPOSIT Rs." + amount);  // push onto undo stack
            System.out.println("Deposited Rs." + amount + " | Balance: Rs." + balance);
        } else if (type.equals("WITHDRAW")) {
            balance -= amount;
            stack.push("WITHDRAW Rs." + amount);
            System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + balance);
        }
        return balance;
    }

    /**
     * Reverses a string using a Stack.
     * Push each character, then pop them all — they come out in reverse order.
     */
    public static String reverseString(String input) {
        Stack<Character> stack = new Stack<>();

        // Push each character onto the stack
        for (char ch : input.toCharArray()) {
            stack.push(ch);
        }

        // Pop all characters — they come out in reverse (LIFO)
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        return reversed.toString();
    }

    /**
     * Checks if brackets in an expression are balanced.
     * Uses a Stack: push opening brackets, pop on closing brackets.
     */
    public static boolean areBracketsBalanced(String expression) {
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            // Push opening brackets
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }
            // For closing brackets, check if it matches the top
            else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) return false;  // nothing to match with

                char top = stack.pop();
                if (ch == ')' && top != '(') return false;
                if (ch == '}' && top != '{') return false;
                if (ch == ']' && top != '[') return false;
            }
        }

        // Stack should be empty if all brackets were matched
        return stack.isEmpty();
    }

    /**
     * Helper to test and print bracket matching results.
     */
    public static void testBrackets(String expression) {
        boolean balanced = areBracketsBalanced(expression);
        System.out.println("\"" + expression + "\" -> " + (balanced ? "BALANCED" : "NOT BALANCED"));
    }
}
