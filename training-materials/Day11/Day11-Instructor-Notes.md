# Day 11 — Java + HTML Integration & SQL Fundamentals

## Instructor Guide

> **Duration**: Full day (~6 hrs)
> **Pre-requisite**: Day 8 REST API + Day 9 HTML/CSS. No JavaScript or SQL experience needed.
> **Case Study**:
> - **Part A**: Connect the banking HTML pages to the Spring Boot REST API using JavaScript fetch()
> - **Part B**: Design SQL tables for the banking app and learn CRUD operations
> **Goal by end of day**: Trainees can connect front-end forms to a REST back-end, and write basic SQL queries

---

## Day Structure

| Part | Topic | Duration | Key Concepts |
|------|-------|----------|-------------|
| A | Java + HTML Integration | ~3 hrs | End-to-end flow, fetch API, form submission, dynamic tables |
| B | SQL Fundamentals | ~3 hrs | RDBMS, tables, keys, relationships, CRUD queries |

---

# Part A: Java + HTML Integration

---

## Topic 1: End-to-End Data Flow

#### Key Points (15 min)

- On Day 9 we built static HTML pages. On Day 8 we built a REST API. Today we **connect them**.
- The bridge is **JavaScript** — specifically the `fetch()` API.

```
User fills form      JavaScript         Spring Boot         Response
─────────────── → ──────────── → ──────────────────── → ──────────────
HTML <form>        fetch() sends     @RestController       JSON data
input fields       HTTP request      @PostMapping          status code
submit button      with JSON body    AccountService        ↓
                                     processes             JavaScript
                                                           updates page
```

#### The complete flow (walk through on board):

```
1. User fills in "Holder Name: Ravi, Balance: 50000" and clicks "Create Account"
2. JavaScript intercepts the form submit (prevents default page reload)
3. JavaScript collects form data and sends:
     POST /api/accounts
     Content-Type: application/json
     {"accountNumber":1004,"holderName":"Ravi","balance":50000,"type":"SAVINGS"}
4. Spring Boot receives → AccountController.createAccount(@RequestBody Account)
5. AccountService.addAccount() validates and stores
6. Returns: 201 Created + {"accountNumber":1004,"holderName":"Ravi",...}
7. JavaScript receives the JSON response
8. JavaScript updates the page (show success message, redirect to accounts list)
```

> **Teaching moment**: "The HTML form doesn't talk to Spring Boot directly. JavaScript is the messenger — it converts form data to JSON, sends the HTTP request, receives the response, and updates the page. Without JavaScript, the form would try to do a traditional page reload."

---

## Topic 2: JavaScript Basics (Just Enough)

#### Key Points (20 min)

> We're NOT teaching JavaScript in depth — just the minimum needed to connect forms to API.

#### Where JavaScript runs

```html
<!-- Option 1: Inline script (for demos) -->
<script>
    console.log("Hello from JavaScript!");
</script>

<!-- Option 2: External file (BEST) -->
<script src="app.js"></script>

<!-- IMPORTANT: Place <script> at the end of <body> -->
```

#### Essential JavaScript for today

```javascript
// 1. Get an element from the page
const form = document.getElementById("createForm");
const nameInput = document.getElementById("name");

// 2. Read input values
const name = nameInput.value;

// 3. Listen for events
form.addEventListener("submit", function(event) {
    event.preventDefault();    // stop the form from reloading the page
    // ... do something ...
});

// 4. Update the page
document.getElementById("message").textContent = "Account created!";
document.getElementById("message").style.color = "green";
```

> "Think of JavaScript as the **wiring** in a building. HTML is the walls and rooms, CSS is the paint, and JavaScript connects the light switches (buttons) to the lights (API calls, page updates)."

---

## Topic 3: The fetch() API

#### Key Points (25 min)

> **This is the core topic** — how JavaScript talks to our REST API.

#### GET request (read data)

```javascript
// Fetch all accounts from the API
fetch("http://localhost:8080/api/accounts")
    .then(response => response.json())      // parse JSON
    .then(data => {
        console.log(data);                  // array of account objects
    })
    .catch(error => {
        console.error("Error:", error);
    });
```

#### POST request (create data)

```javascript
// Create a new account
fetch("http://localhost:8080/api/accounts", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        accountNumber: 1004,
        holderName: "Sneha Reddy",
        balance: 100000,
        type: "CURRENT"
    })
})
.then(response => {
    if (response.status === 201) {
        return response.json();
    } else {
        throw new Error("Failed to create account");
    }
})
.then(account => {
    console.log("Created:", account);
})
.catch(error => {
    console.error("Error:", error);
});
```

#### DELETE request

```javascript
fetch("http://localhost:8080/api/accounts/1001", {
    method: "DELETE"
})
.then(response => {
    if (response.status === 204) {
        console.log("Deleted successfully");
    }
});
```

#### Teaching Tip

> Draw the parallel to Postman/curl from Day 8:
>
> | Postman | JavaScript |
> |---------|-----------|
> | Enter URL | `fetch(url)` |
> | Choose method | `method: "POST"` |
> | Set Content-Type header | `headers: {"Content-Type": "application/json"}` |
> | Body tab (raw JSON) | `body: JSON.stringify({...})` |
> | Send button | The `.then()` chain |
> | Response pane | The `data` in `.then(data => ...)` |

---

## Topic 4: CORS — Quick Fix

#### Key Points (5 min)

> Trainees WILL hit this error. Address it proactively.

When the HTML page (`file:///...` or `localhost:3000`) calls the API (`localhost:8080`), the browser blocks it with a **CORS error**:

```
Access to fetch at 'http://localhost:8080/api/accounts' from origin 'null' has been blocked by CORS policy
```

**Quick fix** — add `@CrossOrigin` to the controller:

```java
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")    // Allow all origins (dev only!)
public class AccountController {
    // ...
}
```

> "In production, you'd restrict origins. For learning, `*` is fine."

---

## Topic 5: Form Submission Flow

#### Key Points (30 min)

> **Build this live** — connect the create-account form to the API.

#### Traditional form submission (DON'T want this)

```html
<form action="/api/accounts" method="POST">
```

This causes a **full page reload** and tries to display raw JSON. Not a good user experience.

#### Modern approach: JavaScript intercepts the form

```html
<form id="createForm">
    <!-- inputs here -->
    <button type="submit">Create Account</button>
</form>

<div id="message"></div>

<script src="app.js"></script>
```

#### Case Study Step 1 — app.js for create account

```javascript
const API_BASE = "http://localhost:8080/api/accounts";

document.getElementById("createForm").addEventListener("submit", function(event) {
    event.preventDefault();    // CRITICAL: stop page reload

    // Collect form data
    const account = {
        accountNumber: parseInt(document.getElementById("accNo").value),
        holderName: document.getElementById("name").value,
        balance: parseFloat(document.getElementById("balance").value),
        type: document.querySelector('input[name="type"]:checked').value
    };

    // Send to API
    fetch(API_BASE, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(account)
    })
    .then(response => {
        if (response.status === 201) {
            return response.json();
        } else if (response.status === 409) {
            throw new Error("Account number already exists!");
        } else {
            throw new Error("Failed to create account");
        }
    })
    .then(created => {
        showMessage("Account #" + created.accountNumber + " created successfully!", "green");
        document.getElementById("createForm").reset();
    })
    .catch(error => {
        showMessage(error.message, "red");
    });
});

function showMessage(text, color) {
    const msg = document.getElementById("message");
    msg.textContent = text;
    msg.style.color = color;
}
```

> **Live demo**: Start the Spring Boot app → open create-account.html → fill form → click Create → see success message. Then try creating a duplicate → see error message.

---

## Topic 6: Dynamic Data — Loading Accounts into a Table

#### Key Points (25 min)

> Replace the hardcoded table rows with live data from the API.

#### Case Study Step 2 — accounts.js (loads accounts into table)

```javascript
const API_BASE = "http://localhost:8080/api/accounts";

// Load accounts when page opens
window.addEventListener("DOMContentLoaded", loadAccounts);

function loadAccounts() {
    fetch(API_BASE)
        .then(response => response.json())
        .then(accounts => {
            const tbody = document.getElementById("accountsBody");
            tbody.innerHTML = "";    // clear existing rows

            accounts.forEach(acc => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${acc.accountNumber}</td>
                    <td>${acc.holderName}</td>
                    <td>${acc.type}</td>
                    <td>${acc.balance.toLocaleString("en-IN", {minimumFractionDigits: 2})}</td>
                    <td><span class="badge badge-${acc.active ? 'active' : 'inactive'}">
                        ${acc.active ? 'Active' : 'Inactive'}</span></td>
                    <td>
                        <button class="btn btn-danger btn-sm"
                                onclick="deleteAccount(${acc.accountNumber})">Delete</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error loading accounts:", error);
        });
}

function deleteAccount(accNo) {
    if (!confirm("Delete account #" + accNo + "?")) return;

    fetch(API_BASE + "/" + accNo, { method: "DELETE" })
        .then(response => {
            if (response.status === 204) {
                loadAccounts();    // refresh the table
            }
        });
}
```

#### Updated accounts.html (add id to tbody, link script)

```html
<tbody id="accountsBody">
    <!-- Rows populated by JavaScript -->
</tbody>
<!-- ... at end of body: -->
<script src="accounts.js"></script>
```

> **Teaching moment**: "The table is now LIVE. Create an account on the form page, go back to accounts, and see it appear. Delete one, and it disappears. No page reload needed."

---

## Topic 7: Mapping Form Data to Java Objects

#### Key Points (15 min)

> Connect this back to the Spring Boot side.

#### The mapping chain

```
HTML form field          JavaScript JSON key        Java field
─────────────           ──────────────────         ──────────
name="holderName"   →   "holderName": "Ravi"   →   account.setHolderName("Ravi")
name="accountNumber" →  "accountNumber": 1001  →   account.setAccountNumber(1001)
name="balance"       →  "balance": 50000       →   account.setBalance(50000)
name="type"          →  "type": "SAVINGS"      →   account.setType("SAVINGS")
```

- HTML `name` attribute → JavaScript reads `element.value` → builds JSON object → `JSON.stringify()`
- Spring receives JSON → Jackson calls setters → Java object is ready
- The `name` attribute in HTML should match the JSON key which should match the Java field name

#### DTO concept (brief mention)

> In real apps, you'd use a **DTO (Data Transfer Object)** — a separate class just for receiving API data, different from the internal model. For our training, the model class serves both purposes.

---

## Topic 8: Separation of Layers

#### Key Points (10 min)

> Reinforce the architecture they've been building across days.

```
┌─────────────────────────────────────────────────────────────┐
│ PRESENTATION LAYER (Browser)                                 │
│   HTML    — structure                                        │
│   CSS     — styling                                          │
│   JS      — behavior, API calls                              │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTP (JSON)
┌──────────────────────────▼──────────────────────────────────┐
│ CONTROLLER LAYER        @RestController                      │
│   Receives HTTP requests, returns HTTP responses             │
│   AccountController.java                                     │
└──────────────────────────┬──────────────────────────────────┘
                           │ Java method calls
┌──────────────────────────▼──────────────────────────────────┐
│ SERVICE LAYER           @Service                             │
│   Business logic, validation, calculations                   │
│   AccountService.java                                        │
└──────────────────────────┬──────────────────────────────────┘
                           │ Data access
┌──────────────────────────▼──────────────────────────────────┐
│ DATA LAYER              (Today: in-memory, Day 12: database) │
│   ArrayList + HashMap (current)                              │
│   JDBC + SQL database (coming)                               │
└─────────────────────────────────────────────────────────────┘
```

> "Each layer has ONE job. If you want to change how data is stored (files → database), you only change the data layer. The controller and front-end don't care."

---

# Part B: SQL Fundamentals

---

## Topic 9: What is a Database?

#### Key Points (15 min)

- A **database** is an organized collection of data stored on disk
- **RDBMS** = Relational Database Management System — stores data in **tables** with **rows** and **columns**
- Examples: MySQL, PostgreSQL, Oracle, SQLite, H2

#### Why databases? (vs files)

| | Files (Day 7) | Database |
|---|---|---|
| Query data | Load entire file, loop through | `SELECT * FROM accounts WHERE balance > 50000` |
| Multiple users | Risky — file locking issues | Built-in concurrency |
| Relationships | Manual — track IDs yourself | Foreign keys, JOINs |
| Data integrity | You must enforce rules in code | Constraints (NOT NULL, UNIQUE, CHECK) |
| Scalability | Slow for large data | Optimized with indexes |

> "Files are like a notebook. Databases are like a filing cabinet with labeled drawers, folders, and cross-references."

#### For training: H2 Database

We'll use **H2** — an in-memory Java database. No installation needed; it runs inside your Spring Boot app.

Add to `pom.xml`:
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Add to `application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:bankdb
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Access H2 console at: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:bankdb`)

---

## Topic 10: Tables, Rows, and Columns

#### Key Points (15 min)

```
TABLE: accounts
┌─────────────────┬────────────────┬──────────┬──────────┬────────┐
│ account_number   │ holder_name    │ balance  │ type     │ active │
│ (BIGINT, PK)     │ (VARCHAR)      │ (DOUBLE) │ (VARCHAR)│ (BOOL) │
├─────────────────┼────────────────┼──────────┼──────────┼────────┤
│ 1001             │ Ravi Kumar     │ 50000.00 │ SAVINGS  │ true   │
│ 1002             │ Priya Sharma   │ 30000.00 │ CURRENT  │ true   │
│ 1003             │ Amit Verma     │ 75000.00 │ SAVINGS  │ true   │
└─────────────────┴────────────────┴──────────┴──────────┴────────┘
```

| Term | Meaning | Banking Example |
|------|---------|----------------|
| **Table** | A collection of related data | `accounts`, `transactions` |
| **Row** (record) | One entry | One account (Ravi, 1001, 50000) |
| **Column** (field) | A property/attribute | `holder_name`, `balance` |
| **Data type** | Kind of data a column holds | BIGINT, VARCHAR, DOUBLE, BOOLEAN |

#### Common SQL data types

| SQL Type | Java Equivalent | Use for |
|----------|----------------|---------|
| `INT` / `BIGINT` | `int` / `long` | Account number, IDs |
| `VARCHAR(100)` | `String` | Names, descriptions |
| `DOUBLE` / `DECIMAL` | `double` | Balance, amounts |
| `BOOLEAN` | `boolean` | Active/inactive flags |
| `TIMESTAMP` | `LocalDateTime` | Transaction dates |

---

## Topic 11: Primary Key and Foreign Key

#### Key Points (15 min)

#### Primary Key (PK)

- Uniquely identifies each row in a table
- Cannot be NULL, must be unique
- Banking app: `account_number` is the PK of the `accounts` table

```sql
CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    holder_name VARCHAR(100) NOT NULL,
    balance DOUBLE DEFAULT 0,
    type VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);
```

#### Foreign Key (FK)

- Links a row in one table to a row in another table
- Enforces **referential integrity** — can't create a transaction for a non-existent account
- Banking app: `account_number` in `transactions` is an FK pointing to `accounts`

```sql
CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    account_number BIGINT NOT NULL,
    balance_before DOUBLE,
    balance_after DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
```

#### Relationship diagram

```
accounts (1) ────────< (many) transactions
─────────                ──────────────
account_number (PK)      id (PK)
holder_name              type
balance                  amount
type                     account_number (FK) → accounts.account_number
active                   balance_before
                         balance_after
                         timestamp
```

> **Teaching moment**: "One account can have MANY transactions. That's a one-to-many relationship. The FK in the transactions table points back to the PK in the accounts table. The database ensures you can't create a transaction for account #9999 if that account doesn't exist."

---

## Topic 12: CRUD Operations in SQL

#### Key Points (40 min)

> **Live demo in H2 console** — have trainees type along.

#### CREATE table

```sql
CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    holder_name VARCHAR(100) NOT NULL,
    balance DOUBLE DEFAULT 0,
    type VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    account_number BIGINT NOT NULL,
    balance_before DOUBLE,
    balance_after DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
```

#### INSERT (Create)

```sql
-- Insert single account
INSERT INTO accounts (account_number, holder_name, balance, type)
VALUES (1001, 'Ravi Kumar', 50000, 'SAVINGS');

-- Insert multiple accounts
INSERT INTO accounts (account_number, holder_name, balance, type) VALUES
(1002, 'Priya Sharma', 30000, 'CURRENT'),
(1003, 'Amit Verma', 75000, 'SAVINGS'),
(1004, 'Sneha Reddy', 120000, 'CURRENT');

-- Insert transactions
INSERT INTO transactions (type, amount, account_number, balance_before, balance_after) VALUES
('DEPOSIT', 5000, 1001, 50000, 55000),
('WITHDRAW', 2000, 1001, 55000, 53000),
('DEPOSIT', 10000, 1002, 30000, 40000);
```

#### SELECT (Read)

```sql
-- All accounts
SELECT * FROM accounts;

-- Specific columns
SELECT account_number, holder_name, balance FROM accounts;

-- Filter with WHERE
SELECT * FROM accounts WHERE type = 'SAVINGS';
SELECT * FROM accounts WHERE balance > 50000;
SELECT * FROM accounts WHERE holder_name LIKE '%Kumar%';

-- Sort
SELECT * FROM accounts ORDER BY balance DESC;

-- Multiple conditions
SELECT * FROM accounts WHERE type = 'SAVINGS' AND balance > 40000;
SELECT * FROM accounts WHERE type = 'SAVINGS' OR balance > 100000;

-- Count and aggregates
SELECT COUNT(*) FROM accounts;
SELECT SUM(balance) FROM accounts;
SELECT AVG(balance) FROM accounts;
SELECT type, COUNT(*) AS count FROM accounts GROUP BY type;

-- Transactions for a specific account
SELECT * FROM transactions WHERE account_number = 1001 ORDER BY timestamp DESC;
```

#### UPDATE (Modify)

```sql
-- Update balance after deposit
UPDATE accounts SET balance = 55000 WHERE account_number = 1001;

-- Deactivate an account
UPDATE accounts SET active = FALSE WHERE account_number = 1003;

-- Update multiple columns
UPDATE accounts SET holder_name = 'Ravi K.', type = 'CURRENT'
WHERE account_number = 1001;
```

> **Warning**: "Always use WHERE with UPDATE! Without it, ALL rows get updated." Demo: `UPDATE accounts SET active = FALSE;` — oops, everyone is deactivated!

#### DELETE (Remove)

```sql
-- Delete specific account
DELETE FROM accounts WHERE account_number = 1004;

-- Delete all inactive accounts
DELETE FROM accounts WHERE active = FALSE;
```

> **Warning**: "Same rule — always use WHERE with DELETE! `DELETE FROM accounts;` wipes everything."

#### CRUD ↔ REST mapping

| SQL | REST | HTTP | Example |
|-----|------|------|---------|
| `INSERT` | Create | `POST /api/accounts` | New account |
| `SELECT` | Read | `GET /api/accounts/1001` | View account |
| `UPDATE` | Update | `PUT /api/accounts/1001` | Modify account |
| `DELETE` | Delete | `DELETE /api/accounts/1001` | Remove account |

> "Notice how SQL CRUD maps directly to REST CRUD. The verbs are different but the concepts are identical. On Day 12, we'll connect them with JDBC."

---

## Day 11 Exercises

### Exercise 1: Transaction Form Integration
**Problem**: Connect the `transaction.html` form to the REST API. When the user selects "Deposit" and submits, call `POST /api/accounts/{accNo}/deposit?amount=X`. For "Withdraw", call the withdraw endpoint. Show success/error messages on the page.

### Exercise 2: Search Integration
**Problem**: Add a search page that calls `GET /api/accounts/search?name=X` and displays results in a table dynamically.

### Exercise 3: SQL — Account Queries
**Problem**: Write SQL queries for:
1. Find all savings accounts with balance > Rs.40,000
2. Find the total balance across all accounts
3. Find the account with the highest balance
4. Count how many accounts of each type exist
5. Find all transactions for account #1001, ordered by date (most recent first)

### Exercise 4: SQL — Transactions Table
**Problem**: Write SQL to:
1. Insert 3 transactions (2 deposits, 1 withdrawal) for account #1002
2. Find the total amount deposited across all accounts
3. Find the total amount withdrawn from account #1001
4. Find accounts that have no transactions (hint: use NOT IN or LEFT JOIN)

### Exercise 5: Full Integration Challenge
**Problem**: Build a complete dashboard page (`dashboard.html` + `dashboard.js`) that on load:
1. Fetches all accounts from the API
2. Shows total accounts count and total balance in summary cards
3. Shows a table of all accounts
4. Has a "Refresh" button that re-fetches data

---

## Day 11 Quiz (10 questions)

1. What does `event.preventDefault()` do in a form submit handler?
2. What is `fetch()` and what does it return?
3. Why do we need `JSON.stringify()` when sending data?
4. What is CORS and how did we fix it?
5. What does RDBMS stand for?
6. What is a primary key? Can it be null?
7. What is a foreign key? What does it enforce?
8. Write a SQL query to find all accounts with balance > 50000.
9. What's the danger of running `UPDATE accounts SET active = FALSE;` without a WHERE clause?
10. How does SQL CRUD map to REST HTTP methods?

---

## Day 11 Summary

### Part A — Integration

| Step | What we built | Key Concepts |
|------|--------------|-------------|
| End-to-end flow | Understood HTML → JS → API → DB pipeline | Full-stack architecture |
| fetch() GET | Load accounts from API into table | Dynamic page content |
| fetch() POST | Submit create form to API | JSON.stringify, Content-Type |
| fetch() DELETE | Delete account from table | Status code checking |
| CORS fix | `@CrossOrigin` on controller | Browser security policy |
| Form interception | `event.preventDefault()` + fetch | Modern web pattern |

### Part B — SQL

| Step | What we learned | Key Concepts |
|------|--------------|-------------|
| Database basics | Why databases over files | RDBMS, tables, rows, columns |
| Schema design | `accounts` and `transactions` tables | Data types, constraints |
| Primary key | Unique row identifier | NOT NULL, UNIQUE |
| Foreign key | Links between tables | Referential integrity, one-to-many |
| INSERT | Add data | Single and multi-row inserts |
| SELECT | Query data | WHERE, ORDER BY, LIKE, COUNT, SUM, GROUP BY |
| UPDATE | Modify data | Always use WHERE! |
| DELETE | Remove data | Always use WHERE! |

### Full-stack architecture so far

```
Day 1-5:   Java console app (logic)
Day 7:     + File persistence
Day 8:     + REST API (Spring Boot)
Day 9:     + HTML/CSS front-end (static)
Day 11:    + JavaScript integration (dynamic)
           + SQL database schema
Day 12:    → JDBC connects Java to the database
```

> **Preview for Day 12**: "We've designed our SQL tables and we know SQL queries. Tomorrow we use **JDBC** to run those queries from Java code — replacing in-memory storage with a real database."
