package com.marciocesar.walletserviceassignment.builder;

import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public final class CustomerBuilder {

    public static CustomerEntity.CustomerEntityBuilder buildEntity() {
        return CustomerEntity.builder()
                .birthday(LocalDateTime.now())
                .customerExternalCode(UUID.randomUUID())
                .email("email@email.com")
                .name("name");
    }
}
