package com.marciocesar.walletserviceassignment.core.database.repositories;

import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByExternalCode(UUID externalCustomerCode);
}
