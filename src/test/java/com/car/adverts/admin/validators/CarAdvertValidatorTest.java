package com.car.adverts.admin.validators;

import com.car.adverts.CarAdvertsApplication;
import com.car.adverts.config.exception.CarAdvertsValidationException;
import com.car.adverts.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.model.CarAdvertsAuthUser;
import com.car.adverts.validators.CarAdvertValidator;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CarAdvertsApplication.class)
public class CarAdvertValidatorTest {

    @Autowired
    private CarAdvertValidator carAdvertValidator;

    @BeforeEach
    public void setup() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        CarAdvertsAuthUser authUser = new CarAdvertsAuthUser("johndoe", "", authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUser, null, authUser.getAuthorities());

        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Test
//    @Disabled
    public void testCarAdvertValidatorRequestNull() {
        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(null, null, false);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.REQUEST_NULL_ERROR));
    }

    @Test
    public void testCarAdvertValidatorTitleNull() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .title(null)
                .build();

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, null, false);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.TITLE_NULL_ERROR));

    }

    @Test
    public void testCarAdvertValidatorTitleEmpty() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .title("")
                .build();

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, null, false);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.TITLE_EMPTY_ERROR));
    }

    @Test
    public void testCarAdvertValidatorPriceNegative() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .price(-50)
                .build();

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, null, false);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.PRICE_NOT_NEGATIVE_NUMBER_ERROR));
    }

    @Test
    public void testCarAdvertValidatorIdNotPositive() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .id(-10L)
                .build();

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, null, false);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.ID_POSITIVE_NUMBER_ERROR));
    }


    @Test
    public void testCarAdvertValidatorMileageNegative() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .mileage(-5000)
                .build();

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, 24L, false);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.MILEAGE_NOT_NEGATIVE_NUMBER_ERROR));
    }

    @Test
    public void testCarAdvertValidatorIdNull() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .id(null)
                .build();

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, null, false);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.ID_NULL_ERROR));
    }

    @Test
    public void testCarAdvertValidatorNewInsertIdNull() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .id(null)
                .build();

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateCarAdvertRequest(carAdvertRequest, null, true);
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.ID_NULL_ERROR));
    }


    @Test
    public void testCarAdvertValidatorSort() {

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateSortParameter("id,");
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.NOT_VALID_SORT_INPUT_ERROR));
    }

    @Test
    public void testCarAdvertValidator() {

        CarAdvertsValidationException exception = assertThrows(CarAdvertsValidationException.class, () -> {
            carAdvertValidator.validateSortParameter("status");
        });

        assert (exception.getMessage().contains(CarAdvertsErrorMessagesConstants.NOT_VALID_COLUMN_NAME_ERROR));
    }
}
