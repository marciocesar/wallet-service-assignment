package com.marciocesar.walletserviceassignment.api.controllers.response;

import java.time.LocalDateTime;

public record CreateWalletResponse(
        String encryptedId,
        LocalDateTime creationDate
) {}
