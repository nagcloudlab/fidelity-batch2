package com.bank.controller;

import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for banking operations.
 * All endpoints return ResponseEntity with proper HTTP status codes.
 * @CrossOrigin allows requests from any origin (for front-end integration).
 */
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    // Constructor injection — Spring auto-provides the AccountService
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET /api/accounts — list all accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);    // 200
    }

    // GET /api/accounts/{accountNumber} — get by account number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable long accountNumber) {
        Account acc = accountService.getByAccountNumber(accountNumber);
        if (acc == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Account #" + accountNumber + " not found"));    // 404
        }
        return ResponseEntity.ok(acc);    // 200
    }

    // POST /api/accounts — create new account
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account created = accountService.createAccount(account);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Account #" + account.getAccountNumber() + " already exists"));    // 400
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);    // 201
    }

    // PUT /api/accounts/{accountNumber} — update account
    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccount(@PathVariable long accountNumber,
                                           @RequestBody Account account) {
        Account updated = accountService.updateAccount(accountNumber, account);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Account #" + accountNumber + " not found"));    // 404
        }
        return ResponseEntity.ok(updated);    // 200
    }

    // DELETE /api/accounts/{accountNumber} — delete account
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable long accountNumber) {
        boolean deleted = accountService.deleteAccount(accountNumber);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Account #" + accountNumber + " not found"));    // 404
        }
        return ResponseEntity.noContent().build();    // 204
    }

    // POST /api/accounts/{accountNumber}/deposit?amount=X — deposit money
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<?> deposit(@PathVariable long accountNumber,
                                     @RequestParam double amount) {
        try {
            Account acc = accountService.deposit(accountNumber, amount);
            if (acc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Account #" + accountNumber + " not found"));    // 404
            }
            return ResponseEntity.ok(acc);    // 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));    // 400
        }
    }

    // POST /api/accounts/{accountNumber}/withdraw?amount=X — withdraw money
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable long accountNumber,
                                      @RequestParam double amount) {
        try {
            Account acc = accountService.withdraw(accountNumber, amount);
            if (acc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Account #" + accountNumber + " not found"));    // 404
            }
            return ResponseEntity.ok(acc);    // 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));    // 400
        }
    }

    // GET /api/accounts/search?name=X — search accounts by holder name
    @GetMapping("/search")
    public ResponseEntity<List<Account>> searchByName(@RequestParam String name) {
        List<Account> accounts = accountService.searchByName(name);
        return ResponseEntity.ok(accounts);    // 200
    }

    // GET /api/accounts/filter?type=X — filter accounts by type (SAVINGS/CURRENT)
    @GetMapping("/filter")
    public ResponseEntity<List<Account>> filterByType(@RequestParam String type) {
        List<Account> accounts = accountService.filterByType(type);
        return ResponseEntity.ok(accounts);    // 200
    }
}
