package com.example.web;


import com.example.dto.TransferRequestDto;
import com.example.dto.TransferResponseDto;
import com.example.entity.Transaction;
import com.example.repository.TransactionRepository;
import com.example.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/transfer")
public class TransferController {

    private TransferService transferService;
    private TransactionRepository transactionRepository;

    @Autowired
    public TransferController(TransferService transferService, TransactionRepository transactionRepository) {
        this.transferService = transferService;
        this.transactionRepository = transactionRepository;
    }

    // GET /transfer  -> show transfer form
    @RequestMapping(method = RequestMethod.GET)
    public String showTransferForm() {
        //... add any necessary model attributes here ...
        return "transfer-form"; // return the name of the view (e.g., transfer-form.html)
    }

    // POST /transfer -> process transfer form
    @RequestMapping(method = RequestMethod.POST)
    public String processTransfer(@ModelAttribute TransferRequestDto transferRequestDto, Model model ) {
        transferService.transfer(transferRequestDto.getFromAccount(), transferRequestDto.getToAccount(), transferRequestDto.getAmount());
        TransferResponseDto response = new TransferResponseDto("success", "Transfer completed successfully.");
        model.addAttribute("response", response);
        return "transfer-status"; // return the name of the view to show transfer status (e.g., transfer-status.html)
    }

    @RequestMapping(method = RequestMethod.GET, value = "/history")
    public String showTransferHistory(Model model) {
        List<Transaction> transactions= transactionRepository.findAll();
        model.addAttribute("transactions", transactions);
        return "transfer-history"; // return the name of the view to show transfer history (e.g., transfer-history.html)
    }

}
