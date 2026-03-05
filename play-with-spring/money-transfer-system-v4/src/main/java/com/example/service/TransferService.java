package com.example.service;

import com.example.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransferService {

    void transfer(String senderAccountNumber, String beneficiaryAccountNumber, double amount);

    List<Transaction> getTransactionHistory(String accountNumber, LocalDate fromDate, LocalDate toDate);
}
