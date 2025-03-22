package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.BalanceNotFoundException;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BalanceSearchService {

    private BalanceRepository balanceRepository;

    public BalanceDTO findByWalletExternalCode(UUID walletExternalCode) {

        BalanceEntity balanceEntity = balanceRepository.findByWalletWalletExternalCode(walletExternalCode)
                .orElseThrow(BalanceNotFoundException::new);

        return BalanceEntityMapper.BALANCE_ENTITY_MAPPER.toDTO(balanceEntity);
    }
}
