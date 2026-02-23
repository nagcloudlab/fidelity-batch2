# Day 7 — File Handling

## Instructor Guide

> **Duration**: Full day
> **Pre-requisite**: Day 5 BankApp (ArrayList, HashMap, Stack, Queue)
> **Case Study**: Save accounts and transactions to files so data persists after the program exits
> **Goal by end of day**: Trainees can read/write text files (CSV), serialize/deserialize objects, and use try-with-resources

---

## Topic 1: Why File Handling in Real Applications

#### Key Points (10 min)

- **The problem**: When our BankApp exits, ALL data is lost. Accounts, balances, transactions — gone.
- **In-memory data** (ArrayList, HashMap) = fast but temporary. Lives only while the program runs.
- **Persistent data** (files, databases) = slower but survives program exit. Still there when you restart.

| | In-Memory (Collections) | Persistent (Files/DB) |
|---|---|---|
| Speed | Very fast | Slower (disk I/O) |
| Lifetime | Program execution only | Survives restart |
| Capacity | Limited by RAM | Limited by disk |
| Sharing | Single program | Multiple programs |

- **Day 7**: Save to **files** (simple, no external tools needed)
- **Day 12**: Save to **database** via JDBC (proper solution for real apps)

#### Teaching Tip

> Run the Day 5 BankApp, create an account, deposit money, exit. Run again — everything is gone. "How do we fix this? Files."

---

## Topic 2: Java File I/O Overview

#### Key Points (10 min)

- **`File` class** — represents a file or directory path. Can check existence, size, create/delete.
- **`Path` / `Paths`** (NIO) — newer API, more features. Just mention for awareness; we use `File` today.
- **Absolute path**: Full path from root — `/home/user/data/accounts.csv`
- **Relative path**: From the project directory — `data/accounts.csv`

#### Micro Example

```java
import java.io.File;

File file = new File("data/accounts.csv");

System.out.println("Exists: " + file.exists());
System.out.println("Name: " + file.getName());
System.out.println("Path: " + file.getAbsolutePath());
System.out.println("Is file: " + file.isFile());
System.out.println("Is dir: " + file.isDirectory());
System.out.println("Size: " + file.length() + " bytes");

// Create parent directories if they don't exist
File dir = new File("data");
if (!dir.exists()) {
    dir.mkdirs();
}
```

#### Teaching Tip

> Show `file.getAbsolutePath()` so trainees understand where files are actually created relative to their project.

---

## Topic 3: Byte Streams vs Character Streams

#### Key Points (15 min)

- **Two families** of I/O in Java:

| | Byte Streams | Character Streams |
|---|---|---|
| Unit | Bytes (raw data) | Characters (text) |
| Base classes | `InputStream` / `OutputStream` | `Reader` / `Writer` |
| Best for | Binary files (images, serialized objects) | Text files (CSV, config, logs) |
| Encoding | No encoding awareness | Handles character encoding (UTF-8, etc.) |

- For our banking CSV files → **Character streams** (Reader/Writer)
- For serialized account objects → **Byte streams** (InputStream/OutputStream)

#### Micro Example — Write and read text with character streams

```java
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

// Write
FileWriter writer = new FileWriter("test.txt");
writer.write("Hello, Bank!\n");
writer.write("Account data here.\n");
writer.close();

// Read
FileReader reader = new FileReader("test.txt");
int ch;
while ((ch = reader.read()) != -1) {   // reads one character at a time!
    System.out.print((char) ch);
}
reader.close();
```

> **Problem**: `FileReader.read()` reads one character at a time — very slow. That's why we need buffered streams.

---

## Topic 4: Buffered Streams

#### Key Points (20 min)

- **BufferedReader / BufferedWriter** wrap around FileReader/FileWriter.
- They use an internal buffer (default 8KB) — reads/writes chunks instead of one char at a time.
- **`BufferedReader.readLine()`** — reads a full line as String. Returns `null` at end of file.
- **`BufferedWriter.write()`** + **`newLine()`** — writes text with platform-appropriate line endings.

#### Case Study Step 1 — Save accounts to CSV

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public static void saveAccountsToCSV(String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        // Header
        writer.write("accountNumber,holderName,balance,type,interestRate,overdraftLimit");
        writer.newLine();

        // Data rows
        for (Account acc : accounts) {
            StringBuilder sb = new StringBuilder();
            sb.append(acc.getAccountNumber()).append(",");
            sb.append(acc.getHolderName()).append(",");
            sb.append(acc.getBalance()).append(",");

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

        System.out.println("Accounts saved to " + filePath + " (" + accounts.size() + " records)");
    } catch (IOException e) {
        System.out.println("Error saving accounts: " + e.getMessage());
    }
}
```

#### Case Study Step 2 — Load accounts from CSV

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public static void loadAccountsFromCSV(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println("No saved data found. Starting fresh.");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String header = reader.readLine();   // skip header
        String line;
        int count = 0;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 6) continue;

            long accNo = Long.parseLong(parts[0].trim());
            String name = parts[1].trim();
            double balance = Double.parseDouble(parts[2].trim());
            String type = parts[3].trim();
            double interestRate = Double.parseDouble(parts[4].trim());
            double overdraftLimit = Double.parseDouble(parts[5].trim());

            Account acc;
            if (type.equals("SAVINGS")) {
                acc = new SavingsAccount(name, accNo, balance, interestRate);
            } else {
                acc = new CurrentAccount(name, accNo, balance, overdraftLimit);
            }

            addAccount(acc);
            count++;
        }

        System.out.println("Loaded " + count + " accounts from " + filePath);
    } catch (IOException e) {
        System.out.println("Error loading accounts: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error parsing data: " + e.getMessage());
    }
}
```

> **Teaching tip**: Show the generated CSV file in a text editor. Open it in a spreadsheet tool. "CSV is universal — any program can read it."

#### Case Study Step 3 — Save transactions to CSV

```java
public static void saveTransactionsToCSV(String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write("timestamp,type,amount,accountNumber,balanceBefore,balanceAfter");
        writer.newLine();

        for (Transaction txn : allTransactions) {
            writer.write(txn.getTimestamp() + ","
                    + txn.getType() + ","
                    + txn.getAmount() + ","
                    + txn.getAccountNumber() + ","
                    + txn.getBalanceBefore() + ","
                    + txn.getBalanceAfter());
            writer.newLine();
        }

        System.out.println("Transactions saved (" + allTransactions.size() + " records)");
    } catch (IOException e) {
        System.out.println("Error saving transactions: " + e.getMessage());
    }
}
```

---

## Topic 5: Object Serialization

#### Key Points (20 min)

- **Serialization** = converting an object into a byte stream (to save to file or send over network).
- **Deserialization** = reading bytes back into an object.
- The class must implement **`Serializable`** interface (marker interface — no methods to implement).
- Use **`ObjectOutputStream`** to write and **`ObjectInputStream`** to read.
- **`serialVersionUID`** — a version number. If the class changes, old serialized objects may not load. Explicitly define it to control compatibility.

```
Object → ObjectOutputStream → file.dat (bytes)
file.dat (bytes) → ObjectInputStream → Object
```

#### Teaching Tip

> "CSV is human-readable but fragile — you have to parse strings, handle commas in names, etc. Serialization saves the entire object graph automatically but produces binary files (not human-readable). Each has trade-offs."

#### Case Study Step 4 — Make Account serializable

```java
import java.io.Serializable;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    // ... all existing fields and methods unchanged ...
}

// SavingsAccount — already extends Account, just add serialVersionUID
public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    // ...
}

// CurrentAccount
public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    // ...
}
```

#### Case Study Step 5 — Save/load with serialization

```java
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

// Save all accounts as serialized objects
public static void saveAccountsSerialized(String filePath) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
        oos.writeObject(accounts);     // writes the entire ArrayList at once
        System.out.println("Accounts serialized to " + filePath);
    } catch (IOException e) {
        System.out.println("Error serializing: " + e.getMessage());
    }
}

// Load serialized accounts
@SuppressWarnings("unchecked")
public static void loadAccountsSerialized(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println("No serialized data found.");
        return;
    }

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
        List<Account> loaded = (List<Account>) ois.readObject();
        for (Account acc : loaded) {
            addAccount(acc);
        }
        System.out.println("Deserialized " + loaded.size() + " accounts.");
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error deserializing: " + e.getMessage());
    }
}
```

> **Demo**: Save, exit the program, start again, load. "Your data survived!"

---

## Topic 6: transient Keyword

#### Key Points (5 min)

- **`transient`** = marks a field to be EXCLUDED from serialization.
- When deserialized, transient fields get their default value (0, null, false).
- **Use when**: The field is sensitive (password), computed (can be recalculated), or non-serializable (Scanner, database connection).

#### Micro Example

```java
public class User implements Serializable {
    private String username;
    private transient String password;   // NOT saved — security
    private transient int loginCount;    // NOT saved — can be recalculated

    // After deserialization:
    // username = "ravi"
    // password = null   (transient → default)
    // loginCount = 0    (transient → default)
}
```

#### Case Study connection

> "In our banking app, we might make `isActive` transient if we want all accounts to require re-activation on load. Or we might have a `transient Scanner` field in the future. For now, all our Account fields should be serialized — no transient needed."

---

## Topic 7: Exception Handling in File Operations

#### Key Points (10 min)

- File I/O always requires exception handling — files may not exist, may be locked, disk may be full.
- **try-with-resources** (Java 7+) = automatically closes resources when the try block exits.
- Any class implementing `AutoCloseable` or `Closeable` can be used in try-with-resources.

#### Without try-with-resources (old way):

```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("data.csv"));
    String line = reader.readLine();
    // process...
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
} finally {
    if (reader != null) {
        try {
            reader.close();
        } catch (IOException e) {
            // even closing can fail!
        }
    }
}
```

#### With try-with-resources (clean way):

```java
try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))) {
    String line = reader.readLine();
    // process...
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
// reader is automatically closed — no finally block needed!
```

#### Multiple resources:

```java
try (
    BufferedReader reader = new BufferedReader(new FileReader("input.csv"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))
) {
    String line;
    while ((line = reader.readLine()) != null) {
        writer.write(line.toUpperCase());
        writer.newLine();
    }
}   // both reader and writer automatically closed
```

> **Teaching point**: "All our file code today already uses try-with-resources. Now you know WHY — it's cleaner and safer. Resources are always closed, even if an exception occurs."

---

## Case Study — Complete Day 7 Integration

### FileManager.java (new utility class)

```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    // --- CSV Operations ---

    public static void saveAccountsCSV(List<Account> accounts, String filePath) {
        ensureDirectoryExists(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("accountNumber,holderName,balance,type,interestRate,overdraftLimit");
            writer.newLine();

            for (Account acc : accounts) {
                StringBuilder sb = new StringBuilder();
                sb.append(acc.getAccountNumber()).append(",");
                sb.append(acc.getHolderName()).append(",");
                sb.append(String.format("%.2f", acc.getBalance())).append(",");

                if (acc instanceof SavingsAccount) {
                    sb.append("SAVINGS,");
                    sb.append(((SavingsAccount) acc).getInterestRate()).append(",");
                    sb.append("0");
                } else if (acc instanceof CurrentAccount) {
                    sb.append("CURRENT,");
                    sb.append("0,");
                    sb.append(((CurrentAccount) acc).getOverdraftLimit());
                }

                writer.write(sb.toString());
                writer.newLine();
            }

            System.out.println("Saved " + accounts.size() + " accounts to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }

    public static List<Account> loadAccountsCSV(String filePath) {
        List<Account> loaded = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No CSV data found at " + filePath);
            return loaded;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();   // skip header
            String line;

            while ((line = reader.readLine()) != null) {
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
                    loaded.add(new SavingsAccount(name, accNo, balance, rate));
                } else if (type.equals("CURRENT")) {
                    loaded.add(new CurrentAccount(name, accNo, balance, limit));
                }
            }

            System.out.println("Loaded " + loaded.size() + " accounts from " + filePath);
        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing CSV: " + e.getMessage());
        }

        return loaded;
    }

    public static void saveTransactionsCSV(List<Transaction> transactions, String filePath) {
        ensureDirectoryExists(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("timestamp,type,amount,accountNumber,balanceBefore,balanceAfter");
            writer.newLine();

            for (Transaction txn : transactions) {
                writer.write(String.format("%s,%s,%.2f,%d,%.2f,%.2f",
                        txn.getTimestamp(), txn.getType(), txn.getAmount(),
                        txn.getAccountNumber(), txn.getBalanceBefore(), txn.getBalanceAfter()));
                writer.newLine();
            }

            System.out.println("Saved " + transactions.size() + " transactions to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // --- Serialization Operations ---

    public static void serializeAccounts(List<Account> accounts, String filePath) {
        ensureDirectoryExists(filePath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(accounts);
            System.out.println("Serialized " + accounts.size() + " accounts to " + filePath);
        } catch (IOException e) {
            System.out.println("Error serializing: " + e.getMessage());
        }
    }

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

    // --- Helpers ---

    private static void ensureDirectoryExists(String filePath) {
        File parent = new File(filePath).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}
```

### BankApp.java — Day 7 additions

```java
// Add constants at top:
static final String ACCOUNTS_CSV = "data/accounts.csv";
static final String TRANSACTIONS_CSV = "data/transactions.csv";
static final String ACCOUNTS_DAT = "data/accounts.dat";

// In main — load on startup:
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    // Load saved data (CSV approach)
    List<Account> loaded = FileManager.loadAccountsCSV(ACCOUNTS_CSV);
    if (loaded.isEmpty()) {
        // First run — pre-load sample data
        addAccount(new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5));
        addAccount(new CurrentAccount("Priya Sharma", 1002L, 30000, 10000));
    } else {
        for (Account acc : loaded) {
            addAccount(acc);
        }
    }

    // ... menu loop ...

    // Save on exit (add before sc.close()):
    case 10:    // or whatever exit option number
        FileManager.saveAccountsCSV(accounts, ACCOUNTS_CSV);
        FileManager.saveTransactionsCSV(allTransactions, TRANSACTIONS_CSV);
        System.out.println("Data saved. Thank you!");
        break;
}

// Add menu options for manual save/load:
// case 11: Save Data (CSV)
// case 12: Save Data (Serialized)
```

> **Demo flow**: Run app → create accounts → deposit → exit (auto-saves CSV) → Run again → data is loaded → modify → exit → data updated. "Persistence achieved!"

---

## Day 7 Exercises

### Exercise 1: Append vs Overwrite
**Problem**: Modify `saveTransactionsCSV` to APPEND new transactions instead of overwriting. Hint: `new FileWriter(path, true)` enables append mode.

### Exercise 2: Transaction Log Loading
**Problem**: Write `loadTransactionsCSV(String filePath)` that reads the transactions CSV and returns a `List<Transaction>`. Rebuild the `transactionsByAccount` map from the loaded data.

### Exercise 3: Export Account Statement to File
**Problem**: Write a method `exportStatement(long accountNumber, String filePath)` that:
- Finds the account
- Writes a formatted statement to a text file with header, all transactions, and closing balance
- Make it look like a real bank statement

### Exercise 4: Serialization Comparison
**Problem**: Save the same accounts using both CSV and serialization. Compare:
- File size
- Readability (open both in a text editor)
- Code complexity for save/load
- Write your observations as comments in the code

### Exercise 5: Full Challenge — Auto-Backup
**Problem**: Add an auto-backup feature:
- Every 5 transactions, automatically save a backup to `data/backup_<timestamp>.csv`
- Keep only the 3 most recent backups (delete older ones)
- On startup, if the main file is corrupted, offer to load from the latest backup

---

## Day 7 Quiz (8 questions)

1. What is the difference between in-memory data and persistent data?
2. What is the difference between byte streams and character streams?
3. Why use BufferedReader/BufferedWriter instead of FileReader/FileWriter directly?
4. What does `readLine()` return when it reaches the end of the file?
5. What interface must a class implement to be serializable?
6. What does the `transient` keyword do?
7. What is the advantage of try-with-resources over traditional try-finally?
8. When would you choose CSV over serialization (and vice versa)?

---

## Day 7 Summary

| Step | Feature | Approach | Key Classes |
|------|---------|----------|-------------|
| Step 1 | Save accounts to CSV | Character streams | BufferedWriter, FileWriter |
| Step 2 | Load accounts from CSV | Character streams | BufferedReader, FileReader |
| Step 3 | Save transactions to CSV | Character streams | BufferedWriter |
| Step 4 | Make Account serializable | Marker interface | Serializable, serialVersionUID |
| Step 5 | Save/load with serialization | Byte streams | ObjectOutputStream, ObjectInputStream |
| All | Exception handling | try-with-resources | AutoCloseable |

### CSV vs Serialization

| | CSV | Serialization |
|---|---|---|
| Human-readable | Yes — open in any editor/Excel | No — binary format |
| Interoperability | Any language can read CSV | Java only |
| Complexity | Manual parsing | Automatic (entire object graph) |
| File size | Usually smaller (text) | Can be larger (metadata) |
| Schema changes | Fragile (column order matters) | `serialVersionUID` for compatibility |
| Best for | Reports, data exchange, logs | Quick save/load within Java apps |

> **Preview for Day 8**: "Saving to files works, but it's fragile — no querying, no concurrent access, no relationships between data. Tomorrow we learn REST APIs with Spring Boot, and later we'll replace files with a proper database."
