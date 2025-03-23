package com.marciocesar.walletserviceassignment.api.controllers.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateWalletRequest(
        @NotNull(message = "Customer external code is required.")
        UUID customerExternalCode
) {
}
