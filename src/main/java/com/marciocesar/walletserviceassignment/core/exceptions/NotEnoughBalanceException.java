package com.marciocesar.walletserviceassignment.core.exceptions;

public class NotEnoughBalanceException extends RuntimeException {
    public NotEnoughBalanceException() {
        super("Not Enough Balance");
    }
}
