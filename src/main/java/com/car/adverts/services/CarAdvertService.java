package com.car.adverts.services;

import com.car.adverts.dto.CarAdvertMapper;
import com.car.adverts.repository.CarAdvertRepository;
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

    public List<CarAdvertResponse> getCarAdverts(String sortby) {

        List<Map<String, Object>> queryResults = carAdvertRepository.getCarAdverts(sortby);

        List<CarAdvertResponse> carAdvertResponses = queryResults.stream()
                .map(CarAdvertMapper::mapRowToCarAdvertResponse)
                .collect(Collectors.toList());

        log.info("Car adverts returned successfully. Number of returned adverts: {}", carAdvertResponses.size());

        return carAdvertResponses;
    }

    public CarAdvertResponse getCarAdvert(Long id) {

        Map<String, Object> row = carAdvertRepository.getCarAdvert(id);

        return CarAdvertMapper.mapRowToCarAdvertResponse(row);
    }

    public CarAdvertResponse addCarAdvert(CarAdvertRequest carAdvertRequest) {

        Long id = carAdvertRepository.addCarAdvert(carAdvertRequest);
        return getCarAdvert(id);
    }


    public CarAdvertResponse updateCarAdvert(Long id, CarAdvertRequest carAdvertRequest) {
        carAdvertRepository.updateCarAdvert(id, carAdvertRequest);

        return getCarAdvert(id);
    }

    public void deleteCarAdvert(Long id) {
        carAdvertRepository.deleteCarAdvert(id);
    }
}
