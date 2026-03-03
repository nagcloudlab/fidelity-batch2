package com.example;

import java.io.InputStreamReader;

public class Example1 {
    public static void main(String[] args) {

        // standard input -> System.in
        // standard output -> System.out
        // standard error -> System.err

        //way-1 : without using BufferedReader
//        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//        System.out.println("Enter your name: ");
//        try {
//            String name = "";
//            int c;
//            while ((c = inputStreamReader.read()) != -1) {
//                if (c == '\n') {
//                    break;
//                }
//                name += (char) c;
//            }
//            System.out.println("Hello, " + name + "!");
//        } catch (Exception e) {
//            System.err.println("An error occurred: " + e.getMessage());
//        }

        //way-2 : using BufferedReader
//        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(inputStreamReader);
//        System.out.println("Enter your name: ");
//        try {
//            String name = bufferedReader.readLine();
//            System.out.println("Hello, " + name + "!");
//        } catch (Exception e) {
//            System.err.println("An error occurred: " + e.getMessage());
//        }


        // way-3 : using Scanner ( java 1.5+ )
//        java.util.Scanner scanner = new java.util.Scanner(System.in);
//        System.out.println("Enter your name: ");
//        String name = scanner.nextLine();
//        System.out.println("Hello, " + name + "!");


    }
}