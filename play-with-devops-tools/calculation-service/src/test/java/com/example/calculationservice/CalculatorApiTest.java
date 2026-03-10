package com.example.calculationservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/calculator";
    }

    // ---- ADD ----

    @Test
    void testAdd() {
        given()
            .queryParam("a", 5)
            .queryParam("b", 3)
        .when()
            .get("/add")
        .then()
            .statusCode(200)
            .body(equalTo("8"));
    }

    @Test
    void testAddWithNegativeNumbers() {
        given()
            .queryParam("a", -10)
            .queryParam("b", -20)
        .when()
            .get("/add")
        .then()
            .statusCode(200)
            .body(equalTo("-30"));
    }

    @Test
    void testAddWithZero() {
        given()
            .queryParam("a", 0)
            .queryParam("b", 0)
        .when()
            .get("/add")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    // ---- SUBTRACT ----

    @Test
    void testSubtract() {
        given()
            .queryParam("a", 10)
            .queryParam("b", 3)
        .when()
            .get("/subtract")
        .then()
            .statusCode(200)
            .body(equalTo("7"));
    }

    @Test
    void testSubtractResultingNegative() {
        given()
            .queryParam("a", 3)
            .queryParam("b", 10)
        .when()
            .get("/subtract")
        .then()
            .statusCode(200)
            .body(equalTo("-7"));
    }

    // ---- MULTIPLY ----

    @Test
    void testMultiply() {
        given()
            .queryParam("a", 4)
            .queryParam("b", 5)
        .when()
            .get("/multiply")
        .then()
            .statusCode(200)
            .body(equalTo("20"));
    }

    @Test
    void testMultiplyByZero() {
        given()
            .queryParam("a", 100)
            .queryParam("b", 0)
        .when()
            .get("/multiply")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    void testMultiplyNegatives() {
        given()
            .queryParam("a", -3)
            .queryParam("b", -4)
        .when()
            .get("/multiply")
        .then()
            .statusCode(200)
            .body(equalTo("12"));
    }

    // ---- DIVIDE ----

    @Test
    void testDivide() {
        given()
            .queryParam("a", 10)
            .queryParam("b", 4)
        .when()
            .get("/divide")
        .then()
            .statusCode(200)
            .body(equalTo("2.5"));
    }

    @Test
    void testDivideExact() {
        given()
            .queryParam("a", 10)
            .queryParam("b", 2)
        .when()
            .get("/divide")
        .then()
            .statusCode(200)
            .body(equalTo("5.0"));
    }

    @Test
    void testDivideByZero() {
        given()
            .queryParam("a", 10)
            .queryParam("b", 0)
        .when()
            .get("/divide")
        .then()
            .statusCode(500);
    }

    // ---- MISSING PARAMS ----

    @Test
    void testAddMissingParam() {
        given()
            .queryParam("a", 5)
        .when()
            .get("/add")
        .then()
            .statusCode(400);
    }

    @Test
    void testDivideMissingAllParams() {
        given()
        .when()
            .get("/divide")
        .then()
            .statusCode(400);
    }
}
