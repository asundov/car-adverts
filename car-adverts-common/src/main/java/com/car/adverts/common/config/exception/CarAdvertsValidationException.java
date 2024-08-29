package com.car.adverts.common.config.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CarAdvertsValidationException extends RuntimeException {

    private List<String> validationMessages = null;

    public CarAdvertsValidationException() {
        super();
    }

    public CarAdvertsValidationException(Exception e) {
        super(e);
    }

    public CarAdvertsValidationException(String message) {
        super(message);
    }

    public CarAdvertsValidationException(List<String> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public CarAdvertsValidationException(String message, List<String> validationMessages) {
        super(message);
        this.validationMessages = validationMessages;
    }
}
