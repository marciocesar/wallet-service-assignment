package com.marciocesar.walletserviceassignment.core.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDTO(
        Long id,
        String name,
        UUID customerExternalCode,
        String email,
        LocalDateTime birthday,
        WalletDTO wallet
) {
}
