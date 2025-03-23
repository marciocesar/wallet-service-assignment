package com.marciocesar.walletserviceassignment.core.mapper;

import com.marciocesar.walletserviceassignment.api.controllers.request.DepositBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.TransferBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.WithdrawalBalanceRequest;
import com.marciocesar.walletserviceassignment.core.database.entities.FinancialMovementEntity;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FinancialMovementMapper {

    FinancialMovementDTO toDTO(DepositBalanceRequest depositBalanceRequest, TypeFinancialMovementEnum type);

    FinancialMovementDTO toDTO(WithdrawalBalanceRequest withdrawalBalanceRequest, TypeFinancialMovementEnum type);

    FinancialMovementDTO toDTO(TransferBalanceRequest transferBalanceRequest, TypeFinancialMovementEnum type);

    FinancialMovementEntity toEntity(FinancialMovementDTO financialMovementDTO);
}
