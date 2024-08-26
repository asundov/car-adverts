package com.car.adverts.services;

import com.car.adverts.config.exception.CarAdvertsException;
import com.car.adverts.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.dto.CarAdvertMapper;
import com.car.adverts.repository.CarAdvertRepository;
import com.car.adverts.validators.CarAdvertValidator;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CarAdvertService {

    private final CarAdvertRepository carAdvertRepository;
    private final CarAdvertValidator carAdvertValidator;

    public List<CarAdvertResponse> getCarAdverts(String sortby) {

        log.info("Getting all active car adverts...");

        if (sortby != null) {
            carAdvertValidator.validateSortParameter(sortby);
        }
        List<Map<String, Object>> queryResults = carAdvertRepository.getCarAdverts(sortby);

        List<CarAdvertResponse> carAdvertResponses = queryResults.stream()
                .map(CarAdvertMapper::mapRowToCarAdvertResponse)
                .collect(Collectors.toList());

        log.info("Car adverts returned successfully. Number of returned adverts: {}", carAdvertResponses.size());

        return carAdvertResponses;
    }

    public CarAdvertResponse getCarAdvert(Long id) {
        log.info("Getting car advert with id: {}", id);

        Map<String, Object> row = carAdvertRepository.getCarAdvert(id);

        log.info("Successfully returned car advert with id: {}", id);
        return CarAdvertMapper.mapRowToCarAdvertResponse(row);
    }

    public CarAdvertResponse addCarAdvert(CarAdvertRequest carAdvertRequest) {
        log.info("Adding new car advert.");

        carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, null, false);

        Long id = carAdvertRepository.addCarAdvert(carAdvertRequest);
        if (id == null) {
            throw new CarAdvertsException(CarAdvertsErrorMessagesConstants.ADD_FAILED_ERROR);
        }

        return getCarAdvert(id);
    }


    public CarAdvertResponse updateCarAdvert(Long id, CarAdvertRequest carAdvertRequest) {
        log.info("Updating car advert with id: {}", id);

        carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, id, true);
        carAdvertRepository.updateCarAdvert(id, carAdvertRequest);

        log.info("Car advert updated successfully");
        return getCarAdvert(id);
    }

    public void deleteCarAdvert(Long id) {
        log.info("Deleting car advert with id: {}", id);

        carAdvertRepository.deleteCarAdvert(id);
        log.info("Car advert successfully deleted!");
    }
}
