package com.marciocesar.walletserviceassignment.api.controllers.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalBalanceRequest(
        @NotNull(message = "Customer external code is required.")
        UUID customerExternalCode,

        @NotNull(message = "Wallet external code is required.")
        UUID walletExternalCode,

        @DecimalMin(value = "0.1", message = "Amount must be greater than zero.")
        BigDecimal amount
) {
    @Override
    public String toString() {
        return "DepositBalanceRequest{" +
                "customerExternalCode=" + customerExternalCode +
                ", walletExternalCode=" + walletExternalCode +
                '}';
    }
}
