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

        return carAdvertResponses;
    }

    public CarAdvertResponse getCarAdvert(Long id) {


        return CarAdvertResponse.builder().build();
    }

    public CarAdvertResponse addCarAdvert(CarAdvertRequest carAdvertRequest) {

        return CarAdvertResponse.builder().build();
    }


    public CarAdvertResponse updateCarAdvert(Long id, CarAdvertRequest carAdvertRequest) {

        return CarAdvertResponse.builder().build();
    }

    public void deleteCarAdvert(Long id) {

    }
}
