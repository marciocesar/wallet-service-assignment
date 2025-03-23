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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransferenceWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;
    private BalanceEntityMapper balanceEntityMapper;
    private FinancialMovementPersistenceService financialMovementPersistenceService;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        return walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                        financialMovementDTO.walletExternalCode(),
                        financialMovementDTO.customerExternalCode()
                ).map(it -> subtractAmount(it, financialMovementDTO.amount()))
                .map(balanceRepository::save)
                .map(transferToThird(financialMovementDTO))
                .map(financialMovementPersistenceService.save(financialMovementDTO))
                .map(balanceEntityMapper::toDTO);
    }

    private Function<BalanceEntity, BalanceEntity> transferToThird(FinancialMovementDTO financialMovementDTO) {
        return balanceEntity -> {

            walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                            financialMovementDTO.thirdWalletExternalCode(),
                            financialMovementDTO.thirdCustomerExternalCode()
                    ).map(walletEntity -> addAmount(walletEntity, financialMovementDTO.amount()))
                    .orElseThrow(WalletNotFoundException::new);
            return balanceEntity;
        };
    }


    @Override
    public boolean shouldExecute(TypeFinancialMovementEnum type) {
        return TypeFinancialMovementEnum.TRANSFER.equals(type);
    }
}
