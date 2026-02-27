package com.example;

import java.io.IOException;
import java.util.stream.Stream;

public class How_To_Create_Stream {
    public static void main(String[] args) {

        // How to create a stream?
        //#1 from values
        var stream1 = java.util.stream.Stream.of("a", "b", "c");
        stream1.forEach(System.out::println);

        // #2 from array
        var array = new String[]{"d", "e", "f"};
        var stream2 = java.util.Arrays.stream(array);
        stream2.forEach(System.out::println);

        // #3 from collection
        var list = java.util.List.of("g", "h", "i");
        var stream3 = list.stream();
        stream3.forEach(System.out::println);

        // #4 from files
        try {
            Stream<String> lines = java.nio.file.Files.lines(java.nio.file.Paths.get("C:\\Users\\nagcl\\fidelity-batch2\\lambda-streams-demo\\lunch.txt"));
            lines.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // #5 from infinite stream
        var stream5 = Stream.iterate(0, n -> n + 1);
        stream5.forEach(System.out::println);

    }
}
