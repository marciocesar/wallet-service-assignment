package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransferenceWalletFinancialMovementService implements WalletFinancialMovement {
    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {
        return null;
    }

    @Override
    public boolean shouldExecute(Type type) {
        return Type.TRANSFERENCE.equals(type);
    }
}
