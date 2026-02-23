package com.training.day02;

/**
 * Day 2 - Polymorphism Demo
 *
 * Demonstrates:
 * - Parent reference holding child objects (Animal ref -> Dog/Cat object)
 * - Dynamic method dispatch: the ACTUAL object type determines which method runs
 * - Processing mixed types in a loop using a parent-type array
 * - instanceof check for type-specific operations
 * - toString() automatic invocation
 */
public class PolymorphismDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. PARENT REFERENCE, CHILD OBJECTS
        // =============================================
        System.out.println("===== POLYMORPHIC REFERENCES =====");

        // The reference type is Animal, but the actual objects are Dog and Cat
        Animal animal1 = new Dog("Buddy", 3, "Golden Retriever");
        Animal animal2 = new Cat("Whiskers", 5, true);
        Animal animal3 = new Dog("Rex", 2, "German Shepherd");
        Animal animal4 = new Cat("Luna", 1, false);

        // =============================================
        // 2. DYNAMIC METHOD DISPATCH
        // =============================================
        System.out.println("\n===== DYNAMIC METHOD DISPATCH =====");
        System.out.println("Same method call (makeSound) -> different behavior:");

        // Java looks at the ACTUAL object type at runtime, not the reference type
        System.out.println(animal1.getName() + " says: " + animal1.makeSound());  // Woof!
        System.out.println(animal2.getName() + " says: " + animal2.makeSound());  // Meow!

        // =============================================
        // 3. PROCESSING MIXED TYPES IN A LOOP
        // =============================================
        System.out.println("\n===== PROCESSING ALL ANIMALS =====");

        // An array of Animal can hold both Dogs and Cats
        Animal[] animals = {animal1, animal2, animal3, animal4};

        // The for-each loop processes all — no need to know the specific type
        for (Animal animal : animals) {
            animal.displayInfo();  // each animal displays in its own format
            System.out.println();
        }

        // =============================================
        // 4. POLYMORPHIC METHOD
        // =============================================
        System.out.println("===== POLYMORPHIC METHOD =====");
        System.out.println("A method that accepts ANY Animal type:\n");

        // This method works for Dog, Cat, or any future Animal subclass
        printAnimalSummary(animal1);
        printAnimalSummary(animal2);

        // =============================================
        // 5. instanceof CHECK
        // =============================================
        System.out.println("\n===== instanceof CHECK =====");

        for (Animal animal : animals) {
            if (animal instanceof Dog) {
                // Downcasting: Animal reference -> Dog reference
                Dog dog = (Dog) animal;
                dog.fetch("ball");  // Dog-specific method
            } else if (animal instanceof Cat) {
                Cat cat = (Cat) animal;
                cat.purr();  // Cat-specific method
            }
        }

        // =============================================
        // 6. toString() — AUTOMATIC INVOCATION
        // =============================================
        System.out.println("\n===== toString() =====");

        // toString() is called automatically when you:
        // - print an object
        // - concatenate an object with a String
        System.out.println(animal1);  // calls animal1.toString()
        System.out.println("Animal: " + animal2);  // also calls toString()

        // =============================================
        // 7. WHY POLYMORPHISM IS POWERFUL
        // =============================================
        System.out.println("\n===== WHY POLYMORPHISM MATTERS =====");
        System.out.println("- Write code that works with the PARENT type (Animal)");
        System.out.println("- It automatically works with ALL child types (Dog, Cat)");
        System.out.println("- Adding a new animal (e.g., Bird) requires NO changes");
        System.out.println("  to existing code that processes Animal references.");
    }

    /**
     * This method works for ANY Animal — present or future.
     * If we add a Bird class tomorrow, this method still works!
     */
    public static void printAnimalSummary(Animal animal) {
        System.out.println("Summary: " + animal.getName()
                + " (age " + animal.getAge() + ")"
                + " says " + animal.makeSound());
    }
}
