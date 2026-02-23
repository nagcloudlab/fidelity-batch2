# Day 15 — All Modules Assessment (Final Comprehensive Assessment)

## Assessment Paper (Instructor Copy — with Answer Key)

> **Duration**: ~5 hours
> **Total Marks**: 150
> **Coverage**: Days 1–13 (Java Fundamentals, OOP, Exceptions, Collections, Data Structures, File I/O, REST API & Spring Boot, HTML & CSS, JavaScript Integration, SQL, JDBC, DevOps)
> **Instructions**: Answer all questions. All code must be compilable Java. SQL must be valid syntax. HTML/CSS must render correctly in a browser. DevOps commands must use valid syntax.

---

## Section A: Multiple Choice (30 marks)

*Choose the single best answer. 1 mark each.*

---

### Java Fundamentals (Day 1)

**Q1.** What is the output?

```java
int a = 10;
int b = 3;
System.out.println(a / b);
```

a) 3.33
b) 3
c) 3.0
d) Compilation error

---

**Q2.** What is the difference between `int` and `Integer`?

a) They are identical
b) `int` is a primitive; `Integer` is a wrapper class (object)
c) `int` is a class; `Integer` is a primitive
d) `Integer` can only hold positive values

---

**Q3.** What does the `static` keyword mean on a method?

a) The method cannot be overridden
b) The method belongs to the class, not to any instance
c) The method runs automatically when the program starts
d) The method can only be called once

---

### OOP (Day 2)

**Q4.** What is the purpose of an abstract class?

a) To create objects directly
b) To define a template with common behavior that subclasses must complete
c) To prevent inheritance
d) To make all methods private

---

**Q5.** In our banking app, `SavingsAccount extends Account`. If `Account` has an abstract method `calculateInterest()`, what must `SavingsAccount` do?

a) Nothing — abstract methods are optional
b) Provide a concrete implementation of `calculateInterest()`
c) Declare itself as abstract too
d) Delete the method

---

**Q6.** What is polymorphism?

a) A class having multiple constructors
b) A child class having more fields than the parent
c) A parent type reference can hold a child type object; the actual method called depends on the object's type at runtime
d) Using interfaces instead of abstract classes

---

### Exception Handling (Day 3)

**Q7.** What is the order of catch blocks?

a) Generic exceptions first, specific last
b) Specific exceptions first, generic last
c) Order doesn't matter
d) Only one catch block is allowed

---

**Q8.** What is the purpose of a custom exception like `InsufficientFundsException`?

a) To replace all Java exceptions
b) To provide a meaningful, domain-specific error message and type
c) To prevent the program from crashing
d) To avoid using try-catch

---

### Collections (Day 4)

**Q9.** What is the difference between `ArrayList` and `HashMap`?

a) ArrayList stores key-value pairs; HashMap stores a list
b) ArrayList stores ordered elements by index; HashMap stores key-value pairs for fast lookup by key
c) They are the same data structure with different names
d) ArrayList is faster than HashMap for all operations

---

**Q10.** What does `map.getOrDefault(key, defaultValue)` do?

a) Always returns the default value
b) Returns the value if the key exists; otherwise returns the default value
c) Throws an exception if the key doesn't exist
d) Inserts the default value for the key

---

**Q11.** What happens when you call `list.remove(0)` on an ArrayList with 5 elements?

a) Removes the last element
b) Removes the first element and shifts all remaining elements left
c) Sets index 0 to null
d) Throws `IndexOutOfBoundsException`

---

### Data Structures (Day 5)

**Q12.** A Stack follows LIFO. What does `stack.pop()` return after: `push("A")`, `push("B")`, `push("C")`?

a) "A"
b) "B"
c) "C"
d) null

---

**Q13.** Which data structure is best for a "first-come, first-served" service counter?

a) Stack
b) Queue
c) HashMap
d) ArrayList

---

### File I/O (Day 7)

**Q14.** What is the advantage of try-with-resources over manually calling `.close()`?

a) It catches more exceptions
b) It automatically closes resources when the block exits, even if an exception occurs
c) It is faster
d) It doesn't need a catch block

---

**Q15.** What does the `transient` keyword do to a field during serialization?

a) Makes it thread-safe
b) Excludes it from serialization — the field gets its default value on deserialization
c) Encrypts the field
d) Makes it read-only

---

### REST API & Spring Boot (Day 8)

**Q16.** What HTTP method is used to update an existing resource?

a) GET
b) POST
c) PUT
d) DELETE

---

**Q17.** What does `@RequestBody` do in a Spring Boot controller?

a) Sets the response body
b) Converts the JSON request body into a Java object
c) Reads a file from disk
d) Sets the HTTP status code

---

**Q18.** What status code should a REST API return when a requested resource doesn't exist?

a) 200 OK
b) 201 Created
c) 400 Bad Request
d) 404 Not Found

---

### HTML & CSS (Day 9)

**Q19.** What is the difference between `<div>` and `<span>`?

a) `<div>` is inline; `<span>` is block
b) `<div>` is block-level (full width, new line); `<span>` is inline (within text flow)
c) They are identical
d) `<div>` is for text only; `<span>` is for images only

---

**Q20.** What does `border-collapse: collapse` do on an HTML table?

a) Hides all borders
b) Rounds border corners
c) Merges adjacent cell borders into a single border
d) Adds a shadow

---

**Q21.** In the CSS box model, what is the correct order from inside to outside?

a) Margin → Border → Padding → Content
b) Content → Padding → Border → Margin
c) Content → Border → Padding → Margin
d) Padding → Content → Border → Margin

---

### JavaScript & HTTP Integration (Day 11)

**Q22.** What does `JSON.stringify()` do?

a) Parses a JSON string into a JavaScript object
b) Converts a JavaScript object into a JSON string
c) Sends a JSON request to a server
d) Validates JSON syntax

---

**Q23.** Why is `event.preventDefault()` needed in a form submit handler that uses `fetch()`?

a) To prevent JavaScript errors
b) To stop the browser from submitting the form via its default HTML action, which would refresh the page
c) To prevent CORS errors
d) To prevent the server from responding

---

### SQL (Day 11)

**Q24.** What SQL clause filters rows before grouping?

a) HAVING
b) GROUP BY
c) WHERE
d) ORDER BY

---

**Q25.** What does this SQL return? `SELECT holder_name FROM accounts WHERE balance = (SELECT MAX(balance) FROM accounts);`

a) All account holders
b) The holder with the highest balance
c) The maximum balance value
d) An error — subqueries are not allowed

---

**Q26.** What is the purpose of a JOIN?

a) To delete rows from two tables at once
b) To combine rows from two or more tables based on a related column
c) To create a new table
d) To sort results from multiple tables

---

### JDBC (Day 12)

**Q27.** What is the correct sequence for JDBC operations?

a) Get ResultSet → Create Connection → Execute Query
b) Get Connection → Create PreparedStatement → Set Parameters → Execute → Process ResultSet
c) Create PreparedStatement → Get Connection → Execute
d) Execute → Get Connection → Process ResultSet

---

**Q28.** What does `ps.setLong(1, accountNumber)` do?

a) Sets the first column in the result to `accountNumber`
b) Sets the first `?` placeholder in the PreparedStatement to the value of `accountNumber`
c) Creates a new column called `accountNumber`
d) Sets the query timeout to `accountNumber` milliseconds

---

### DevOps (Day 13)

**Q29.** What is the correct order of Git operations?

a) commit → add → push
b) add → commit → push
c) push → commit → add
d) add → push → commit

---

**Q30.** In a Dockerfile, what is the difference between `RUN` and `CMD`?

a) They are identical
b) `RUN` executes during image build; `CMD` specifies the command to run when a container starts
c) `RUN` is for Linux only; `CMD` is cross-platform
d) `RUN` starts a container; `CMD` stops it

---

### Section A — Answer Key

| Q | Answer | Explanation |
|---|--------|-------------|
| 1 | b | Integer division: `10 / 3 = 3` (truncates decimal, both operands are int) |
| 2 | b | `int` is a primitive (stack, fast); `Integer` is a wrapper object (heap, supports null, Collections) |
| 3 | b | Static methods belong to the class — called via `ClassName.method()`, no instance needed |
| 4 | b | Abstract class = template; can have abstract methods (no body) that subclasses must implement |
| 5 | b | Concrete subclass MUST implement all abstract methods, or declare itself abstract |
| 6 | c | Polymorphism: `Account acc = new SavingsAccount();` — `acc.calculateInterest()` calls SavingsAccount's version |
| 7 | b | Specific first (e.g., `InsufficientFundsException`), then generic (`Exception`); otherwise specific is unreachable |
| 8 | b | Custom exceptions give domain meaning: `InsufficientFundsException` is clearer than generic `RuntimeException` |
| 9 | b | ArrayList = ordered list by index; HashMap = key→value lookup in O(1) |
| 10 | b | Returns the mapped value if key exists; otherwise returns the provided default (no exception) |
| 11 | b | Removes element at index 0 and shifts indices: [A,B,C,D,E] → [B,C,D,E] |
| 12 | c | LIFO: last pushed ("C") is first popped |
| 13 | b | Queue = FIFO (First In, First Out) = first-come, first-served |
| 14 | b | Auto-closes on block exit; no risk of forgetting `.close()` or leaking resources on exception |
| 15 | b | `transient` excludes the field; on deserialization it gets null/0/false depending on type |
| 16 | c | PUT updates/replaces an existing resource; POST creates a new one |
| 17 | b | Jackson deserializes JSON body into the method parameter's Java type |
| 18 | d | 404 Not Found — the resource with the given ID/key doesn't exist |
| 19 | b | `<div>` = block (new line, full width); `<span>` = inline (flows within text) |
| 20 | c | Without collapse, each cell has its own border (double lines); collapse merges them |
| 21 | b | Content → Padding → Border → Margin (inside out) |
| 22 | b | Converts JS object `{name: "Ravi"}` to JSON string `'{"name":"Ravi"}'` for fetch body |
| 23 | b | Default form submission refreshes the page; `preventDefault()` stops that so `fetch()` can complete |
| 24 | c | WHERE filters individual rows before GROUP BY; HAVING filters groups after GROUP BY |
| 25 | b | Subquery finds MAX(balance); outer query returns the holder with that balance |
| 26 | b | JOIN combines rows from related tables using a common column (e.g., account_number) |
| 27 | b | Connection → PreparedStatement → setParameters → execute → process ResultSet → close |
| 28 | b | Sets the first `?` in the SQL to the value of `accountNumber` (1-based indexing) |
| 29 | b | Stage (add) → Save locally (commit) → Share remotely (push) |
| 30 | b | `RUN` = build-time command (install packages, compile); `CMD` = runtime command (start the app) |

---

## Section B: Short Answer (30 marks)

*Answer in 2–4 sentences. 3 marks each.*

---

**Q1.** What is the difference between a local variable and an instance variable in Java? Where is each stored and when is each created?

**Q2.** Explain the difference between an abstract class and an interface. When would you use each?

**Q3.** In our banking app, we created `InsufficientFundsException`. Why is a custom exception better than just using `System.out.println("Error: not enough funds")`?

**Q4.** Explain the difference between `ArrayList` and `HashMap`. Give a banking app example where each is the best choice.

**Q5.** What is the difference between saving data to a CSV file and using Java Serialization? When would you choose each?

**Q6.** Explain the separation of concerns in our Spring Boot banking app. What are the roles of `AccountController`, `AccountService`, and `AccountRepository`?

**Q7.** What is the difference between HTML and CSS? Why do we use an external stylesheet instead of inline styles?

**Q8.** Describe the complete data flow when a user clicks "Create Account" on the HTML form and sees a success message. Name the technologies involved at each step.

**Q9.** Why did we replace the in-memory ArrayList storage with a database (JDBC)? What are the advantages of using a database?

**Q10.** Explain the DevOps concept of CI/CD. How does our GitHub Actions pipeline automate the build process for the banking app?

---

### Section B — Answer Key

**Q1.**
A **local variable** is declared inside a method — it exists only while the method runs and is stored on the **stack**. An **instance variable** is declared inside a class (outside methods) — it exists as long as the object exists and is stored on the **heap**. Local variables must be initialized before use; instance variables get default values (0, null, false). Example: `double balance` (instance) vs `double interest` inside a method (local).

**Q2.**
An **abstract class** can have both abstract methods (no body) and concrete methods (with body), plus fields with state. A class can extend only one abstract class. An **interface** (pre-Java 8) declares only abstract methods — a class can implement multiple interfaces. Use abstract class when subclasses share common state and behavior (e.g., `Account` with `balance` field). Use interface when unrelated classes need to share a contract (e.g., `Serializable`, `Comparable`).

**Q3.**
A custom exception provides **type safety** and **structured error handling**. With `println`, the program continues running in an invalid state — the caller doesn't know an error occurred. With `throw new InsufficientFundsException(amount, balance)`, the program **forces** the caller to handle the error (try-catch) or propagate it. We can also carry data (requested amount, current balance) in the exception object, and catch it specifically: `catch (InsufficientFundsException e)` — separate from other errors.

**Q4.**
**ArrayList** stores elements in order by index — best for sequential access and iteration. Banking example: storing a list of all accounts to display in a table (`List<Account> allAccounts`). **HashMap** stores key-value pairs for instant lookup by key — best when you need to find items by a unique identifier. Banking example: `Map<Long, Account>` to look up an account by account number in O(1) time, instead of iterating through a list.

**Q5.**
**CSV** saves data as human-readable text (comma-separated fields). You can open it in Notepad or Excel. Requires manual parsing (split + parse). Best for: reports, data exchange with other systems, human inspection. **Serialization** saves Java objects as binary using `ObjectOutputStream`. It's automatic (one call: `writeObject`) but Java-only and unreadable by humans. Best for: quick persistence of complex Java object graphs within a Java application. We chose CSV for portability and serialization for convenience.

**Q6.**
**AccountController** (`@RestController`) handles HTTP concerns — it receives HTTP requests, extracts parameters (`@PathVariable`, `@RequestBody`), calls the service, and returns HTTP responses with proper status codes (`ResponseEntity`). **AccountService** (`@Service`) contains business logic — validation, calculations, and orchestration. **AccountRepository** handles data access — JDBC queries, PreparedStatements, ResultSet mapping. This separation means each layer can change independently: we swapped from ArrayList to JDBC without changing the controller or front-end.

**Q7.**
**HTML** defines the **structure and content** — headings, tables, forms, links. **CSS** controls the **visual presentation** — colors, fonts, spacing, layout. We use an **external stylesheet** (`styles.css` linked via `<link>`) instead of inline styles because: (1) **Reusability** — all pages share the same CSS file, (2) **Maintainability** — change the color theme in one file instead of every HTML file, (3) **Separation of concerns** — structure (HTML) and presentation (CSS) are in separate files, (4) **Caching** — the browser downloads the CSS once and reuses it.

**Q8.**
1. **Browser (HTML)**: User fills form fields and clicks Submit
2. **Browser (JavaScript)**: `event.preventDefault()` stops page refresh; `getElementById()` reads values; `JSON.stringify()` converts to JSON string
3. **Browser (fetch)**: `fetch("http://localhost:8080/api/accounts", {method:"POST", body:json})` sends HTTP POST
4. **Spring Boot Controller**: `@PostMapping` receives request; `@RequestBody` + Jackson deserialize JSON → Java Account object
5. **Service**: `accountService.createAccount(account)` validates and delegates
6. **Repository (JDBC)**: `PreparedStatement` executes `INSERT INTO accounts VALUES (?,?,?,?)` on H2 database
7. **Response chain**: Account object → Service → Controller → `ResponseEntity.status(201).body(account)` → Jackson serializes to JSON → HTTP 201 response
8. **Browser (JavaScript)**: `.then(response => response.json())` parses JSON; updates DOM: `textContent = "Account created!"`

**Q9.**
In-memory storage (ArrayList/HashMap) loses all data when the application restarts. File-based persistence (CSV) is slow, hard to query, and doesn't support concurrent access. A **database** via JDBC provides: (1) **Persistence** — data survives restarts, (2) **SQL queries** — powerful filtering, sorting, aggregation without Java code, (3) **Concurrency** — multiple users can read/write simultaneously, (4) **Data integrity** — constraints (PK, FK, NOT NULL) enforce rules at the database level, (5) **Scalability** — databases handle millions of rows efficiently.

**Q10.**
**CI (Continuous Integration)** means every code change is automatically built and tested. **CD (Continuous Delivery/Deployment)** means the tested code is automatically prepared for (or deployed to) production. Our GitHub Actions pipeline triggers on every push to `main`: (1) checks out the code, (2) sets up Java 17, (3) compiles with `mvn compile`, (4) runs tests with `mvn test`, (5) packages the JAR with `mvn package`. If any step fails, the team is notified immediately. This catches bugs early — a broken build is detected within minutes, not days later.

---

## Section C: Code Reading & Debugging (30 marks)

*Read the code carefully and answer the questions. 5 marks each.*

---

### C1. What is the output of this program?

```java
public abstract class Vehicle {
    String type;

    Vehicle(String type) {
        this.type = type;
    }

    abstract double fuelCost(double km);

    void describe() {
        System.out.println(type + ": Rs." + fuelCost(100) + " per 100km");
    }
}

class Car extends Vehicle {
    Car() { super("Car"); }

    double fuelCost(double km) {
        return km * 8.5;  // Rs.8.5 per km
    }
}

class Bike extends Vehicle {
    Bike() { super("Bike"); }

    double fuelCost(double km) {
        return km * 3.0;  // Rs.3.0 per km
    }
}

// In main:
Vehicle v1 = new Car();
Vehicle v2 = new Bike();
v1.describe();
v2.describe();
```

---

### C2. Find and fix TWO bugs in this exception handling code:

```java
public void withdraw(double amount) {
    try {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
        System.out.println("Withdrawn: Rs." + amount);
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    } catch (InsufficientFundsException e) {
        System.out.println("Insufficient funds! Needed: " + e.getAmount() + ", Available: " + e.getBalance());
    }
}
```

---

### C3. What is the output?

```java
ArrayList<String> names = new ArrayList<>();
names.add("Ravi");
names.add("Priya");
names.add("Amit");
names.add("Neha");

HashMap<String, Integer> scores = new HashMap<>();
scores.put("Ravi", 85);
scores.put("Priya", 92);
scores.put("Amit", 78);

Stack<String> toppers = new Stack<>();

for (String name : names) {
    int score = scores.getOrDefault(name, 0);
    if (score >= 80) {
        toppers.push(name + "(" + score + ")");
    }
}

System.out.println("Toppers (most recent first):");
while (!toppers.isEmpty()) {
    System.out.println("  " + toppers.pop());
}
```

---

### C4. Find and fix TWO bugs in this Spring Boot controller method:

```java
@PostMapping
public ResponseEntity<Account> createAccount(@RequestBody Account account) {
    Account existing = accountService.getByAccountNumber(account.getAccountNumber());
    if (existing == null) {
        return ResponseEntity.status(409).body("Duplicate account number");
    }
    Account created = accountService.createAccount(account);
    return ResponseEntity.ok(created);
}
```

---

### C5. What does this JavaScript + HTML code do? Identify ONE bug.

```html
<select id="accType">
    <option value="SAVINGS">Savings</option>
    <option value="CURRENT">Current</option>
</select>
<button id="filterBtn">Filter</button>
<table id="resultsTable"><tbody></tbody></table>

<script>
document.getElementById("filterBtn").addEventListener("click", function() {
    const type = document.getElementById("accType").value;

    fetch("http://localhost:8080/api/accounts/search?type=" + type)
        .then(response => response.json())
        .then(accounts => {
            const tbody = document.getElementById("resultsTable").querySelector("tbody");
            accounts.forEach(acc => {
                const row = document.createElement("tr");
                row.innerHTML = `<td>${acc.holderName}</td><td>${acc.balance}</td>`;
                tbody.appendChild(row);
            });
        });
});
```

---

### C6. Find and fix TWO bugs in this JDBC code:

```java
public List<Account> findByType(String accountType) {
    String sql = "SELECT * FROM accounts WHERE account_type = '" + accountType + "'";
    List<Account> accounts = new ArrayList<>();
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account acc = new Account();
                acc.setAccountNumber(rs.getLong("account_number"));
                acc.setHolderName(rs.getString("holder_name"));
                acc.setAccountType(rs.getString("account_type"));
                acc.setBalance(rs.getInt("balance"));
                accounts.add(acc);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return accounts;
}
```

---

### Section C — Answer Key

**C1.** Output:
```
Car: Rs.850.0 per 100km
Bike: Rs.300.0 per 100km
```

`v1` is a `Car` — `describe()` calls `fuelCost(100)` which uses `Car`'s implementation: `100 * 8.5 = 850.0`. `v2` is a `Bike` — `fuelCost(100)` uses `Bike`'s implementation: `100 * 3.0 = 300.0`. This demonstrates **polymorphism** — the `describe()` method is in the abstract parent, but `fuelCost()` is resolved at runtime based on the actual object type.

(2 marks for correct output, 3 marks for explaining polymorphism)

**C2.** Two bugs:

**Bug 1 — Catch order is wrong**: `catch (Exception e)` comes before `catch (InsufficientFundsException e)`. Since `InsufficientFundsException` is a subclass of `Exception`, the generic catch catches everything — the specific catch is **unreachable** (compilation error).

**Bug 2 — Both catches handle errors but program continues normally**: After catching `InsufficientFundsException`, the withdrawal should not proceed. However, with the current structure, if the exception is caught, the method still exits normally. The specific catch should either re-throw or the method should return/exit after logging.

Fix:
```java
} catch (InsufficientFundsException e) {
    System.out.println("Insufficient funds! Needed: " + e.getAmount() + ", Available: " + e.getBalance());
} catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
}
```

(2.5 marks per bug — the catch order is the critical bug; full marks if they identify the ordering and provide the fix)

**C3.** Output:
```
Toppers (most recent first):
  Priya(92)
  Ravi(85)
```

Walkthrough:
- "Ravi" → score 85 → ≥80 → push "Ravi(85)"
- "Priya" → score 92 → ≥80 → push "Priya(92)"
- "Amit" → score 78 → <80 → skip
- "Neha" → score 0 (not in map, getOrDefault returns 0) → <80 → skip

Stack has: [Ravi(85), Priya(92)] (Priya on top). Pop order: Priya(92), then Ravi(85).

(2 marks for correct output, 1 mark for explaining getOrDefault with Neha, 2 marks for explaining Stack LIFO order)

**C4.** Two bugs:

**Bug 1 — Logic is inverted**: The condition checks `if (existing == null)` and returns 409 Conflict. But null means the account **doesn't** exist, which is the good case — we should create it. Non-null means duplicate. Fix: `if (existing != null)`.

**Bug 2 — Wrong status code for creation**: `ResponseEntity.ok(created)` returns 200. For a newly created resource, it should return **201 Created**.

Fix:
```java
@PostMapping
public ResponseEntity<?> createAccount(@RequestBody Account account) {
    Account existing = accountService.getByAccountNumber(account.getAccountNumber());
    if (existing != null) {                                 // Bug 1: was == null
        return ResponseEntity.status(409).body("Duplicate account number");
    }
    Account created = accountService.createAccount(account);
    return ResponseEntity.status(201).body(created);         // Bug 2: was .ok()
}
```

(2.5 marks per bug)

**C5.** The code creates a filter feature: when the user selects an account type from the dropdown and clicks "Filter", it fetches accounts of that type from the REST API and displays them in a table.

**Bug: The `<tbody>` is never cleared before appending new rows.** Each time the user clicks "Filter", the new results are appended below the previous results. After 3 clicks, the table shows 3x the data.

Fix — clear the tbody before the forEach:
```javascript
.then(accounts => {
    const tbody = document.getElementById("resultsTable").querySelector("tbody");
    tbody.innerHTML = "";  // Clear previous results
    accounts.forEach(acc => {
        // ... append rows
    });
});
```

(2 marks for describing what the code does, 3 marks for identifying the bug and fix)

**C6.** Two bugs:

**Bug 1 — SQL injection vulnerability**: The query uses string concatenation (`"... = '" + accountType + "'"`) instead of a PreparedStatement parameter placeholder. If `accountType` is `' OR 1=1 --`, all accounts are returned.

Fix:
```java
String sql = "SELECT * FROM accounts WHERE account_type = ?";
// ...
ps.setString(1, accountType);
```

**Bug 2 — Wrong data type for balance**: `rs.getInt("balance")` reads the balance as an integer. The column is defined as `DOUBLE` and balance likely has decimal values (e.g., 50000.75). Should be `rs.getDouble("balance")`.

Fix:
```java
acc.setBalance(rs.getDouble("balance"));
```

(2.5 marks per bug)

---

## Section D: Practical Assignments (60 marks)

*Write complete, working code. You may use an IDE, browser, SQL console, and terminal.*

---

### D1. Core Java — Library Management System (20 marks)

Build a small library management system that demonstrates Java fundamentals, OOP, exceptions, and collections.

**Requirements:**

1. **(3 marks)** Create an abstract class `LibraryItem` with:
   - Fields: `id` (int), `title` (String), `isAvailable` (boolean, default true)
   - Constructor, getters, setters, `toString()`
   - Abstract method: `double lateFee(int daysLate)`

2. **(3 marks)** Create two subclasses:
   - `Book` — has `author` (String); lateFee = Rs.5 per day
   - `DVD` — has `duration` (int, minutes); lateFee = Rs.10 per day

3. **(3 marks)** Create a custom exception:
   - `ItemNotAvailableException` — thrown when trying to borrow an item that's already borrowed
   - Should carry the item title and current borrower info

4. **(5 marks)** Create `LibraryService` with:
   - `HashMap<Integer, LibraryItem>` to store items by ID
   - `addItem(LibraryItem item)` — add to collection
   - `borrowItem(int id)` — set `isAvailable = false`; throw `ItemNotAvailableException` if already borrowed; throw `IllegalArgumentException` if ID not found
   - `returnItem(int id, int daysLate)` — set `isAvailable = true`, calculate and print late fee
   - `listAvailable()` — return only available items
   - `searchByTitle(String keyword)` — case-insensitive search

5. **(3 marks)** Create a `main` method that:
   - Adds 4 items (mix of Books and DVDs)
   - Borrows an item, then tries to borrow it again (demonstrating exception handling)
   - Returns an item 5 days late (showing the late fee calculation)
   - Lists available items
   - Searches by title keyword

6. **(3 marks)** Code quality:
   - Proper use of polymorphism (process items as `LibraryItem` references)
   - Collections used appropriately
   - Exception handling with try-catch
   - Clean, readable code

---

### D1 — Answer Key (Model Solution)

```java
// ── LibraryItem.java ──
public abstract class LibraryItem {
    private int id;
    private String title;
    private boolean isAvailable = true;

    public LibraryItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public abstract double lateFee(int daysLate);

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s)", id, title, isAvailable ? "Available" : "Borrowed");
    }
}
```

```java
// ── Book.java ──
public class Book extends LibraryItem {
    private String author;

    public Book(int id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    @Override
    public double lateFee(int daysLate) {
        return daysLate * 5.0;  // Rs.5 per day
    }

    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return super.toString() + " - Book by " + author;
    }
}
```

```java
// ── DVD.java ──
public class DVD extends LibraryItem {
    private int duration;

    public DVD(int id, String title, int duration) {
        super(id, title);
        this.duration = duration;
    }

    @Override
    public double lateFee(int daysLate) {
        return daysLate * 10.0;  // Rs.10 per day
    }

    public int getDuration() { return duration; }

    @Override
    public String toString() {
        return super.toString() + " - DVD (" + duration + " min)";
    }
}
```

```java
// ── ItemNotAvailableException.java ──
public class ItemNotAvailableException extends Exception {
    private String itemTitle;

    public ItemNotAvailableException(String itemTitle) {
        super("Item not available: " + itemTitle);
        this.itemTitle = itemTitle;
    }

    public String getItemTitle() { return itemTitle; }
}
```

```java
// ── LibraryService.java ──
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private HashMap<Integer, LibraryItem> items = new HashMap<>();

    public void addItem(LibraryItem item) {
        items.put(item.getId(), item);
    }

    public void borrowItem(int id) throws ItemNotAvailableException {
        LibraryItem item = items.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Item ID " + id + " not found");
        }
        if (!item.isAvailable()) {
            throw new ItemNotAvailableException(item.getTitle());
        }
        item.setAvailable(false);
        System.out.println("Borrowed: " + item.getTitle());
    }

    public double returnItem(int id, int daysLate) {
        LibraryItem item = items.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Item ID " + id + " not found");
        }
        item.setAvailable(true);
        double fee = item.lateFee(daysLate);   // Polymorphism!
        System.out.println("Returned: " + item.getTitle());
        if (daysLate > 0) {
            System.out.println("Late fee (" + daysLate + " days): Rs." + fee);
        }
        return fee;
    }

    public List<LibraryItem> listAvailable() {
        List<LibraryItem> available = new ArrayList<>();
        for (LibraryItem item : items.values()) {
            if (item.isAvailable()) {
                available.add(item);
            }
        }
        return available;
    }

    public List<LibraryItem> searchByTitle(String keyword) {
        List<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : items.values()) {
            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }
}
```

```java
// ── Main.java ──
public class Main {
    public static void main(String[] args) {
        LibraryService library = new LibraryService();

        // Add items
        library.addItem(new Book(1, "Clean Code", "Robert C. Martin"));
        library.addItem(new Book(2, "Java in Action", "Raoul-Gabriel Urma"));
        library.addItem(new DVD(3, "The Social Network", 120));
        library.addItem(new DVD(4, "Java Tutorial Series", 90));

        // Borrow an item
        try {
            library.borrowItem(1);  // Success
            library.borrowItem(1);  // Should throw ItemNotAvailableException
        } catch (ItemNotAvailableException e) {
            System.out.println("Cannot borrow: " + e.getMessage());
        }

        // Return an item 5 days late
        library.returnItem(1, 5);  // Book: 5 * Rs.5 = Rs.25

        // List available items
        System.out.println("\n--- Available Items ---");
        for (LibraryItem item : library.listAvailable()) {
            System.out.println("  " + item);
        }

        // Search by title
        System.out.println("\n--- Search: 'java' ---");
        for (LibraryItem item : library.searchByTitle("java")) {
            System.out.println("  " + item);
        }
    }
}
```

Expected output:
```
Borrowed: Clean Code
Cannot borrow: Item not available: Clean Code
Returned: Clean Code
Late fee (5 days): Rs.25.0

--- Available Items ---
  [1] Clean Code (Available) - Book by Robert C. Martin
  [2] Java in Action (Available) - Book by Raoul-Gabriel Urma
  [3] The Social Network (Available) - DVD (120 min)
  [4] Java Tutorial Series (Available) - DVD (90 min)

--- Search: 'java' ---
  [2] Java in Action (Available) - Book by Raoul-Gabriel Urma
  [4] Java Tutorial Series (Available) - DVD (90 min)
```

---

### D2. Full-Stack — Employee Directory (20 marks)

Build a simple employee directory with a REST API back-end and HTML front-end.

**Requirements:**

**Back-end (10 marks):**

1. **(2 marks)** Create `Employee` model with: `id` (int), `name` (String), `department` (String), `salary` (double). Include default constructor, parameterized constructor, all getters/setters.

2. **(3 marks)** Create `EmployeeService` with:
   - Pre-load 4 sample employees
   - `getAll()` — return all employees
   - `getById(int id)` — return employee or null
   - `add(Employee emp)` — add and return the employee
   - `delete(int id)` — remove and return true/false
   - `searchByDepartment(String dept)` — case-insensitive match

3. **(5 marks)** Create `EmployeeController` with proper REST endpoints:
   - `GET /api/employees` — list all (200)
   - `GET /api/employees/{id}` — get one (200 or 404)
   - `POST /api/employees` — create (201)
   - `DELETE /api/employees/{id}` — delete (204 or 404)
   - `GET /api/employees/search?department=IT` — search (200)
   - Use `@CrossOrigin` for CORS support

**Front-end (10 marks):**

4. **(4 marks)** Create `employees.html` with:
   - A styled header bar with "Employee Directory" title
   - A table that loads all employees on page load (via JavaScript fetch)
   - Each row shows: ID, Name, Department, Salary, a Delete button
   - The Delete button calls the DELETE endpoint and removes the row

5. **(4 marks)** Create `add-employee.html` with:
   - A form with inputs: Name (text), Department (dropdown: IT, Finance, HR, Marketing), Salary (number)
   - On submit: use JavaScript to POST to the API, display success/error message
   - Use `event.preventDefault()` and `fetch()`

6. **(2 marks)** Create `styles.css` with:
   - Consistent styling shared across both pages
   - Styled table with header color, zebra rows, hover effect
   - Styled form with labels, input padding, and a colored submit button

---

### D2 — Answer Key (Model Solution)

**Employee.java:**
```java
public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee() {}

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}
```

**EmployeeService.java:**
```java
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private List<Employee> employees = new ArrayList<>();
    private int nextId = 1;

    public EmployeeService() {
        add(new Employee(0, "Ravi Kumar", "IT", 75000));
        add(new Employee(0, "Priya Shah", "Finance", 82000));
        add(new Employee(0, "Amit Verma", "IT", 68000));
        add(new Employee(0, "Neha Gupta", "HR", 71000));
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) return emp;
        }
        return null;
    }

    public Employee add(Employee emp) {
        emp.setId(nextId++);
        employees.add(emp);
        return emp;
    }

    public boolean delete(int id) {
        return employees.removeIf(emp -> emp.getId() == id);
    }

    public List<Employee> searchByDepartment(String dept) {
        List<Employee> results = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDepartment().equalsIgnoreCase(dept)) {
                results.add(emp);
            }
        }
        return results;
    }
}
```

**EmployeeController.java:**
```java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable int id) {
        Employee emp = employeeService.getById(id);
        if (emp == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(emp);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee created = employeeService.add(employee);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = employeeService.delete(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Employee> searchByDepartment(@RequestParam String department) {
        return employeeService.searchByDepartment(department);
    }
}
```

**employees.html:**
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Directory</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header><h1>Employee Directory</h1></header>
    <nav><a href="employees.html">All Employees</a> | <a href="add-employee.html">Add Employee</a></nav>
    <div class="container">
        <h2>All Employees</h2>
        <table>
            <thead>
                <tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th><th>Action</th></tr>
            </thead>
            <tbody id="empTable"></tbody>
        </table>
    </div>
    <script>
        function loadEmployees() {
            fetch("http://localhost:8080/api/employees")
                .then(response => response.json())
                .then(employees => {
                    const tbody = document.getElementById("empTable");
                    tbody.innerHTML = "";
                    employees.forEach(emp => {
                        const row = document.createElement("tr");
                        row.innerHTML = `
                            <td>${emp.id}</td>
                            <td>${emp.name}</td>
                            <td>${emp.department}</td>
                            <td>Rs.${emp.salary.toLocaleString()}</td>
                            <td><button onclick="deleteEmployee(${emp.id})">Delete</button></td>
                        `;
                        tbody.appendChild(row);
                    });
                });
        }

        function deleteEmployee(id) {
            fetch("http://localhost:8080/api/employees/" + id, { method: "DELETE" })
                .then(() => loadEmployees());
        }

        loadEmployees();
    </script>
</body>
</html>
```

**add-employee.html:**
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Employee</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header><h1>Employee Directory</h1></header>
    <nav><a href="employees.html">All Employees</a> | <a href="add-employee.html">Add Employee</a></nav>
    <div class="container">
        <h2>Add New Employee</h2>
        <form id="addForm">
            <label>Name: <input type="text" id="name" required></label>
            <label>Department:
                <select id="department">
                    <option value="IT">IT</option>
                    <option value="Finance">Finance</option>
                    <option value="HR">HR</option>
                    <option value="Marketing">Marketing</option>
                </select>
            </label>
            <label>Salary: <input type="number" id="salary" required></label>
            <button type="submit">Add Employee</button>
        </form>
        <p id="result"></p>
    </div>
    <script>
        document.getElementById("addForm").addEventListener("submit", function(event) {
            event.preventDefault();
            const employee = {
                name: document.getElementById("name").value,
                department: document.getElementById("department").value,
                salary: parseFloat(document.getElementById("salary").value)
            };
            fetch("http://localhost:8080/api/employees", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(employee)
            })
            .then(response => response.json())
            .then(data => {
                document.getElementById("result").textContent =
                    "Employee added: " + data.name + " (ID: " + data.id + ")";
                document.getElementById("addForm").reset();
            });
        });
    </script>
</body>
</html>
```

**styles.css:**
```css
body { font-family: 'Segoe UI', sans-serif; margin: 0; background: #f5f6fa; }
header { background: #1a5276; color: white; padding: 15px 30px; }
header h1 { margin: 0; }
nav { background: #2c3e50; padding: 10px 30px; }
nav a { color: #ecf0f1; text-decoration: none; margin-right: 15px; }
nav a:hover { text-decoration: underline; }
.container { max-width: 800px; margin: 30px auto; padding: 0 20px; }
table { width: 100%; border-collapse: collapse; }
th { background: #1a5276; color: white; padding: 10px; text-align: left; }
td { padding: 10px; border-bottom: 1px solid #ddd; }
tr:nth-child(even) { background: #eaf2f8; }
tr:hover { background: #d4e6f1; }
form { display: flex; flex-direction: column; gap: 15px; max-width: 400px; }
label { font-weight: bold; }
input, select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; width: 100%; }
button { background: #1a5276; color: white; padding: 10px; border: none; border-radius: 4px; cursor: pointer; }
button:hover { background: #2c3e50; }
#result { color: #27ae60; font-weight: bold; }
```

---

### D3. SQL + JDBC + DevOps — Employee Database & Deployment (20 marks)

**Part A: SQL (7 marks)**

Given these tables:

```sql
CREATE TABLE departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL,
    location VARCHAR(50)
);

CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(100) NOT NULL,
    dept_id INT,
    salary DOUBLE,
    hire_date DATE,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);
```

Sample data:
```sql
INSERT INTO departments VALUES (1, 'IT', 'Mumbai');
INSERT INTO departments VALUES (2, 'Finance', 'Delhi');
INSERT INTO departments VALUES (3, 'HR', 'Bangalore');

INSERT INTO employees VALUES (101, 'Ravi Kumar', 1, 75000, '2025-06-15');
INSERT INTO employees VALUES (102, 'Priya Shah', 2, 82000, '2025-03-20');
INSERT INTO employees VALUES (103, 'Amit Verma', 1, 68000, '2025-09-01');
INSERT INTO employees VALUES (104, 'Neha Gupta', 3, 71000, '2025-07-10');
INSERT INTO employees VALUES (105, 'Karan Patel', 2, 90000, '2025-01-05');
INSERT INTO employees VALUES (106, 'Sneha Rao', 1, 72000, '2026-01-15');
```

Write SQL queries for:

1. **(1 mark)** List all employees in the IT department with their department location (requires JOIN).

2. **(2 marks)** Find the department name, number of employees, and average salary per department. Sort by average salary descending.

3. **(2 marks)** Find the highest-paid employee in each department (show employee name, department name, and salary).

4. **(2 marks)** Find all departments that have more than 1 employee and an average salary above 70,000.

**Part B: JDBC (7 marks)**

Write a `DepartmentRepository` class with:

1. **(3 marks)** `findEmployeesByDeptName(String deptName)` — takes a department name, returns `List<Employee>` using a JOIN query with PreparedStatement.

2. **(2 marks)** `getDepartmentStats()` — returns department name + employee count + average salary for each department (prints to console).

3. **(2 marks)** All methods must use PreparedStatement, try-with-resources, and proper ResultSet handling.

**Part C: DevOps (6 marks)**

1. **(2 marks)** Write a Dockerfile that containerizes a Spring Boot application that uses the employee database.

2. **(2 marks)** Write the Docker commands to build the image, run the container, and verify it's working.

3. **(2 marks)** Write a GitHub Actions CI workflow that compiles, tests, and packages the application on every push to main.

---

### D3 — Answer Key

**Part A: SQL**

**1.** IT employees with location:
```sql
SELECT e.emp_name, e.salary, d.dept_name, d.location
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
WHERE d.dept_name = 'IT';
```

**2.** Department stats:
```sql
SELECT d.dept_name, COUNT(*) AS emp_count, AVG(e.salary) AS avg_salary
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name
ORDER BY avg_salary DESC;
```

**3.** Highest-paid per department:
```sql
SELECT e.emp_name, d.dept_name, e.salary
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
WHERE e.salary = (
    SELECT MAX(e2.salary) FROM employees e2 WHERE e2.dept_id = e.dept_id
);
```

Alternative using window functions (if students know them):
```sql
SELECT emp_name, dept_name, salary FROM (
    SELECT e.emp_name, d.dept_name, e.salary,
           ROW_NUMBER() OVER (PARTITION BY e.dept_id ORDER BY e.salary DESC) AS rn
    FROM employees e JOIN departments d ON e.dept_id = d.dept_id
) ranked WHERE rn = 1;
```

**4.** Departments with >1 employee and avg salary >70000:
```sql
SELECT d.dept_name, COUNT(*) AS emp_count, AVG(e.salary) AS avg_salary
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name
HAVING COUNT(*) > 1 AND AVG(e.salary) > 70000;
```

Result: IT (3 employees, avg ~71666.67) and Finance (2 employees, avg 86000).

**Part B: JDBC**

```java
import java.sql.*;
import java.util.*;

public class DepartmentRepository {

    public List<Employee> findEmployeesByDeptName(String deptName) {
        String sql = "SELECT e.emp_id, e.emp_name, e.salary, e.hire_date " +
                     "FROM employees e JOIN departments d ON e.dept_id = d.dept_id " +
                     "WHERE d.dept_name = ?";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deptName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("emp_id"));
                    emp.setName(rs.getString("emp_name"));
                    emp.setSalary(rs.getDouble("salary"));
                    employees.add(emp);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
        }
        return employees;
    }

    public void getDepartmentStats() {
        String sql = "SELECT d.dept_name, COUNT(*) AS emp_count, AVG(e.salary) AS avg_salary " +
                     "FROM departments d JOIN employees e ON d.dept_id = e.dept_id " +
                     "GROUP BY d.dept_name ORDER BY avg_salary DESC";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-15s %-10s %-15s%n", "Department", "Count", "Avg Salary");
            System.out.println("-".repeat(40));
            while (rs.next()) {
                System.out.printf("%-15s %-10d Rs.%,.2f%n",
                        rs.getString("dept_name"),
                        rs.getInt("emp_count"),
                        rs.getDouble("avg_salary"));
            }
        } catch (SQLException e) {
            System.err.println("Stats query failed: " + e.getMessage());
        }
    }
}
```

**Part C: DevOps**

**Dockerfile:**
```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/employee-app-1.0.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

**Docker commands:**
```bash
# Build
docker build -t employee-app:1.0 .

# Run
docker run -d --name emp-app -p 8080:8080 employee-app:1.0

# Verify
docker ps
docker logs emp-app
curl http://localhost:8080/api/employees

# Cleanup
docker stop emp-app
docker rm emp-app
```

**GitHub Actions CI:**
```yaml
name: Employee App CI

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up Java 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Compile
        run: mvn compile

      - name: Run tests
        run: mvn test

      - name: Package
        run: mvn package -DskipTests
```

---

## Bonus Questions (optional, 10 extra marks)

*For trainees who finish early.*

### Bonus 1 (5 marks): Banking App Architecture Diagram

Draw (ASCII or description) the complete architecture of the banking app as it exists at the end of Day 13. Include:
- All layers (browser, JavaScript, REST API, service, repository, database)
- Data flow direction (arrows)
- Technologies used at each layer
- The DevOps pipeline (Git → CI → Docker)

---

### Bonus 2 (5 marks): Full-Stack Bug Hunt

The following code has **5 bugs** spread across the controller, JavaScript, SQL, and Dockerfile. Find all 5.

**Controller:**
```java
@GetMapping("/api/accounts/{id}")
public Account getAccount(@RequestParam int id) {
    return accountService.getById(id);
}
```

**JavaScript:**
```javascript
document.getElementById("form").addEventListener("submit", function() {
    const data = { name: document.getElementById("name").value };
    fetch("/api/accounts", {
        method: "POST",
        body: data
    })
    .then(response => response.json())
    .then(result => console.log(result));
});
```

**SQL:**
```sql
SELECT holder_name, balance FROM accounts WHERE account_type = SAVINGS ORDER BY balance;
```

**Dockerfile:**
```dockerfile
FROM eclipse-temurin:17-jdk-alpine
COPY target/app.jar /app/app.jar
RUN java -jar /app/app.jar
```

---

### Bonus Answers

**Bonus 1: Architecture Diagram**

```
┌──────────────────────────────────────────────────────────────────┐
│                        DEVOPS PIPELINE                            │
│  [Git Repo] → [GitHub Actions CI] → [Docker Build] → [Deploy]   │
│   git push     mvn compile/test      docker build     docker run │
└──────────────────────────────────────────────────────────────────┘

┌─────────────────┐    HTTP/JSON     ┌──────────────────────────────┐
│   BROWSER        │ ←────────────→  │   SPRING BOOT (port 8080)    │
│                  │                  │                               │
│  HTML            │    fetch()       │  ┌─────────────────────────┐ │
│  (structure)     │   POST/GET/      │  │  AccountController      │ │
│                  │   DELETE          │  │  @RestController        │ │
│  CSS             │                  │  │  @GetMapping            │ │
│  (styling)       │                  │  │  @PostMapping           │ │
│                  │  JSON response   │  │  ResponseEntity         │ │
│  JavaScript      │ ←────────────── │  └───────────┬─────────────┘ │
│  (fetch + DOM)   │                  │              │ calls          │
│                  │                  │  ┌───────────▼─────────────┐ │
│  event.          │                  │  │  AccountService         │ │
│  preventDefault()│                  │  │  @Service               │ │
│  JSON.stringify()│                  │  │  Business logic         │ │
│  response.json() │                  │  └───────────┬─────────────┘ │
└─────────────────┘                  │              │ calls          │
                                      │  ┌───────────▼─────────────┐ │
                                      │  │  AccountRepository      │ │
                                      │  │  PreparedStatement      │ │
                                      │  │  ResultSet mapping      │ │
                                      │  └───────────┬─────────────┘ │
                                      └──────────────┼───────────────┘
                                                     │ JDBC
                                      ┌──────────────▼───────────────┐
                                      │   H2 DATABASE                 │
                                      │   accounts table              │
                                      │   transactions table          │
                                      │   SQL (INSERT/SELECT/         │
                                      │        UPDATE/DELETE)         │
                                      └───────────────────────────────┘
```

Technologies per layer:
- **Browser**: HTML5, CSS3, JavaScript (ES6+)
- **Network**: HTTP/HTTPS, JSON, CORS
- **Controller**: Spring Boot, Jackson (JSON↔Java), annotations
- **Service**: Plain Java, business rules, validation
- **Repository**: JDBC, PreparedStatement, try-with-resources
- **Database**: H2 (SQL, tables, PK/FK constraints)
- **DevOps**: Git, GitHub Actions (CI), Docker (containerization)

(1 mark per correctly shown layer, max 5)

**Bonus 2: Five Bugs**

| # | Location | Bug | Fix |
|---|----------|-----|-----|
| 1 | Controller | `@RequestParam` should be `@PathVariable` (URL has `{id}`) | `@PathVariable int id` |
| 2 | Controller | No null check — returns null as 200 if not found | Return `ResponseEntity.notFound()` if null |
| 3 | JavaScript | Missing `event.preventDefault()` — page will refresh | Add `event.preventDefault()` as first line |
| 4 | JavaScript | Missing `headers` and `JSON.stringify()` — sending raw JS object | Add `headers: {"Content-Type":"application/json"}` and `body: JSON.stringify(data)` |
| 5 | SQL | `SAVINGS` not quoted — treated as column name | `WHERE account_type = 'SAVINGS'` |
| 6* | Dockerfile | `RUN` should be `CMD` — runs app during build | `CMD ["java", "-jar", "/app/app.jar"]` |
| 7* | Dockerfile | Missing `WORKDIR` — not critical but poor practice | Add `WORKDIR /app` |

*Students need to find any 5 of the 7 bugs for full marks.*

(1 mark per correctly identified bug, max 5)

---

## Assessment Summary Checklist (for instructor)

After grading, tally how trainees performed per module:

| Module | Days | Related Questions | Class Average |
|--------|------|------------------|---------------|
| Java Fundamentals | 1 | A1-A3, B1, C1 | ___% |
| OOP (classes, inheritance, abstract, polymorphism) | 2 | A4-A6, B2, C1, C2, D1 | ___% |
| Exception Handling | 3 | A7-A8, B3, C2, D1 | ___% |
| Collections (ArrayList, HashMap) | 4 | A9-A11, B4, C3, D1 | ___% |
| Data Structures (Stack, Queue) | 5 | A12-A13, C3 | ___% |
| File I/O (CSV, Serialization) | 7 | A14-A15, B5 | ___% |
| REST API & Spring Boot | 8 | A16-A18, B6, C4, D2 | ___% |
| HTML & CSS | 9 | A19-A21, B7, D2 | ___% |
| JavaScript & HTTP Integration | 11 | A22-A23, B8, C5, D2 | ___% |
| SQL (queries, JOINs, GROUP BY) | 11 | A24-A26, B9, C6, D3-A | ___% |
| JDBC (PreparedStatement, ResultSet) | 12 | A27-A28, B9, C6, D3-B | ___% |
| DevOps (Git, CI/CD, Docker) | 13 | A29-A30, B10, D3-C | ___% |

---

## Final Training Summary — 15-Day Banking App Journey

| Day | What We Built | Key Concepts |
|-----|--------------|-------------|
| 1 | Procedural BankApp | Variables, methods, Scanner, control flow |
| 2 | OOP Account hierarchy | Classes, inheritance, abstract, polymorphism |
| 3 | Custom exceptions | try-catch-finally, custom exceptions |
| 4 | Collections-based storage | ArrayList, HashMap, iteration |
| 5 | Stack undo + Queue requests | Stack (LIFO), Queue (FIFO), LinkedList |
| 6 | **Assessment 1** | Days 1–5 |
| 7 | File persistence | CSV, Serialization, try-with-resources |
| 8 | REST API | Spring Boot, HTTP, JSON, ResponseEntity |
| 9 | Banking portal UI | HTML5, CSS3, tables, forms |
| 10 | **Assessment 2** | Days 5–9 |
| 11 | Front-end ↔ Back-end + SQL | JavaScript fetch(), CORS, SQL CRUD |
| 12 | Database integration | JDBC, PreparedStatement, H2 |
| 13 | DevOps practices | Git, GitHub Actions CI, Docker |
| 14 | **Assessment 3** | Days 11–13 |
| 15 | **Final Assessment** | All modules |
