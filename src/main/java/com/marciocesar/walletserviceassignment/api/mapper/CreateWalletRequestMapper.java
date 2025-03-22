package com.marciocesar.walletserviceassignment.api.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.request.CreateWalletRequest;
import com.marciocesar.walletserviceassignment.core.dtos.CreateWalletRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreateWalletRequestMapper {

    CreateWalletRequestMapper CREATE_WALLET_REQUEST_MAPPER = Mappers.getMapper(CreateWalletRequestMapper.class);

    CreateWalletRequestDTO toDTO(CreateWalletRequest createWalletRequest);
}
