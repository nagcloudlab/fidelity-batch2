package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Example5 {
    public static void main(String[] args) {

        File file = new File("Technical Training TOC.pdf");
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(file); // byte stream for reading data from the file
            byte[] byteArray = new byte[(int) file.length()]; // create a byte array to hold the file data
            fis.read(byteArray); // read the file data into the byte array

            fos = new FileOutputStream("Technical Training TOC Copy.pdf"); // byte stream for writing data to a new file
            fos.write(byteArray);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close(); // close the input stream
                }
                if (fos != null) {
                    fos.close(); // close the output stream
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
