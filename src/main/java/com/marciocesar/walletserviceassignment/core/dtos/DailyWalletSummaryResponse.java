package com.marciocesar.walletserviceassignment.core.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record DailyWalletSummaryResponse(
        LocalDate date,
        BigDecimal amount
) {
}
