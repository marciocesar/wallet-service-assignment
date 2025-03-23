package com.marciocesar.walletserviceassignment.api.controllers;

import com.marciocesar.walletserviceassignment.api.controllers.request.DepositBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.TransferBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.WithdrawalBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.response.BalanceResponse;
import com.marciocesar.walletserviceassignment.api.mapper.BalanceResponseMapper;
import com.marciocesar.walletserviceassignment.core.mapper.FinancialMovementMapper;
import com.marciocesar.walletserviceassignment.core.services.WalletFinancialMovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.marciocesar.walletserviceassignment.core.enums.TypeFinancialMovementEnum.*;

@RestController
@RequestMapping("/wallets/balances")
@AllArgsConstructor
public class FinancialMovementController {

    private WalletFinancialMovementService service;
    private FinancialMovementMapper financialMovementMapper;
    private BalanceResponseMapper balanceResponseMapper;

    @PutMapping("/deposit")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse deposit(@RequestBody DepositBalanceRequest request) {

        final var balanceDTO = service.executeFinancialMovement(
                financialMovementMapper.toDTO(request, DEPOSIT)
        );

        return balanceResponseMapper.toResponse(balanceDTO);
    }

    @PutMapping("/withdraw")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse withdraw(@RequestBody WithdrawalBalanceRequest request) {

        final var balanceDTO = service.executeFinancialMovement(
                financialMovementMapper.toDTO(request, WITHDRAW)
        );

        return balanceResponseMapper.toResponse(balanceDTO);
    }

    @PutMapping("/transfer")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse transfer(@RequestBody TransferBalanceRequest request) {

        final var balanceDTO = service.executeFinancialMovement(
                financialMovementMapper.toDTO(request, TRANSFER)
        );

        return balanceResponseMapper.toResponse(balanceDTO);
    }
}
