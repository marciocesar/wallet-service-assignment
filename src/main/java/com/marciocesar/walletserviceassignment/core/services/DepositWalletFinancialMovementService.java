package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum.DEPOSIT;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DepositWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;
    private BalanceEntityMapper balanceEntityMapper;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        log.info("Initiating deposit wallet financial movement, walletExternalCode: {}", financialMovementDTO);

        return walletRepository.findByWalletExternalCodeAndCustomerCustomerExternalCode(
                        financialMovementDTO.walletExternalCode(),
                        financialMovementDTO.customerExternalCode()
                )
                .map(it -> addAmount(it, financialMovementDTO.amount()))
                .map(balanceRepository::save)
                .map(balanceEntityMapper::toDTO);
    }

    @Override
    public boolean shouldExecute(TypeFinancialMovementEnum type) {
        return DEPOSIT.equals(type);
    }
}
