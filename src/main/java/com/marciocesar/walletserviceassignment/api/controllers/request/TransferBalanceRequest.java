package com.marciocesar.walletserviceassignment.api.controllers.request;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferBalanceRequest(
        UUID customerCode,
        String encryptedWalletId,
        BigDecimal amount,
        UUID thirdCustomerCode,
        String thirdEncryptedWalletId
) {
}
