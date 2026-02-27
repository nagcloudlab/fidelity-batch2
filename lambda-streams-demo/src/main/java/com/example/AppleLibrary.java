package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/*

    style or programming
    ------------------------

    1. Imperative style

        -> solving a problem using steb-by-step instructions
        -> intention & implementation are mixed together

    2. Declarative  style

        -> solving a problem by describing what you want to achieve
        -> intention & implementation are separated

        how?
        -> by parameterization
            -> value or object parameterization
            -> behavior(function) parameterization


 */

public class AppleLibrary {

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
    public static List<Apple> filterRedApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("red".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
    public  static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory,Predicate<Apple> predicate) {
            List<Apple> result = new ArrayList<>();
            for (Apple apple : inventory) {
                if (predicate.test(apple)) {
                    result.add(apple);
                }
            }
            return result;
    }

}
