package com.marciocesar.walletserviceassignment.core.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BalanceDTO(
        Long id,
        BigDecimal amount,
        LocalDateTime creationDate,
        LocalDateTime updateDate
) {
    @Override
    public String toString() {
        return "BalanceDTO{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}

