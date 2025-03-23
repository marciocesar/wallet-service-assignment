package com.marciocesar.walletserviceassignment.core.dtos;

import com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record FinancialMovementDTO(
        UUID customerExternalCode,
        UUID walletExternalCode,
        BigDecimal amount,
        TypeFinancialMovementEnum type,
        UUID thirdCustomerExternalCode,
        UUID thirdWalletExternalCode
) {
}
