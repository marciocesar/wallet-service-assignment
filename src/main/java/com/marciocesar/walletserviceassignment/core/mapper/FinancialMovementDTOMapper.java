package com.marciocesar.walletserviceassignment.core.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.request.DepositBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.TransferBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.WithdrawalBalanceRequest;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FinancialMovementDTOMapper {

    FinancialMovementDTOMapper FINANCIAL_MOVEMENT_DTO_MAPPER = Mappers.getMapper(FinancialMovementDTOMapper.class);

    FinancialMovementDTO toDTO(DepositBalanceRequest depositBalanceRequest, WalletFinancialMovement.Type type);

    FinancialMovementDTO toDTO(WithdrawalBalanceRequest withdrawalBalanceRequest, WalletFinancialMovement.Type type);

    FinancialMovementDTO toDTO(TransferBalanceRequest transferBalanceRequest, WalletFinancialMovement.Type type);
}
