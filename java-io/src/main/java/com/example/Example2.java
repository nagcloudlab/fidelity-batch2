package com.example;

import java.io.File;
import java.io.IOException;

public class Example2 {

    public static void main(String[] args) {

        File file = new File("file1.txt");

        // information about the file
        System.out.println("File name: " + file.getName());
        System.out.println("Absolute path: " + file.getAbsolutePath());
        System.out.println("Writable: " + file.canWrite());
        System.out.println("Readable: " + file.canRead());
        System.out.println("File size in bytes: " + file.length());
        System.out.println("Is it a directory? " + file.isDirectory());
        System.out.println("Is it a file? " + file.isFile());
        System.out.println("Is it hidden? " + file.isHidden());
        System.out.println("Last modified: " + file.lastModified());

        File file2 = new File("file2.txt");;
        boolean exists = file2.exists();
        System.out.println("Does file2.txt exist? " + exists);
        if (!exists) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
