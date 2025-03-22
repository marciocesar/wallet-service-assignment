package com.marciocesar.walletserviceassignment.core.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record WalletDTO(
        UUID walletExternalCode,
        LocalDateTime creationDate
) {
}
