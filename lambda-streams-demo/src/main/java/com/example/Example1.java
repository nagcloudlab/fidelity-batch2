package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Example1 {
    public static void main(String[] args) {

        List<Apple> inventory = List.of(
                new Apple("red", 150),
                new Apple("green", 120),
                new Apple("red", 170),
                new Apple("green", 130)
        );

        // Req-1: Filter green apples from the inventory
        //List<Apple> result1 = AppleLibrary.filterGreenApples(inventory);
        //List<Apple> result1 = AppleLibrary.filterApplesByColor(inventory, "green");
        List<Apple> result1 = AppleLibrary.filterApples(inventory, new GreetApplePredicate());
        System.out.println(result1);

        // Req-2: Filter red apples from the inventory
        //List<Apple> result2 = AppleLibrary.filterRedApples(inventory);
        //List<Apple> result2 = AppleLibrary.filterApplesByColor(inventory, "red");
        List<Apple> result2 = AppleLibrary.filterApples(inventory, new Predicate<Apple>(){
            public  boolean test(Apple a) {
                return a.getColor().equals("red");
            }
        });
        System.out.println(result2);

        // Req-3 : filter Apples by weight
        // from Java-8 : Lambda expression
        List<Apple>  result3=AppleLibrary.filterApples(inventory, apple->apple.getWeight()==150);
        System.out.println(result3);

        List<Apple> result4=AppleLibrary.filterApples(inventory, apple->apple.getWeight()==120 && apple.getColor().equals("green"));

        // why we need Lambdas?
        // to make concise code while using libraries..

    }

}
