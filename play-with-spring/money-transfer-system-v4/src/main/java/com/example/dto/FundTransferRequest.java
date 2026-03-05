package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FundTransferRequest {

    @NotBlank(message = "Sender account number is required")
    private String senderAccountNumber;

    @NotBlank(message = "Beneficiary account number is required")
    private String beneficiaryAccountNumber;

    @Positive(message = "Transfer amount must be greater than zero")
    private double amount;
}
