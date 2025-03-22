package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepositWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        return walletRepository.findByIdAndCustomerExternalCode(
                        Long.valueOf(financialMovementDTO.encryptedWalletId()),
                        financialMovementDTO.customerExternalCode()
                ).map(it -> addAmount(it, financialMovementDTO.amount()))
                .map(BalanceEntityMapper.BALANCE_ENTITY_MAPPER::toDTO);
    }

    private BalanceEntity addAmount(WalletEntity wallet, BigDecimal amount) {
        BalanceEntity balance = wallet.getBalance();
        balance.setAmount(balance.getAmount().add(amount));
        return balanceRepository.save(balance);
    }

    @Override
    public boolean shouldExecute(Type type) {
        return Type.DEPOSIT.equals(type);
    }
}
