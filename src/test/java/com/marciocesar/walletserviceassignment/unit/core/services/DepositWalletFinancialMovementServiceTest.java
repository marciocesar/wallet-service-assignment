package com.marciocesar.walletserviceassignment.unit.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import com.marciocesar.walletserviceassignment.core.services.DepositWalletFinancialMovementService;
import com.marciocesar.walletserviceassignment.unit.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TWO;
import static org.assertj.core.api.Assertions.assertThat;

class DepositWalletFinancialMovementServiceTest extends UnitTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @Spy
    private BalanceEntityMapper balanceEntityMapper = Mappers.getMapper(BalanceEntityMapper.class);

    @InjectMocks
    private DepositWalletFinancialMovementService depositWalletFinancialMovementService;

    private FinancialMovementDTO financialMovementDTO;
    private BalanceEntity balanceEntity;
    private WalletEntity walletEntity;

    @BeforeEach
    void setUp() {
        financialMovementDTO = FinancialMovementDTO.builder()
                .walletExternalCode(UUID.randomUUID())
                .customerExternalCode(UUID.randomUUID())
                .amount(BigDecimal.ONE)
                .type(TypeFinancialMovementEnum.DEPOSIT)
                .build();

        balanceEntity = BalanceEntity.builder().amount(ONE).build();
        walletEntity = WalletEntity.builder().balance(balanceEntity).build();
    }

    @Test
    void shouldExecuteDepositSuccessfully() {
        // given
        Mockito.when(walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                financialMovementDTO.walletExternalCode(),
                financialMovementDTO.customerExternalCode()
        )).thenReturn(Optional.of(walletEntity));


        BalanceEntity balanceEntityUpdated = BalanceEntity.builder().amount(TWO).build();
        Mockito.when(balanceRepository.save(balanceEntityUpdated)).thenReturn(balanceEntityUpdated);

        // when
        BalanceDTO result = depositWalletFinancialMovementService.execute(financialMovementDTO).get();

        // Assert
        assertThat(result).isNotNull();

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(balanceEntityUpdated);
    }

    @Test
    void shouldNotExecuteDepositWhenWalletNotFound() {
        // given
        Mockito.when(walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                financialMovementDTO.walletExternalCode(),
                financialMovementDTO.customerExternalCode()
        )).thenReturn(Optional.empty());

        // when
        final var result = depositWalletFinancialMovementService.execute(financialMovementDTO);

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void checkShouldExecute() {
        // given
        final var result = depositWalletFinancialMovementService.shouldExecute(TypeFinancialMovementEnum.DEPOSIT);

        //assert
        assertThat(result).isTrue();
    }
}