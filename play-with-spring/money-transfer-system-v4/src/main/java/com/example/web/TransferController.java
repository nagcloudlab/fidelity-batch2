package com.example.web;

import com.example.dto.FundTransferRequest;
import com.example.dto.FundTransferResponse;
import com.example.entity.Transaction;
import com.example.service.TransferService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public String showTransferForm(Model model) {
        model.addAttribute("transferRequest", new FundTransferRequest());
        return "transfer-form";
    }

    @PostMapping
    public String processTransfer(@Valid @ModelAttribute("transferRequest") FundTransferRequest request,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transfer-form";
        }

        try {
            transferService.transfer(
                    request.getSenderAccountNumber(),
                    request.getBeneficiaryAccountNumber(),
                    request.getAmount()
            );
            model.addAttribute("response",
                    new FundTransferResponse("success", "Fund transfer of " + request.getAmount()
                            + " from " + request.getSenderAccountNumber()
                            + " to " + request.getBeneficiaryAccountNumber()
                            + " completed successfully."));
        } catch (Exception e) {
            logger.error("Fund transfer failed: {}", e.getMessage());
            model.addAttribute("response", new FundTransferResponse("failure", e.getMessage()));
        }
        return "transfer-status";
    }

    @GetMapping("/history")
    public String showTransactionHistory(
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Model model) {
        List<Transaction> transactions = transferService.getTransactionHistory(accountNumber, fromDate, toDate);
        model.addAttribute("transactions", transactions);
        model.addAttribute("accountNumber", accountNumber);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "transfer-history";
    }
}
