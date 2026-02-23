package com.training.day07;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Day 7 - Serialization Demo
 *
 * Demonstrates:
 * - Serialization: convert Java objects to bytes and save to a file
 * - Deserialization: read bytes from a file and reconstruct Java objects
 * - ObjectOutputStream (write) and ObjectInputStream (read)
 * - transient field behavior (excluded from serialization)
 * - @SuppressWarnings("unchecked") for generic type casting
 *
 * Serialization saves the ENTIRE object graph in one call.
 * Much simpler than CSV (no manual parsing) but produces binary files.
 */
public class SerializationDemo {

    public static void main(String[] args) {

        String filePath = "data/employees.dat";

        // =============================================
        // 1. CREATE EMPLOYEES
        // =============================================
        System.out.println("===== CREATING EMPLOYEES =====");

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(101, "Ravi Kumar", "Engineering", 85000));
        employees.add(new Employee(102, "Priya Sharma", "Marketing", 72000));
        employees.add(new Employee(103, "Amit Verma", "Engineering", 90000));

        System.out.println("Before serialization:");
        for (Employee emp : employees) {
            System.out.println("  " + emp);
            // Note: sessionToken is set (it's a transient field)
        }

        // =============================================
        // 2. SERIALIZE (WRITE) TO FILE
        // =============================================
        System.out.println("\n===== SERIALIZING TO FILE =====");
        serializeEmployees(employees, filePath);

        // =============================================
        // 3. DESERIALIZE (READ) FROM FILE
        // =============================================
        System.out.println("\n===== DESERIALIZING FROM FILE =====");
        List<Employee> loaded = deserializeEmployees(filePath);

        System.out.println("\nAfter deserialization:");
        for (Employee emp : loaded) {
            System.out.println("  " + emp);
            // Note: sessionToken is null (transient fields are not saved)
        }

        // =============================================
        // 4. TRANSIENT FIELD BEHAVIOR
        // =============================================
        System.out.println("\n===== TRANSIENT FIELD DEMO =====");
        System.out.println("The 'sessionToken' field is marked as transient.");
        System.out.println("Before serialization: sessionToken = SESSION-...");
        System.out.println("After deserialization: sessionToken = null");
        System.out.println("Transient fields get their default value when deserialized:");
        System.out.println("  String -> null, int -> 0, boolean -> false");

        // =============================================
        // 5. FILE SIZE COMPARISON
        // =============================================
        System.out.println("\n===== FILE COMPARISON =====");

        File datFile = new File(filePath);
        File csvFile = new File("data/employees.csv");

        if (datFile.exists()) {
            System.out.println("Serialized (.dat): " + datFile.length() + " bytes");
        }
        if (csvFile.exists()) {
            System.out.println("CSV (.csv):        " + csvFile.length() + " bytes");
        }
        System.out.println("\nCSV: human-readable, any language, manual parsing");
        System.out.println("DAT: binary, Java-only, one-line save/load");
    }

    /**
     * Serializes a list of employees to a binary file.
     * ObjectOutputStream converts the entire object graph to bytes.
     */
    public static void serializeEmployees(List<Employee> employees, String filePath) {
        // Ensure directory exists
        File parent = new File(filePath).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // ObjectOutputStream wraps FileOutputStream for writing objects
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {

            // writeObject saves the ENTIRE list (and all Employee objects) in one call
            oos.writeObject(employees);
            System.out.println("Serialized " + employees.size() + " employees to " + filePath);

        } catch (IOException e) {
            System.out.println("Error serializing: " + e.getMessage());
        }
    }

    /**
     * Deserializes a list of employees from a binary file.
     * ObjectInputStream reconstructs the Java objects from bytes.
     */
    @SuppressWarnings("unchecked")  // suppress warning about unchecked cast
    public static List<Employee> deserializeEmployees(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No serialized data at " + filePath);
            return new ArrayList<>();
        }

        // ObjectInputStream wraps FileInputStream for reading objects
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {

            // readObject returns Object — we must cast it to the expected type
            List<Employee> employees = (List<Employee>) ois.readObject();
            System.out.println("Deserialized " + employees.size() + " employees from " + filePath);
            return employees;

        } catch (IOException e) {
            System.out.println("Error deserializing: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // This happens if the Employee class has been removed or renamed
            System.out.println("Class not found during deserialization: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}
