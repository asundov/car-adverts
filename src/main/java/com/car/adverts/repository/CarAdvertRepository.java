package com.car.adverts.repository;

import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class CarAdvertRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getCarAdverts(String sortby) {

        String findAllEntitiesQuery = String.format(FIND_ALL_CAR_ADVERTS);
        return jdbcTemplate.queryForList(findAllEntitiesQuery);
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

    private static final String FIND_ALL_CAR_ADVERTS = "SELECT * " +
            "FROM core.car_advert ca " +
            "WHERE ca.active = 1 " +
            "order by id";
}
