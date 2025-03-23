package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.BalanceNotFoundException;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BalanceSearchService {

    private BalanceRepository balanceRepository;
    private BalanceEntityMapper BalanceEntityMapper;

    public BalanceDTO findByWalletExternalCode(UUID walletExternalCode) {

        log.info("searching balance, walletExternalCode: {}", walletExternalCode);

        BalanceEntity balanceEntity = balanceRepository.findByWalletWalletExternalCode(walletExternalCode)
                .orElseThrow(BalanceNotFoundException::new);

        return BalanceEntityMapper.toDTO(balanceEntity);
    }
}
