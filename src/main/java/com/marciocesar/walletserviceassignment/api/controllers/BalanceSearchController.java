package com.marciocesar.walletserviceassignment.api.controllers;

import com.marciocesar.walletserviceassignment.api.controllers.response.BalanceResponse;
import com.marciocesar.walletserviceassignment.core.dtos.BalanceDTO;
import com.marciocesar.walletserviceassignment.core.services.BalanceSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.marciocesar.walletserviceassignment.api.mapper.BalanceResponseMapper.BALANCE_RESPONSE_MAPPER;

@RestController
@RequestMapping("/wallets/{encryptedWalletId}/balances")
@AllArgsConstructor
public class BalanceSearchController {

    private BalanceSearchService balanceSearchService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse searchBalance(@PathVariable String encryptedWalletId) {

        BalanceDTO balanceDTO = balanceSearchService.findByEncryptedWalletId(encryptedWalletId);

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }
}
