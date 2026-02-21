package com.training.day02;

/**
 * Day 2 - Constructor Demo
 *
 * Demonstrates:
 * - Default constructor (no parameters)
 * - Parameterized constructor (set all fields at once)
 * - The 'this' keyword (distinguishing field from parameter)
 * - Constructor chaining with super(...)
 * - What happens when you DON'T write a default constructor
 */
public class ConstructorDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. DEFAULT CONSTRUCTOR
        // =============================================
        System.out.println("===== DEFAULT CONSTRUCTOR =====");

        // Using the no-arg constructor — fields get default values
        Dog defaultDog = new Dog();
        System.out.println("Default dog: " + defaultDog);
        defaultDog.displayInfo();

        // =============================================
        // 2. PARAMETERIZED CONSTRUCTOR
        // =============================================
        System.out.println("\n===== PARAMETERIZED CONSTRUCTOR =====");

        // Set all fields at creation time — clean and concise
        Dog buddy = new Dog("Buddy", 3, "Golden Retriever");
        System.out.println("Buddy: " + buddy);
        buddy.displayInfo();

        Cat whiskers = new Cat("Whiskers", 5, true);
        System.out.println("Whiskers: " + whiskers);
        whiskers.displayInfo();

        // =============================================
        // 3. THE 'this' KEYWORD
        // =============================================
        System.out.println("\n===== THE 'this' KEYWORD =====");
        System.out.println("When a parameter has the same name as a field:");
        System.out.println("  this.name = name;");
        System.out.println("  'this.name' refers to the OBJECT's field");
        System.out.println("  'name' (without this) refers to the PARAMETER");

        // Demonstrate with setters (which use 'this')
        buddy.setName("Buddy Jr.");
        System.out.println("After setName: " + buddy.getName());

        // =============================================
        // 4. CONSTRUCTOR CHAINING (super)
        // =============================================
        System.out.println("\n===== CONSTRUCTOR CHAINING WITH super() =====");
        System.out.println("When Dog's constructor calls super(name, age),");
        System.out.println("it invokes Animal's constructor to set inherited fields.");

        Dog rex = new Dog("Rex", 2, "German Shepherd");
        System.out.println("Rex's name (from Animal): " + rex.getName());
        System.out.println("Rex's breed (from Dog):    " + rex.getBreed());

        // =============================================
        // 5. SETTER VALIDATION
        // =============================================
        System.out.println("\n===== SETTER VALIDATION =====");

        // Try setting an invalid name — setter rejects it
        rex.setName("");  // prints "Name cannot be empty."
        System.out.println("After trying empty name: " + rex.getName());  // still "Rex"

        // Try setting a negative age — setter rejects it
        rex.setAge(-5);  // prints "Age cannot be negative."
        System.out.println("After trying negative age: " + rex.getAge());  // still 2
    }
}
