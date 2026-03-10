package com.example.calculationservice;

import com.example.calculationservice.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
class CalculatorServiceTest {

    CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void testAdd() {
        assertEquals(8, calculatorService.add(5, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(2, calculatorService.subtract(5, 3));
    }

    @Test
    void testMultiply() {
        assertEquals(15, calculatorService.multiply(5, 3));
    }

    @Test
    void testDivide() {
        assertEquals(2.5, calculatorService.divide(5, 2));
    }

    @Test
    void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> calculatorService.divide(5, 0));
    }
}
