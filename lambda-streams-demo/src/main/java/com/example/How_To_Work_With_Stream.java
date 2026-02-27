package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class How_To_Work_With_Stream {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /*
        stream
            - create stream from a data source (e.g., collection, array, generator)
            - use 1 or more intermediate operations (e.g., filter, map, sorted)
            - use terminal operation (e.g., forEach, collect, reduce) to produce a result or side-effect
         */

        Stream<Integer> stream = numbers
                .stream()
                .filter(n -> n % 2 == 0) // intermediate operation: filter even numbers
                .limit(2); // intermediate operation: limit to first 2 elements

        stream.forEach(System.out::println); // terminal operation: print each element

//        stream.forEach(System.out::println); // This will throw an IllegalStateException because the stream has already been consumed by the previous terminal operation.

    }
}
