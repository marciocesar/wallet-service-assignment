package com.marciocesar.walletserviceassignment.api.controllers;

import com.marciocesar.walletserviceassignment.api.controllers.response.BalanceResponse;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.api.controllers.response.DailyWalletSummaryResponse;
import com.marciocesar.walletserviceassignment.core.services.BalanceLogService;
import com.marciocesar.walletserviceassignment.core.services.BalanceSearchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

import static com.marciocesar.walletserviceassignment.api.mapper.BalanceResponseMapper.BALANCE_RESPONSE_MAPPER;
import static org.springframework.data.domain.Pageable.ofSize;

@RestController
@RequestMapping("/wallets/{walletExternalCode}/balances")
@AllArgsConstructor
public class BalanceSearchController {

    private BalanceSearchService balanceSearchService;
    private BalanceLogService balanceLogService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse searchBalance(@PathVariable UUID walletExternalCode) {

        BalanceDTO balanceDTO = balanceSearchService.findByWalletExternalCode(walletExternalCode);

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }

    @GetMapping("/statement")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<DailyWalletSummaryResponse> searchBalancePerPeriod(
            @PathVariable(value = "walletExternalCode") UUID walletExternalCode,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return balanceLogService.getLastDailyBalancesByPeriod(startDate,
                endDate,
                ofSize(size).withPage(page),
                walletExternalCode
        );
    }

}
