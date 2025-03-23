package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.CustomerRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.CreateWalletDTO;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.CustomerNotFoundException;
import com.marciocesar.walletserviceassignment.core.mapper.WalletEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class WalletCreationService {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;
    private CustomerRepository customerRepository;
    private WalletEntityMapper walletEntityMapper;

    public WalletDTO create(CreateWalletDTO createWalletDTO) {

        log.info("Initiating wallet creation, customerExternalCode: {}", createWalletDTO.customerExternalCode());

        final var customerEntity = customerRepository.findByCustomerExternalCode(createWalletDTO.customerExternalCode())
                .orElseThrow(CustomerNotFoundException::new);

        if (nonNull(customerEntity.getWallet())) {
            return walletEntityMapper.toDTO(customerEntity.getWallet());
        }

        final var walletEntity = persistWallet(customerEntity);
        persistBalance(walletEntity);

        return walletEntityMapper.toDTO(walletEntity);
    }

    private WalletEntity persistWallet(CustomerEntity customerEntity) {
        return walletRepository.save(
                WalletEntity.builder()
                        .customer(customerEntity)
                        .build()
        );
    }

    private void persistBalance(WalletEntity walletEntity) {
        balanceRepository.save(
                BalanceEntity.builder()
                        .amount(BigDecimal.ZERO)
                        .wallet(walletEntity)
                        .build()
        );
    }
}
