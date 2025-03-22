package com.marciocesar.walletserviceassignment.api.controllers.request;

import java.util.UUID;

public record CreateWalletRequest(
        UUID externalCustomerCode
) {
}
