package com.marciocesar.walletserviceassignment.integration.api.controllers;

import com.marciocesar.walletserviceassignment.builder.CustomerBuilder;
import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.CustomerRepository;
import com.marciocesar.walletserviceassignment.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletControllerTest extends AbstractIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCreateWalletSuccessfully() throws Exception {
        CustomerEntity customerEntity = customerRepository.save(CustomerBuilder.buildEntity().build());

        mockMvc.perform(post("/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                    "customerExternalCode": "%s"
                                }
                                """, customerEntity.getCustomerExternalCode())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.walletExternalCode").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty());
    }

    @Test
    void shouldFailToCreateWalletWhenCustomerNotFound() throws Exception {
        mockMvc.perform(post("/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerExternalCode": "6a86f698-de2b-4aee-a3a5-4db2efe0a822"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailToCreateWalletWhenRequestBodyInvalid() throws Exception {
        mockMvc.perform(post("/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerExternalCodes": "6a86f698-de2b-4aee-a3a5-4db2efe0a822"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }
}