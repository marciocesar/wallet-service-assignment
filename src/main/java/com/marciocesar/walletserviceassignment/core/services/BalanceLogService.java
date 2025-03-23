package com.marciocesar.walletserviceassignment.core.services;

import com.marciocesar.walletserviceassignment.api.controllers.response.DailyWalletSummaryResponse;
import com.marciocesar.walletserviceassignment.core.database.repositories.BalanceLogRepository;
import com.marciocesar.walletserviceassignment.core.database.repositories.WalletRepository;
import com.marciocesar.walletserviceassignment.core.exceptions.WalletNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BalanceLogService {

    private final BalanceLogRepository balanceLogRepository;
    private final WalletRepository walletRepository;

    public Page<DailyWalletSummaryResponse> getLastDailyBalancesByPeriod(LocalDate startDate,
                                                                         LocalDate endDate,
                                                                         Pageable pageable,
                                                                         UUID walletExternalCode) {

        log.info("searching statement startDate: {}, endDate: {}, walletExternalCode: {}", startDate, endDate, walletExternalCode);

        final var startDateTime = startDate.atStartOfDay();
        final var endDateTime = endDate.atTime(23, 59, 59);

        final var walletId = walletRepository.findByWalletExternalCode(walletExternalCode)
                .orElseThrow(WalletNotFoundException::new)
                .getId();

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
