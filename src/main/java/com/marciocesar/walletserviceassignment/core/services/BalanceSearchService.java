package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.BalanceNotFoundException;
import com.marciocesar.walletserviceassignment.core.mapper.BalanceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceSearchService {

    //todo implementar CryptographyService
    private BalanceRepository balanceRepository;

    public BalanceDTO findByEncryptedWalletId(String encryptedWalletId) {

        BalanceEntity balanceEntity = balanceRepository.findByWalletId(Long.valueOf(encryptedWalletId))
                .orElseThrow(BalanceNotFoundException::new);

        return BalanceEntityMapper.BALANCE_ENTITY_MAPPER.toDTO(balanceEntity);
    }
}
