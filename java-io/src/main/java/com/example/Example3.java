package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Example3 {
    public static void main(String[] args) {

        File file2 = new File("file2.txt");

        // way-1: without buffered reader
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(file2); // character stream opened for reading
            int i;
            while ((i = fileReader.read()) != -1) {
                System.out.print((char) i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close(); // closing the stream
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("-----------------------------");

        // way-2: with buffered reader

        fileReader = null;
        try {
            fileReader = new FileReader(file2); // character stream opened for reading
            BufferedReader bufferedReader = new BufferedReader(fileReader); // wrapping the file reader with buffered reader
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close(); // closing the stream
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // way-3: with try-with-resources (Java 7 and above)
        try (FileReader fr = new FileReader(file2);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // important: no need to explicitly close the streams in try-with-resources, it will be done automatically

        // way-4 : Scanner class (Java 5 and above)
        try (Scanner scanner = new Scanner(file2)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
