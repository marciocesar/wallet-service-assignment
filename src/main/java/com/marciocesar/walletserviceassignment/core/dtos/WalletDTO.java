package com.marciocesar.walletserviceassignment.core.dtos;

import java.time.LocalDateTime;

public record WalletDTO(
        Long id,
        LocalDateTime creationDate
) {
}
