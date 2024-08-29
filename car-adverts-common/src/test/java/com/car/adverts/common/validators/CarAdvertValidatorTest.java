package test.java.com.car.adverts.common.validators;

import com.car.adverts.CarAdvertsApplication;
import com.car.adverts.admin.api.AuthApiControllerImplTest;
import com.car.adverts.common.config.exception.CarAdvertsValidationException;
import com.car.adverts.common.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.validators.CarAdvertValidator;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CarAdvertsApplication.class)
public class CarAdvertValidatorTest {

    @Autowired
    private CarAdvertValidator carAdvertValidator;

    private static final Logger log = LogManager.getLogger(AuthApiControllerImplTest.class);

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
