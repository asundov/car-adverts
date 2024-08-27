package com.car.adverts.config.exception;

import hr.ericsson.eb.car.adverts.api.model.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Log4j2
public class CarAdvertsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> generalException(Exception e) {
        log.error("", e);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .title(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException e) {
        log.error("", e);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .title(e.getMessage())
                        .build(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({CarAdvertsException.class})
    public ResponseEntity<ErrorResponse> carAdvertsException(CarAdvertsException e) {
        log.error("", e);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .title(e.getMessage())
                        .build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CarAdvertsNotFoundException.class})
    public ResponseEntity<ErrorResponse> carAdvertsNotFoundException(CarAdvertsNotFoundException e) {
        log.error("", e);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .title(e.getMessage())
                        .build()
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CarAdvertsValidationException.class})
    public ResponseEntity<ErrorResponse> carAdvertsValidationException(CarAdvertsValidationException e) {
        log.error("", e);
        List<String> validationMessages = e.getValidationMessages();
        ErrorResponse response = ErrorResponse.builder()
                .title(e.getMessage())
                .validationErrors(validationMessages)
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({TokenRefreshException.class})
    public ResponseEntity<ErrorResponse> tokenRefreshException(TokenRefreshException e) {
        log.error("", e);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .title(e.getMessage())
                        .build()
                , HttpStatus.BAD_REQUEST);
    }
}

