package com.marciocesar.walletserviceassignment.integration.api.controllers;

import com.marciocesar.walletserviceassignment.builder.BalanceBuilder;
import com.marciocesar.walletserviceassignment.builder.CustomerBuilder;
import com.marciocesar.walletserviceassignment.builder.WalletBuilder;
import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.CustomerRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.integration.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.*;

class BalanceSearchControllerTest extends AbstractIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    private CustomerEntity customerEntity;
    private WalletEntity walletEntity;

    @BeforeEach
    void setUp() {
        customerEntity = customerRepository.save(CustomerBuilder.buildEntity().build());
        walletEntity = walletRepository.save(WalletBuilder.buildEntity(customerEntity).build());
        balanceRepository.save(BalanceBuilder.buildEntity(walletEntity)
                .amount(BigDecimal.ONE)
                .updateDate(LocalDate.of(2025, 2, 1).atStartOfDay())
                .build());
    }

    @Test
    void shouldReturnBalanceWhenWalletExists() {

        RestAssured.given()
                .pathParam("walletExternalCode", walletEntity.getWalletExternalCode())
                .queryParam("startDate", "2025-01-01")
                .queryParam("endDate", "2025-10-31")
                .when()
                .get("/wallets/{walletExternalCode}/balances")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("amount", equalTo(BigDecimal.ONE.toString()))
                .body("updateDate", notNullValue());
    }

    @Test
    void shouldReturn404WhenWalletDoesNotExist() {
        UUID invalidWalletExternalCode = UUID.randomUUID();

        RestAssured.given()
                .pathParam("walletExternalCode", invalidWalletExternalCode)
                .when()
                .get("/wallets/{walletExternalCode}/balances")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(ContentType.JSON)
                .body("error", containsString("WalletNotFoundException"));
    }

    @Test
    void shouldReturnDailySummariesWhenValidPeriodProvided() {
        UUID walletExternalCode = UUID.randomUUID(); // Substituir por um wallet válido ou mockado no banco.
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now();

        RestAssured.given()
                .pathParam("walletExternalCode", walletExternalCode)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .queryParam("page", 0)
                .queryParam("size", 5)
                .when()
                .get("/wallets/{walletExternalCode}/balances/statement")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("content", notNullValue())
                .body("content.size()", lessThanOrEqualTo(5))
                .body("content[0].date", notNullValue())
                .body("content[0].amount", notNullValue());
    }

    @Test
    void shouldReturn400WhenStartDateAfterEndDate() {
        UUID walletExternalCode = UUID.randomUUID(); // Substituir por um wallet válido ou mockado no banco.
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(5);

        RestAssured.given()
                .pathParam("walletExternalCode", walletExternalCode)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .queryParam("page", 0)
                .queryParam("size", 5)
                .when()
                .get("/wallets/{walletExternalCode}/balances/statement")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", containsString("Invalid date range"));
    }

    @Test
    void shouldReturn404WhenStatementWalletDoesNotExist() {
        UUID invalidWalletExternalCode = UUID.randomUUID();
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now();

        RestAssured.given()
                .pathParam("walletExternalCode", invalidWalletExternalCode)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .queryParam("page", 0)
                .queryParam("size", 5)
                .when()
                .get("/wallets/{walletExternalCode}/balances/statement")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(ContentType.JSON)
                .body("error", containsString("WalletNotFoundException"));
    }

    @Test
    void shouldReturn400WhenMissingRequiredParamsForStatement() {
        UUID walletExternalCode = UUID.randomUUID(); // Substituir por um wallet válido ou mockado no banco.

        RestAssured.given()
                .pathParam("walletExternalCode", walletExternalCode)
                .when()
                .get("/wallets/{walletExternalCode}/balances/statement")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", notNullValue());
    }
}
