package com.car.adverts.config.exception;

import hr.ericsson.eb.car.adverts.api.model.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Log4j2
public class CarAdvertsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> generalException(Exception e) {
        log.error("", e);
        ErrorResponse response = ErrorResponse.builder()
                .title(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CarAdvertsException.class})
    public ResponseEntity<ErrorResponse> carAdvertsException(CarAdvertsException e) {
        log.error("", e);
        ErrorResponse response = ErrorResponse.builder()
                .title(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CarAdvertsNotFoundException.class})
    public ResponseEntity<ErrorResponse> carAdvertsNotFoundException(CarAdvertsNotFoundException e) {
        log.error("", e);
        ErrorResponse response = ErrorResponse.builder()
                .title(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CarAdvertsValidationException.class})
    public ResponseEntity<ErrorResponse> carAdvertsValidationException(CarAdvertsValidationException e) {
        log.error("", e);
        StringBuilder detailBuilder = new StringBuilder();
        List<String> validationMessages = e.getValidationMessages();
        if (validationMessages != null && !validationMessages.isEmpty()) {
            for (String validationMessage : validationMessages) {
                detailBuilder.append(validationMessage).append("; ");
            }
        }
        ErrorResponse response = ErrorResponse.builder()
                .title(e.getMessage())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("", ex);

        ErrorResponse response = ErrorResponse.builder()
                .title("Validation failed")
                .status(status.value())
                .instance(request.getDescription(true))
                .build();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getField());
            sb.append(": ");
            sb.append(fieldError.getDefaultMessage());
            sb.append("; ");
            response.setDetail(sb.toString());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse generateErrorResponse(String code, String message) {
        return ErrorResponse.builder()
                .type(code)
                .title(message)
                .build();
    }
}

