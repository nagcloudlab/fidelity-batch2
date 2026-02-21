# Day 8 — REST API & Spring Boot

## Student Handout

> **What you'll build today**: Transform your console-based BankApp into a REST API that any client (browser, Postman, mobile app) can access over HTTP. You'll learn REST architecture, HTTP methods, Spring Boot, and how to build CRUD endpoints.

---

## 1. From Console App to Web API

**The problem**: Our BankApp works great — but only one person can use it, from a single terminal, on one machine.

| | Console App (Days 1-7) | REST API (Day 8) |
|---|---|---|
| User interface | Terminal menu | HTTP endpoints |
| Client | Single user at keyboard | Any HTTP client (browser, Postman, mobile app) |
| Data format | Text output | JSON |
| Access | Local only | Network accessible |
| Multiple users | No | Yes (stateless) |

Today we expose our banking operations as **HTTP endpoints** that any client can call.

---

## 2. REST Architecture

### What is REST?

**REST** = **RE**presentational **S**tate **T**ransfer — an architectural style for building web APIs.

Key ideas:

1. **Everything is a resource** — Account, Transaction, Customer are all resources
2. **Each resource has a URI** — `/api/accounts/1001` identifies Account #1001
3. **Standard operations** — Use HTTP methods (GET, POST, PUT, DELETE) to act on resources
4. **Stateless** — Every request contains everything the server needs (no session memory)
5. **JSON** — Data is exchanged as JSON (lightweight, human-readable)

### REST vs SOAP

| | REST | SOAP |
|---|---|---|
| Format | JSON (lightweight) | XML (verbose) |
| Protocol | HTTP | Any (HTTP, SMTP, etc.) |
| Style | Architectural guidelines | Strict protocol/standard |
| Simplicity | Simple, flexible | Complex, rigid |
| Modern usage | Dominant for web APIs | Legacy/enterprise |

> 99% of modern web APIs are REST. That's what we'll build.

### Resources and URIs

Think of a URI as the "address" of a resource — like a phone number for a specific account.

| Resource | URI | Meaning |
|----------|-----|---------|
| All accounts | `GET /api/accounts` | List all accounts |
| Specific account | `GET /api/accounts/1001` | Get account #1001 |
| Create account | `POST /api/accounts` | Create a new account |
| Update account | `PUT /api/accounts/1001` | Update account #1001 |
| Delete account | `DELETE /api/accounts/1001` | Delete account #1001 |

### Idempotent vs Non-idempotent

- **Idempotent**: Calling it multiple times gives the same result. `GET`, `PUT`, `DELETE` are idempotent.
  - Example: `GET /api/accounts/1001` always returns the same account (until something changes it).
  - Example: `DELETE /api/accounts/1001` — first call deletes, subsequent calls return "not found". The end state is the same.
- **Non-idempotent**: Each call may produce a different result. `POST` is non-idempotent.
  - Example: `POST /api/accounts` creates a **new** account each time.

---

## 3. HTTP Essentials

### HTTP Methods

| Method | Purpose | Idempotent | Banking Example |
|--------|---------|-----------|----------------|
| `GET` | Read / retrieve | Yes | Get account details |
| `POST` | Create new | No | Create new account |
| `PUT` | Update (full replace) | Yes | Update entire account |
| `PATCH` | Update (partial) | Yes | Update just the name |
| `DELETE` | Remove | Yes | Close account |

### HTTP Status Codes

Status codes tell the client what happened. Think of them as the "result code" of the operation.

| Range | Category | Common Codes |
|-------|----------|-------------|
| 2xx | Success | `200 OK`, `201 Created`, `204 No Content` |
| 4xx | Client error | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| 5xx | Server error | `500 Internal Server Error` |

**Banking examples:**

| Code | When to use | Example |
|------|------------|---------|
| `200 OK` | Successful read/update | Account retrieved successfully |
| `201 Created` | Resource created | New account created |
| `204 No Content` | Success, nothing to return | Account deleted |
| `400 Bad Request` | Invalid input | Negative deposit amount |
| `404 Not Found` | Resource doesn't exist | Account #9999 not found |
| `409 Conflict` | Business rule violation | Insufficient balance |

### Request/Response Structure

Every HTTP interaction has a **request** (what the client sends) and a **response** (what the server returns).

```
Request:
┌─────────────────────────────────────┐
│ POST /api/accounts HTTP/1.1         │  ← method + URI
│ Content-Type: application/json      │  ← headers
│ Host: localhost:8080                │
│                                     │
│ {                                   │  ← body (JSON)
│   "holderName": "Ravi Kumar",       │
│   "accountNumber": 1001,            │
│   "balance": 50000,                 │
│   "type": "SAVINGS"                 │
│ }                                   │
└─────────────────────────────────────┘

Response:
┌─────────────────────────────────────┐
│ HTTP/1.1 201 Created                │  ← status code
│ Content-Type: application/json      │  ← headers
│                                     │
│ {                                   │  ← body (JSON)
│   "accountNumber": 1001,            │
│   "holderName": "Ravi Kumar",       │
│   "balance": 50000.0,               │
│   "type": "SAVINGS"                 │
│ }                                   │
└─────────────────────────────────────┘
```

### JSON

**JSON** (JavaScript Object Notation) is the standard data format for REST APIs:

```json
{
  "accountNumber": 1001,
  "holderName": "Ravi Kumar",
  "balance": 50000.00,
  "type": "SAVINGS",
  "active": true
}
```

- Key-value pairs enclosed in `{ }`
- Keys are strings (in double quotes)
- Values can be: string, number, boolean, null, array, or nested object
- Spring Boot automatically converts Java objects ↔ JSON (using the **Jackson** library)

---

## 4. Spring Boot Introduction

### What is Spring Boot?

**Spring Boot** is a framework that makes building Java web applications fast and easy.

**Why Spring Boot:**
- **Auto-configuration** — sensible defaults, minimal setup
- **Embedded server** (Tomcat) — no need to install a separate web server
- **Starter dependencies** — one dependency pulls in everything you need
- **Production-ready** — logging, health checks, metrics built-in

### Project Setup

#### Step 1: Generate the project

Go to [start.spring.io](https://start.spring.io) and configure:

| Setting | Value |
|---------|-------|
| Project | Maven |
| Language | Java |
| Spring Boot | 3.x (latest stable) |
| Group | `com.bank` |
| Artifact | `bank-api` |
| Packaging | Jar |
| Java | 17 |
| Dependencies | **Spring Web** |

Click **Generate** → download → extract → open in your IDE.

#### Step 2: Understand the project structure

```
bank-api/
├── src/
│   ├── main/
│   │   ├── java/com/bank/api/
│   │   │   ├── BankApiApplication.java     ← entry point (@SpringBootApplication)
│   │   │   ├── controller/
│   │   │   │   └── AccountController.java  ← REST endpoints
│   │   │   ├── model/
│   │   │   │   └── Account.java            ← data model
│   │   │   └── service/
│   │   │       └── AccountService.java     ← business logic
│   │   └── resources/
│   │       └── application.properties      ← config
│   └── test/
├── pom.xml                                  ← Maven dependencies
```

Three layers:
- **Model** — data classes (POJOs)
- **Service** — business logic (validation, calculations)
- **Controller** — HTTP endpoints (receives requests, calls service, returns responses)

#### Step 3: Your first endpoint

```java
package com.bank.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to Bank API!";
    }
}
```

**Run the application** → Open browser → `http://localhost:8080/hello` → See "Welcome to Bank API!"

One class, one annotation, one method — and you have a working web API. Spring Boot handles the server, routing, and HTTP. You focus on business logic.

---

## 5. Spring Boot Annotations

| Annotation | Purpose | Example |
|-----------|---------|---------|
| `@RestController` | Marks a class as a REST controller (combines `@Controller` + `@ResponseBody`) | `@RestController public class AccountController` |
| `@RequestMapping("/api/accounts")` | Base URL path for all endpoints in this controller | Applied at class level |
| `@GetMapping` | Handles GET requests | `@GetMapping` or `@GetMapping("/{id}")` |
| `@PostMapping` | Handles POST requests | `@PostMapping` |
| `@PutMapping` | Handles PUT requests | `@PutMapping("/{id}")` |
| `@DeleteMapping` | Handles DELETE requests | `@DeleteMapping("/{id}")` |
| `@PathVariable` | Extracts value from URL path | `/accounts/{id}` → `@PathVariable long id` |
| `@RequestParam` | Extracts query parameter | `/accounts?type=SAVINGS` → `@RequestParam String type` |
| `@RequestBody` | Converts JSON body to Java object | `@RequestBody Account account` |
| `@Service` | Marks a class as a service (business logic) | `@Service public class AccountService` |

### @PathVariable vs @RequestParam

```
URL: /api/accounts/1001
                    ^^^^
                    @PathVariable — part of the URL path
                    Used for: identifying a specific resource

URL: /api/accounts/search?name=kumar
                          ^^^^^^^^^^^
                          @RequestParam — after the ? mark
                          Used for: filtering, searching, optional parameters
```

---

## 6. Building the Banking API

### Step 1: Account Model (simplified for REST)

For REST APIs, we use a simpler flat model (not the abstract hierarchy from Day 2). The `type` field distinguishes account types. Jackson (the JSON library) needs a default constructor and getters/setters.

```java
package com.bank.api.model;

public class Account {
    private long accountNumber;
    private String holderName;
    private double balance;
    private String type;         // "SAVINGS" or "CURRENT"
    private boolean active;

    // Default constructor (required by Jackson for JSON deserialization)
    public Account() {}

    public Account(long accountNumber, String holderName, double balance, String type) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.type = type;
        this.active = true;
    }

    // Getters and setters (ALL needed for JSON conversion)
    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
```

**Why a default constructor?** When a client sends JSON like `{"holderName":"Ravi"}`, Jackson:
1. Creates an empty `Account` using the no-arg constructor
2. Calls `setHolderName("Ravi")` to populate the field
3. Any fields not in the JSON keep their default values

**Why getters and setters?** Jackson uses getter names to determine JSON field names:
- `getHolderName()` → JSON field `"holderName"`
- `isActive()` → JSON field `"active"` (boolean getters use `is` prefix)

### Step 2: AccountService (business logic)

The service handles all business logic — the controller just delegates to it.

```java
package com.bank.api.service;

import com.bank.api.model.Account;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<>();
    private Map<Long, Account> accountMap = new HashMap<>();

    public AccountService() {
        // Pre-load sample data
        addAccount(new Account(1001L, "Ravi Kumar", 50000, "SAVINGS"));
        addAccount(new Account(1002L, "Priya Sharma", 30000, "CURRENT"));
        addAccount(new Account(1003L, "Amit Verma", 75000, "SAVINGS"));
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getByAccountNumber(long accountNumber) {
        return accountMap.get(accountNumber);
    }

    public Account addAccount(Account account) {
        if (accountMap.containsKey(account.getAccountNumber())) {
            return null;    // duplicate
        }
        account.setActive(true);
        accounts.add(account);
        accountMap.put(account.getAccountNumber(), account);
        return account;
    }

    public Account updateAccount(long accountNumber, Account updated) {
        Account existing = accountMap.get(accountNumber);
        if (existing == null) return null;

        existing.setHolderName(updated.getHolderName());
        existing.setType(updated.getType());
        return existing;
    }

    public boolean deleteAccount(long accountNumber) {
        Account acc = accountMap.remove(accountNumber);
        if (acc == null) return false;
        accounts.remove(acc);
        return true;
    }

    public Account deposit(long accountNumber, double amount) {
        Account acc = accountMap.get(accountNumber);
        if (acc == null) return null;
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        acc.setBalance(acc.getBalance() + amount);
        return acc;
    }

    public Account withdraw(long accountNumber, double amount) {
        Account acc = accountMap.get(accountNumber);
        if (acc == null) return null;
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > acc.getBalance()) throw new IllegalArgumentException("Insufficient balance");
        acc.setBalance(acc.getBalance() - amount);
        return acc;
    }

    public List<Account> searchByName(String keyword) {
        List<Account> results = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.getHolderName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(acc);
            }
        }
        return results;
    }

    public List<Account> filterByType(String type) {
        List<Account> results = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.getType().equalsIgnoreCase(type)) {
                results.add(acc);
            }
        }
        return results;
    }
}
```

**Notice**: The service uses the same `ArrayList` + `HashMap` pattern from Day 4! The data structures haven't changed — only the way clients access them has changed (HTTP instead of Scanner).

### Step 3: AccountController (REST endpoints)

This is the core of our REST API. Each method handles a specific HTTP request.

```java
package com.bank.api.controller;

import com.bank.api.model.Account;
import com.bank.api.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    // Constructor injection — Spring auto-provides the AccountService
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET /api/accounts — list all
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // GET /api/accounts/1001 — get by account number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable long accountNumber) {
        Account acc = accountService.getByAccountNumber(accountNumber);
        if (acc == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(acc);                   // 200
    }

    // POST /api/accounts — create new
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account created = accountService.addAccount(account);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Account #" + account.getAccountNumber() + " already exists"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);    // 201
    }

    // PUT /api/accounts/1001 — update
    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccount(@PathVariable long accountNumber,
                                            @RequestBody Account account) {
        Account updated = accountService.updateAccount(accountNumber, account);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/accounts/1001 — delete
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable long accountNumber) {
        boolean deleted = accountService.deleteAccount(accountNumber);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();    // 204
    }

    // POST /api/accounts/1001/deposit?amount=5000
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<?> deposit(@PathVariable long accountNumber,
                                      @RequestParam double amount) {
        try {
            Account acc = accountService.deposit(accountNumber, amount);
            if (acc == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(acc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/accounts/1001/withdraw?amount=2000
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable long accountNumber,
                                       @RequestParam double amount) {
        try {
            Account acc = accountService.withdraw(accountNumber, amount);
            if (acc == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(acc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/accounts/search?name=kumar
    @GetMapping("/search")
    public List<Account> searchByName(@RequestParam String name) {
        return accountService.searchByName(name);
    }

    // GET /api/accounts?type=SAVINGS
    @GetMapping(params = "type")
    public List<Account> filterByType(@RequestParam String type) {
        return accountService.filterByType(type);
    }
}
```

---

## 7. ResponseEntity — Controlling HTTP Responses

By default, Spring returns `200 OK` for everything. `ResponseEntity` gives you full control over the status code, headers, and body.

### Common patterns:

```java
// 200 OK with body
return ResponseEntity.ok(account);

// 201 Created with body
return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);

// 204 No Content (success, no body)
return ResponseEntity.noContent().build();

// 400 Bad Request with error message
return ResponseEntity.badRequest().body(Map.of("error", "Invalid amount"));

// 404 Not Found (no body)
return ResponseEntity.notFound().build();

// 409 Conflict with error message
return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", "Account already exists"));
```

### How Jackson converts Java → JSON

```
Java return type          →  JSON response
──────────────────────────────────────────────────
Account object            →  {"accountNumber":1001,"holderName":"Ravi",...}
List<Account>             →  [{"accountNumber":1001,...}, {"accountNumber":1002,...}]
Map.of("error","msg")     →  {"error":"msg"}
String "hello"            →  "hello"
```

Jackson reads **getter names** to determine JSON field names:
- `getHolderName()` → `"holderName"` (removes "get", lowercases first letter)
- `isActive()` → `"active"` (removes "is" for booleans)
- No getter = field not included in JSON output

---

## 8. Testing with Postman and curl

### Postman

Postman is a GUI tool for testing REST APIs. Download from [postman.com](https://www.postman.com/).

**Testing steps:**

| # | Method | URL | Body | Expected |
|---|--------|-----|------|----------|
| 1 | GET | `http://localhost:8080/api/accounts` | — | 200, list of 3 accounts |
| 2 | GET | `http://localhost:8080/api/accounts/1001` | — | 200, Ravi's account |
| 3 | GET | `http://localhost:8080/api/accounts/9999` | — | 404 |
| 4 | POST | `http://localhost:8080/api/accounts` | JSON (see below) | 201, new account |
| 5 | POST | `http://localhost:8080/api/accounts/1001/deposit?amount=5000` | — | 200, updated balance |
| 6 | POST | `http://localhost:8080/api/accounts/1001/withdraw?amount=2000` | — | 200, updated balance |
| 7 | GET | `http://localhost:8080/api/accounts/search?name=kumar` | — | 200, matching accounts |
| 8 | DELETE | `http://localhost:8080/api/accounts/1001` | — | 204 |

**POST body for creating an account** (set Content-Type to `application/json`):

```json
{
    "accountNumber": 1004,
    "holderName": "Sneha Reddy",
    "balance": 100000,
    "type": "CURRENT"
}
```

### curl commands

```bash
# GET all accounts
curl http://localhost:8080/api/accounts

# GET one account
curl http://localhost:8080/api/accounts/1001

# CREATE a new account (POST with JSON body)
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":1004,"holderName":"Sneha Reddy","balance":100000,"type":"CURRENT"}'

# DEPOSIT
curl -X POST "http://localhost:8080/api/accounts/1001/deposit?amount=5000"

# WITHDRAW
curl -X POST "http://localhost:8080/api/accounts/1001/withdraw?amount=2000"

# SEARCH by name
curl "http://localhost:8080/api/accounts/search?name=kumar"

# FILTER by type
curl "http://localhost:8080/api/accounts?type=SAVINGS"

# UPDATE (PUT)
curl -X PUT http://localhost:8080/api/accounts/1002 \
  -H "Content-Type: application/json" \
  -d '{"holderName":"Priya Sharma Updated","type":"CURRENT"}'

# DELETE
curl -X DELETE http://localhost:8080/api/accounts/1001
```

---

## 9. Understanding the Flow

When a client sends `POST /api/accounts/1001/deposit?amount=5000`:

```
Client (Postman/curl/browser)
    │
    │ HTTP POST /api/accounts/1001/deposit?amount=5000
    ▼
┌─────────────────────────────────┐
│     Spring Boot (Tomcat)        │  ← embedded server receives request
│                                 │
│  URL matching:                  │
│  /api/accounts/{accNo}/deposit  │
│        ↓                        │
│  AccountController.deposit()    │  ← @PathVariable accNo = 1001
│        │                        │     @RequestParam amount = 5000
│        ▼                        │
│  AccountService.deposit()       │  ← business logic, validation
│        │                        │
│        ▼                        │
│  Return Account object          │
│        │                        │
│  Jackson converts to JSON       │  ← automatic POJO → JSON
│        │                        │
│  ResponseEntity.ok(account)     │  ← 200 status code
└─────────────────────────────────┘
    │
    │ HTTP 200 OK
    │ {"accountNumber":1001,"holderName":"Ravi Kumar","balance":55000.0,...}
    ▼
Client receives response
```

---

## Exercises

### Exercise 1: Transfer Endpoint

**Problem**: Add a transfer endpoint:

`POST /api/accounts/transfer?from=1001&to=1002&amount=5000`

It should withdraw from the source account, deposit to the destination, and return both updated accounts in the response.

<details>
<summary><strong>Solution</strong></summary>

**AccountService — add method:**

```java
public Map<String, Account> transfer(long fromAccNo, long toAccNo, double amount) {
    Account from = accountMap.get(fromAccNo);
    Account to = accountMap.get(toAccNo);
    if (from == null || to == null) return null;
    if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    if (amount > from.getBalance()) throw new IllegalArgumentException("Insufficient balance");

    from.setBalance(from.getBalance() - amount);
    to.setBalance(to.getBalance() + amount);

    Map<String, Account> result = new HashMap<>();
    result.put("from", from);
    result.put("to", to);
    return result;
}
```

**AccountController — add endpoint:**

```java
@PostMapping("/transfer")
public ResponseEntity<?> transfer(@RequestParam long from,
                                   @RequestParam long to,
                                   @RequestParam double amount) {
    try {
        Map<String, Account> result = accountService.transfer(from, to, amount);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
```

**Test:**

```bash
curl -X POST "http://localhost:8080/api/accounts/transfer?from=1001&to=1002&amount=5000"
```
</details>

---

### Exercise 2: Validation and Error Responses

**Problem**: Add proper validation to the `createAccount` endpoint:
- `holderName` must not be empty
- `accountNumber` must be positive
- `balance` must be >= 0
- `type` must be "SAVINGS" or "CURRENT"

Return `400 Bad Request` with a JSON body listing all validation failures.

<details>
<summary><strong>Solution</strong></summary>

**AccountService — add validation method:**

```java
public List<String> validateAccount(Account account) {
    List<String> errors = new ArrayList<>();

    if (account.getHolderName() == null || account.getHolderName().trim().isEmpty()) {
        errors.add("holderName must not be empty");
    }
    if (account.getAccountNumber() <= 0) {
        errors.add("accountNumber must be positive");
    }
    if (account.getBalance() < 0) {
        errors.add("balance must be >= 0");
    }
    if (account.getType() == null ||
        (!account.getType().equals("SAVINGS") && !account.getType().equals("CURRENT"))) {
        errors.add("type must be SAVINGS or CURRENT");
    }

    return errors;
}
```

**AccountController — update createAccount:**

```java
@PostMapping
public ResponseEntity<?> createAccount(@RequestBody Account account) {
    List<String> errors = accountService.validateAccount(account);
    if (!errors.isEmpty()) {
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    Account created = accountService.addAccount(account);
    if (created == null) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Account #" + account.getAccountNumber() + " already exists"));
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}
```

**Test with invalid data:**

```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":-1,"holderName":"","balance":-500,"type":"INVALID"}'
```

**Expected response (400):**

```json
{
    "errors": [
        "holderName must not be empty",
        "accountNumber must be positive",
        "balance must be >= 0",
        "type must be SAVINGS or CURRENT"
    ]
}
```
</details>

---

### Exercise 3: Account Summary Endpoint

**Problem**: Add `GET /api/accounts/summary` that returns aggregate statistics:

```json
{
  "totalAccounts": 4,
  "totalBalance": 275000.00,
  "averageBalance": 68750.00,
  "savingsCount": 2,
  "currentCount": 2
}
```

<details>
<summary><strong>Solution</strong></summary>

**AccountService — add method:**

```java
public Map<String, Object> getSummary() {
    Map<String, Object> summary = new HashMap<>();
    summary.put("totalAccounts", accounts.size());

    double total = 0;
    int savings = 0, current = 0;
    for (Account acc : accounts) {
        total += acc.getBalance();
        if ("SAVINGS".equals(acc.getType())) savings++;
        else if ("CURRENT".equals(acc.getType())) current++;
    }

    summary.put("totalBalance", total);
    summary.put("averageBalance", accounts.isEmpty() ? 0 : total / accounts.size());
    summary.put("savingsCount", savings);
    summary.put("currentCount", current);
    return summary;
}
```

**AccountController — add endpoint:**

```java
@GetMapping("/summary")
public Map<String, Object> getSummary() {
    return accountService.getSummary();
}
```

**Important**: Place `/summary` mapping **before** `/{accountNumber}` in the controller, or Spring may try to interpret "summary" as an account number.

**Test:**

```bash
curl http://localhost:8080/api/accounts/summary
```
</details>

---

### Exercise 4: Transaction Endpoints

**Problem**: Add a `Transaction` model and track all deposits/withdrawals:
- `GET /api/accounts/{accNo}/transactions` — list transactions for an account
- `GET /api/transactions` — list all transactions
- Record transactions automatically on deposit/withdraw

<details>
<summary><strong>Solution</strong></summary>

**Transaction.java:**

```java
package com.bank.api.model;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private String type;
    private double amount;
    private long accountNumber;
    private double balanceBefore;
    private double balanceAfter;
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String type, double amount, long accountNumber,
                       double balanceBefore, double balanceAfter) {
        this.id = java.util.UUID.randomUUID().toString().substring(0, 8);
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters for all fields...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }
    public double getBalanceBefore() { return balanceBefore; }
    public void setBalanceBefore(double balanceBefore) { this.balanceBefore = balanceBefore; }
    public double getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(double balanceAfter) { this.balanceAfter = balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
```

**AccountService — add transaction tracking:**

```java
private List<Transaction> allTransactions = new ArrayList<>();
private Map<Long, List<Transaction>> transactionsByAccount = new HashMap<>();

// Update deposit method:
public Account deposit(long accountNumber, double amount) {
    Account acc = accountMap.get(accountNumber);
    if (acc == null) return null;
    if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

    double before = acc.getBalance();
    acc.setBalance(before + amount);
    recordTransaction("DEPOSIT", amount, accountNumber, before, acc.getBalance());
    return acc;
}

// Update withdraw method:
public Account withdraw(long accountNumber, double amount) {
    Account acc = accountMap.get(accountNumber);
    if (acc == null) return null;
    if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    if (amount > acc.getBalance()) throw new IllegalArgumentException("Insufficient balance");

    double before = acc.getBalance();
    acc.setBalance(before - amount);
    recordTransaction("WITHDRAW", amount, accountNumber, before, acc.getBalance());
    return acc;
}

private void recordTransaction(String type, double amount, long accNo,
                                double before, double after) {
    Transaction txn = new Transaction(type, amount, accNo, before, after);
    allTransactions.add(txn);
    transactionsByAccount.computeIfAbsent(accNo, k -> new ArrayList<>()).add(txn);
}

public List<Transaction> getAllTransactions() { return allTransactions; }
public List<Transaction> getTransactions(long accNo) {
    return transactionsByAccount.getOrDefault(accNo, new ArrayList<>());
}
```

**TransactionController.java:**

```java
@RestController
public class TransactionController {
    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/transactions")
    public List<Transaction> getAll() {
        return accountService.getAllTransactions();
    }

    @GetMapping("/api/accounts/{accNo}/transactions")
    public List<Transaction> getForAccount(@PathVariable long accNo) {
        return accountService.getTransactions(accNo);
    }
}
```
</details>

---

### Exercise 5: Full Challenge — Complete Banking API

**Problem**: Add these additional endpoints:

- `PATCH /api/accounts/{accNo}` — partial update (only update fields that are provided, not null)
- `POST /api/accounts/{accNo}/deactivate` — set active to false
- `POST /api/accounts/{accNo}/activate` — set active to true
- `GET /api/accounts?minBalance=50000&maxBalance=100000` — filter by balance range

<details>
<summary><strong>Solution</strong></summary>

**AccountService — add methods:**

```java
public Account patchAccount(long accountNumber, Account patch) {
    Account existing = accountMap.get(accountNumber);
    if (existing == null) return null;

    if (patch.getHolderName() != null) existing.setHolderName(patch.getHolderName());
    if (patch.getType() != null) existing.setType(patch.getType());
    return existing;
}

public Account setActive(long accountNumber, boolean active) {
    Account acc = accountMap.get(accountNumber);
    if (acc == null) return null;
    acc.setActive(active);
    return acc;
}

public List<Account> filterByBalanceRange(double min, double max) {
    List<Account> results = new ArrayList<>();
    for (Account acc : accounts) {
        if (acc.getBalance() >= min && acc.getBalance() <= max) {
            results.add(acc);
        }
    }
    return results;
}
```

**AccountController — add endpoints:**

```java
@PatchMapping("/{accountNumber}")
public ResponseEntity<?> patchAccount(@PathVariable long accountNumber,
                                       @RequestBody Account account) {
    Account patched = accountService.patchAccount(accountNumber, account);
    if (patched == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(patched);
}

@PostMapping("/{accountNumber}/deactivate")
public ResponseEntity<?> deactivate(@PathVariable long accountNumber) {
    Account acc = accountService.setActive(accountNumber, false);
    if (acc == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(acc);
}

@PostMapping("/{accountNumber}/activate")
public ResponseEntity<?> activate(@PathVariable long accountNumber) {
    Account acc = accountService.setActive(accountNumber, true);
    if (acc == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(acc);
}

@GetMapping(params = {"minBalance", "maxBalance"})
public List<Account> filterByBalanceRange(@RequestParam double minBalance,
                                           @RequestParam double maxBalance) {
    return accountService.filterByBalanceRange(minBalance, maxBalance);
}
```

**Test:**

```bash
# Partial update
curl -X PATCH http://localhost:8080/api/accounts/1001 \
  -H "Content-Type: application/json" \
  -d '{"holderName":"Ravi Kumar Updated"}'

# Deactivate
curl -X POST http://localhost:8080/api/accounts/1001/deactivate

# Activate
curl -X POST http://localhost:8080/api/accounts/1001/activate

# Filter by balance range
curl "http://localhost:8080/api/accounts?minBalance=40000&maxBalance=80000"
```
</details>

---

## Quick Quiz

1. What does REST stand for?
2. Which HTTP method is used to create a new resource?
3. What status code should you return when a resource is created?
4. What is the difference between `@PathVariable` and `@RequestParam`?
5. What annotation makes a class a REST controller in Spring?
6. What does `@RequestBody` do?
7. What is the difference between `PUT` and `PATCH`?
8. Why do we need a default (no-arg) constructor in the Account model for REST?

<details>
<summary><strong>Answers</strong></summary>

1. **REpresentational State Transfer** — an architectural style for building web APIs based on resources, URIs, and standard HTTP methods.

2. **POST** — it creates a new resource on the server. Unlike GET/PUT/DELETE, POST is not idempotent (each call may create a new resource).

3. **201 Created** — indicates the resource was successfully created. The response body usually contains the created resource.

4. **@PathVariable** extracts a value from the URL path (`/accounts/{id}` → `id`). Used for identifying specific resources. **@RequestParam** extracts a query parameter (`/accounts?type=SAVINGS` → `type`). Used for filtering, searching, and optional parameters.

5. **@RestController** — it combines `@Controller` (handles HTTP requests) and `@ResponseBody` (return values are written directly to the response body as JSON, not forwarded to a view template).

6. **@RequestBody** tells Spring to read the HTTP request body (usually JSON) and convert it into a Java object using Jackson. For example, `@RequestBody Account account` converts `{"holderName":"Ravi",...}` into an Account object.

7. **PUT** replaces the entire resource — you must send all fields. **PATCH** partially updates — you only send the fields you want to change. For example, PUT requires sending the full Account object, while PATCH lets you send just `{"holderName":"New Name"}`.

8. Jackson (the JSON library) needs the **no-arg constructor** to create an empty object first, then it uses **setters** to populate fields from the JSON data. Without it, Jackson can't deserialize incoming JSON into Java objects.
</details>

---

## What We Built Today

| Step | What we built | Key Annotations |
|------|--------------|----------------|
| REST concepts | Understood resources, URIs, HTTP methods, status codes | — |
| Project setup | Created Spring Boot project with Spring Web | `@SpringBootApplication` |
| Account model | Flat POJO with getters/setters for JSON conversion | — |
| AccountService | Business logic (in-memory ArrayList + HashMap) | `@Service` |
| AccountController | CRUD endpoints for accounts | `@RestController`, `@GetMapping`, `@PostMapping`, etc. |
| Path params | `/accounts/{accountNumber}` | `@PathVariable` |
| Query params | `/accounts/search?name=kumar` | `@RequestParam` |
| Request body | POST/PUT with JSON | `@RequestBody` |
| Response control | Status codes, error bodies | `ResponseEntity` |

### How the BankApp evolved

```
Day 1-5:  Console app — Scanner input, System.out output, in-memory data
Day 7:    + File persistence — data survives program restarts
Day 8:    REST API — any client can access banking operations over HTTP
```

The business logic (deposit, withdraw, search) hasn't fundamentally changed — we've just changed how clients interact with it. That's the power of separating concerns into layers (model, service, controller).

## What's Next (Day 9 Preview)

We have a REST API backend. But how do users interact with it? They won't use Postman! Tomorrow we build the front-end — **HTML pages** with forms to create accounts, make deposits, and view balances.
