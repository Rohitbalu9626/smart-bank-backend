package com.bank.system.service;

import com.bank.system.entity.Account;
import com.bank.system.entity.Transaction;
import com.bank.system.entity.UserProfile;
import com.bank.system.repository.AccountRepository;
import com.bank.system.repository.TransactionRepository;
import com.bank.system.repository.UserProfileRepository; // 🆕 Added missing import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserProfileRepository userProfileRepository; // 🆕 Injected to save data to user_profiles table

    @Transactional
    public Account createAccount(Account account) {
        // 1. Save the main bank account details to the 'account' table
        Account savedAccount = accountRepository.save(account);

        // 2. ⚡ THE FIX: Automatically generate a matching UserProfile for authentication
        UserProfile profile = new UserProfile();
        profile.setUsername(savedAccount.getAccountNumber()); // Uses the account number as username
        profile.setPassword("BaluBank2026");                 // Sets standard default password
        profile.setRole("ROLE_USER");                         // Sets standard user role

        // 3. Save to the empty 'user_profiles' table inside MySQL
        userProfileRepository.save(profile);

        return savedAccount;
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account profile not found inside MySQL database server."));
    }

    @Transactional
    public void depositFunds(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
        accountRepository.save(account);

        // Automatically record this deposit to your ledger table
        Transaction tx = new Transaction("DEPOSIT", amount, account);
        transactionRepository.save(tx);
    }

    @Transactional
    public void withdrawFunds(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (!account.withdraw(amount)) {
            throw new RuntimeException("Insufficient funds!");
        }
        accountRepository.save(account);

        // Automatically record this withdrawal to your ledger table
        Transaction tx = new Transaction("WITHDRAWAL", amount, account);
        transactionRepository.save(tx);
    }

    @Transactional
    public void transferFunds(String sourceAccountNumber, String destAccountNumber, double amount) {
        Account sourceAccount = getAccount(sourceAccountNumber);
        Account destAccount = getAccount(destAccountNumber);

        if (!sourceAccount.withdraw(amount)) {
            throw new RuntimeException("Transfer declined! Insufficient funds in source account.");
        }
        destAccount.deposit(amount);

        accountRepository.save(sourceAccount);
        accountRepository.save(destAccount);

        // Record both sides of the transfer transaction history
        Transaction debitTx = new Transaction("TRANSFER_DEBIT", amount, sourceAccount);
        Transaction creditTx = new Transaction("TRANSFER_CREDIT", amount, destAccount);

        transactionRepository.save(debitTx);
        transactionRepository.save(creditTx);
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionRepository.findByAccount_AccountNumber(accountNumber);
    }

    public Account findAccountByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("No wallet profile linked to this mobile number."));
    }

    public List<Account> getSuggestedDashboardProfiles() {
        return accountRepository.findTop5By();
    }
}