package com.example;

import com.example.model.UserSession;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/*

serialization:
------------------

-> Serialization is the process of converting an object into a byte stream,
   which can then be easily stored or transmitted.
   This is useful for saving the state of an object to a file,
   sending it over a network, or storing it in a database.

 */

/*

authoR: bar

 */
public class Example8 {
    public static void main(String[] args) {

        // User Logging In
        String sessionId = "abc123";
        String userId = "user1";
        long lastAccessTime = System.currentTimeMillis();
        boolean isActive = true;
        String ipAddress = "127.0.0.1";

        UserSession userSession = new UserSession(sessionId, userId, lastAccessTime, isActive, ipAddress);

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos= new FileOutputStream("userSession.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(userSession); // Serialize the userSession object to a file
            System.out.println("User session serialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }





    }
}
