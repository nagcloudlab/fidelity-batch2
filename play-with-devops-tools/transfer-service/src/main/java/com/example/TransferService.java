package com.example;

public class TransferService {

    public int performTransfer(int amount1, int amount2) {
        int n1 = amount1;
        int n2 = amount2;
        Calculator calculator = new Calculator();
        int sum = calculator.add(n1, n2);
        System.out.println("Transfer performed. Total amount: " + sum);
        return sum;
    }

}