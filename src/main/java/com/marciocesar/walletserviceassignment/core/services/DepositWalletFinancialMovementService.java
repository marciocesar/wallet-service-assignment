package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum.DEPOSIT;


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DepositWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;
    private FinancialMovementPersistenceService financialMovementPersistenceService;
    private BalanceEntityMapper balanceEntityMapper;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        return walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                        financialMovementDTO.walletExternalCode(),
                        financialMovementDTO.customerExternalCode()
                ).map(it -> addAmount(it, financialMovementDTO.amount()))
                .map(balanceRepository::save)
                .map(financialMovementPersistenceService.save(financialMovementDTO))
                .map(balanceEntityMapper::toDTO);
    }

    @Override
    public boolean shouldExecute(TypeFinancialMovementEnum type) {
        return DEPOSIT.equals(type);
    }
}
