package com.marciocesar.walletserviceassignment.core.interfaces;

import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;

import java.util.Optional;

public interface WalletFinancialMovement {
    Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO);

    boolean shouldExecute(Type type);

    enum Type {
        DEPOSIT, TRANSFERENCE, WITHDRAWAL
    }
}
