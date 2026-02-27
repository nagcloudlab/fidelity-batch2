package com.example;

import java.util.function.Predicate;

public class GreetApplePredicate implements Predicate<Apple> {
    @Override
    public boolean test(Apple apple) {
        return "green".equals(apple.getColor());
    }
}
