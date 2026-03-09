package com.example;

public class TransferServiceTest {

    @org.junit.jupiter.api.Test
    public void testPerformTransfer() {
        TransferService transferService = new TransferService();
        int amount = transferService.performTransfer(10, 20);
        org.junit.jupiter.api.Assertions.assertEquals(30, amount);
    }

}