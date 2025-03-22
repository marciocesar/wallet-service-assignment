package com.marciocesar.walletserviceassignment.api.controllers;

import com.marciocesar.walletserviceassignment.api.controllers.request.DepositBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.TransferBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.WithdrawalBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.response.BalanceResponse;
import com.marciocesar.walletserviceassignment.core.services.WalletFinancialMovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.marciocesar.walletserviceassignment.api.mapper.BalanceResponseMapper.BALANCE_RESPONSE_MAPPER;
import static com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement.Type.*;
import static com.marciocesar.walletserviceassignment.core.mapper.FinancialMovementDTOMapper.FINANCIAL_MOVEMENT_DTO_MAPPER;

@RestController
@RequestMapping("/wallets/balances")
@AllArgsConstructor
public class FinancialMovementController {

    private WalletFinancialMovementService walletFinancialMovementService;

    @PutMapping("/deposit")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse deposit(@RequestBody DepositBalanceRequest request) {

        final var balanceDTO = walletFinancialMovementService.executeFinancialMovement(
                FINANCIAL_MOVEMENT_DTO_MAPPER.toDTO(request, DEPOSIT)
        );

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }

    @PutMapping("/withdrawal")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse deposit(@RequestBody WithdrawalBalanceRequest request) {

        final var balanceDTO = walletFinancialMovementService.executeFinancialMovement(
                FINANCIAL_MOVEMENT_DTO_MAPPER.toDTO(request, WITHDRAWAL)
        );

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }

    @PutMapping("/transfer")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse transfer(@RequestBody TransferBalanceRequest request) {

        final var balanceDTO = walletFinancialMovementService.executeFinancialMovement(
                FINANCIAL_MOVEMENT_DTO_MAPPER.toDTO(request, TRANSFERENCE)
        );

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }
}
