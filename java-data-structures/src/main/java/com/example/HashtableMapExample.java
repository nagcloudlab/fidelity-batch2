package com.example;

import java.util.*;

public class HashtableMapExample {

    public static void main(String[] args) {

        // HashMap
        // LinkedHashMap

        // Map -> user-identity -> key -> value

        Owner owner1 = new Owner("John Doe", "123 Main St");
        Owner owner2 = new Owner("Jane Smith", "456 Elm St");

        Car car1 = new Car("Toyota", "Camry", 2020);
        Car car2 = new Car("Honda", "Civic", 2019);
//
//        List<Car> cars = new ArrayList<>();
//        cars.add(car1);
//        cars.add(car2);
//
//        Set<Car> carSet = new HashSet<>();
//        carSet.add(car1);
//        carSet.add(car2);

        Map<Owner, Car> map = new HashMap<>();
        //Map<Owner, Car> map2 = new LinkedHashMap<>();
        map.put(owner1, car1);
        map.put(owner2, car2);


        //--------------------------------------------------
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter owner name: ");
//        String ownerName = sc.nextLine();
//        System.out.println("Enter owner address: ");
//        String ownerAddress = sc.nextLine();
//        Owner key = new Owner(ownerName, ownerAddress); // Create a temporary Owner object for searching
//        Car foundCar = map.get(key); // Retrieve the car associated with the search owner
//        if (foundCar != null) {
//            System.out.println("Car found: " + foundCar);
//        } else {
//            System.out.println("Owner not found in the map.");
//        }

        //----------------------------------------------
        // Iterate over the map entries
        System.out.println("\nAll owners and their cars:");
        Set<Owner> owners = map.keySet();
        for (Owner owner : owners) {
            Car car = map.get(owner);
            System.out.println(owner + " owns " + car);
        }

        //----------------------------------------------

        //In web Web application,
        // we can use Map to store session data, where the key is the session ID and the value is the user information or preferences associated with that session.
        // This allows us to quickly retrieve user-specific data based on their session ID, enhancing the user experience and enabling personalized interactions.

    }

}
