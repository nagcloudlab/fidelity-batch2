package com.example.repository;

import com.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByOrderByTransactionDateDesc();

    List<Transaction> findByAccountAccountNumberOrderByTransactionDateDesc(String accountNumber);

    List<Transaction> findByTransactionDateBetweenOrderByTransactionDateDesc(LocalDateTime from, LocalDateTime to);

    List<Transaction> findByAccountAccountNumberAndTransactionDateBetweenOrderByTransactionDateDesc(
            String accountNumber, LocalDateTime from, LocalDateTime to);
}
