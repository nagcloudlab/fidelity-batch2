-- =============================================
-- Day 11 - SQL Fundamentals for Banking App
--
-- Demonstrates:
-- - CREATE TABLE with data types and constraints
-- - PRIMARY KEY, FOREIGN KEY, NOT NULL, DEFAULT, AUTO_INCREMENT
-- - INSERT — adding sample data (single and bulk inserts)
-- - SELECT — querying with WHERE, ORDER BY, LIKE
-- - Aggregate functions — COUNT, SUM, AVG, MAX, MIN
-- - GROUP BY — summarizing data by category
-- - JOIN — combining data from related tables
-- - UPDATE and DELETE with WHERE clauses
--
-- Database: H2 (in-memory, Java-based — no installation needed)
-- These queries also work in MySQL/PostgreSQL with minor adjustments.
-- =============================================


-- =============================================
-- 1. CREATE TABLES
-- =============================================

-- Accounts table — stores bank account information
-- PRIMARY KEY: account_number uniquely identifies each account
-- NOT NULL: holder_name and type must have values
-- DEFAULT: balance starts at 0, active starts at TRUE
CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    holder_name    VARCHAR(100) NOT NULL,
    balance        DOUBLE DEFAULT 0,
    type           VARCHAR(20) NOT NULL,
    active         BOOLEAN DEFAULT TRUE
);

-- Transactions table — stores deposit/withdraw history
-- AUTO_INCREMENT: id is automatically assigned by the database
-- FOREIGN KEY: account_number must reference an existing account
-- TIMESTAMP: records when the transaction occurred
CREATE TABLE transactions (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    type            VARCHAR(20) NOT NULL,
    amount          DOUBLE NOT NULL,
    account_number  BIGINT NOT NULL,
    balance_before  DOUBLE,
    balance_after   DOUBLE,
    timestamp       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);


-- =============================================
-- 2. INSERT — Add sample data
-- =============================================

-- Insert individual accounts
INSERT INTO accounts (account_number, holder_name, balance, type)
VALUES (1001, 'Ravi Kumar', 50000, 'SAVINGS');

-- Insert multiple accounts in one statement (bulk insert)
INSERT INTO accounts (account_number, holder_name, balance, type) VALUES
(1002, 'Priya Sharma', 30000, 'CURRENT'),
(1003, 'Amit Verma', 75000, 'SAVINGS'),
(1004, 'Sneha Reddy', 120000, 'CURRENT'),
(1005, 'Vikram Patel', 15000, 'SAVINGS');

-- Insert sample transactions
INSERT INTO transactions (type, amount, account_number, balance_before, balance_after) VALUES
('DEPOSIT',  5000,  1001, 50000, 55000),
('WITHDRAW', 2000,  1001, 55000, 53000),
('DEPOSIT',  10000, 1002, 30000, 40000),
('DEPOSIT',  5000,  1003, 75000, 80000),
('WITHDRAW', 8000,  1003, 80000, 72000),
('DEPOSIT',  25000, 1004, 120000, 145000);


-- =============================================
-- 3. SELECT — Query data
-- =============================================

-- Get ALL accounts (all columns)
SELECT * FROM accounts;

-- Get specific columns only
SELECT account_number, holder_name, balance FROM accounts;

-- =============================================
-- 3a. WHERE — Filter rows
-- =============================================

-- Find only SAVINGS accounts
SELECT * FROM accounts WHERE type = 'SAVINGS';

-- Find accounts with balance greater than Rs.50,000
SELECT * FROM accounts WHERE balance > 50000;

-- Multiple conditions with AND
SELECT * FROM accounts WHERE type = 'SAVINGS' AND balance > 40000;

-- Multiple conditions with OR
SELECT * FROM accounts WHERE type = 'SAVINGS' OR balance > 100000;

-- Pattern matching with LIKE (% = any characters)
SELECT * FROM accounts WHERE holder_name LIKE '%Kumar%';

-- Find active accounts only
SELECT * FROM accounts WHERE active = TRUE;


-- =============================================
-- 3b. ORDER BY — Sort results
-- =============================================

-- Sort by balance, highest first (DESC = descending)
SELECT * FROM accounts ORDER BY balance DESC;

-- Sort by name, A to Z (ASC = ascending, which is the default)
SELECT * FROM accounts ORDER BY holder_name ASC;

-- Sort by type first, then by balance within each type
SELECT * FROM accounts ORDER BY type, balance DESC;


-- =============================================
-- 3c. Aggregate Functions — Calculate summaries
-- =============================================

-- COUNT — how many accounts exist?
SELECT COUNT(*) AS total_accounts FROM accounts;

-- SUM — total balance across all accounts
SELECT SUM(balance) AS total_balance FROM accounts;

-- AVG — average account balance
SELECT AVG(balance) AS average_balance FROM accounts;

-- MAX — highest balance
SELECT MAX(balance) AS highest_balance FROM accounts;

-- MIN — lowest balance
SELECT MIN(balance) AS lowest_balance FROM accounts;

-- Find the account with the highest balance
SELECT * FROM accounts ORDER BY balance DESC LIMIT 1;

-- Alternative: using a subquery
SELECT * FROM accounts WHERE balance = (SELECT MAX(balance) FROM accounts);


-- =============================================
-- 3d. GROUP BY — Summarize by category
-- =============================================

-- Count accounts and total balance by type (SAVINGS vs CURRENT)
SELECT type,
       COUNT(*) AS count,
       SUM(balance) AS total_balance,
       AVG(balance) AS avg_balance
FROM accounts
GROUP BY type;
-- Result:
-- type     | count | total_balance | avg_balance
-- SAVINGS  | 3     | 140000        | 46666.67
-- CURRENT  | 2     | 150000        | 75000.00


-- =============================================
-- 4. JOIN — Combine data from multiple tables
-- =============================================

-- INNER JOIN: show transactions with account holder names
-- Links transactions to accounts using the shared account_number column
SELECT
    t.id,
    t.type AS txn_type,
    t.amount,
    a.holder_name,
    a.account_number,
    t.balance_before,
    t.balance_after,
    t.timestamp
FROM transactions t
INNER JOIN accounts a ON t.account_number = a.account_number
ORDER BY t.timestamp DESC;

-- LEFT JOIN: show ALL accounts, even those with no transactions
-- Accounts without transactions will have NULL in the transaction columns
SELECT
    a.account_number,
    a.holder_name,
    a.balance,
    t.type AS txn_type,
    t.amount
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
ORDER BY a.account_number;

-- Find accounts that have NO transactions
SELECT a.*
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
WHERE t.id IS NULL;

-- Alternative: using NOT IN with a subquery
SELECT * FROM accounts
WHERE account_number NOT IN (
    SELECT DISTINCT account_number FROM transactions
);


-- =============================================
-- 5. Transaction-specific queries
-- =============================================

-- All transactions for account #1001, newest first
SELECT * FROM transactions
WHERE account_number = 1001
ORDER BY timestamp DESC;

-- Total deposited across ALL accounts
SELECT SUM(amount) AS total_deposited
FROM transactions
WHERE type = 'DEPOSIT';

-- Total withdrawn from account #1001
SELECT SUM(amount) AS total_withdrawn
FROM transactions
WHERE account_number = 1001 AND type = 'WITHDRAW';

-- Transaction count per account
SELECT
    a.holder_name,
    a.account_number,
    COUNT(t.id) AS txn_count
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
GROUP BY a.account_number, a.holder_name
ORDER BY txn_count DESC;


-- =============================================
-- 6. UPDATE — Modify existing data
-- =============================================

-- Update balance after a deposit
UPDATE accounts SET balance = 55000 WHERE account_number = 1001;

-- Deactivate an account
UPDATE accounts SET active = FALSE WHERE account_number = 1005;

-- Update multiple columns at once
UPDATE accounts SET holder_name = 'Ravi K.', type = 'CURRENT'
WHERE account_number = 1001;

-- WARNING: Always use WHERE with UPDATE!
-- Without WHERE, ALL rows are updated:
-- UPDATE accounts SET active = FALSE;   <-- DANGEROUS: deactivates EVERY account!


-- =============================================
-- 7. DELETE — Remove data
-- =============================================

-- Delete a specific account (must delete related transactions first due to FK)
-- DELETE FROM transactions WHERE account_number = 1005;
-- DELETE FROM accounts WHERE account_number = 1005;

-- Delete all inactive accounts
-- DELETE FROM accounts WHERE active = FALSE;

-- WARNING: Always use WHERE with DELETE!
-- Without WHERE, ALL rows are deleted:
-- DELETE FROM accounts;   <-- DANGEROUS: deletes ALL accounts!


-- =============================================
-- REFERENCE: CRUD maps to REST
-- =============================================
-- | SQL          | REST   | HTTP Method | Banking Example              |
-- |--------------|--------|-------------|------------------------------|
-- | INSERT INTO  | Create | POST        | POST /api/accounts           |
-- | SELECT       | Read   | GET         | GET  /api/accounts/1001      |
-- | UPDATE       | Update | PUT         | PUT  /api/accounts/1001      |
-- | DELETE FROM  | Delete | DELETE      | DELETE /api/accounts/1001     |
--
-- On Day 12, these SQL operations are connected to Java code using JDBC.


-- =============================================
-- REFERENCE: Common SQL Data Types
-- =============================================
-- | SQL Type      | Java Equivalent  | Use for                    |
-- |---------------|------------------|----------------------------|
-- | INT / BIGINT  | int / long       | Account numbers, IDs       |
-- | VARCHAR(100)  | String           | Names, descriptions        |
-- | DOUBLE        | double           | Balances, amounts          |
-- | BOOLEAN       | boolean          | Active/inactive flags      |
-- | TIMESTAMP     | LocalDateTime    | Transaction dates          |
