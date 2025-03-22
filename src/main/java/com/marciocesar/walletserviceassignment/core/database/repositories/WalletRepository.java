package com.marciocesar.walletserviceassignment.core.database.repositories;

import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends CrudRepository<WalletEntity, Long> {
    Optional<WalletEntity> findBywalletExternalCode(UUID walletExternalCode);

    Optional<WalletEntity> findBywalletExternalCodeAndCustomerExternalCustomerCode(UUID walletExternalCode, UUID customerExternalCode);
}
