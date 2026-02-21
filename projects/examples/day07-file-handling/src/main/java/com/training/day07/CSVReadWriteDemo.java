package com.training.day07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Day 7 - CSV Read/Write Demo
 *
 * Demonstrates:
 * - Writing CSV data with BufferedWriter (line by line with headers)
 * - Reading CSV data with BufferedReader (parse with split(","))
 * - try-with-resources for automatic resource closing
 * - Ensuring parent directories exist before writing
 *
 * CSV (Comma-Separated Values) is a simple text format readable by
 * any program, including Excel, text editors, and other languages.
 */
public class CSVReadWriteDemo {

    public static void main(String[] args) {

        // File path for our CSV
        String filePath = "data/employees.csv";

        // =============================================
        // 1. WRITE EMPLOYEES TO CSV
        // =============================================
        System.out.println("===== WRITING CSV =====");

        // Create sample employees
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(101, "Ravi Kumar", "Engineering", 85000));
        employees.add(new Employee(102, "Priya Sharma", "Marketing", 72000));
        employees.add(new Employee(103, "Amit Verma", "Engineering", 90000));
        employees.add(new Employee(104, "Sneha Reddy", "HR", 68000));
        employees.add(new Employee(105, "Vikram Patel", "Finance", 95000));

        writeCSV(filePath, employees);

        // =============================================
        // 2. READ EMPLOYEES FROM CSV
        // =============================================
        System.out.println("\n===== READING CSV =====");

        List<Employee> loaded = readCSV(filePath);

        System.out.println("\nLoaded " + loaded.size() + " employees:");
        for (Employee emp : loaded) {
            System.out.println("  " + emp);
        }

        // =============================================
        // 3. CALCULATE SUMMARY FROM LOADED DATA
        // =============================================
        System.out.println("\n===== SUMMARY FROM CSV DATA =====");

        double totalSalary = 0;
        double maxSalary = Double.MIN_VALUE;
        String topEarner = "";

        for (Employee emp : loaded) {
            totalSalary += emp.getSalary();
            if (emp.getSalary() > maxSalary) {
                maxSalary = emp.getSalary();
                topEarner = emp.getName();
            }
        }

        System.out.println("Total employees: " + loaded.size());
        System.out.printf("Total salary:    Rs.%.2f%n", totalSalary);
        System.out.printf("Average salary:  Rs.%.2f%n", totalSalary / loaded.size());
        System.out.printf("Top earner:      %s (Rs.%.2f)%n", topEarner, maxSalary);
    }

    /**
     * Writes a list of employees to a CSV file.
     * Uses BufferedWriter for efficient writing with an 8KB buffer.
     */
    public static void writeCSV(String filePath, List<Employee> employees) {
        // Ensure the directory exists before writing
        File parent = new File(filePath).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();  // creates parent directories if needed
            System.out.println("Created directory: " + parent.getAbsolutePath());
        }

        // try-with-resources: writer is automatically closed when block exits
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Write header row
            writer.write("id,name,department,salary");
            writer.newLine();  // platform-appropriate line ending

            // Write each employee as a CSV row
            for (Employee emp : employees) {
                String line = emp.getId() + ","
                        + emp.getName() + ","
                        + emp.getDepartment() + ","
                        + String.format("%.2f", emp.getSalary());
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Wrote " + employees.size() + " employees to " + filePath);

        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
        // writer is automatically closed here — no need for finally block
    }

    /**
     * Reads employees from a CSV file.
     * Uses BufferedReader with readLine() for efficient line-by-line reading.
     */
    public static List<Employee> readCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return employees;
        }

        // try-with-resources: reader is automatically closed
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // Skip the header row
            String header = reader.readLine();
            System.out.println("CSV Header: " + header);

            // Read data rows until readLine() returns null (end of file)
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;  // skip blank lines

                // Split the line by comma into an array of fields
                String[] parts = line.split(",");
                if (parts.length < 4) continue;  // skip malformed lines

                // Parse each field into the correct type
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String department = parts[2].trim();
                double salary = Double.parseDouble(parts[3].trim());

                employees.add(new Employee(id, name, department, salary));
            }

            System.out.println("Read " + employees.size() + " employees from " + filePath);

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in CSV: " + e.getMessage());
        }

        return employees;
    }
}
