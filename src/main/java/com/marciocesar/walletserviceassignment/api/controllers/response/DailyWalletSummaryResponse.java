package com.marciocesar.walletserviceassignment.api.controllers.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record DailyWalletSummaryResponse(
        LocalDate date,
        BigDecimal amount
) {
}
