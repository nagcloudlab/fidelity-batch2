package com.training.day07;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Day 7 - Try-With-Resources Demo
 *
 * Demonstrates:
 * - try-with-resources: resources declared in try(...) are automatically closed
 * - Multiple resources in one try statement
 * - Custom AutoCloseable class
 * - Comparison: old way (try-finally) vs new way (try-with-resources)
 *
 * Resources (files, connections, streams) MUST be closed after use.
 * try-with-resources handles this automatically — no need for finally blocks.
 */
public class TryWithResourcesDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. TRY-WITH-RESOURCES — SINGLE RESOURCE
        // =============================================
        System.out.println("===== TRY-WITH-RESOURCES: SINGLE RESOURCE =====");

        // The resource (writer) is declared inside try(...) parentheses
        // It will be automatically closed when the try block exits
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/sample.txt"))) {
            writer.write("Line 1: Hello from try-with-resources!");
            writer.newLine();
            writer.write("Line 2: Resources are auto-closed.");
            writer.newLine();
            writer.write("Line 3: No need for finally blocks.");
            writer.newLine();
            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing: " + e.getMessage());
        }
        // writer is automatically closed here — even if an exception occurred

        // Read it back
        try (BufferedReader reader = new BufferedReader(new FileReader("data/sample.txt"))) {
            String line;
            System.out.println("\nFile contents:");
            while ((line = reader.readLine()) != null) {
                System.out.println("  " + line);
            }
        } catch (IOException e) {
            System.out.println("Error reading: " + e.getMessage());
        }

        // =============================================
        // 2. MULTIPLE RESOURCES
        // =============================================
        System.out.println("\n===== MULTIPLE RESOURCES =====");

        // You can declare multiple resources separated by semicolons
        // ALL are automatically closed when the block exits
        try (
            BufferedReader reader = new BufferedReader(new FileReader("data/sample.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/sample-upper.txt"))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line.toUpperCase());  // convert to uppercase
                writer.newLine();
            }
            System.out.println("Copied and uppercased file contents.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // BOTH reader and writer are automatically closed

        // Verify the copy
        try (BufferedReader reader = new BufferedReader(new FileReader("data/sample-upper.txt"))) {
            String line;
            System.out.println("Uppercased contents:");
            while ((line = reader.readLine()) != null) {
                System.out.println("  " + line);
            }
        } catch (IOException e) {
            System.out.println("Error reading: " + e.getMessage());
        }

        // =============================================
        // 3. CUSTOM AutoCloseable
        // =============================================
        System.out.println("\n===== CUSTOM AutoCloseable =====");

        // Any class that implements AutoCloseable can be used with try-with-resources
        try (DatabaseConnection db = new DatabaseConnection("jdbc:h2:mem:testdb")) {
            db.query("SELECT * FROM accounts");
            db.query("INSERT INTO accounts VALUES (1001, 'Ravi')");
            // db.close() is called automatically at the end of this block
        }

        // =============================================
        // 4. COMPARISON: OLD WAY vs NEW WAY
        // =============================================
        System.out.println("\n===== OLD WAY (try-finally) =====");
        System.out.println("BufferedReader reader = null;");
        System.out.println("try {");
        System.out.println("    reader = new BufferedReader(new FileReader(\"file.txt\"));");
        System.out.println("    // process...");
        System.out.println("} catch (IOException e) {");
        System.out.println("    // handle error");
        System.out.println("} finally {");
        System.out.println("    if (reader != null) {");
        System.out.println("        try { reader.close(); } catch (IOException e) { }");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("// Verbose and error-prone!\n");

        System.out.println("===== NEW WAY (try-with-resources) =====");
        System.out.println("try (BufferedReader reader = new BufferedReader(new FileReader(\"file.txt\"))) {");
        System.out.println("    // process...");
        System.out.println("} catch (IOException e) {");
        System.out.println("    // handle error");
        System.out.println("}");
        System.out.println("// Clean, concise, and safe!");
    }

    /**
     * A custom resource class that implements AutoCloseable.
     * This allows it to be used with try-with-resources.
     */
    static class DatabaseConnection implements AutoCloseable {

        private final String url;
        private boolean connected;

        public DatabaseConnection(String url) {
            this.url = url;
            this.connected = true;
            System.out.println("Connected to database: " + url);
        }

        public void query(String sql) {
            if (!connected) {
                System.out.println("Error: Not connected!");
                return;
            }
            System.out.println("Executing: " + sql);
        }

        // This method is called automatically by try-with-resources
        @Override
        public void close() {
            if (connected) {
                connected = false;
                System.out.println("Database connection closed: " + url);
            }
        }
    }
}
