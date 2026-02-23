package com.training.day02;

/**
 * Day 2 - Cat extends Animal
 *
 * Cat IS-A Animal. Like Dog, it inherits everything from Animal
 * and provides its own makeSound() implementation.
 * It adds an indoor/outdoor field specific to cats.
 */
public class Cat extends Animal {

    // Cat-specific field
    private boolean indoor;

    // Parameterized constructor
    public Cat(String name, int age, boolean indoor) {
        super(name, age);  // calls Animal(String, int)
        this.indoor = indoor;
    }

    // Default constructor
    public Cat() {
        super();
        this.indoor = true;
    }

    // ---- OVERRIDE abstract method from Animal ----
    @Override
    public String makeSound() {
        return "Meow!";
    }

    // ---- OVERRIDE displayInfo for custom format ----
    @Override
    public void displayInfo() {
        System.out.print("[CAT] ");
        super.displayInfo();  // reuse parent's display logic
        System.out.println("  Indoor: " + (indoor ? "Yes" : "No"));
    }

    // Cat-specific method
    public void purr() {
        System.out.println(getName() + " purrs contentedly...");
    }

    // Getter for indoor
    public boolean isIndoor() {
        return indoor;
    }

    @Override
    public String toString() {
        return "Cat{name='" + getName() + "', age=" + getAge() + ", indoor=" + indoor + "}";
    }
}
