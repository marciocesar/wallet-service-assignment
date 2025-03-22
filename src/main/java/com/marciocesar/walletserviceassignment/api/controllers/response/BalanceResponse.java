package com.marciocesar.walletserviceassignment.api.controllers.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record BalanceResponse(
        BigDecimal amount,
        LocalDateTime updateDate
) {}
