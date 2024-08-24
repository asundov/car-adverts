package com.car.adverts.repository;

import com.car.adverts.config.exception.CarAdvertsNotFoundException;
import com.car.adverts.constants.CarAdvertsConstants;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class CarAdvertRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getCarAdverts(String sortBy) {

        sortBy = sortBy != null ? sortBy.toLowerCase() : "id";

        String findAllEntitiesQuery = String.format(FIND_ALL_CAR_ADVERTS, sortBy);
        return jdbcTemplate.queryForList(findAllEntitiesQuery);
    }

    public Map<String, Object> getCarAdvert(Long id) {

        try {
            Map<String, Object> row = jdbcTemplate.queryForMap(FIND_CAR_ADVERT_BY_ID, id);
            log.info("Successfully found car advert with id: {}", id);
            return row;
        } catch (EmptyResultDataAccessException e) {
            throw new CarAdvertsNotFoundException();
        }
    }

    public Long addCarAdvert(CarAdvertRequest carAdvertRequest) {

        return jdbcTemplate.queryForObject(INSERT_CAR_ADVERT, Long.class,
                carAdvertRequest.getTitle(),
                carAdvertRequest.getFuelType(),
                carAdvertRequest.getPrice(),
                carAdvertRequest.getIsNew(),
                carAdvertRequest.getMileage(),
                carAdvertRequest.getFirstRegistration(),
                CarAdvertsConstants.STATUS_ACTIVE);

    }


    public void updateCarAdvert(Long id, CarAdvertRequest carAdvertRequest) {

        int rowsAffected = jdbcTemplate.update(UPDATE_CAR_ADVERT,
                carAdvertRequest.getTitle(),
                carAdvertRequest.getFuelType(),
                carAdvertRequest.getPrice(),
                carAdvertRequest.getIsNew(),
                carAdvertRequest.getMileage(),
                carAdvertRequest.getFirstRegistration(),
                id);

        if (rowsAffected == 0) {
            throw new CarAdvertsNotFoundException("Car advert not found with id: " + id);
        }
    }

    public void deleteCarAdvert(Long id) {
        int rowsAffected = jdbcTemplate.update(DELETE_CAR_ADVERT,
                CarAdvertsConstants.STATUS_INACTIVE,
                id);

        if (rowsAffected == 0) {
            throw new CarAdvertsNotFoundException("Car advert not found with id: " + id);
        }
    }

    private static final String FIND_ALL_CAR_ADVERTS = "SELECT * " +
            "FROM core.car_advert ca " +
            "WHERE ca.active = 1 " +
            "ORDER BY %s";

    private static final String FIND_CAR_ADVERT_BY_ID = "SELECT * " +
            "FROM core.car_advert ca " +
            "WHERE ca.id = ? " +
            "AND ca.active = 1";

    private static final String INSERT_CAR_ADVERT = "INSERT INTO core.car_advert " +
            "(title, fuel_type, price, is_new, mileage, first_registration, active) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CAR_ADVERT = "UPDATE core.car_advert " +
            "SET title = ?, fuel_type = ?, price = ?, is_new = ?, mileage = ?, first_registration = ?" +
            "WHERE id = ? AND active = 1";

    private static final String DELETE_CAR_ADVERT = "UPDATE core.car_advert " +
            "SET active = ?" +
            "WHERE id = ? AND active = 1";

//    DELETE FROM core.car_advert
//    WHERE id = ? AND active = 1;
}
