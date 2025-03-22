package com.marciocesar.walletserviceassignment.core.mapper;

import com.marciocesar.walletserviceassignment.core.database.entities.CustomerEntity;
import com.marciocesar.walletserviceassignment.core.dtos.CustomerDTO;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static com.marciocesar.walletserviceassignment.core.mapper.WalletEntityMapper.WALLET_ENTITY_MAPPER;

@Mapper(componentModel = "spring", uses = {WalletEntityMapper.class})
public interface CustomerEntityMapper {

    CustomerEntityMapper CUSTOMER_ENTITY_MAPPER = Mappers.getMapper(CustomerEntityMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "externalCode", source = "externalCode")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "birthday", source = "birthday")
    @Mapping(target = "wallet", source = "wallet")
    CustomerDTO toDTO(CustomerEntity customerEntity);
}
