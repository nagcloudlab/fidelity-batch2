package com.training.day03;

/**
 * Day 3 - Custom Checked Exception
 *
 * A custom CHECKED exception (extends Exception, NOT RuntimeException).
 * The caller is FORCED to handle it with try-catch or declare it with throws.
 *
 * Key concepts:
 * - Extends Exception = checked (compiler forces handling)
 * - Extends RuntimeException = unchecked (optional handling)
 * - Custom fields (age) provide context about what went wrong
 * - super(message) passes the message to the parent Exception class
 */
public class InvalidAgeException extends Exception {

    // Custom field — stores the invalid age that caused the exception
    private final int age;

    /**
     * Constructor takes a descriptive message and the invalid age value.
     *
     * @param message description of what went wrong
     * @param age     the invalid age value that caused this exception
     */
    public InvalidAgeException(String message, int age) {
        super(message);    // pass the message to Exception's constructor
        this.age = age;
    }

    /**
     * Returns the invalid age that caused this exception.
     * This allows catch blocks to access the specific value for better error reporting.
     */
    public int getAge() {
        return age;
    }
}
