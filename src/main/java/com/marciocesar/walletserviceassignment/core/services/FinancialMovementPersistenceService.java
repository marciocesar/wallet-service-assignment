package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.repositories.FinancialMovementRepository;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.mapper.FinancialMovementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FinancialMovementPersistenceService {

    private FinancialMovementRepository financialMovementRepository;
    private FinancialMovementMapper financialMovementMapper;

    public void save(FinancialMovementDTO financialMovementDTO) {

        log.info("Initiating financial movement persistence, financialMovementDTO: {}", financialMovementDTO);

        financialMovementRepository.save(financialMovementMapper.toEntity(financialMovementDTO));
    }
}
