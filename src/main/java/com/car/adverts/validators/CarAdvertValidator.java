package com.car.adverts.validators;

import com.car.adverts.config.exception.CarAdvertsException;
import com.car.adverts.constants.CarAdvertsErrorMessagesConstants;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class CarAdvertValidator {

    public void validateCarAdvertRequest(CarAdvertRequest carAdvertRequest, Long id, Boolean newInsert) {
        List<String> errorMessages = new ArrayList<>();

        if (carAdvertRequest.getPrice() < 0) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.PRICE_NOT_NEGATIVE_NUMBER_ERROR);
        }

        if (carAdvertRequest.getId() == null || carAdvertRequest.getId() < 1) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.ID_POSITIVE_NUMBER_ERROR);
        }

        if (newInsert && !carAdvertRequest.getId().equals(id)) {
            errorMessages.add(CarAdvertsErrorMessagesConstants.ID_NOT_EQUAL_ERROR);
        }

        if (!errorMessages.isEmpty()) {
            throw new CarAdvertsException(String.join("; ", errorMessages));
        }
    }
}