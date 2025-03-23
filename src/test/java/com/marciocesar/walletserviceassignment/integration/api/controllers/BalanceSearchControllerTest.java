package com.marciocesar.walletserviceassignment.integration.api.controllers;

import com.marciocesar.walletserviceassignment.builder.BalanceBuilder;
import com.marciocesar.walletserviceassignment.builder.CustomerBuilder;
import com.marciocesar.walletserviceassignment.builder.WalletBuilder;
import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.CustomerRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BalanceSearchControllerTest extends AbstractIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    private WalletEntity walletEntity;
    private LocalDateTime updateDate;
    private BalanceEntity balanceEntity;

    @BeforeEach
    void setUp() {
        updateDate = LocalDate.of(2025, 2, 1).atStartOfDay();
        final var customerEntity = customerRepository.save(CustomerBuilder.buildEntity().build());
        walletEntity = walletRepository.save(WalletBuilder.buildEntity(customerEntity).build());
        balanceEntity = balanceRepository.save(BalanceBuilder.buildEntity(walletEntity)
                .amount(BigDecimal.ONE)
                .updateDate(updateDate)
                .build());
    }

    @Test
    void shouldReturnBalanceWhenWalletExists() throws Exception {
        mockMvc.perform(get("/wallets/{walletExternalCode}/balances", walletEntity.getWalletExternalCode())
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-10-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount").value("1.0"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty());
    }

    @Test
    void shouldReturn404WhenWalletDoesNotExist() throws Exception {
        UUID invalidWalletExternalCode = UUID.randomUUID();

        mockMvc.perform(get("/wallets/{walletExternalCode}/balances", invalidWalletExternalCode))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty());
    }

    @Test
    void shouldReturnDailySummariesWhenValidPeriodProvided() throws Exception {

        balanceEntity.setAmount(BigDecimal.ONE);
        balanceEntity.setUpdateDate(updateDate.plusDays(1));
        balanceRepository.save(balanceEntity);

        balanceEntity.setAmount(BigDecimal.TEN);
        balanceEntity.setUpdateDate(updateDate.plusDays(2));
        balanceRepository.save(balanceEntity);

        mockMvc.perform(get("/wallets/{walletExternalCode}/balances/statement", walletEntity.getWalletExternalCode())
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-10-31")
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[0].date").isNotEmpty())
                .andExpect(jsonPath("$.content[0].amount").value("10.0"));
    }

    @Test
    void shouldReturn404WhenStatementWalletDoesNotExist() throws Exception {
        mockMvc.perform(get("/wallets/{walletExternalCode}/balances/statement", walletEntity.getWalletExternalCode())
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-10-31")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    void shouldReturn400WhenMissingRequiredParamsForStatement() throws Exception {
        mockMvc.perform(get("/wallets/{walletExternalCode}/balances/statement", walletEntity.getWalletExternalCode()))
                .andExpect(status().isBadRequest());
    }
}
