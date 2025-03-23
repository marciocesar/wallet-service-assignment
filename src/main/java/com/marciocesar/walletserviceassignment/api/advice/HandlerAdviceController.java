package com.marciocesar.walletserviceassignment.api.advice;

import com.marciocesar.walletserviceassignment.core.exceptions.BalanceNotFoundException;
import com.marciocesar.walletserviceassignment.core.exceptions.CustomerNotFoundException;
import com.marciocesar.walletserviceassignment.core.exceptions.NotEnoughBalanceException;
import com.marciocesar.walletserviceassignment.core.exceptions.WalletNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class HandlerAdviceController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {
            NotEnoughBalanceException.class,
    })
    protected ApiErrorResponse badRequestHandler(RuntimeException ex) {

        final var apiErrorResponse = new ApiErrorResponse(BAD_REQUEST);
        apiErrorResponse.setMessage(ex.getMessage());
        return apiErrorResponse;
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {
            BalanceNotFoundException.class,
            CustomerNotFoundException.class,
            WalletNotFoundException.class,
    })
    protected ApiErrorResponse notFoundHandler(RuntimeException ex) {

        final var apiErrorResponse = new ApiErrorResponse(NOT_FOUND);
        apiErrorResponse.setMessage(ex.getMessage());
        return apiErrorResponse;
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiErrorResponse MethodArgumentNotValidHandler(MethodArgumentNotValidException ex) {

        final var apiErrorResponse = new ApiErrorResponse(BAD_REQUEST);
        apiErrorResponse.setMessage("Validation error");
        apiErrorResponse.addSubError(ex.getBindingResult().getFieldErrors());

        return apiErrorResponse;
    }

}