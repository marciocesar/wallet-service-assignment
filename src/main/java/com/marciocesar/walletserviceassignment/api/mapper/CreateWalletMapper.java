package com.marciocesar.walletserviceassignment.api.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.request.CreateWalletRequest;
import com.marciocesar.walletserviceassignment.api.controllers.response.CreateWalletResponse;
import com.marciocesar.walletserviceassignment.core.dtos.CreateWalletDTO;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateWalletMapper {
    CreateWalletDTO toDTO(CreateWalletRequest createWalletRequest);

    @Mapping(source = "walletExternalCode", target = "walletExternalCode")
    CreateWalletResponse toResponse(WalletDTO walletDTO);
}
