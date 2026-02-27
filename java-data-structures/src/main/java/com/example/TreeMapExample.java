package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TreeMapExample {
    public static void main(String[] args) {

        Map<String, Car> map = new java.util.TreeMap<>(); // tree implementation of Map ( b-tree -> red-black tree) // sorted map
        map.put("123123", new Car("BMW", "X5", 2020));
        map.put("567567", new Car("Audi", "Q7", 2021));
        map.put("345345", new Car("Mercedes", "GLS", 2019));
        map.put("213123", new Car("Tesla", "Model X", 2022));
        map.put("123123", new Car("BMW", "X5", 2020)); // duplicate key, will replace the previous value


        System.out.println("Number of entries in the map: " + map.size());
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println("Key: " + key + ", Value: " + map.get(key).toString());
        }


    }
}
