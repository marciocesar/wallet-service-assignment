package com.marciocesar.walletserviceassignment.api.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonProperty("dateTime")
    private final LocalDateTime localDateTime = now();

    @JsonProperty("errors")
    private List<ApiValidationError> subErrors = new ArrayList<>();

    private final HttpStatus status;

    @Setter
    private String message;

    public ApiErrorResponse(HttpStatus status) {
        this.status = status;
    }

    public void addSubError(List<FieldError> fieldErrors) {
        fieldErrors.stream()
                .map(fieldError -> new ApiValidationError(
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                )).forEach(subErrors::add);
    }
}