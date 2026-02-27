package com.example;

import java.util.List;
import java.util.stream.Collectors;

public class Stream_Terminal_Operations {

    public static void main(String[] args) {


        List<Dish> menu = Dish.menu;

        // 1. forEach
        System.out.println("forEach:");
        menu.stream()
            .filter(d -> d.getCalories() > 300)
            .forEach(d -> System.out.println(d.getName()));

        // 2. collect
        System.out.println("\ncollect:");
        // a. Collect to List
        List<String> highCalorieDishNames = menu.stream()
            .filter(d -> d.getCalories() > 300)
            .map(Dish::getName)
            .collect(Collectors.toList());
        System.out.println(highCalorieDishNames);

        // b. Collect to Set
        System.out.println("\ncollect to Set:");
        var highCalorieDishNamesSet = menu.stream()
            .filter(d -> d.getCalories() > 300)
            .map(Dish::getName)
            .collect(Collectors.toSet());
        System.out.println(highCalorieDishNamesSet);

        // c. Collect to Map
        System.out.println("\ncollect to Map:");
        var dishMap = menu.stream()
            .filter(d -> d.getCalories() > 300)
            .collect(Collectors.toMap(Dish::getName, Dish::getCalories));
        System.out.println(dishMap);

        // groupingBy
        System.out.println("\ngroupingBy:");
        var dishesByType = menu.stream()
            .collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishesByType);

        // partitioningBy
        System.out.println("\npartitioningBy:");
        var partitionedByVegetarian = menu.stream()
            .collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedByVegetarian);

    }

}
