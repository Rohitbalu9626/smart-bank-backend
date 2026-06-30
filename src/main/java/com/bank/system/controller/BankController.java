package com.bank.system.controller;

import com.bank.system.entity.Account;
import com.bank.system.entity.Transaction;
import com.bank.system.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // CRITICAL FOR FRONTEND ACCESS
@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/account")
    public ResponseEntity<Account> openAccount(@RequestBody Account account) {
        return ResponseEntity.ok(bankService.createAccount(account));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(bankService.getAccount(accountNumber));
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam String accountNumber, @RequestParam double amount) {
        bankService.depositFunds(accountNumber, amount);
        return ResponseEntity.ok("Deposit complete");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam String accountNumber, @RequestParam double amount) {
        bankService.withdrawFunds(accountNumber, amount);
        return ResponseEntity.ok("Withdrawal complete");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String sourceAccountNumber,
            @RequestParam String destAccountNumber,
            @RequestParam double amount) {
        bankService.transferFunds(sourceAccountNumber, destAccountNumber, amount);
        return ResponseEntity.ok("Transfer successful");
    }

    @GetMapping("/account/{accountNumber}/history")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable String accountNumber) {
        return ResponseEntity.ok(bankService.getTransactionHistory(accountNumber));
    }

    @GetMapping("/account/phone/{phoneNumber}")
    public ResponseEntity<Account> getAccountByPhone(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(bankService.findAccountByPhoneNumber(phoneNumber));
    }

    @GetMapping("/dashboard/suggestions")
    public ResponseEntity<List<Account>> getDashboardSuggestions() {
        return ResponseEntity.ok(bankService.getSuggestedDashboardProfiles());
    }
}