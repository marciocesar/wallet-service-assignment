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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class WithdrawalWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;
    private BalanceEntityMapper balanceEntityMapper;
    private FinancialMovementPersistenceService financialMovementPersistenceService;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        return walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                        financialMovementDTO.walletExternalCode(),
                        financialMovementDTO.customerExternalCode()
                ).map(balance -> subtractAmount(balance, financialMovementDTO.amount()))
                .map(balanceRepository::save)
                .map(financialMovementPersistenceService.save(financialMovementDTO))
                .map(balanceEntityMapper::toDTO);
    }

    @Override
    public boolean shouldExecute(final TypeFinancialMovementEnum type) {
        return TypeFinancialMovementEnum.WITHDRAW.equals(type);
    }
}
