package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.NotEnoughBalanceException;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WithdrawalWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        return walletRepository.findByIdAndCustomerExternalCode(
                        Long.valueOf(financialMovementDTO.encryptedWalletId()),
                        financialMovementDTO.customerExternalCode()
                ).map(it -> subtractAmount(it, financialMovementDTO.amount()))
                .map(BalanceEntityMapper.BALANCE_ENTITY_MAPPER::toDTO);
    }

    private BalanceEntity subtractAmount(WalletEntity wallet, BigDecimal amount) {
        BalanceEntity balance = wallet.getBalance();

        if (balance.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughBalanceException();
        }

        balance.setAmount(balance.getAmount().subtract(amount));
        return balanceRepository.save(balance);
    }

    @Override
    public boolean shouldExecute(final Type type) {
        return Type.WITHDRAWAL.equals(type);
    }
}
