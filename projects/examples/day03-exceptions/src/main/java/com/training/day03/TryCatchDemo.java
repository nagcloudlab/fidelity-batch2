package com.training.day03;

/**
 * Day 3 - Try-Catch-Finally Demo
 *
 * Demonstrates:
 * - Basic try-catch to handle exceptions gracefully
 * - finally block that ALWAYS runs (cleanup)
 * - Multiple catch blocks — specific exceptions before generic ones
 * - Common exception types: ArithmeticException, ArrayIndexOutOfBoundsException,
 *   NumberFormatException, NullPointerException
 */
public class TryCatchDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. BASIC TRY-CATCH
        // =============================================
        System.out.println("===== BASIC TRY-CATCH =====");

        try {
            // This code might throw an exception
            int result = 10 / 0;  // ArithmeticException: division by zero
            System.out.println("Result: " + result);  // this line never executes
        } catch (ArithmeticException e) {
            // What to do if that exception happens
            System.out.println("Error: Cannot divide by zero!");
            System.out.println("Exception message: " + e.getMessage());
        }
        System.out.println("Program continues normally after the catch.\n");

        // =============================================
        // 2. TRY-CATCH-FINALLY
        // =============================================
        System.out.println("===== TRY-CATCH-FINALLY =====");

        try {
            System.out.println("Inside try block...");
            int[] numbers = {1, 2, 3};
            System.out.println("Element at index 5: " + numbers[5]);  // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Invalid array index! " + e.getMessage());
        } finally {
            // finally ALWAYS runs — whether an exception occurred or not
            // Used for cleanup: closing files, database connections, etc.
            System.out.println("Finally block executed (always runs).");
        }

        // =============================================
        // 3. FINALLY RUNS EVEN WITHOUT EXCEPTION
        // =============================================
        System.out.println("\n===== FINALLY WITHOUT EXCEPTION =====");

        try {
            System.out.println("No exception here.");
            int result = 10 / 2;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("This won't execute.");
        } finally {
            System.out.println("Finally still runs even with no exception.");
        }

        // =============================================
        // 4. MULTIPLE CATCH BLOCKS
        // =============================================
        System.out.println("\n===== MULTIPLE CATCH BLOCKS =====");
        System.out.println("Rule: specific exceptions FIRST, generic LAST\n");

        // Test case 1: NumberFormatException
        parseAndPrint("abc");

        // Test case 2: ArithmeticException
        parseAndPrint("0");

        // Test case 3: Success
        parseAndPrint("5");

        // Test case 4: null
        parseAndPrint(null);

        // =============================================
        // 5. EXCEPTION FLOW SUMMARY
        // =============================================
        System.out.println("\n===== EXCEPTION FLOW =====");
        System.out.println("No exception:       try (completes) -> finally -> continues");
        System.out.println("Exception caught:   try (fails) -> catch -> finally -> continues");
        System.out.println("Exception NOT caught: try (fails) -> finally -> program crashes");
    }

    /**
     * Demonstrates multiple catch blocks with specific-before-generic ordering.
     * Parses a string as a number and divides 100 by it.
     */
    public static void parseAndPrint(String input) {
        System.out.println("--- Parsing: \"" + input + "\" ---");
        try {
            // This can throw NullPointerException if input is null
            int number = Integer.parseInt(input);  // can throw NumberFormatException

            // This can throw ArithmeticException if number is 0
            int result = 100 / number;

            System.out.println("100 / " + number + " = " + result);

        } catch (NumberFormatException e) {
            // Specific exception — caught first
            System.out.println("Error: '" + input + "' is not a valid number.");

        } catch (ArithmeticException e) {
            // Another specific exception
            System.out.println("Error: Cannot divide by zero.");

        } catch (NullPointerException e) {
            // Another specific exception
            System.out.println("Error: Input is null.");

        } catch (Exception e) {
            // Generic catch-all — safety net for anything else
            // MUST be last — it catches everything
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
