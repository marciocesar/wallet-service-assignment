package com.marciocesar.walletserviceassignment.core.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.request.CreateWalletRequest;
import com.marciocesar.walletserviceassignment.core.dtos.CreateWalletRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BalanceEntityMapper {

    BalanceEntityMapper BALANCE_ENTITY_MAPPER = Mappers.getMapper(BalanceEntityMapper.class);

    CreateWalletRequestDTO toDTO(CreateWalletRequest createWalletRequest);
}
