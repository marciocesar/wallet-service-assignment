package com.marciocesar.walletserviceassignment.core.mapper;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BalanceEntityMapper {
    BalanceDTO toDTO(BalanceEntity balanceEntity);
}
