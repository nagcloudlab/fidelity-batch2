package com.example.service;

import com.example.entity.Account;
import com.example.entity.Transaction;
import com.example.entity.TransactionType;
import com.example.exception.AccountNotFoundException;
import com.example.exception.InsufficientBalanceException;
import com.example.exception.InvalidTransferException;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpiTransferService implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(UpiTransferService.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public UpiTransferService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public void transfer(String senderAccountNumber, String beneficiaryAccountNumber, double amount) {
        logger.info("Initiating fund transfer: {} from {} to {}", amount, senderAccountNumber, beneficiaryAccountNumber);

        if (senderAccountNumber.equals(beneficiaryAccountNumber)) {
            throw new InvalidTransferException("Sender and beneficiary accounts cannot be the same");
        }

        if (amount <= 0) {
            throw new InvalidTransferException("Transfer amount must be greater than zero");
        }

        Account senderAccount = accountRepository.findById(senderAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException(senderAccountNumber));

        Account beneficiaryAccount = accountRepository.findById(beneficiaryAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException(beneficiaryAccountNumber));

        if (senderAccount.getBalance() < amount) {
            throw new InsufficientBalanceException(senderAccountNumber, amount, senderAccount.getBalance());
        }

        senderAccount.debit(amount);
        beneficiaryAccount.credit(amount);

        accountRepository.save(senderAccount);
        accountRepository.save(beneficiaryAccount);

        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(senderAccount);
        debitTransaction.setAmount(amount);
        debitTransaction.setType(TransactionType.DEBIT);
        debitTransaction.setDescription("UPI transfer to account " + beneficiaryAccountNumber);
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(beneficiaryAccount);
        creditTransaction.setAmount(amount);
        creditTransaction.setType(TransactionType.CREDIT);
        creditTransaction.setDescription("UPI transfer from account " + senderAccountNumber);
        transactionRepository.save(creditTransaction);

        logger.info("Fund transfer completed: {} from {} to {}", amount, senderAccountNumber, beneficiaryAccountNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionHistory(String accountNumber, LocalDate fromDate, LocalDate toDate) {
        boolean hasAccountFilter = accountNumber != null && !accountNumber.isBlank();
        boolean hasDateFilter = fromDate != null && toDate != null;

        if (hasAccountFilter && hasDateFilter) {
            LocalDateTime from = fromDate.atStartOfDay();
            LocalDateTime to = toDate.plusDays(1).atStartOfDay();
            return transactionRepository.findByAccountAccountNumberAndTransactionDateBetweenOrderByTransactionDateDesc(
                    accountNumber, from, to);
        }

        if (hasAccountFilter) {
            return transactionRepository.findByAccountAccountNumberOrderByTransactionDateDesc(accountNumber);
        }

        if (hasDateFilter) {
            LocalDateTime from = fromDate.atStartOfDay();
            LocalDateTime to = toDate.plusDays(1).atStartOfDay();
            return transactionRepository.findByTransactionDateBetweenOrderByTransactionDateDesc(from, to);
        }

        return transactionRepository.findAllByOrderByTransactionDateDesc();
    }
}
