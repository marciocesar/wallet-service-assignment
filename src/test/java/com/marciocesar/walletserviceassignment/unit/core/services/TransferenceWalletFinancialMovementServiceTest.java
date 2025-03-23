package com.marciocesar.walletserviceassignment.unit.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import com.marciocesar.walletserviceassignment.core.exceptions.WalletNotFoundException;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import com.marciocesar.walletserviceassignment.core.services.FinancialMovementPersistenceService;
import com.marciocesar.walletserviceassignment.core.services.TransferenceWalletFinancialMovementService;
import com.marciocesar.walletserviceassignment.unit.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Optional;
import java.util.UUID;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;

class TransferenceWalletFinancialMovementServiceTest extends UnitTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @Spy
    private BalanceEntityMapper balanceEntityMapper = Mappers.getMapper(BalanceEntityMapper.class);

    @Mock
    private FinancialMovementPersistenceService financialMovementPersistenceService;

    @InjectMocks
    private TransferenceWalletFinancialMovementService transferenceWalletFinancialMovementService;

    private FinancialMovementDTO financialMovementDTO;
    private BalanceEntity balanceEntity;
    private WalletEntity walletEntity;
    private WalletEntity thirdWalletEntity;
    private BalanceEntity thirdBalanceEntity;

    @BeforeEach
    void setUp() {
        financialMovementDTO = FinancialMovementDTO.builder()
                .walletExternalCode(UUID.randomUUID())
                .customerExternalCode(UUID.randomUUID())
                .thirdCustomerExternalCode(UUID.randomUUID())
                .thirdWalletExternalCode(UUID.randomUUID())
                .amount(ONE)
                .type(TypeFinancialMovementEnum.TRANSFER)
                .build();

        balanceEntity = BalanceEntity.builder().amount(ONE).build();
        walletEntity = WalletEntity.builder().balance(balanceEntity).build();

        thirdBalanceEntity = BalanceEntity.builder().amount(ONE).build();
        thirdWalletEntity = WalletEntity.builder().balance(thirdBalanceEntity).build();
    }

    @Test
    void shouldExecuteTransferSuccessfully() {
        // given
        Mockito.when(walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                financialMovementDTO.walletExternalCode(),
                financialMovementDTO.customerExternalCode()
        )).thenReturn(Optional.of(walletEntity));

        BalanceEntity balanceEntityZeroed = BalanceEntity.builder().amount(ZERO).build();
        Mockito.when(balanceRepository.save(
                        balanceEntityZeroed
                )
        ).thenReturn(balanceEntity);

        Mockito.when(walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                financialMovementDTO.thirdWalletExternalCode(),
                financialMovementDTO.thirdCustomerExternalCode()
        )).thenReturn(Optional.of(thirdWalletEntity));

        BalanceEntity balanceEntityReceiver = BalanceEntity.builder().amount(TWO).build();

        Mockito.when(balanceRepository.save(balanceEntityReceiver)).thenReturn(balanceEntityReceiver);

        // when
        BalanceDTO result = transferenceWalletFinancialMovementService.execute(financialMovementDTO).get();

        // Assert
        assertThat(result).isNotNull();

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(balanceEntityZeroed);
    }

    @Test
    void whenNotFoundThirdWalletThenThrowException() {
        // given
        Mockito.when(walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                financialMovementDTO.walletExternalCode(),
                financialMovementDTO.customerExternalCode()
        )).thenReturn(Optional.of(walletEntity));

        BalanceEntity balanceEntityZeroed = BalanceEntity.builder().amount(ZERO).build();
        Mockito.when(balanceRepository.save(
                        balanceEntityZeroed
                )
        ).thenReturn(balanceEntity);

        Mockito.when(walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                financialMovementDTO.thirdWalletExternalCode(),
                financialMovementDTO.thirdCustomerExternalCode()
        )).thenReturn(Optional.empty());

        // when
        Assertions.assertThrows(WalletNotFoundException.class,
                () -> transferenceWalletFinancialMovementService.execute(financialMovementDTO));
    }

    @Test
    void shouldNotExecuteDepositWhenWalletNotFound() {
        // given
        Mockito.when(walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                financialMovementDTO.walletExternalCode(),
                financialMovementDTO.customerExternalCode()
        )).thenReturn(Optional.empty());

        // when
        final var result = transferenceWalletFinancialMovementService.execute(financialMovementDTO);

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void checkShouldExecute() {
        // given
        final var result = transferenceWalletFinancialMovementService.shouldExecute(TypeFinancialMovementEnum.TRANSFER);

        //assert
        assertThat(result).isTrue();
    }
}