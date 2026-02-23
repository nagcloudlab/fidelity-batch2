# Day 14 — SQL and Networks Assessment

## Assessment Paper

> **Duration**: ~4 hours
> **Total Marks**: 120
> **Coverage**: Days 11–13 (Java + HTML Integration / HTTP & Networks, SQL Fundamentals, JDBC Integration, Basics of DevOps)
> **Instructions**: Answer all questions. SQL sections must use valid SQL syntax. Java code must be compilable. DevOps sections must use valid command syntax.

---

## Section A: Multiple Choice (20 marks)

*Choose the single best answer. 1 mark each.*

---

**Q1.** What does `fetch()` return in JavaScript?

a) A JSON object
b) A Promise
c) An HTML element
d) A string

---

**Q2.** What must you call on a `fetch()` response to extract the JSON body?

a) `response.text()`
b) `response.json()`
c) `response.body()`
d) `response.getData()`

---

**Q3.** What does `event.preventDefault()` do when called inside a form's submit handler?

a) Prevents the JavaScript from running
b) Prevents the browser from sending the form via its default HTML action (page refresh)
c) Prevents CSS from loading
d) Prevents the server from responding

---

**Q4.** Why does the browser block a `fetch()` from `http://localhost:63342` to `http://localhost:8080`?

a) The server is down
b) The JavaScript has a syntax error
c) Same-origin policy — the ports are different, so it's a cross-origin request
d) JSON is not supported across servers

---

**Q5.** What Spring Boot annotation fixes CORS errors?

a) `@RequestBody`
b) `@CrossOrigin`
c) `@PathVariable`
d) `@ResponseBody`

---

**Q6.** In a relational database, what uniquely identifies each row in a table?

a) Foreign key
b) Index
c) Primary key
d) Column name

---

**Q7.** What does a FOREIGN KEY do?

a) Encrypts the column data
b) Creates a reference from one table to another table's primary key
c) Makes a column unique
d) Prevents null values

---

**Q8.** Which SQL statement retrieves data from a table?

a) INSERT
b) UPDATE
c) DELETE
d) SELECT

---

**Q9.** What does this SQL return? `SELECT COUNT(*) FROM accounts WHERE account_type = 'SAVINGS';`

a) All savings accounts with full details
b) The number of savings accounts
c) The total balance of savings accounts
d) The account numbers of savings accounts

---

**Q10.** Which SQL clause sorts results?

a) GROUP BY
b) WHERE
c) ORDER BY
d) HAVING

---

**Q11.** What does JDBC stand for?

a) Java Data Base Connector
b) Java Database Connectivity
c) Java Direct Bridge Connection
d) Java Data Binding Component

---

**Q12.** Which JDBC class represents an active session with the database?

a) `Driver`
b) `Connection`
c) `ResultSet`
d) `PreparedStatement`

---

**Q13.** What is the correct way to set a parameter in a PreparedStatement?

a) `ps.setString(0, "Ravi");` — 0-based index
b) `ps.setString(1, "Ravi");` — 1-based index
c) `ps.setString("name", "Ravi");` — by column name
d) `ps.set(1, "Ravi");` — generic setter

---

**Q14.** Which method do you call on a PreparedStatement for an INSERT query?

a) `executeQuery()`
b) `executeUpdate()`
c) `executeInsert()`
d) `execute()`

---

**Q15.** What is the main advantage of PreparedStatement over Statement?

a) It runs faster for one-time queries
b) It prevents SQL injection by separating SQL from data
c) It doesn't require a Connection
d) It automatically creates tables

---

**Q16.** What does DevOps combine?

a) Design and Operations
b) Development and Operations
c) Database and Optimization
d) Deployment and Orchestration

---

**Q17.** In Git, what does `git add .` do?

a) Creates a new repository
b) Pushes changes to GitHub
c) Stages all modified and new files for the next commit
d) Commits all changes

---

**Q18.** What is the correct order of Git operations to save and share changes?

a) commit → add → push
b) push → add → commit
c) add → commit → push
d) add → push → commit

---

**Q19.** In a Dockerfile, what does the `FROM` instruction do?

a) Copies files into the container
b) Specifies the base image to build upon
c) Exposes a port
d) Runs a command inside the container

---

**Q20.** What is the difference between a Docker image and a container?

a) They are the same thing
b) An image is a running instance; a container is a blueprint
c) An image is a blueprint (read-only); a container is a running instance of that image
d) An image is for Linux only; a container is cross-platform

---

## Section B: Short Answer (30 marks)

*Answer in 2–4 sentences. 3 marks each.*

---

**Q1.** Explain the role of JavaScript `fetch()` in connecting the banking app's HTML pages to the Spring Boot REST API. What three things does the JavaScript code do?

**Q2.** What is CORS (Cross-Origin Resource Sharing)? Why did we need to add `@CrossOrigin` to our banking app's controller?

**Q3.** What is the difference between a PRIMARY KEY and a FOREIGN KEY? Give an example from the banking app's database tables.

**Q4.** Write the SQL query to find all accounts with a balance greater than 50,000, sorted by balance in descending order.

**Q5.** Explain the difference between `executeQuery()` and `executeUpdate()` in JDBC. When do you use each?

**Q6.** Why is PreparedStatement preferred over Statement? What security vulnerability does Statement have?

**Q7.** What is the purpose of `try-with-resources` when using JDBC Connection and PreparedStatement?

**Q8.** Explain the DevOps lifecycle. Name at least 5 of the 8 phases in order.

**Q9.** What is the difference between `git commit` and `git push`? Where do the changes go in each case?

**Q10.** Explain the difference between a Docker image and a Docker container. How do you create each?

---

## Section C: Code Reading & Debugging (25 marks)

*Read the code carefully and answer the questions. 5 marks each.*

---

### Q1. What does this JavaScript code do? Identify ONE bug.

```javascript
document.getElementById("createForm").addEventListener("submit", function(event) {
    const account = {
        accountNumber: document.getElementById("accNo").value,
        holderName: document.getElementById("name").value,
        balance: document.getElementById("deposit").value,
        accountType: document.querySelector('input[name="type"]:checked').value
    };

    fetch("http://localhost:8080/api/accounts", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(account)
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("result").textContent = "Account created: " + data.holderName;
    });
});
```

---

### Q2. Write the SQL output for these queries.

Given this `accounts` table:

| account_number | holder_name | account_type | balance |
|---------------|-------------|-------------|---------|
| 1001 | Ravi Kumar | SAVINGS | 50000 |
| 1002 | Priya Shah | CURRENT | 120000 |
| 1003 | Amit Verma | SAVINGS | 30000 |
| 1004 | Neha Gupta | SAVINGS | 75000 |
| 1005 | Karan Patel | CURRENT | 45000 |

**Query A:**
```sql
SELECT account_type, COUNT(*) AS total, AVG(balance) AS avg_bal
FROM accounts
GROUP BY account_type;
```

**Query B:**
```sql
SELECT holder_name, balance FROM accounts
WHERE balance > 40000
ORDER BY balance DESC;
```

---

### Q3. Find and fix TWO bugs in this JDBC code:

```java
public Account findByAccountNumber(long accountNumber) {
    String sql = "SELECT * FROM accounts WHERE account_number = " + accountNumber;
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Account acc = new Account();
            acc.setAccountNumber(rs.getLong("account_number"));
            acc.setHolderName(rs.getString("holder_name"));
            acc.setBalance(rs.getDouble("balance"));
            return acc;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
```

---

### Q4. What Git commands would you use for each scenario?

Match each scenario to the correct command(s):

| Scenario | Command(s)? |
|----------|------------|
| Create a new feature branch called `add-login` | ___ |
| Stage all modified files | ___ |
| Save staged changes with a message | ___ |
| Upload local commits to GitHub | ___ |
| Download latest changes from GitHub and merge | ___ |

Choose from: `git add .`, `git commit -m "message"`, `git push`, `git pull`, `git checkout -b add-login`

---

### Q5. Find TWO errors in this Dockerfile:

```dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/banking-app.jar /app/app.jar

EXPOSE 8080

RUN java -jar app.jar
```

---

## Section D: Practical Assignments (45 marks)

*Write complete, working code. You may use an IDE, SQL console, and terminal.*

---

### D1. SQL Queries — Banking Database (15 marks)

Given the following two tables have been created and populated:

```sql
CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    holder_name VARCHAR(100) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DOUBLE DEFAULT 0,
    created_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number BIGINT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
```

**Sample data:**

```sql
INSERT INTO accounts VALUES (1001, 'Ravi Kumar', 'SAVINGS', 50000, '2026-01-15');
INSERT INTO accounts VALUES (1002, 'Priya Shah', 'CURRENT', 120000, '2026-01-20');
INSERT INTO accounts VALUES (1003, 'Amit Verma', 'SAVINGS', 30000, '2026-02-01');
INSERT INTO accounts VALUES (1004, 'Neha Gupta', 'SAVINGS', 75000, '2026-02-05');
INSERT INTO accounts VALUES (1005, 'Karan Patel', 'CURRENT', 45000, '2026-02-10');

INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1001, 'DEPOSIT', 10000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1001, 'WITHDRAW', 5000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1002, 'DEPOSIT', 20000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1003, 'WITHDRAW', 3000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1004, 'DEPOSIT', 15000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1001, 'DEPOSIT', 7000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1005, 'WITHDRAW', 10000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1002, 'WITHDRAW', 8000);
```

**Write SQL queries for each requirement:**

1. **(2 marks)** List all savings accounts sorted by balance (highest first).

2. **(2 marks)** Find the total balance across all accounts and the average balance per account type.

3. **(3 marks)** List all transactions for account 1001 with the holder's name (requires JOIN).

4. **(3 marks)** Find each account's total deposit amount and total withdrawal amount (requires GROUP BY with CASE or multiple queries).

5. **(2 marks)** Find accounts that have never made a withdrawal.

6. **(3 marks)** Create a view or query that shows a summary: account number, holder name, number of transactions, and total transaction amount for each account.

---

### D2. JDBC — Transaction Repository (15 marks)

Build a `TransactionRepository` class that manages transactions in the database using JDBC.

**Requirements:**

1. Create a `Transaction` model class with: `transactionId` (int), `accountNumber` (long), `transactionType` (String), `amount` (double), `transactionDate` (String). Include default constructor, parameterized constructor, getters, setters, and `toString()`.

2. Create `TransactionRepository` with these methods:
   - `insert(Transaction t)` — inserts a new transaction using PreparedStatement
   - `findByAccountNumber(long accountNumber)` — returns `List<Transaction>` for an account
   - `findAll()` — returns all transactions
   - `getTotalByType(long accountNumber, String type)` — returns the sum of amounts for a given account and transaction type (DEPOSIT or WITHDRAW)
   - `deleteByAccountNumber(long accountNumber)` — deletes all transactions for an account

3. All methods must:
   - Use `PreparedStatement` (not Statement)
   - Use `try-with-resources` for all database resources
   - Handle `SQLException` properly

4. Write a `main` method that:
   - Inserts 3 test transactions
   - Retrieves and prints transactions for one account
   - Prints total deposits for that account
   - Deletes transactions for an account
   - Prints remaining transactions

**Grading rubric:**
- Transaction model class: 2 marks
- insert() with PreparedStatement: 2 marks
- findByAccountNumber() with parameter binding: 3 marks
- findAll() correctly mapping ResultSet to List: 2 marks
- getTotalByType() with SUM query: 3 marks
- deleteByAccountNumber(): 1 mark
- try-with-resources throughout: 1 mark
- Main method demonstrating all operations: 1 mark

---

### D3. DevOps — Containerize the Banking App (15 marks)

Perform the following DevOps tasks for the banking application:

**Part A: Git Workflow (5 marks)**

Write the exact Git commands for the following workflow:

1. Initialize a new Git repository
2. Create a `.gitignore` file that excludes: `target/`, `.idea/`, `*.class`, `.DS_Store`
3. Stage all files
4. Create an initial commit
5. Create a new branch called `feature/transaction-history`
6. Switch to that branch
7. (Assume you make changes here) Stage and commit the changes with the message "Add transaction history endpoint"
8. Switch back to main
9. Merge the feature branch into main

**Part B: Dockerfile (5 marks)**

Write a complete Dockerfile for the banking Spring Boot application that:
- Uses `eclipse-temurin:17-jdk-alpine` as the base image
- Sets the working directory to `/app`
- Copies the JAR file from `target/banking-app-1.0.jar`
- Exposes port 8080
- Runs the application when the container starts

Then write the Docker commands to:
- Build the image with tag `simple-bank:1.0`
- Run a container named `bank-app` mapping port 8080
- View the running container's logs
- Stop and remove the container

**Part C: CI Pipeline (5 marks)**

Write a GitHub Actions workflow file (`.github/workflows/ci.yml`) that:
- Triggers on push to `main` and on pull requests to `main`
- Runs on `ubuntu-latest`
- Has these steps:
  1. Checkout the code
  2. Set up Java 17
  3. Compile the project with Maven
  4. Run tests with Maven
  5. Build the JAR package with Maven

---

## Bonus Questions (optional, 10 extra marks)

*For trainees who finish early.*

### Bonus 1 (5 marks): Full-Stack Data Flow Trace

Trace the complete journey of a "Create Account" request from the browser to the database and back. For each step, state:
- **Where** (browser / network / controller / service / repository / database)
- **What happens**
- **What data format** is used (HTML form / JSON / Java object / SQL / ResultSet)

Start from: "User fills in the form and clicks Submit"
End at: "User sees success message on the page"

---

### Bonus 2 (5 marks): Advanced SQL — Bank Report

Write a single SQL query that produces this report:

```
┌─────────────────────────────────────────────────────────────────┐
│ holder_name  │ type    │ balance │ txn_count │ net_flow │ status│
├─────────────────────────────────────────────────────────────────┤
│ Priya Shah   │ CURRENT │ 120000  │ 2         │ 12000    │ HIGH  │
│ Neha Gupta   │ SAVINGS │ 75000   │ 1         │ 15000    │ HIGH  │
│ Ravi Kumar   │ SAVINGS │ 50000   │ 3         │ 12000    │ MEDIUM│
│ Karan Patel  │ CURRENT │ 45000   │ 1         │ -10000   │ MEDIUM│
│ Amit Verma   │ SAVINGS │ 30000   │ 1         │ -3000    │ LOW   │
└─────────────────────────────────────────────────────────────────┘
```

Where:
- `txn_count` = total number of transactions for the account
- `net_flow` = SUM of deposits - SUM of withdrawals (deposits are positive, withdrawals are negative)
- `status` = 'HIGH' if balance >= 60000, 'MEDIUM' if balance >= 40000, 'LOW' otherwise
- Sorted by balance descending
