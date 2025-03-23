package com.marciocesar.walletserviceassignment.integration.api.controllers;

import com.marciocesar.walletserviceassignment.builder.BalanceBuilder;
import com.marciocesar.walletserviceassignment.builder.CustomerBuilder;
import com.marciocesar.walletserviceassignment.builder.WalletBuilder;
import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.CustomerRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.FinancialMovementRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.integration.AbstractIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FinancialMovementControllerTest extends AbstractIntegrationTest {

    private static final BigDecimal A_HUNDRED_BIG_DECIMAL = BigDecimal.valueOf(100);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private FinancialMovementRepository financialMovementRepository;

    private WalletEntity walletEntity;

    @BeforeEach
    void setUp() {
        LocalDateTime updateDate = LocalDate.of(2025, 2, 1).atStartOfDay();
        CustomerEntity customerEntity = customerRepository.save(CustomerBuilder.buildEntity().build());
        walletEntity = walletRepository.save(WalletBuilder.buildEntity(customerEntity).build());
        balanceRepository.save(BalanceBuilder.buildEntity(walletEntity)
                .amount(A_HUNDRED_BIG_DECIMAL)
                .updateDate(updateDate)
                .build());
    }

    @Test
    void shouldDepositSuccessfully() throws Exception {
        mockMvc.perform(put("/wallets/balances/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "walletExternalCode": "%s",
                                            "customerExternalCode": "%s",
                                            "amount": 100.00
                                        }
                                        """, walletEntity.getWalletExternalCode(),
                                walletEntity.getCustomer().getCustomerExternalCode())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("200.0"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty());

        final var balance = balanceRepository.findByWalletWalletExternalCode(walletEntity.getWalletExternalCode()).get();
        Assertions.assertThat(balance.getAmount()).isEqualByComparingTo("200.0");
    }

    @Test
    void shouldWithdrawSuccessfully() throws Exception {
        mockMvc.perform(put("/wallets/balances/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "walletExternalCode": "%s",
                                            "customerExternalCode": "%s",
                                            "amount": 100
                                        }
                                        """, walletEntity.getWalletExternalCode(),
                                walletEntity.getCustomer().getCustomerExternalCode())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("0.0"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty());

        final var balance = balanceRepository.findByWalletWalletExternalCode(walletEntity.getWalletExternalCode()).get();
        Assertions.assertThat(balance.getAmount()).isEqualByComparingTo("0.0");
    }

    @Test
    void shouldTransferSuccessfully() throws Exception {

        // given
        final var thirdCustomerEntity = customerRepository.save(CustomerBuilder.buildEntity().build());
        final var thirdWalletEntity = walletRepository.save(WalletBuilder.buildEntity(thirdCustomerEntity).build());
        balanceRepository.save(BalanceBuilder.buildEntity(thirdWalletEntity).amount(BigDecimal.ZERO).build());

        // then
        mockMvc.perform(put("/wallets/balances/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "walletExternalCode": "%s",
                                            "customerExternalCode": "%s",
                                            "thirdWalletExternalCode": "%s",
                                            "thirdCustomerExternalCode": "%s",
                                            "amount": 30.00
                                        }
                                        """, walletEntity.getWalletExternalCode(),
                                walletEntity.getCustomer().getCustomerExternalCode(),
                                thirdWalletEntity.getWalletExternalCode(),
                                thirdWalletEntity.getCustomer().getCustomerExternalCode())))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("70.0"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty());

        // assert
        final var balance = balanceRepository.findByWalletWalletExternalCode(walletEntity.getWalletExternalCode()).get();
        Assertions.assertThat(balance.getAmount()).isEqualByComparingTo("70.0");

        final var thirdBalance = balanceRepository.findByWalletWalletExternalCode(thirdWalletEntity.getWalletExternalCode()).get();
        Assertions.assertThat(thirdBalance.getAmount()).isEqualByComparingTo("30.0");
    }

    @Test
    void shouldFailDepositWhenWalletNotFound() throws Exception {
        mockMvc.perform(put("/wallets/balances/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "customerExternalCode": "%s",
                                            "walletExternalCode": "%s",
                                            "amount": 100
                                        }
                                        """, UUID.randomUUID(),
                                UUID.randomUUID())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void shouldFailWithdrawWhenWalletNotFound() throws Exception {
        mockMvc.perform(put("/wallets/balances/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "customerExternalCode": "%s",
                                            "walletExternalCode": "%s",
                                            "amount": 100
                                        }
                                        """, UUID.randomUUID(),
                                UUID.randomUUID())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void shouldFailTransferWhenSourceWalletNotFound() throws Exception {

        // given
        final var thirdCustomerEntity = customerRepository.save(CustomerBuilder.buildEntity().build());
        final var thirdWalletEntity = walletRepository.save(WalletBuilder.buildEntity(thirdCustomerEntity).build());
        balanceRepository.save(BalanceBuilder.buildEntity(thirdWalletEntity).amount(BigDecimal.ZERO).build());

        //then
        mockMvc.perform(put("/wallets/balances/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "walletExternalCode": "%s",
                                            "customerExternalCode": "%s",
                                            "thirdWalletExternalCode": "%s",
                                            "thirdCustomerExternalCode": "%s",
                                            "amount": 30.00
                                        }
                                        """, UUID.randomUUID(),
                                UUID.randomUUID(),
                                thirdWalletEntity.getWalletExternalCode(),
                                thirdWalletEntity.getCustomer().getCustomerExternalCode())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void shouldFailTransferWhenTargetWalletNotFound() throws Exception {
        //then
        mockMvc.perform(put("/wallets/balances/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "walletExternalCode": "%s",
                                            "customerExternalCode": "%s",
                                            "thirdWalletExternalCode": "%s",
                                            "thirdCustomerExternalCode": "%s",
                                            "amount": 30.00
                                        }
                                        """,
                                walletEntity.getWalletExternalCode(),
                                walletEntity.getCustomer().getCustomerExternalCode(),
                                UUID.randomUUID(),
                                UUID.randomUUID())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
}