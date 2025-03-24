package com.marciocesar.walletserviceassignment.builder;

import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public final class CustomerBuilder {

    public static CustomerEntity.CustomerEntityBuilder buildEntity() {
        return CustomerEntity.builder()
                .customerExternalCode(UUID.randomUUID())
                .email(UUID.randomUUID().getMostSignificantBits() + "@test.com")
                .name(UUID.randomUUID().toString());
    }
}
