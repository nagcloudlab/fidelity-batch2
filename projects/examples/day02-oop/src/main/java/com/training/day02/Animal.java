package com.training.day02;

/**
 * Day 2 - Abstract Class Demo
 *
 * Animal is an ABSTRACT class — it cannot be instantiated directly.
 * It defines a contract: every animal MUST provide its own makeSound() implementation.
 *
 * Key concepts:
 * - abstract class: cannot use "new Animal()" — too generic
 * - abstract method: declared with no body — children MUST override it
 * - concrete method: fully implemented — children inherit it as-is
 * - protected fields: accessible by subclasses but not by outside code
 */
public abstract class Animal {

    // Private fields — encapsulated, accessible only through getters/setters
    private String name;
    private int age;

    // Constructor — called by child classes via super(...)
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Default constructor
    public Animal() {
        this.name = "Unknown";
        this.age = 0;
    }

    // ---- ABSTRACT METHOD ----
    // No body (no curly braces) — each subclass MUST provide its own implementation
    public abstract String makeSound();

    // ---- CONCRETE METHOD ----
    // Fully implemented — all subclasses inherit this behavior
    public void displayInfo() {
        System.out.println("Name: " + name + " | Age: " + age + " | Sound: " + makeSound());
    }

    // ---- GETTERS AND SETTERS ----

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Validation: reject empty names
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        this.name = name;  // 'this.name' = the field, 'name' = the parameter
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        // Validation: reject negative ages
        if (age < 0) {
            System.out.println("Age cannot be negative.");
            return;
        }
        this.age = age;
    }

    // ---- toString() ----
    // Called automatically when you print an object or concatenate with a String
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', age=" + age + "}";
    }
}
