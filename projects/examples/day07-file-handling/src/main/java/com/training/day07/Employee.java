package com.training.day07;

import java.io.Serializable;

/**
 * Day 7 - Serializable POJO (Plain Old Java Object)
 *
 * This class implements Serializable so it can be:
 * - Written to a file with ObjectOutputStream (serialization)
 * - Read back from a file with ObjectInputStream (deserialization)
 *
 * Key concepts:
 * - implements Serializable: marker interface (no methods to implement)
 * - serialVersionUID: version number for the class structure
 * - transient field: excluded from serialization (e.g., sensitive data)
 */
public class Employee implements Serializable {

    // serialVersionUID is a version number for this class.
    // If you change the class structure later, change this number.
    // This helps Java detect incompatible versions when deserializing.
    private static final long serialVersionUID = 1L;

    // Regular fields — these WILL be serialized (saved to file)
    private int id;
    private String name;
    private String department;
    private double salary;

    // transient field — this will NOT be serialized
    // When deserialized, it will be null (default value for String)
    // Use transient for: passwords, session data, cached values
    private transient String sessionToken;

    // Default constructor
    public Employee() {
    }

    // Parameterized constructor
    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.sessionToken = "SESSION-" + System.currentTimeMillis();  // temporary data
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    @Override
    public String toString() {
        return "Employee{id=" + id
                + ", name='" + name + "'"
                + ", dept='" + department + "'"
                + ", salary=" + salary
                + ", session=" + sessionToken + "}";
    }
}
