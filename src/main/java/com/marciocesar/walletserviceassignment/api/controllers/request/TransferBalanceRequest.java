package com.marciocesar.walletserviceassignment.api.controllers.request;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferBalanceRequest(
        UUID externalCustomerCode,
        UUID walletExternalCode,
        BigDecimal amount,
        UUID thirdCustomerExternalCode,
        UUID thirdWalletExternalCode
) {
}
