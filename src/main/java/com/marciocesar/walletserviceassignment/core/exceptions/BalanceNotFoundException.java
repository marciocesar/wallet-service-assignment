package com.marciocesar.walletserviceassignment.core.exceptions;

public class BalanceNotFoundException extends RuntimeException {
    public BalanceNotFoundException() {
        super("Balance Not Found");
    }
}
