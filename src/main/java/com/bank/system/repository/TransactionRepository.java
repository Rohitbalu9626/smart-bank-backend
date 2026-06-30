package com.bank.system.repository;

import com.bank.system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Provides BankService the exact mapping method signature it needs
    List<Transaction> findByAccount_AccountNumber(String accountNumber);
}