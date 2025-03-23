package com.marciocesar.walletserviceassignment.api.advice;

public record ApiValidationError(
        String field,
        Object rejectedValue,
        String message
) {
}