package com.marciocesar.walletserviceassignment.core.dtos;

import com.marciocesar.walletserviceassignment.core.enums.CustomerStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDTO(
        Long id,
        String name,
        UUID customerExternalCode,
        String email,
        CustomerStatusEnum status,
        LocalDateTime birthday,
        WalletDTO wallet
) {
}
