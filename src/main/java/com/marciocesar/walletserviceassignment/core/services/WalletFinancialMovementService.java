package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.WalletNotFoundException;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WalletFinancialMovementService {

    private List<WalletFinancialMovement> walletFinancialMovementList;

    public BalanceDTO executeFinancialMovement(FinancialMovementDTO financialMovementDTO) {

        return walletFinancialMovementList.stream()
                .filter(it -> it.shouldExecute(financialMovementDTO.type()))
                .findFirst()
                .flatMap(it -> it.execute(financialMovementDTO))
                .orElseThrow(WalletNotFoundException::new);
    }
}
