package com.example;

import java.io.File;
import java.io.FileWriter;

public class Example4 {

    public static void main(String[] args) {

        File file = new File("report.csv");
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file, false); // character stream to write to the file
            fileWriter.write("Name, Age, City\n");
            fileWriter.write("Alice, 30, New York\n");
            fileWriter.write("Bob, 25, Los Angeles\n");
            fileWriter.write("Charlie, 35, Chicago\n");
            fileWriter.flush();
            fileWriter.write("David, 28, Miami\n");
            fileWriter.close();
            //fileWriter.write("Eve, 22, Seattle\n"); // This will throw an IOException since the stream is closed
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
