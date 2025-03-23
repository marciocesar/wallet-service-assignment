package com.marciocesar.walletserviceassignment.api.controllers;

import com.marciocesar.walletserviceassignment.api.controllers.request.CreateWalletRequest;
import com.marciocesar.walletserviceassignment.api.controllers.response.CreateWalletResponse;
import com.marciocesar.walletserviceassignment.api.mapper.CreateWalletMapper;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import com.marciocesar.walletserviceassignment.core.services.WalletCreationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/wallets")
@AllArgsConstructor
public class WalletController {

    private WalletCreationService walletCreationService;
    private CreateWalletMapper createWalletMapper;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateWalletResponse createWallet(@Valid @RequestBody CreateWalletRequest createWalletRequest) {

        WalletDTO walletDTO = walletCreationService.create(createWalletMapper.toDTO(createWalletRequest));

        return createWalletMapper.toResponse(walletDTO);
    }
}
