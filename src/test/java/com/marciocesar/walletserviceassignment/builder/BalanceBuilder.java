package com.marciocesar.walletserviceassignment.builder;

import com.marciocesar.walletserviceassignment.core.database.entities.BalanceEntity;
import com.marciocesar.walletserviceassignment.core.database.entities.WalletEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class BalanceBuilder {

    public static BalanceEntity.BalanceEntityBuilder buildEntity(WalletEntity walletEntity) {
        return BalanceEntity.builder()
                .creationDate(LocalDateTime.now())
                .amount(BigDecimal.TEN)
                .wallet(walletEntity);
    }
}
