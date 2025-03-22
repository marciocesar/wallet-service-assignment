package com.marciocesar.walletserviceassignment.api.controllers;

import com.marciocesar.walletserviceassignment.api.controllers.request.CreateWalletRequest;
import com.marciocesar.walletserviceassignment.api.controllers.response.CreateWalletResponse;
import com.marciocesar.walletserviceassignment.api.mapper.CreateWalletResponseMapper;
import com.marciocesar.walletserviceassignment.core.dtos.WalletDTO;
import com.marciocesar.walletserviceassignment.core.services.WalletCreationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.marciocesar.walletserviceassignment.api.mapper.CreateWalletRequestMapper.CREATE_WALLET_REQUEST_MAPPER;

@RestController
@RequestMapping("/wallets")
@AllArgsConstructor
public class WalletController {

    private WalletCreationService walletCreationService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateWalletResponse createWallet(@RequestBody CreateWalletRequest createWalletRequest) {

        WalletDTO walletDTO = walletCreationService.create(CREATE_WALLET_REQUEST_MAPPER.toDTO(createWalletRequest));

        return CreateWalletResponseMapper.CREATE_WALLET_RESPONSE_MAPPER.toResponse(walletDTO);
    }
}
