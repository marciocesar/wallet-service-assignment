package com.marciocesar.walletserviceassignment.api.controllers.request;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositBalanceRequest(
        UUID customerExternalCode,
        UUID walletExternalCode,
        BigDecimal amount
) {
}
