package com.marciocesar.walletserviceassignment.api.controllers;

import com.marciocesar.walletserviceassignment.api.controllers.request.DepositBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.TransferBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.request.WithdrawalBalanceRequest;
import com.marciocesar.walletserviceassignment.api.controllers.response.BalanceResponse;
import com.marciocesar.walletserviceassignment.core.dtos.FinancialMovementDTO;
import com.marciocesar.walletserviceassignment.core.interfaces.WalletFinancialMovement;
import com.marciocesar.walletserviceassignment.core.services.WalletFinancialMovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.marciocesar.walletserviceassignment.api.mapper.BalanceResponseMapper.BALANCE_RESPONSE_MAPPER;

@RestController
@RequestMapping("/wallets/balances")
@AllArgsConstructor
public class FinancialMovementController {

    private WalletFinancialMovementService walletFinancialMovementService;

    @PutMapping("/deposit")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse deposit(@RequestBody DepositBalanceRequest request) {

        final var balanceDTO = walletFinancialMovementService.executeFinancialMovement(FinancialMovementDTO.builder()
                .walletExternalCode(request.walletExternalCode())
                .customerExternalCode(request.customerExternalCode())
                .amount(request.amount())
                .type(WalletFinancialMovement.Type.DEPOSIT)
                .build()
        );

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }

    @PutMapping("/withdrawal")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse deposit(@RequestBody WithdrawalBalanceRequest request) {

        final var balanceDTO = walletFinancialMovementService.executeFinancialMovement(FinancialMovementDTO.builder()
                .walletExternalCode(request.walletExternalCode())
                .customerExternalCode(request.customerExternalCode())
                .amount(request.amount())
                .type(WalletFinancialMovement.Type.WITHDRAWAL)
                .build()
        );

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }

    @PutMapping("/transfer")
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceResponse transfer(@RequestBody TransferBalanceRequest request) {

        final var balanceDTO = walletFinancialMovementService.executeFinancialMovement(FinancialMovementDTO.builder()
                .walletExternalCode(request.walletExternalCode())
                .customerExternalCode(request.customerExternalCode())
                .thirdCustomerExternalCode(request.thirdCustomerExternalCode())
                .thirdWalletExternalCode(request.thirdWalletExternalCode())
                .amount(request.amount())
                .type(WalletFinancialMovement.Type.TRANSFERENCE)
                .build()
        );

        return BALANCE_RESPONSE_MAPPER.toResponse(balanceDTO);
    }
}
