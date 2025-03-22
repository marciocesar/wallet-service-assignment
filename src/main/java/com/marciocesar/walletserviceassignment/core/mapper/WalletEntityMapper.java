package com.marciocesar.walletserviceassignment.core.mapper;

import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WalletEntityMapper {

    WalletEntityMapper WALLET_ENTITY_MAPPER = Mappers.getMapper(WalletEntityMapper.class);

    WalletDTO toDTO(WalletEntity walletEntity);
}
