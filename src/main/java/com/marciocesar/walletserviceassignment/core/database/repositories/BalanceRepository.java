package com.marciocesar.walletserviceassignment.core.database.repositories;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BalanceRepository extends CrudRepository<BalanceEntity, Long> {
    Optional<BalanceEntity> findByWalletWalletExternalCode(UUID walletExternalCode);
}
