# Day 12 — JDBC Integration

## Instructor Guide

> **Duration**: ~2 hours
> **Pre-requisite**: Day 11 SQL Fundamentals (CREATE TABLE, INSERT, SELECT, UPDATE, DELETE)
> **Case Study**: Replace in-memory storage (ArrayList + HashMap) in the banking app with a real database using JDBC
> **Goal by end of day**: Trainees can connect Java to a database, execute CRUD queries using PreparedStatement, and process results with ResultSet

---

## Important Setup Note

> Trainees need:
> 1. Their Day 8 Spring Boot project (or any Java project)
> 2. **H2 database** dependency (already added on Day 11) — no external DB installation needed
> 3. Alternatively, if MySQL/PostgreSQL is available, they can use that
>
> H2 is recommended for training — zero setup, runs in-memory.

---

## Topic 1: Why JDBC?

#### Key Points (10 min)

- **JDBC** = Java Database Connectivity — the standard Java API for talking to databases
- It replaces our file-based persistence (Day 7) and in-memory storage with a real database
- JDBC is the **bridge** between Java code and SQL:

```
Java Code                    JDBC                     Database
──────────                ──────────                ──────────
AccountService      →     Connection           →    accounts table
  .deposit()              PreparedStatement          UPDATE balance
  .getAll()               ResultSet             ←   rows of data
```

#### The evolution of our data layer

| Day | Storage | Pros | Cons |
|-----|---------|------|------|
| Day 1-5 | In-memory (ArrayList + HashMap) | Fast, simple | Lost on exit |
| Day 7 | Files (CSV, Serialization) | Persists | No querying, no concurrency |
| Day 8 | Still in-memory (via REST API) | Network accessible | Still lost on restart |
| **Day 12** | **Database (via JDBC)** | **Persists, queryable, concurrent** | **Slightly more complex** |

> **Teaching moment**: "We've been building the same banking app for 12 days. Today we upgrade the foundation — instead of keeping data in memory (gone on restart) or files (hard to query), we store it in a proper database. The rest of the app (controllers, service logic, HTML pages) barely changes."

---

## Topic 2: JDBC Architecture

#### Key Points (15 min)

#### The four key JDBC components

```
┌──────────────────────────────────────────────────────────┐
│ Java Application (AccountService)                         │
│                                                           │
│   1. Load Driver       DriverManager.getConnection()      │
│   2. Get Connection    Connection conn = ...               │
│   3. Create Statement  PreparedStatement ps = ...          │
│   4. Execute Query     ResultSet rs = ps.executeQuery()    │
│   5. Process Results   while (rs.next()) { ... }           │
│   6. Close Resources   conn.close()                        │
└──────────────────────────────────────────────────────────┘
         │
         │  JDBC API (java.sql.*)
         ▼
┌──────────────────────────────────────────────────────────┐
│ JDBC Driver (database-specific)                           │
│   H2 Driver / MySQL Driver / PostgreSQL Driver            │
└──────────────────────────────────────────────────────────┘
         │
         │  Database protocol
         ▼
┌──────────────────────────────────────────────────────────┐
│ Database (H2 / MySQL / PostgreSQL)                        │
│   accounts table, transactions table                      │
└──────────────────────────────────────────────────────────┘
```

| Component | Class/Interface | Purpose |
|-----------|----------------|---------|
| **Driver** | `java.sql.Driver` | Translates JDBC calls to database-specific protocol |
| **Connection** | `java.sql.Connection` | An active session with the database |
| **Statement** | `java.sql.PreparedStatement` | Sends SQL to the database |
| **ResultSet** | `java.sql.ResultSet` | Holds the rows returned by a SELECT query |

#### JDBC URL format

```
jdbc:h2:mem:bankdb              ← H2 in-memory
jdbc:mysql://localhost:3306/bankdb   ← MySQL
jdbc:postgresql://localhost:5432/bankdb  ← PostgreSQL
```

> "The JDBC URL tells Java which database to connect to. It's like a phone number — different format for each database, but the dialing process (JDBC API) is the same."

---

## Topic 3: JDBC Workflow

#### Key Points (20 min)

> **Live code this step by step.** Build a standalone example first, then integrate into the banking app.

#### Step-by-step workflow

```java
import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        // 1. Database URL, username, password
        String url = "jdbc:h2:mem:bankdb";
        String user = "sa";
        String password = "";

        // 2. Get a connection (try-with-resources auto-closes)
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // 3. Create the table
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("""
                    CREATE TABLE accounts (
                        account_number BIGINT PRIMARY KEY,
                        holder_name VARCHAR(100) NOT NULL,
                        balance DOUBLE DEFAULT 0,
                        type VARCHAR(20) NOT NULL,
                        active BOOLEAN DEFAULT TRUE
                    )
                """);
                System.out.println("Table created.");
            }

            // 4. Insert data
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)")) {
                ps.setLong(1, 1001);
                ps.setString(2, "Ravi Kumar");
                ps.setDouble(3, 50000);
                ps.setString(4, "SAVINGS");
                ps.executeUpdate();
                System.out.println("Inserted Ravi.");
            }

            // 5. Query data
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getLong("account_number") + " | " +
                                       rs.getString("holder_name") + " | " +
                                       rs.getDouble("balance"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
```

#### Key methods

| Method | When to use | Returns |
|--------|-----------|---------|
| `ps.executeQuery()` | SELECT (read) | `ResultSet` |
| `ps.executeUpdate()` | INSERT, UPDATE, DELETE (write) | `int` (rows affected) |
| `ps.execute()` | CREATE TABLE, DDL | `boolean` |

#### ResultSet navigation

```java
ResultSet rs = ps.executeQuery();

while (rs.next()) {                         // move cursor to next row
    long accNo = rs.getLong("account_number");    // by column name (preferred)
    String name = rs.getString("holder_name");
    double balance = rs.getDouble("balance");
    boolean active = rs.getBoolean("active");

    // Alternative: by column index (1-based)
    long accNo2 = rs.getLong(1);
}
```

> "ResultSet is like a cursor pointing at rows. `next()` moves to the next row and returns `false` when there are no more rows."

---

## Topic 4: PreparedStatement vs Statement

#### Key Points (15 min)

#### Why NOT use Statement?

```java
// DANGEROUS — SQL Injection vulnerability!
String name = userInput;    // What if user enters: ' OR '1'='1
String sql = "SELECT * FROM accounts WHERE holder_name = '" + name + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);
// Actual SQL: SELECT * FROM accounts WHERE holder_name = '' OR '1'='1'
// Returns ALL accounts!
```

#### PreparedStatement — the safe way

```java
// SAFE — parameters are escaped automatically
String sql = "SELECT * FROM accounts WHERE holder_name = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, userInput);    // ? is replaced safely
ResultSet rs = ps.executeQuery();
```

#### Comparison

| | Statement | PreparedStatement |
|---|---|---|
| SQL injection | **Vulnerable** | **Safe** (auto-escapes) |
| Performance | Parsed every time | Parsed once, reused |
| Parameters | String concatenation | `?` placeholders + setters |
| Rule | **Never use for user input** | **Always use this** |

> **Teaching moment**: "SQL injection is one of the most common security vulnerabilities. In 2024, it's still in the OWASP Top 10. PreparedStatement prevents it completely. There is NEVER a reason to use Statement with user-provided data."

#### Parameter index is 1-based

```java
// INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)
//                                                                           1  2  3  4
ps.setLong(1, 1001);           // first ?
ps.setString(2, "Ravi Kumar"); // second ?
ps.setDouble(3, 50000);        // third ?
ps.setString(4, "SAVINGS");    // fourth ?
```

---

## Topic 5: Case Study — Banking App with JDBC

#### Key Points (30 min)

> **This is the main deliverable** — refactor AccountService to use JDBC.

#### Case Study Step 1 — DatabaseManager (connection helper)

```java
import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    account_number BIGINT PRIMARY KEY,
                    holder_name VARCHAR(100) NOT NULL,
                    balance DOUBLE DEFAULT 0,
                    type VARCHAR(20) NOT NULL,
                    active BOOLEAN DEFAULT TRUE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(20) NOT NULL,
                    amount DOUBLE NOT NULL,
                    account_number BIGINT NOT NULL,
                    balance_before DOUBLE,
                    balance_after DOUBLE,
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
                )
            """);

            // Insert sample data if empty
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM accounts");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.executeUpdate("""
                    INSERT INTO accounts (account_number, holder_name, balance, type) VALUES
                    (1001, 'Ravi Kumar', 50000, 'SAVINGS'),
                    (1002, 'Priya Sharma', 30000, 'CURRENT'),
                    (1003, 'Amit Verma', 75000, 'SAVINGS')
                """);
            }

            System.out.println("Database initialized.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
```

> **Note**: `DB_CLOSE_DELAY=-1` keeps the H2 in-memory database alive as long as the JVM is running. Without it, the DB is destroyed when the last connection closes.

#### Case Study Step 2 — AccountRepository (JDBC operations)

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    // CREATE
    public Account insert(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, account.getAccountNumber());
            ps.setString(2, account.getHolderName());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getType());
            ps.executeUpdate();
            return account;
        }
    }

    // READ — all
    public List<Account> findAll() throws SQLException {
        String sql = "SELECT * FROM accounts ORDER BY account_number";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    // READ — by account number
    public Account findByAccountNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            }
            return null;
        }
    }

    // UPDATE — balance
    public void updateBalance(long accountNumber, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setLong(2, accountNumber);
            ps.executeUpdate();
        }
    }

    // UPDATE — full
    public Account update(long accountNumber, Account updated) throws SQLException {
        String sql = "UPDATE accounts SET holder_name = ?, type = ? WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, updated.getHolderName());
            ps.setString(2, updated.getType());
            ps.setLong(3, accountNumber);

            int rows = ps.executeUpdate();
            if (rows == 0) return null;
            return findByAccountNumber(accountNumber);
        }
    }

    // DELETE
    public boolean delete(long accountNumber) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            return ps.executeUpdate() > 0;
        }
    }

    // SEARCH — by name
    public List<Account> searchByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE LOWER(holder_name) LIKE ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    // Helper — map ResultSet row to Account object
    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        Account acc = new Account(
            rs.getLong("account_number"),
            rs.getString("holder_name"),
            rs.getDouble("balance"),
            rs.getString("type")
        );
        acc.setActive(rs.getBoolean("active"));
        return acc;
    }
}
```

> **Teaching moment**: "Notice the pattern — every method: get connection → prepare statement → set parameters → execute → process results → auto-close. The `mapRowToAccount` helper avoids repeating the ResultSet→Account conversion."

#### Case Study Step 3 — Updated AccountService

```java
@Service
public class AccountService {

    private final AccountRepository repository = new AccountRepository();

    public AccountService() {
        DatabaseManager.initializeDatabase();
    }

    public List<Account> getAllAccounts() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching accounts", e);
        }
    }

    public Account getByAccountNumber(long accountNumber) {
        try {
            return repository.findByAccountNumber(accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching account", e);
        }
    }

    public Account addAccount(Account account) {
        try {
            if (repository.findByAccountNumber(account.getAccountNumber()) != null) {
                return null;    // duplicate
            }
            account.setActive(true);
            return repository.insert(account);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating account", e);
        }
    }

    public Account deposit(long accountNumber, double amount) {
        try {
            Account acc = repository.findByAccountNumber(accountNumber);
            if (acc == null) return null;
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

            double newBalance = acc.getBalance() + amount;
            repository.updateBalance(accountNumber, newBalance);
            acc.setBalance(newBalance);
            return acc;
        } catch (SQLException e) {
            throw new RuntimeException("Error depositing", e);
        }
    }

    public Account withdraw(long accountNumber, double amount) {
        try {
            Account acc = repository.findByAccountNumber(accountNumber);
            if (acc == null) return null;
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            if (amount > acc.getBalance()) throw new IllegalArgumentException("Insufficient balance");

            double newBalance = acc.getBalance() - amount;
            repository.updateBalance(accountNumber, newBalance);
            acc.setBalance(newBalance);
            return acc;
        } catch (SQLException e) {
            throw new RuntimeException("Error withdrawing", e);
        }
    }

    // ... deleteAccount, updateAccount, searchByName follow same pattern
}
```

> **Key insight**: "The controller doesn't change AT ALL. The service barely changes — it just delegates to the repository instead of using ArrayList/HashMap. That's the power of layered architecture."

---

## Day 12 Exercises

### Exercise 1: Transaction Repository
**Problem**: Create a `TransactionRepository` with JDBC methods: `insert(Transaction)`, `findByAccountNumber(long accNo)`, `findAll()`. Record transactions automatically on deposit/withdraw.

### Exercise 2: Account Exists Check
**Problem**: Write a JDBC method `boolean accountExists(long accNo)` that uses `SELECT COUNT(*) FROM accounts WHERE account_number = ?` and returns true/false. Use it in `addAccount()` instead of `findByAccountNumber()` for better performance.

### Exercise 3: Batch Insert
**Problem**: Write a method `insertBatch(List<Account> accounts)` that uses `PreparedStatement.addBatch()` and `executeBatch()` to insert multiple accounts in one database call.

### Exercise 4: SQL Injection Demo
**Problem**: Write two versions of a `findByName(String name)` method — one using String concatenation (Statement) and one using PreparedStatement. Demonstrate that the Statement version is vulnerable to SQL injection with the input `' OR '1'='1`.

---

## Day 12 Quiz (8 questions)

1. What does JDBC stand for?
2. Name the four main JDBC interfaces/classes.
3. What is the difference between `executeQuery()` and `executeUpdate()`?
4. Why should you always use PreparedStatement instead of Statement?
5. What does `rs.next()` return when there are no more rows?
6. Are JDBC parameter indices 0-based or 1-based?
7. Why do we use try-with-resources for Connection, PreparedStatement, and ResultSet?
8. What changed in AccountController when we switched from in-memory to JDBC?

---

## Day 12 Summary

| Step | What we built | Key Classes |
|------|--------------|-------------|
| JDBC concepts | Understood Driver → Connection → Statement → ResultSet | `java.sql.*` |
| DatabaseManager | Connection helper + table initialization | `DriverManager`, `Connection` |
| AccountRepository | CRUD operations with PreparedStatement | `PreparedStatement`, `ResultSet` |
| Updated AccountService | Delegates to repository instead of in-memory storage | Same interface, new implementation |
| Security | PreparedStatement prevents SQL injection | `?` placeholders |

### Architecture with JDBC

```
Browser (HTML/JS)  →  REST Controller  →  Service  →  Repository  →  Database
                      @RestController     @Service     JDBC calls     H2/MySQL
                      (unchanged)         (minor changes)  (new)      (new)
```

> **Preview for Day 13**: "Our full-stack banking app is complete — front-end, REST API, and database. Tomorrow we shift gears to **DevOps** — how do we version control this code, build it automatically, and deploy it? We'll learn Git, CI/CD, and Docker."
