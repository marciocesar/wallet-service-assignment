package com.marciocesar.walletserviceassignment.api.controllers.request;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalBalanceRequest(
        UUID customerExternalCode,
        UUID walletExternalCode,
        BigDecimal amount
) {
}
