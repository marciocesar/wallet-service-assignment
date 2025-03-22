package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.CustomerRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.dtos.CreateWalletRequestDTO;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import com.marciocesar.walletserviceassignment.core.exceptions.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.marciocesar.walletserviceassignment.core.mapper.WalletEntityMapper.WALLET_ENTITY_MAPPER;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class WalletCreationService {

    private WalletRepository walletRepository;
    private BalanceRepository balanceRepository;
    private CustomerRepository customerRepository;

    public WalletDTO create(CreateWalletRequestDTO createWalletRequestDTO) {

        final var customerEntity = customerRepository.findByExternalCode(createWalletRequestDTO.externalCustomerCode())
                .orElseThrow(CustomerNotFoundException::new);

        //todo implementar CustomerValidationService, irá validar status

        if (nonNull(customerEntity.getWallet())) {
            return WALLET_ENTITY_MAPPER.toDTO(customerEntity.getWallet());
        }

        WalletEntity walletEntity = persistWallet(customerEntity);
        persistBalance(walletEntity);

        return WALLET_ENTITY_MAPPER.toDTO(walletEntity);
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
