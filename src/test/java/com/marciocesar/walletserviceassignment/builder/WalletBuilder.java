package com.marciocesar.walletserviceassignment.builder;

import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public final class WalletBuilder {

    public static WalletEntity.WalletEntityBuilder buildEntity(CustomerEntity customerEntity) {
        return WalletEntity.builder()
                .customer(customerEntity)
                .walletExternalCode(UUID.randomUUID())
                .creationDate(LocalDateTime.now());
    }
}
