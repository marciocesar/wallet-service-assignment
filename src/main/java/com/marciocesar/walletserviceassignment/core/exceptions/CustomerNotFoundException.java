package com.marciocesar.walletserviceassignment.core.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Customer Not Found");
    }
}
