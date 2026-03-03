package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Example6 {

    public static void main(String[] args) {

        File file = new File("Technical Training TOC.pdf");
        try (PDDocument document = PDDocument.load(file)) {
            if (!document.isEncrypted()) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                System.out.println(text);
            } else {
                System.out.println("The PDF is encrypted.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
