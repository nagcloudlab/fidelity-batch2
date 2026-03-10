package com.example.calculationservice;

import com.example.calculationservice.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalculatorService calculatorService;

    // ===== Context & Wiring =====

    @Test
    void contextLoads() {
        assertThat(calculatorService).isNotNull();
    }

    // ===== ADD - Controller + Service Integration =====

    @Test
    void testAdd_ReturnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/add")
                .param("a", "10")
                .param("b", "20"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("30"));
    }

    @Test
    void testAdd_WithLargeNumbers() throws Exception {
        mockMvc.perform(get("/api/calculator/add")
                .param("a", "1000000")
                .param("b", "2000000"))
            .andExpect(status().isOk())
            .andExpect(content().string("3000000"));
    }

    @Test
    void testAdd_PositiveAndNegative() throws Exception {
        mockMvc.perform(get("/api/calculator/add")
                .param("a", "50")
                .param("b", "-30"))
            .andExpect(status().isOk())
            .andExpect(content().string("20"));
    }

    // ===== SUBTRACT - Controller + Service Integration =====

    @Test
    void testSubtract_ReturnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/subtract")
                .param("a", "100")
                .param("b", "40"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("60"));
    }

    @Test
    void testSubtract_SameNumbers_ReturnsZero() throws Exception {
        mockMvc.perform(get("/api/calculator/subtract")
                .param("a", "25")
                .param("b", "25"))
            .andExpect(status().isOk())
            .andExpect(content().string("0"));
    }

    // ===== MULTIPLY - Controller + Service Integration =====

    @Test
    void testMultiply_ReturnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/multiply")
                .param("a", "7")
                .param("b", "8"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("56"));
    }

    @Test
    void testMultiply_WithOne_ReturnsSameNumber() throws Exception {
        mockMvc.perform(get("/api/calculator/multiply")
                .param("a", "99")
                .param("b", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("99"));
    }

    @Test
    void testMultiply_NegativeAndPositive() throws Exception {
        mockMvc.perform(get("/api/calculator/multiply")
                .param("a", "-5")
                .param("b", "6"))
            .andExpect(status().isOk())
            .andExpect(content().string("-30"));
    }

    // ===== DIVIDE - Controller + Service Integration =====

    @Test
    void testDivide_ReturnsCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "20")
                .param("b", "4"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("5.0"));
    }

    @Test
    void testDivide_FractionResult() throws Exception {
        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "7")
                .param("b", "2"))
            .andExpect(status().isOk())
            .andExpect(content().string("3.5"));
    }

    @Test
    void testDivide_ByZero_ThrowsIllegalArgument() {
        Exception exception = assertThrows(Exception.class, () ->
            mockMvc.perform(get("/api/calculator/divide")
                    .param("a", "10")
                    .param("b", "0"))
        );
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
    }

    // ===== ERROR HANDLING - Missing & Invalid Params =====

    @Test
    void testAdd_MissingParamB_Returns400() throws Exception {
        mockMvc.perform(get("/api/calculator/add")
                .param("a", "5"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testSubtract_MissingAllParams_Returns400() throws Exception {
        mockMvc.perform(get("/api/calculator/subtract"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testMultiply_InvalidParamType_Returns400() throws Exception {
        mockMvc.perform(get("/api/calculator/multiply")
                .param("a", "abc")
                .param("b", "5"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testDivide_InvalidParamType_Returns400() throws Exception {
        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "10")
                .param("b", "xyz"))
            .andExpect(status().isBadRequest());
    }

    // ===== HTTP METHOD VALIDATION =====

    @Test
    void testAdd_PostMethod_Returns405() throws Exception {
        mockMvc.perform(post("/api/calculator/add")
                .param("a", "5")
                .param("b", "3"))
            .andExpect(status().isMethodNotAllowed());
    }

    // ===== INVALID ENDPOINT =====

    @Test
    void testInvalidEndpoint_Returns404() throws Exception {
        mockMvc.perform(get("/api/calculator/modulus")
                .param("a", "5")
                .param("b", "3"))
            .andExpect(status().isNotFound());
    }

    // ===== END-TO-END: Service + Controller consistency =====

    @Test
    void testServiceAndControllerReturnSameResult_Add() throws Exception {
        int serviceResult = calculatorService.add(15, 25);

        mockMvc.perform(get("/api/calculator/add")
                .param("a", "15")
                .param("b", "25"))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(serviceResult)));
    }

    @Test
    void testServiceAndControllerReturnSameResult_Divide() throws Exception {
        double serviceResult = calculatorService.divide(22, 7);

        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "22")
                .param("b", "7"))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(serviceResult)));
    }
}
