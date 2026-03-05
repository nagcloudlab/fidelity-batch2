package com.example.api;


import com.example.dto.FundTransferRequest;
import com.example.dto.FundTransferResponse;
import com.example.service.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferApiController {

    private final TransferService transferService;

    public TransferApiController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public FundTransferResponse transfer(@RequestBody FundTransferRequest fundTransferRequest) {
        transferService.transfer(fundTransferRequest.getSenderAccountNumber(),
                fundTransferRequest.getBeneficiaryAccountNumber(),
                fundTransferRequest.getAmount());
        FundTransferResponse fundTransferResponse = new FundTransferResponse();
        fundTransferResponse.setStatus("success");
        fundTransferResponse.setMessage("Money transferred successfully");
        return  fundTransferResponse;
    }

}
