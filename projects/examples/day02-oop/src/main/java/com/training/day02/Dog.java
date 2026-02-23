package com.training.day02;

/**
 * Day 2 - Dog extends Animal
 *
 * Dog IS-A Animal. It inherits name, age, displayInfo(), getters/setters from Animal.
 * It MUST override makeSound() because it is abstract in the parent.
 * It can also add its own fields and methods (e.g., breed, fetch).
 */
public class Dog extends Animal {

    // Dog-specific field — not present in Animal
    private String breed;

    // Parameterized constructor — calls Animal's constructor via super(...)
    public Dog(String name, int age, String breed) {
        super(name, age);  // calls Animal(String, int)
        this.breed = breed;
    }

    // Default constructor
    public Dog() {
        super();  // calls Animal()
        this.breed = "Mixed";
    }

    // ---- OVERRIDE abstract method from Animal ----
    // @Override tells the compiler we are intentionally overriding
    @Override
    public String makeSound() {
        return "Woof! Woof!";
    }

    // ---- OVERRIDE displayInfo for custom format ----
    @Override
    public void displayInfo() {
        System.out.print("[DOG] ");
        super.displayInfo();  // call parent's displayInfo() first
        System.out.println("  Breed: " + breed);
    }

    // Dog-specific method — not in Animal
    public void fetch(String item) {
        System.out.println(getName() + " fetches the " + item + "!");
    }

    // Getter for breed
    public String getBreed() {
        return breed;
    }

    @Override
    public String toString() {
        return "Dog{name='" + getName() + "', age=" + getAge() + ", breed='" + breed + "'}";
    }
}
