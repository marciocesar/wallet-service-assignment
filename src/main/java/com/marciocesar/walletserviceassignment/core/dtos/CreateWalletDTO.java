package com.marciocesar.walletserviceassignment.core.dtos;


import java.util.UUID;

public record CreateWalletDTO(
        UUID customerExternalCode
) {
}
