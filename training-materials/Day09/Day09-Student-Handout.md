# Day 9 — HTML Basics & CSS

## Student Handout

> **What you'll build today**: Front-end pages for your Banking REST API — an account list table, a create account form, a transaction form, all styled with CSS to look like a real banking portal.

---

## 1. The Big Picture: Front-End vs Back-End

Yesterday (Day 8) we built a REST API — the **back-end**. Today we build what users actually see — the **front-end**.

```
┌────────────────┐     HTTP Request      ┌────────────────────┐
│   FRONT-END    │ ──────────────────→   │    BACK-END        │
│   (Browser)    │                        │   (Spring Boot)    │
│                │     HTTP Response      │                    │
│   HTML + CSS   │ ←──────────────────   │   REST API (JSON)  │
│   (+ JS later) │                        │   AccountService   │
└────────────────┘                        └────────────────────┘
     Day 9                                     Day 8
```

| Technology | Role | Analogy |
|-----------|------|---------|
| **HTML** | Structure — what's on the page | The blueprint of a building |
| **CSS** | Presentation — how it looks | The paint, furniture, decoration |
| **JavaScript** (Day 11) | Behavior — what it does | The electricity, plumbing |
| **REST API** (Day 8) | Data — where information comes from | The warehouse |

---

## 2. HTML Basics

### What is HTML?

**HTML** = HyperText Markup Language. It tells the browser **what** to display — headings, paragraphs, tables, forms, images, links.

### Basic HTML structure

Every HTML page follows this skeleton:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple Bank</title>
</head>
<body>
    <h1>Welcome to Simple Bank</h1>
    <p>Your trusted banking partner.</p>
</body>
</html>
```

| Element | Purpose |
|---------|---------|
| `<!DOCTYPE html>` | Tells the browser this is an HTML5 document |
| `<html>` | Root element — everything lives inside this |
| `<head>` | Metadata — title, character encoding, CSS links (not visible on the page) |
| `<title>` | Text shown in the browser tab |
| `<body>` | All visible content — what the user sees |

### Key HTML rules

- Tags come in **pairs**: `<h1>Hello</h1>` (opening + closing)
- Some tags are **self-closing**: `<br>`, `<img>`, `<input>`, `<hr>`
- Tags can be **nested**: `<div><p>text</p></div>`
- HTML is **not case-sensitive** but convention is **lowercase**

---

## 3. Common HTML Tags

### Headings

```html
<h1>Simple Bank</h1>          <!-- Main heading — use only once per page -->
<h2>Our Services</h2>         <!-- Section heading -->
<h3>Savings Account</h3>      <!-- Sub-section -->
<!-- h4, h5, h6 exist but are rarely needed -->
```

Use headings for hierarchy, not size. Control size with CSS.

### Paragraphs and text formatting

```html
<p>Open a savings account with just Rs.1000 minimum balance.</p>

<strong>Bold text</strong>     <!-- important text -->
<em>Italic text</em>           <!-- emphasized text -->
<br>                            <!-- line break -->
<hr>                            <!-- horizontal line -->
```

### div and span

```html
<!-- div = block container (takes full width, starts a new line) -->
<div>
    <h2>Account Details</h2>
    <p>Account Number: 1001</p>
</div>

<!-- span = inline container (stays in the flow of text) -->
<p>Balance: <span style="color: green;">Rs.50,000</span></p>
```

Think of `<div>` as a **cardboard box** (groups things together) and `<span>` as a **highlighter** (marks text within a line).

### Lists

```html
<!-- Unordered list (bullet points) -->
<ul>
    <li>Savings Account</li>
    <li>Current Account</li>
    <li>Fixed Deposit</li>
</ul>

<!-- Ordered list (numbered) -->
<ol>
    <li>Fill application form</li>
    <li>Submit documents</li>
    <li>Receive account number</li>
</ol>
```

### Links and images

```html
<!-- Link to another page -->
<a href="accounts.html">View Accounts</a>

<!-- Link to external website -->
<a href="https://www.rbi.org.in">Reserve Bank of India</a>

<!-- Image -->
<img src="bank-logo.png" alt="Simple Bank Logo" width="200">
```

### Banking App — Step 1: Home page

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple Bank</title>
</head>
<body>
    <h1>Simple Bank</h1>
    <p>Your trusted banking partner since 2024.</p>

    <hr>

    <h2>Quick Links</h2>
    <ul>
        <li><a href="accounts.html">View All Accounts</a></li>
        <li><a href="create-account.html">Open New Account</a></li>
        <li><a href="transaction.html">Deposit / Withdraw</a></li>
    </ul>

    <hr>
    <p><em>Simple Bank &copy; 2024</em></p>
</body>
</html>
```

Save this as `index.html` and double-click to open in your browser.

---

## 4. HTML Tables

Tables are perfect for displaying structured data — like our account list.

### Table structure

```
<table>
├── <thead>         ← header section
│   └── <tr>        ← table row
│       ├── <th>    ← header cell (bold, centered by default)
│       └── <th>
├── <tbody>         ← body section
│   └── <tr>        ← table row
│       ├── <td>    ← data cell
│       └── <td>
└── </table>
```

| Tag | Stands for | Purpose |
|-----|-----------|---------|
| `<table>` | Table | Container for the entire table |
| `<thead>` | Table Head | Groups header rows |
| `<tbody>` | Table Body | Groups data rows |
| `<tr>` | Table Row | A single row |
| `<th>` | Table Header cell | Bold header cell |
| `<td>` | Table Data cell | Regular data cell |

### Banking App — Step 2: Account list table

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Accounts — Simple Bank</title>
</head>
<body>
    <h1>All Accounts</h1>

    <table border="1">
        <thead>
            <tr>
                <th>Account No.</th>
                <th>Holder Name</th>
                <th>Type</th>
                <th>Balance (Rs.)</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1001</td>
                <td>Ravi Kumar</td>
                <td>SAVINGS</td>
                <td>50,000.00</td>
                <td>Active</td>
            </tr>
            <tr>
                <td>1002</td>
                <td>Priya Sharma</td>
                <td>CURRENT</td>
                <td>30,000.00</td>
                <td>Active</td>
            </tr>
            <tr>
                <td>1003</td>
                <td>Amit Verma</td>
                <td>SAVINGS</td>
                <td>75,000.00</td>
                <td>Active</td>
            </tr>
        </tbody>
    </table>

    <p><a href="index.html">← Back to Home</a></p>
</body>
</html>
```

> **Note**: `border="1"` is a quick way to see borders. We'll replace this with proper CSS styling later.

---

## 5. HTML Forms

Forms are how users **send data** to the server. Every create, deposit, withdraw, and search operation starts with a form.

### Form basics

```html
<form action="/api/accounts" method="POST">
    <!-- input controls go here -->
    <button type="submit">Submit</button>
</form>
```

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `action` | URL where form data is sent | `/api/accounts` (our REST API) |
| `method` | HTTP method | `GET` (search) or `POST` (create) |

### GET vs POST in forms

| | GET | POST |
|---|---|---|
| Data location | In the URL (visible) | In request body (hidden) |
| Bookmarkable | Yes | No |
| Use for | Search, filter | Create, update, sensitive data |
| Banking example | Search by name | Create new account |

---

## 6. Input Controls

### Text, Number, Email, Password

```html
<label for="name">Account Holder:</label>
<input type="text" id="name" name="holderName" placeholder="Enter full name" required>

<label for="accNo">Account Number:</label>
<input type="number" id="accNo" name="accountNumber" min="1000" max="9999" required>

<label for="email">Email:</label>
<input type="email" id="email" name="email" placeholder="ravi@example.com">

<label for="pin">PIN:</label>
<input type="password" id="pin" name="pin" maxlength="4">
```

**Important attributes:**

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `type` | Kind of input | `text`, `number`, `email`, `password` |
| `name` | Field name sent to server | `holderName`, `accountNumber` |
| `id` | Unique identifier (for `<label>`) | `name`, `accNo` |
| `placeholder` | Hint text shown when empty | `"Enter full name"` |
| `required` | Must be filled before submit | (no value needed) |
| `min`, `max` | Range for numbers | `min="1000" max="9999"` |

Always use `<label>` — clicking the label focuses the input (accessibility).

### Radio buttons (choose ONE)

```html
<label>Account Type:</label>
<label>
    <input type="radio" name="type" value="SAVINGS" checked> Savings
</label>
<label>
    <input type="radio" name="type" value="CURRENT"> Current
</label>
```

Radio buttons with the same `name` form a group — only one can be selected at a time.

### Checkboxes (choose MANY)

```html
<label>
    <input type="checkbox" name="smsAlerts" value="true"> Enable SMS Alerts
</label>
<label>
    <input type="checkbox" name="emailAlerts" value="true" checked> Enable Email Alerts
</label>
```

### Select dropdown

```html
<label for="branch">Branch:</label>
<select id="branch" name="branch">
    <option value="">-- Select Branch --</option>
    <option value="MG_ROAD">MG Road, Bangalore</option>
    <option value="ANNA_NAGAR">Anna Nagar, Chennai</option>
    <option value="BANDRA">Bandra, Mumbai</option>
</select>
```

### Textarea (multi-line text)

```html
<label for="address">Address:</label>
<textarea id="address" name="address" rows="3" cols="40" placeholder="Enter address"></textarea>
```

### Buttons

```html
<button type="submit">Create Account</button>   <!-- sends the form -->
<button type="reset">Clear Form</button>         <!-- resets all fields -->
<button type="button">Cancel</button>            <!-- does nothing by default -->
```

### Banking App — Step 3: Create Account form

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Open Account — Simple Bank</title>
</head>
<body>
    <h1>Open New Account</h1>

    <form action="/api/accounts" method="POST">
        <p>
            <label for="accNo">Account Number:</label><br>
            <input type="number" id="accNo" name="accountNumber"
                   min="1000" max="9999" required>
        </p>

        <p>
            <label for="name">Account Holder Name:</label><br>
            <input type="text" id="name" name="holderName"
                   placeholder="Enter full name" required>
        </p>

        <p>
            <label for="balance">Initial Deposit (Rs.):</label><br>
            <input type="number" id="balance" name="balance"
                   min="0" step="0.01" value="0" required>
        </p>

        <p>
            <label>Account Type:</label><br>
            <label>
                <input type="radio" name="type" value="SAVINGS" checked> Savings
            </label>
            <label>
                <input type="radio" name="type" value="CURRENT"> Current
            </label>
        </p>

        <p>
            <button type="submit">Create Account</button>
            <button type="reset">Clear</button>
        </p>
    </form>

    <p><a href="index.html">← Back to Home</a></p>
</body>
</html>
```

### Banking App — Step 4: Transaction form

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Transaction — Simple Bank</title>
</head>
<body>
    <h1>Deposit / Withdraw</h1>

    <form>
        <p>
            <label for="accNo">Account Number:</label><br>
            <input type="number" id="accNo" name="accountNumber"
                   min="1000" max="9999" required>
        </p>

        <p>
            <label>Transaction Type:</label><br>
            <label>
                <input type="radio" name="txnType" value="deposit" checked> Deposit
            </label>
            <label>
                <input type="radio" name="txnType" value="withdraw"> Withdraw
            </label>
        </p>

        <p>
            <label for="amount">Amount (Rs.):</label><br>
            <input type="number" id="amount" name="amount"
                   min="1" step="0.01" required>
        </p>

        <p>
            <button type="submit">Submit Transaction</button>
        </p>
    </form>

    <p><a href="index.html">← Back to Home</a></p>
</body>
</html>
```

> **Note**: These forms won't actually send data to the API yet — that requires JavaScript (Day 11). Today we're building the visual structure.

---

## 7. CSS Basics

### What is CSS?

**CSS** = Cascading Style Sheets — controls **how** HTML looks. Without CSS, every page looks like a plain text document.

| Without CSS | With CSS |
|---|---|
| Black text on white background | Custom colors, fonts, spacing |
| Default browser fonts | Professional typography |
| No spacing or alignment | Clean, structured layout |
| Tables with plain borders | Styled tables with hover effects |

### Three ways to add CSS

```html
<!-- 1. Inline (on the element) — avoid for real projects -->
<h1 style="color: blue; font-size: 24px;">Simple Bank</h1>

<!-- 2. Internal (in <head>) — OK for single-page demos -->
<head>
    <style>
        h1 { color: blue; }
    </style>
</head>

<!-- 3. External file (BEST practice) -->
<head>
    <link rel="stylesheet" href="styles.css">
</head>
```

**Always use external CSS** in real projects — keeps HTML clean, lets multiple pages share the same styles.

### CSS syntax

```css
selector {
    property: value;
    property: value;
}
```

Example:

```css
h1 {
    color: #1a5276;        /* dark blue */
    font-size: 28px;
    text-align: center;
}
```

---

## 8. CSS Selectors

Selectors determine **which elements** a CSS rule applies to.

| Selector | Syntax | Matches | Example |
|----------|--------|---------|---------|
| Element | `h1` | All `<h1>` elements | `h1 { color: blue; }` |
| Class | `.card` | All elements with `class="card"` | `.card { border: 1px solid #ccc; }` |
| ID | `#header` | The one element with `id="header"` | `#header { background: navy; }` |
| Descendant | `table td` | All `<td>` inside a `<table>` | `table td { padding: 10px; }` |
| Group | `h1, h2, h3` | All h1, h2, and h3 | `h1, h2 { color: blue; }` |

### Classes vs IDs

```html
<!-- Class: reusable — use for styling -->
<div class="account-card">Ravi's Account</div>
<div class="account-card">Priya's Account</div>

<!-- ID: unique — use for one specific element -->
<div id="main-header">Simple Bank</div>
```

- **Classes** (`.card`) — can be used on **multiple** elements. Use for styling.
- **IDs** (`#header`) — must be **unique** per page. Use for unique elements.

### Multiple classes on one element

```html
<p class="balance positive">Rs.50,000</p>
<p class="balance negative">Rs.-5,000</p>
```

```css
.balance  { font-weight: bold; font-size: 18px; }
.positive { color: green; }
.negative { color: red; }
```

---

## 9. CSS Properties

### Text & Font

```css
body {
    font-family: 'Segoe UI', Arial, sans-serif;   /* font stack (fallbacks) */
    font-size: 14px;
    color: #333;                                    /* text color */
    line-height: 1.6;                               /* space between lines */
}

h1 {
    font-size: 28px;
    font-weight: bold;
    text-align: center;                             /* left, center, right */
    text-transform: uppercase;                      /* UPPERCASE */
    letter-spacing: 2px;                            /* space between letters */
}
```

### Colors

```css
/* Three ways to specify colors */
color: red;                     /* named color */
color: #1a5276;                 /* hex code (most common) */
color: rgb(26, 82, 118);       /* RGB values */
```

### Backgrounds

```css
body {
    background-color: #f5f6fa;      /* light gray */
}

.header {
    background-color: #1a5276;      /* dark blue */
    color: white;                    /* white text on dark background */
}
```

### Box Model

Every HTML element is a **box** with four layers:

```
┌─────────────── margin ────────────────┐
│  ┌──────────── border ─────────────┐  │
│  │  ┌──────── padding ─────────┐   │  │
│  │  │                           │   │  │
│  │  │        CONTENT            │   │  │
│  │  │    (text, image, etc.)    │   │  │
│  │  │                           │   │  │
│  │  └───────────────────────────┘   │  │
│  └──────────────────────────────────┘  │
└────────────────────────────────────────┘
```

| Property | What it does | Example |
|----------|-------------|---------|
| `margin` | Space **outside** the border (between elements) | `margin: 10px;` |
| `border` | The visible edge | `border: 1px solid #ddd;` |
| `padding` | Space **inside** the border (between border and content) | `padding: 15px;` |
| `width` | Width of the content area | `width: 300px;` |

```css
.account-card {
    margin: 10px;
    border: 1px solid #ddd;
    padding: 15px;
    width: 300px;
    border-radius: 8px;         /* rounded corners */
}
```

> **Tip**: Open DevTools (F12) → Inspect an element → see the box model visualization. This is the #1 debugging tool for layout issues.

### Shorthand values

```css
/* All four sides */
margin: 10px;                   /* all sides 10px */

/* Vertical | Horizontal */
margin: 10px 20px;              /* top/bottom 10px, left/right 20px */

/* Top | Right | Bottom | Left (clockwise) */
margin: 10px 20px 10px 20px;
padding: 10px 15px 10px 15px;
```

---

## 10. Styling Tables

```css
table {
    width: 100%;
    border-collapse: collapse;      /* removes double borders */
}

th, td {
    border: 1px solid #ddd;
    padding: 10px 15px;
    text-align: left;
}

th {
    background-color: #1a5276;
    color: white;
}

/* Zebra stripes — every even row gets a light background */
tbody tr:nth-child(even) {
    background-color: #f2f2f2;
}

/* Highlight row on mouse hover */
tbody tr:hover {
    background-color: #d5e8f0;
}
```

**`border-collapse: collapse`** is important — without it, each cell has its own separate border, creating ugly double lines.

---

## 11. Styling Forms

```css
/* All form inputs share the same base style */
input, select, textarea {
    width: 100%;
    padding: 8px 12px;
    margin: 5px 0 15px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}

/* Blue border when the input is focused (clicked/selected) */
input:focus, select:focus, textarea:focus {
    border-color: #1a5276;
    outline: none;
    box-shadow: 0 0 4px rgba(26, 82, 118, 0.3);
}

/* Submit button */
button[type="submit"] {
    background-color: #1a5276;
    color: white;
    padding: 10px 25px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

/* Darker on hover */
button[type="submit"]:hover {
    background-color: #154360;
}
```

### Pseudo-classes

Pseudo-classes apply styles when the element is in a certain **state**:

| Pseudo-class | When it applies | Example |
|-------------|----------------|---------|
| `:hover` | Mouse is over the element | `button:hover { background: #154360; }` |
| `:focus` | Element is selected/clicked | `input:focus { border-color: blue; }` |
| `:nth-child(even)` | Every even child | `tr:nth-child(even) { background: #f2f2f2; }` |

---

## 12. Complete Styled Banking Pages

Now let's combine everything into a polished banking front-end.

### File structure

```
bank-frontend/
├── index.html              ← home page
├── accounts.html           ← account list (table)
├── create-account.html     ← new account form
├── transaction.html        ← deposit/withdraw form
└── styles.css              ← shared stylesheet
```

### styles.css (shared by all pages)

```css
/* === RESET & BASE === */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    font-size: 14px;
    color: #333;
    line-height: 1.6;
    background-color: #f5f6fa;
}

/* === HEADER === */
.header {
    background-color: #1a5276;
    color: white;
    padding: 15px 30px;
    text-align: center;
}

.header h1 {
    font-size: 24px;
    letter-spacing: 1px;
}

/* === NAVIGATION === */
.nav {
    background-color: #154360;
    padding: 10px 30px;
    text-align: center;
}

.nav a {
    color: #aed6f1;
    text-decoration: none;
    margin: 0 15px;
    font-size: 14px;
}

.nav a:hover {
    color: white;
    text-decoration: underline;
}

/* === MAIN CONTENT === */
.container {
    max-width: 900px;
    margin: 20px auto;
    padding: 20px;
    background-color: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
    color: #1a5276;
    margin-bottom: 15px;
    border-bottom: 2px solid #aed6f1;
    padding-bottom: 5px;
}

/* === TABLE === */
table {
    width: 100%;
    border-collapse: collapse;
    margin: 15px 0;
}

th, td {
    padding: 10px 15px;
    text-align: left;
    border: 1px solid #ddd;
}

th {
    background-color: #1a5276;
    color: white;
    font-weight: normal;
}

tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

tbody tr:hover {
    background-color: #eaf2f8;
}

/* === FORMS === */
.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #1a5276;
}

.form-group input,
.form-group select,
.form-group textarea {
    width: 100%;
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}

.form-group input:focus,
.form-group select:focus {
    border-color: #1a5276;
    outline: none;
    box-shadow: 0 0 4px rgba(26, 82, 118, 0.3);
}

.radio-group label {
    display: inline;
    font-weight: normal;
    margin-right: 20px;
}

/* === BUTTONS === */
.btn {
    padding: 10px 25px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    margin-right: 10px;
    text-decoration: none;
    display: inline-block;
}

.btn-primary {
    background-color: #1a5276;
    color: white;
}

.btn-primary:hover {
    background-color: #154360;
}

.btn-secondary {
    background-color: #95a5a6;
    color: white;
}

.btn-secondary:hover {
    background-color: #7f8c8d;
}

.btn-danger {
    background-color: #e74c3c;
    color: white;
}

.btn-danger:hover {
    background-color: #c0392b;
}

/* === STATUS BADGES === */
.badge {
    padding: 3px 10px;
    border-radius: 12px;
    font-size: 12px;
    font-weight: bold;
}

.badge-active {
    background-color: #d4efdf;
    color: #27ae60;
}

.badge-inactive {
    background-color: #fadbd8;
    color: #e74c3c;
}

/* === FOOTER === */
.footer {
    text-align: center;
    padding: 15px;
    color: #999;
    font-size: 12px;
    margin-top: 30px;
}
```

### Styled accounts.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accounts — Simple Bank</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>All Accounts</h2>

        <table>
            <thead>
                <tr>
                    <th>Account No.</th>
                    <th>Holder Name</th>
                    <th>Type</th>
                    <th>Balance (Rs.)</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1001</td>
                    <td>Ravi Kumar</td>
                    <td>SAVINGS</td>
                    <td>50,000.00</td>
                    <td><span class="badge badge-active">Active</span></td>
                </tr>
                <tr>
                    <td>1002</td>
                    <td>Priya Sharma</td>
                    <td>CURRENT</td>
                    <td>30,000.00</td>
                    <td><span class="badge badge-active">Active</span></td>
                </tr>
                <tr>
                    <td>1003</td>
                    <td>Amit Verma</td>
                    <td>SAVINGS</td>
                    <td>75,000.00</td>
                    <td><span class="badge badge-active">Active</span></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="create-account.html" class="btn btn-primary">+ Open New Account</a>
        </p>
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```

### Styled create-account.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Open Account — Simple Bank</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>Open New Account</h2>

        <form action="/api/accounts" method="POST">
            <div class="form-group">
                <label for="accNo">Account Number</label>
                <input type="number" id="accNo" name="accountNumber"
                       min="1000" max="9999" placeholder="e.g. 1004" required>
            </div>

            <div class="form-group">
                <label for="name">Account Holder Name</label>
                <input type="text" id="name" name="holderName"
                       placeholder="Enter full name" required>
            </div>

            <div class="form-group">
                <label for="balance">Initial Deposit (Rs.)</label>
                <input type="number" id="balance" name="balance"
                       min="0" step="0.01" value="0" required>
            </div>

            <div class="form-group radio-group">
                <label>Account Type</label><br>
                <label>
                    <input type="radio" name="type" value="SAVINGS" checked> Savings
                </label>
                <label>
                    <input type="radio" name="type" value="CURRENT"> Current
                </label>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Create Account</button>
                <button type="reset" class="btn btn-secondary">Clear</button>
            </div>
        </form>
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```

---

## Exercises

### Exercise 1: Transaction Form Page

**Problem**: Create a styled `transaction.html` page with:
- Account number input
- Transaction type (radio buttons: Deposit / Withdraw)
- Amount input
- Submit button
- Use the shared `styles.css` and include the header, nav, and footer.

<details>
<summary><strong>Solution</strong></summary>

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction — Simple Bank</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>Deposit / Withdraw</h2>

        <form>
            <div class="form-group">
                <label for="accNo">Account Number</label>
                <input type="number" id="accNo" name="accountNumber"
                       min="1000" max="9999" placeholder="e.g. 1001" required>
            </div>

            <div class="form-group radio-group">
                <label>Transaction Type</label><br>
                <label>
                    <input type="radio" name="txnType" value="deposit" checked> Deposit
                </label>
                <label>
                    <input type="radio" name="txnType" value="withdraw"> Withdraw
                </label>
            </div>

            <div class="form-group">
                <label for="amount">Amount (Rs.)</label>
                <input type="number" id="amount" name="amount"
                       min="1" step="0.01" placeholder="Enter amount" required>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Submit Transaction</button>
                <button type="reset" class="btn btn-secondary">Clear</button>
            </div>
        </form>
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```
</details>

---

### Exercise 2: Account Detail Card

**Problem**: Create `account-detail.html` that shows a single account's information in a **card-style layout** (not a table). Include: account number, holder name, type, balance, status. Style the card with a border, padding, shadow, and colored balance (green for positive).

<details>
<summary><strong>Solution</strong></summary>

Add to `styles.css`:

```css
.detail-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 25px;
    max-width: 400px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.detail-card h3 {
    color: #1a5276;
    margin-bottom: 15px;
    font-size: 20px;
}

.detail-row {
    display: flex;
    justify-content: space-between;
    padding: 8px 0;
    border-bottom: 1px solid #eee;
}

.detail-row:last-child {
    border-bottom: none;
}

.detail-label {
    color: #666;
    font-weight: bold;
}

.detail-value {
    color: #333;
}

.balance-amount {
    color: #27ae60;
    font-size: 18px;
    font-weight: bold;
}
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Detail — Simple Bank</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>Account Details</h2>

        <div class="detail-card">
            <h3>Ravi Kumar</h3>
            <div class="detail-row">
                <span class="detail-label">Account Number</span>
                <span class="detail-value">1001</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Type</span>
                <span class="detail-value">SAVINGS</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Balance</span>
                <span class="detail-value balance-amount">Rs.50,000.00</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Status</span>
                <span class="badge badge-active">Active</span>
            </div>
        </div>

        <p style="margin-top: 20px;">
            <a href="accounts.html" class="btn btn-secondary">← Back to Accounts</a>
        </p>
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```
</details>

---

### Exercise 3: Search Form with Results Table

**Problem**: Create `search.html` with:
- A search form at the top (text input for name + search button)
- A results table below showing matching accounts
- The search input should be wider than standard inputs

<details>
<summary><strong>Solution</strong></summary>

Add to `styles.css`:

```css
.search-form {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
}

.search-form input {
    flex: 1;
    padding: 10px 15px;
    border: 2px solid #1a5276;
    border-radius: 4px;
    font-size: 16px;
}

.search-form input:focus {
    outline: none;
    box-shadow: 0 0 6px rgba(26, 82, 118, 0.3);
}
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search — Simple Bank</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>Search Accounts</h2>

        <form class="search-form">
            <input type="text" name="name" placeholder="Search by account holder name..."
                   required>
            <button type="submit" class="btn btn-primary">Search</button>
        </form>

        <h3>Results</h3>
        <table>
            <thead>
                <tr>
                    <th>Account No.</th>
                    <th>Holder Name</th>
                    <th>Type</th>
                    <th>Balance (Rs.)</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1001</td>
                    <td>Ravi Kumar</td>
                    <td>SAVINGS</td>
                    <td>50,000.00</td>
                </tr>
            </tbody>
        </table>
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```
</details>

---

### Exercise 4: Styled Home Dashboard

**Problem**: Create a styled `index.html` home page with:
- Header and navigation (same as other pages)
- Two "summary cards" side by side: Total Accounts (3) and Total Balance (Rs.1,55,000)
- Quick links section below the cards

<details>
<summary><strong>Solution</strong></summary>

Add to `styles.css`:

```css
.dashboard-cards {
    display: flex;
    gap: 20px;
    margin: 20px 0;
}

.card {
    flex: 1;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    text-align: center;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.card h3 {
    color: #666;
    font-size: 14px;
    text-transform: uppercase;
    margin-bottom: 10px;
}

.card .card-value {
    font-size: 28px;
    font-weight: bold;
    color: #1a5276;
}

.quick-links {
    list-style: none;
    padding: 0;
}

.quick-links li {
    margin: 10px 0;
}

.quick-links a {
    color: #1a5276;
    text-decoration: none;
    font-size: 16px;
}

.quick-links a:hover {
    text-decoration: underline;
}
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple Bank — Home</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>Dashboard</h2>

        <div class="dashboard-cards">
            <div class="card">
                <h3>Total Accounts</h3>
                <div class="card-value">3</div>
            </div>
            <div class="card">
                <h3>Total Balance</h3>
                <div class="card-value">Rs.1,55,000</div>
            </div>
        </div>

        <h2>Quick Links</h2>
        <ul class="quick-links">
            <li><a href="accounts.html">View All Accounts</a></li>
            <li><a href="create-account.html">Open New Account</a></li>
            <li><a href="transaction.html">Deposit / Withdraw</a></li>
            <li><a href="search.html">Search Accounts</a></li>
        </ul>
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```
</details>

---

### Exercise 5: Complete Banking Portal (Challenge)

**Problem**: Build the complete set of pages — `index.html`, `accounts.html`, `create-account.html`, `transaction.html`, `search.html`, and `styles.css`. All pages must share the same header, navigation, and footer. Add these extra touches:
- "Active" page link in navigation should be styled differently (add a CSS class `nav-active`)
- Add a "Delete" button (styled in red) next to each account in the table
- Transaction form should have a "Transfer" option in addition to Deposit/Withdraw

<details>
<summary><strong>Solution</strong></summary>

Add to `styles.css`:

```css
/* Active navigation link */
.nav a.nav-active {
    color: white;
    font-weight: bold;
    border-bottom: 2px solid white;
    padding-bottom: 3px;
}

/* Small action buttons in tables */
.btn-sm {
    padding: 4px 12px;
    font-size: 12px;
    margin: 0 2px;
}
```

In each page's nav, mark the current page:

```html
<!-- In accounts.html -->
<div class="nav">
    <a href="index.html">Home</a>
    <a href="accounts.html" class="nav-active">Accounts</a>
    <a href="create-account.html">Open Account</a>
    <a href="transaction.html">Transactions</a>
</div>
```

Add action buttons to each table row:

```html
<td>
    <a href="account-detail.html" class="btn btn-primary btn-sm">View</a>
    <button class="btn btn-danger btn-sm">Delete</button>
</td>
```

Add Transfer radio option:

```html
<label>
    <input type="radio" name="txnType" value="transfer"> Transfer
</label>
```
</details>

---

## Quick Quiz

1. What is the difference between HTML and CSS?
2. What tag is used for the largest heading?
3. What is the difference between `<div>` and `<span>`?
4. What does the `action` attribute in a `<form>` specify?
5. What is the difference between radio buttons and checkboxes?
6. Name three ways to include CSS in an HTML page. Which is preferred?
7. What is the difference between a class selector (`.card`) and an ID selector (`#header`)?
8. What does `border-collapse: collapse` do on a table?

<details>
<summary><strong>Answers</strong></summary>

1. **HTML** defines the structure and content of a page (what's on the page). **CSS** controls the presentation and styling (how it looks). HTML = skeleton, CSS = clothing.

2. **`<h1>`** is the largest heading. Heading tags go from `<h1>` (largest/most important) to `<h6>` (smallest). Use `<h1>` only once per page for the main title.

3. **`<div>`** is a block-level element — it takes the full width and starts a new line. Used to group sections. **`<span>`** is inline — it stays within the flow of text. Used to style a piece of text within a paragraph.

4. The **`action`** attribute specifies the **URL** where the form data will be sent when submitted. For our banking app, `action="/api/accounts"` sends data to our REST API endpoint.

5. **Radio buttons** (same `name`) allow selecting **only one** option from a group — like choosing SAVINGS or CURRENT. **Checkboxes** allow selecting **multiple** options independently — like enabling both SMS and email alerts.

6. Three ways: **(1) Inline** — `style` attribute on the element. **(2) Internal** — `<style>` tag in `<head>`. **(3) External** — `<link rel="stylesheet" href="styles.css">`. **External is preferred** because it separates concerns, enables caching, and lets multiple pages share the same styles.

7. **Class selector (`.card`)** matches **all elements** with that class — reusable across many elements. **ID selector (`#header`)** matches the **one element** with that unique ID. Classes for styling (reusable), IDs for unique elements.

8. **`border-collapse: collapse`** merges adjacent cell borders into a single border. Without it, each cell has its own separate border, creating ugly double-width lines between cells.
</details>

---

## What We Built Today

| Step | What we built | Key Concepts |
|------|--------------|-------------|
| Step 1 | Bank home page | HTML structure, headings, lists, links |
| Step 2 | Account list table | `<table>`, `<thead>`, `<tbody>`, `<tr>`, `<th>`, `<td>` |
| Step 3 | Create account form | `<form>`, `<input>`, radio buttons, `<label>` |
| Step 4 | Transaction form | Form controls, input types, GET vs POST |
| Step 5 | Shared stylesheet | CSS selectors, properties, external CSS file |
| Step 6 | Styled accounts page | Table styling, zebra stripes, hover effects, badges |
| Step 7 | Styled create form | Form styling, focus states, button classes |

### How it all connects

```
Day 1-7:   Console BankApp (Java)
Day 8:     REST API (Spring Boot) — backend serves JSON over HTTP
Day 9:     HTML + CSS pages (static) — frontend shows forms & tables
Day 11:    JavaScript connects them — forms call REST API, tables show live data
```

The forms we built today have `action="/api/accounts"` — that's the same URL as our Day 8 REST API. On Day 11, JavaScript will bridge the gap and make these forms actually talk to the API.

## What's Next (Day 10 Preview)

Tomorrow is **assessment day** — Data Structures and HTML assessment, plus you'll create web pages to demonstrate what you learned today.
