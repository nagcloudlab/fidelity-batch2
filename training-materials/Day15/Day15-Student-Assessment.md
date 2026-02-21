# Day 15 — All Modules Assessment (Final Comprehensive Assessment)

## Assessment Paper

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
