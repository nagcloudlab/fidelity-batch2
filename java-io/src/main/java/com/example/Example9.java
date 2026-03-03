package com.example;

import com.example.model.UserSession;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Example9 {

    public static void main(String[] args) {

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("userSession.ser");
            ois = new ObjectInputStream(fis);

            UserSession userSession = (UserSession) ois.readObject();
            System.out.println("User ID: " + userSession.getUserId());
            System.out.println("Session ID: " + userSession.getSessionId());
            System.out.println("Last Access Time: " + userSession.getLastAccessTime());
            System.out.println("Is Active: " + userSession.isActive());
            System.out.println("IP Address: " + userSession.getIpAddress());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
