package com.car.adverts.common.config.exception;

public class CarAdvertsException extends RuntimeException {
    public CarAdvertsException() {
        super();
    }

    public CarAdvertsException(String message) {
        super(message);
    }

    public CarAdvertsException(Exception e) {
        super(e);
    }
}
