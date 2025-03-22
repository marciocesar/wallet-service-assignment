package com.marciocesar.walletserviceassignment.core.dtos;

import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record FinancialMovementDTO(
        UUID customerExternalCode,
        String encryptedWalletId,
        BigDecimal amount,
        WalletFinancialMovement.Type type
) {
}
