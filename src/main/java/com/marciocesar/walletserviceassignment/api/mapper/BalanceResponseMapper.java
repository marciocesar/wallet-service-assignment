package com.marciocesar.walletserviceassignment.api.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.response.BalanceResponse;
import com.marciocesar.walletserviceassignment.api.controllers.response.CreateWalletResponse;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BalanceResponseMapper {

    BalanceResponseMapper BALANCE_RESPONSE_MAPPER = Mappers.getMapper(BalanceResponseMapper.class);

    BalanceResponse toResponse(BalanceDTO balanceDTO);
}
