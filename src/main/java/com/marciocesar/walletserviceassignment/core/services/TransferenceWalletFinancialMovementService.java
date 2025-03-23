package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import com.marciocesar.walletserviceassignment.core.exceptions.WalletNotFoundException;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TransferenceWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;
    private BalanceEntityMapper balanceEntityMapper;
    private FinancialMovementPersistenceService financialMovementPersistenceService;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        log.info("Initiating transfer wallet financial movement, walletExternalCode: {}", financialMovementDTO);

        Optional<BalanceDTO> balanceDTO = walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                        financialMovementDTO.walletExternalCode(),
                        financialMovementDTO.customerExternalCode()
                ).map(it -> subtractAmount(it, financialMovementDTO.amount()))
                .map(balanceRepository::save)
                .map(transferToThird(financialMovementDTO))
                .map(balanceEntityMapper::toDTO);

        financialMovementPersistenceService.save(financialMovementDTO);
        return balanceDTO;
    }

    private Function<BalanceEntity, BalanceEntity> transferToThird(FinancialMovementDTO financialMovementDTO) {

        log.info("Initiating transfer to third wallet financial movement, walletExternalCode: {}", financialMovementDTO);

        return balanceEntity -> {

            walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                            financialMovementDTO.thirdWalletExternalCode(),
                            financialMovementDTO.thirdCustomerExternalCode()
                    )
                    .map(walletEntity -> addAmount(walletEntity, financialMovementDTO.amount()))
                    .map(balanceRepository::save)
                    .orElseThrow(WalletNotFoundException::new);

            return balanceEntity;
        };
    }


    @Override
    public boolean shouldExecute(TypeFinancialMovementEnum type) {
        return TypeFinancialMovementEnum.TRANSFER.equals(type);
    }
}
