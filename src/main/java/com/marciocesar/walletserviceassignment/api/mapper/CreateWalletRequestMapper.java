package com.marciocesar.walletserviceassignment.api.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.request.CreateWalletRequest;
import com.marciocesar.walletserviceassignment.core.dtos.CreateWalletDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreateWalletRequestMapper {

    CreateWalletRequestMapper CREATE_WALLET_DTO_MAPPER = Mappers.getMapper(CreateWalletRequestMapper.class);

    CreateWalletDTO toDTO(CreateWalletRequest createWalletRequest);
}
