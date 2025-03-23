package com.marciocesar.walletserviceassignment.integration.api.controllers;

import com.marciocesar.walletserviceassignment.builder.CustomerBuilder;
import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.CustomerRepository;
import com.marciocesar.walletserviceassignment.integration.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.notNullValue;

class WalletControllerTest extends AbstractIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCreateWalletSuccessfully() {

        CustomerEntity customerEntity = customerRepository.save(CustomerBuilder.buildEntity().build());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                            "customerExternalCode": "%s"
                        }
                        """, customerEntity.getCustomerExternalCode()))
                .when()
                .post("/wallets")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("walletExternalCode", notNullValue())
                .body("creationDate", notNullValue());
    }

    @Test
    void shouldFailToCreateWalletWhenCustomerNotFound() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "customerExternalCode": "6a86f698-de2b-4aee-a3a5-4db2efe0a822"
                        }
                        """)
                .when()
                .post("/wallets")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldFailToCreateWalletWhenRequestBodyInvalid() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "customerExternalCodes": "6a86f698-de2b-4aee-a3a5-4db2efe0a822"
                        }
                        """)
                .when()
                .post("/wallets")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())  // Validation error
                .body("errors", notNullValue());  // Assuming standard error response has validation errors
    }

}