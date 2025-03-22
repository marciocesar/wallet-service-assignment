package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.WalletNotFoundException;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransferenceWalletFinancialMovementService implements WalletFinancialMovement {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;

    @Override
    public Optional<BalanceDTO> execute(FinancialMovementDTO financialMovementDTO) {

        return walletRepository.findBywalletExternalCodeAndCustomerExternalCustomerCode(
                        financialMovementDTO.walletExternalCode(),
                        financialMovementDTO.customerExternalCode()
                ).map(it -> subtractAmount(it, financialMovementDTO.amount()))
                .map(balanceRepository::save)
                .map(it -> {
                            transferToTarget(financialMovementDTO);
                            return it;
                        }
                ).map(BalanceEntityMapper.BALANCE_ENTITY_MAPPER::toDTO);
    }

    private void transferToTarget(FinancialMovementDTO financialMovementDTO) {
        walletRepository.findBywalletExternalCodeAndCustomerExternalCustomerCode(
                        financialMovementDTO.thirdWalletExternalCode(),
                        financialMovementDTO.thirdCustomerExternalCode()
                ).map(it -> addAmount(it, financialMovementDTO.amount()))
                .orElseThrow(WalletNotFoundException::new);
    }

    @Override
    public boolean shouldExecute(Type type) {
        return Type.TRANSFERENCE.equals(type);
    }
}
