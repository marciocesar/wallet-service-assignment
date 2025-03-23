package com.marciocesar.walletserviceassignment.core.exceptions;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException() {
        super("Wallet Not Found");
    }
}
