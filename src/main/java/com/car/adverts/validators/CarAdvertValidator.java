package com.car.adverts.validators;

import com.car.adverts.config.exception.CarAdvertsException;
import com.car.adverts.config.exception.CarAdvertsValidationException;
import com.car.adverts.constants.CarAdvertsConstants;
import com.car.adverts.constants.CarAdvertsErrorMessagesConstants;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class CarAdvertValidator {

    public void validateCarAdvertRequest(CarAdvertRequest carAdvertRequest, Long id, Boolean newInsert) {
        List<String> errorMessages = new ArrayList<>();
        log.info("Validating input data for car adverts...");

        if (carAdvertRequest == null) {
            throw new CarAdvertsException(CarAdvertsErrorMessagesConstants.REQUEST_NULL_ERROR);
        }

        if (carAdvertRequest.getPrice() != null && carAdvertRequest.getPrice() < 0) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.PRICE_NOT_NEGATIVE_NUMBER_ERROR);
        }

        if (carAdvertRequest.getId() == null || carAdvertRequest.getId() < 1) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.ID_POSITIVE_NUMBER_ERROR);
        }

        if (carAdvertRequest.getId() == null) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.ID_NULL_ERROR);
        }

        if (newInsert && id == null) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.ID_NULL_ERROR);
        }

        if (newInsert && id != null && !id.equals(carAdvertRequest.getId())) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.ID_NOT_EQUAL_ERROR);
        }

        if (carAdvertRequest.getMileage() != null && carAdvertRequest.getMileage() < 0) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.MILEAGE_NOT_NEGATIVE_NUMBER_ERROR);
        }

        if (!errorMessages.isEmpty()) {
            log.error("Validation failed!");
            throw new CarAdvertsValidationException(String.join("; ", errorMessages));
        }
    }

    public void validateSortParameter(String sortByList) {
        List<String> errorMessages = new ArrayList<>();
        log.info("Validating input data for car adverts...");

        if (sortByList.trim().endsWith(",")) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.NOT_VALID_SORT_INPUT_ERROR);
        }

        String[] words = sortByList.split("\\s*,\\s*");

        Arrays.stream(words)
                .filter(word -> !word.isEmpty())
                .map(String::trim)
                .forEach(w -> {
                    if (!CarAdvertsConstants.VALID_SORT_COLUMNS.contains(w.toLowerCase())) {
                        errorMessages.add(w + CarAdvertsErrorMessagesConstants.NOT_VALID_COLUMN_NAME_ERROR);
                    }
                });

        if (!errorMessages.isEmpty()) {
            log.error("Validation failed!");
            throw new CarAdvertsValidationException(String.join("; ", errorMessages));
        }
    }
}