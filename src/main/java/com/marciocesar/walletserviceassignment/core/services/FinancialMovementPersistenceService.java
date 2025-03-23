package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.FinancialMovementRepository;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.mapper.FinancialMovementMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class FinancialMovementPersistenceService {

    private FinancialMovementRepository financialMovementRepository;
    private FinancialMovementMapper financialMovementMapper;

    public Function<BalanceEntity, BalanceEntity> save(FinancialMovementDTO financialMovementDTO) {
        return it -> {
            financialMovementRepository.save(financialMovementMapper.toEntity(financialMovementDTO));
            return it;
        };
    }
}
