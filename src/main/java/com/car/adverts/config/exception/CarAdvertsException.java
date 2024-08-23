package com.car.adverts.config.exception;

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
