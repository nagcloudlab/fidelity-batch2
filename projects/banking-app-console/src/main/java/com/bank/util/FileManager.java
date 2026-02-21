package com.bank.util;

import com.bank.model.Account;
import com.bank.model.CurrentAccount;
import com.bank.model.SavingsAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager provides static utility methods for saving and loading accounts
 * using two approaches:
 *
 * 1. CSV (Comma-Separated Values) — human-readable text format
 *    - Uses BufferedWriter / BufferedReader (character streams)
 *    - Files can be opened in any text editor or spreadsheet
 *    - Requires manual parsing (split, parse)
 *
 * 2. Serialization — binary format
 *    - Uses ObjectOutputStream / ObjectInputStream (byte streams)
 *    - One line to save/load entire object graphs
 *    - Java-only, not human-readable
 *
 * Key concepts demonstrated:
 * - try-with-resources: automatically closes streams (no need for finally block)
 * - Buffered streams: efficient I/O with internal buffer (8KB default)
 * - Object serialization: convert Java objects to/from bytes
 * - Exception handling: IOException, ClassNotFoundException
 *
 * Day 7: Created for file persistence
 *
 * CSV vs Serialization:
 * | Feature          | CSV                    | Serialization           |
 * |-----------------|------------------------|------------------------|
 * | Human-readable? | Yes                    | No (binary)            |
 * | Cross-language? | Yes                    | No (Java only)         |
 * | Code complexity | Manual parsing         | One line               |
 * | Best for        | Reports, data exchange | Quick Java persistence |
 */
public class FileManager {

    // ==================== CSV OPERATIONS ====================

    /**
     * Save accounts to a CSV file using BufferedWriter.
     *
     * CSV format:
     * accountNumber,holderName,balance,type,interestRate,overdraftLimit
     * 1001,Ravi Kumar,50000.00,SAVINGS,4.5,0
     * 1002,Priya Sharma,30000.00,CURRENT,0,10000.0
     *
     * @param accounts List of accounts to save
     * @param filePath Path to the CSV file
     */
    public static void saveAccountsCSV(List<Account> accounts, String filePath) {
        ensureDirectoryExists(filePath);

        // try-with-resources: writer is automatically closed when the block exits
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header row
            writer.write("accountNumber,holderName,balance,type,interestRate,overdraftLimit");
            writer.newLine();

            // Write data rows
            for (Account acc : accounts) {
                StringBuilder sb = new StringBuilder();
                sb.append(acc.getAccountNumber()).append(",");
                sb.append(acc.getHolderName()).append(",");
                sb.append(String.format("%.2f", acc.getBalance())).append(",");

                if (acc instanceof SavingsAccount) {
                    SavingsAccount sa = (SavingsAccount) acc;
                    sb.append("SAVINGS,");
                    sb.append(sa.getInterestRate()).append(",");
                    sb.append("0");
                } else if (acc instanceof CurrentAccount) {
                    CurrentAccount ca = (CurrentAccount) acc;
                    sb.append("CURRENT,");
                    sb.append("0,");
                    sb.append(ca.getOverdraftLimit());
                }

                writer.write(sb.toString());
                writer.newLine();
            }

            System.out.println("Saved " + accounts.size() + " accounts to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }

    /**
     * Load accounts from a CSV file using BufferedReader.
     *
     * readLine() returns null at end of file — this is how we detect
     * when to stop reading.
     *
     * @param filePath Path to the CSV file
     * @return List of loaded accounts (empty list if file not found)
     */
    public static List<Account> loadAccountsCSV(String filePath) {
        List<Account> loaded = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No saved data found at " + filePath);
            return loaded;
        }

        // try-with-resources: reader is automatically closed
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();   // skip header row
            String line;

            while ((line = reader.readLine()) != null) {   // null = end of file
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                long accNo = Long.parseLong(parts[0].trim());
                String name = parts[1].trim();
                double balance = Double.parseDouble(parts[2].trim());
                String type = parts[3].trim();
                double rate = Double.parseDouble(parts[4].trim());
                double limit = Double.parseDouble(parts[5].trim());

                if (type.equals("SAVINGS")) {
                    loaded.add(new SavingsAccount(accNo, name, balance, rate));
                } else if (type.equals("CURRENT")) {
                    loaded.add(new CurrentAccount(accNo, name, balance, limit));
                }
            }

            System.out.println("Loaded " + loaded.size() + " accounts from " + filePath);
        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing data: " + e.getMessage());
        }

        return loaded;
    }

    // ==================== SERIALIZATION OPERATIONS ====================

    /**
     * Save accounts using Java Object Serialization (binary format).
     *
     * ObjectOutputStream converts the entire List<Account> into bytes
     * and writes it to a file. All Account objects must implement Serializable.
     *
     * @param accounts List of accounts to serialize
     * @param filePath Path to the binary file (e.g., "data/accounts.dat")
     */
    public static void serializeAccounts(List<Account> accounts, String filePath) {
        ensureDirectoryExists(filePath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(accounts);   // saves the ENTIRE list in one call!
            System.out.println("Serialized " + accounts.size() + " accounts to " + filePath);
        } catch (IOException e) {
            System.out.println("Error serializing: " + e.getMessage());
        }
    }

    /**
     * Load accounts from a serialized binary file.
     *
     * ObjectInputStream reads bytes and reconstructs the List<Account>.
     * The @SuppressWarnings("unchecked") silences the compiler warning
     * about casting Object to List<Account>.
     *
     * @param filePath Path to the binary file
     * @return List of deserialized accounts (empty list if file not found)
     */
    @SuppressWarnings("unchecked")
    public static List<Account> deserializeAccounts(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No serialized data found at " + filePath);
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Account> loaded = (List<Account>) ois.readObject();
            System.out.println("Deserialized " + loaded.size() + " accounts from " + filePath);
            return loaded;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Ensure the parent directory of the given file path exists.
     * Creates it (and any necessary parent directories) if it doesn't.
     *
     * @param filePath The file path whose parent directory should exist
     */
    private static void ensureDirectoryExists(String filePath) {
        File parent = new File(filePath).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}
