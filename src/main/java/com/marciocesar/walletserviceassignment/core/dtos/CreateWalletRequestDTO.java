package com.marciocesar.walletserviceassignment.core.dtos;


import java.util.UUID;

public record CreateWalletRequestDTO(
        UUID externalCustomerCode
) {
}
