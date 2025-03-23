package com.marciocesar.walletserviceassignment.api.controllers.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferBalanceRequest(

        @NotNull(message = "Customer external code is required.")
        UUID customerExternalCode,

        @NotNull(message = "Wallet external code is required.")
        UUID walletExternalCode,

        @DecimalMin(value = "0.1", message = "Amount must be greater than zero.")
        BigDecimal amount,

        @NotNull(message = "Third party external code is required.")
        UUID thirdCustomerExternalCode,

        @NotNull(message = "Third party wallet external code is required.")
        UUID thirdWalletExternalCode
) {
}
