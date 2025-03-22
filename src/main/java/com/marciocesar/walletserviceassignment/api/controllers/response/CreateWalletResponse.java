package com.marciocesar.walletserviceassignment.api.controllers.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateWalletResponse(
        UUID walletExternalCode,
        LocalDateTime creationDate
) {
}
