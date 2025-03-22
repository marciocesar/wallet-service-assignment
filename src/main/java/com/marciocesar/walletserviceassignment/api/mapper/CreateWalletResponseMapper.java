package com.marciocesar.walletserviceassignment.api.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.response.CreateWalletResponse;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreateWalletResponseMapper {

    CreateWalletResponseMapper CREATE_WALLET_RESPONSE_MAPPER = Mappers.getMapper(CreateWalletResponseMapper.class);

    @Mapping(source = "id", target = "encryptedId")
    CreateWalletResponse toResponse(WalletDTO walletDTO);
}
