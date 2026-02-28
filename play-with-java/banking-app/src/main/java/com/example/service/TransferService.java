package com.example.service;

public interface TransferService {
    void transfer(long fromAccountNumber, long toAccountNumber, double amount);
}