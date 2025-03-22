package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceLogRepository;
import com.marciocesar.walletserviceassignment.core.dtos.DailyWalletSummaryResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BalanceLogService {

    private final BalanceLogRepository balanceLogRepository;

    public Page<DailyWalletSummaryResponse> getLastDailyBalancesByPeriod(LocalDate startDate,
                                                                         LocalDate endDate,
                                                                         Pageable pageable,
                                                                         Long walletId) {

        final var startDateTime = startDate.atStartOfDay();
        final var endDateTime = endDate.atTime(23, 59, 59);
        final var results = balanceLogRepository.findLastDailyBalanceByPeriod(
                walletId,
                startDateTime,
                endDateTime,
                pageable);

        return results.map(balanceLog -> DailyWalletSummaryResponse.builder()
                .date(balanceLog.getUpdateDate().toLocalDate())
                .amount(balanceLog.getAmount())
                .build()
        );
    }
}
