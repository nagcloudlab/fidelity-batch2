package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Example2 {

    public static void main(String[] args) {


        List<Dish> menu = Dish.menu; // input-data..

        //output: find low caloric ( < 400 ) dish names sorted by calories
        List<String> lowCaloricDishesName = findLowCaloricDishName_v1(menu);
        System.out.println(lowCaloricDishesName);


        lowCaloricDishesName = findLowCaloricDishName_v2(menu);
        System.out.println(lowCaloricDishesName);

    }

    private static List<String> findLowCaloricDishName_v1(List<Dish> menu) {
        // step-1: filter low caloric dishes
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }
        // step-2: sort by calories
        lowCaloricDishes.sort((d1, d2) -> Integer.compare(d1.getCalories(), d2.getCalories()));
        // step-3: extract dish name
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesName.add(dish.getName());
        }
        return lowCaloricDishesName;
    }

    private static List<String> findLowCaloricDishName_v2(List<Dish> menu) {
        return menu
                .stream()
                .filter(dish -> dish.getCalories() < 400)
                .sorted((d1, d2) -> Integer.compare(d1.getCalories(), d2.getCalories()))
                //.map(dish -> dish.getName())
                .map(Dish::getName) // method reference
                .collect(Collectors.toList());
    }
}

/*

    data-processing-pipeline
    ---------------------------

    input -> step-1-> step-2-> step-3-> output

     step(s)
     -> filter
     -> transform/map
     -> sort
     -> min/max
     -> group
     -> partition
     -..................

     code -> imperative-style -

     solution: using streams (declarative-style) to write more concise and readable code


 */
