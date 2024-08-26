package com.car.adverts.repository;

import com.car.adverts.config.exception.CarAdvertsException;
import com.car.adverts.config.exception.CarAdvertsNotFoundException;
import com.car.adverts.constants.CarAdvertsConstants;
import com.car.adverts.constants.CarAdvertsErrorMessagesConstants;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class CarAdvertRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getCarAdverts(String sortBy) {

        sortBy = sortBy != null ? sortBy.toLowerCase() : "id";
        log.info("Sorting by: {}", sortBy);

        String findAllEntitiesQuery = String.format(FIND_ALL_CAR_ADVERTS, sortBy);
        return jdbcTemplate.queryForList(findAllEntitiesQuery);
    }

    public Map<String, Object> getCarAdvert(Long id) {

        try {
            Map<String, Object> row = jdbcTemplate.queryForMap(FIND_CAR_ADVERT_BY_ID, id);
            log.info("Successfully found car advert with id: {}", id);
            return row;
        } catch (EmptyResultDataAccessException e) {
            throw new CarAdvertsNotFoundException(CarAdvertsErrorMessagesConstants.CAR_ADVERT_NOT_FOUND_ERROR);
        }
    }

    public Long addCarAdvert(CarAdvertRequest carAdvertRequest) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_CAR_ADVERT, new String[]{"id"});
            ps.setLong(1, carAdvertRequest.getId());
            ps.setString(2, carAdvertRequest.getTitle());
            if (carAdvertRequest.getFuelType() != null) {
                ps.setString(3, carAdvertRequest.getFuelType());
            } else {
                ps.setNull(3, java.sql.Types.VARCHAR);
            }

            if (carAdvertRequest.getPrice() != null) {
                ps.setInt(4, carAdvertRequest.getPrice());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            if (carAdvertRequest.getIsNew() != null) {
                ps.setBoolean(5, carAdvertRequest.getIsNew());
            } else {
                ps.setNull(5, java.sql.Types.BOOLEAN);
            }

            if (carAdvertRequest.getMileage() != null) {
                ps.setInt(6, carAdvertRequest.getMileage());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            if (carAdvertRequest.getFirstRegistration() != null) {
                ps.setDate(7, java.sql.Date.valueOf(carAdvertRequest.getFirstRegistration()));
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }
            ps.setInt(8, CarAdvertsConstants.STATUS_ACTIVE);
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }


    public void updateCarAdvert(Long id, CarAdvertRequest carAdvertRequest) {

        int rowsAffected = jdbcTemplate.update(UPDATE_CAR_ADVERT,
                carAdvertRequest.getTitle(),
                carAdvertRequest.getFuelType(),
                carAdvertRequest.getPrice(),
                carAdvertRequest.getIsNew(),
                carAdvertRequest.getMileage(),
                carAdvertRequest.getFirstRegistration(),
                carAdvertRequest.getId());

        if (rowsAffected == 0) {
            throw new CarAdvertsException(CarAdvertsErrorMessagesConstants.UPDATE_FAILED_ERROR + id);
        }
    }

    public void deleteCarAdvert(Long id) {
        int rowsAffected = jdbcTemplate.update(DELETE_CAR_ADVERT,
                CarAdvertsConstants.STATUS_INACTIVE,
                id);

        log.info("Number of affected rows: {}", rowsAffected);

        if (rowsAffected == 0) {
            throw new CarAdvertsException(CarAdvertsErrorMessagesConstants.DELETE_FAILED_ERROR + id);
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
            "(id, title, fuel_type, price, is_new, mileage, first_registration, active) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CAR_ADVERT = "UPDATE core.car_advert " +
            "SET title = ?, fuel_type = ?, price = ?, is_new = ?, mileage = ?, first_registration = ? " +
            "WHERE id = ? AND active = 1";

    private static final String DELETE_CAR_ADVERT = "UPDATE core.car_advert " +
            "SET active = ? " +
            "WHERE id = ? AND active = 1";

//    DELETE FROM core.car_advert
//    WHERE id = ? AND active = 1;
}
