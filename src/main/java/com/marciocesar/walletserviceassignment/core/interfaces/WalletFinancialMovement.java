package com.marciocesar.walletserviceassignment.core.interfaces;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import com.marciocesar.walletserviceassignment.core.exceptions.NotEnoughBalanceException;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletFinancialMovement {
    Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO);

    boolean shouldExecute(TypeFinancialMovementEnum type);

    default BalanceEntity addAmount(WalletEntity wallet, BigDecimal amount) {
        BalanceEntity balance = wallet.getBalance();
        balance.setAmount(balance.getAmount().add(amount));
        return balance;
    }

    default BalanceEntity subtractAmount(WalletEntity wallet, BigDecimal amount) {
        BalanceEntity balance = wallet.getBalance();

        if (balance.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughBalanceException();
        }

        balance.setAmount(balance.getAmount().subtract(amount));
        return balance;
    }
}
