package test.java.com.car.adverts.bl.api;

import com.car.adverts.CarAdvertsApplication;
import com.car.adverts.api.CarAdvertsControllerImpl;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CarAdvertsApplication.class)
public class CarAdvertsControllerImplTest {

    @Autowired
    private CarAdvertsControllerImpl carAdvertsControllerImpl;

    private static final Logger log = LogManager.getLogger(AuthApiControllerImplTest.class);

    private final Random random = new Random();


    @Test
//    @Disabled
    public void testGetCarAdverts() {
        ResponseEntity<List<CarAdvertResponse>> carAdvertsEntity = carAdvertsControllerImpl.getCarAdverts(null);
        log.info(carAdvertsEntity.getBody());
        assertEquals(HttpStatus.OK, carAdvertsEntity.getStatusCode());
    }

    @Test
//    @Disabled
    public void testGetCarAdvertsPaged() {
        ResponseEntity<List<CarAdvertResponse>> carAdvertsEntity = carAdvertsControllerImpl.getCarAdvertsPaged(5, 0, null);
        log.info(carAdvertsEntity.getBody());
        assertEquals(HttpStatus.OK, carAdvertsEntity.getStatusCode());
    }

    @Test
//    @Disabled
    public void addCarAdvert() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .id(random.nextLong(1000L))
                .title("testCar")
                .firstRegistration(LocalDate.now())
                .fuelType("Diesel")
                .build();

        ResponseEntity<CarAdvertResponse> carAdvertEntity = carAdvertsControllerImpl.addCarAdvert(carAdvertRequest);
        log.info(carAdvertEntity.getBody());
        assertNotNull(carAdvertEntity.getBody());
        assertNotNull(carAdvertEntity.getBody().getId());
        assertEquals(HttpStatus.CREATED, carAdvertEntity.getStatusCode());
    }

    @Test
//    @Disabled
    public void updateCarAdvert() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .id(random.nextLong(1000L))
                .title("testCar")
                .firstRegistration(LocalDate.now())
                .fuelType("Diesel")
                .build();

        ResponseEntity<CarAdvertResponse> carAdvertEntity = carAdvertsControllerImpl.addCarAdvert(carAdvertRequest);
        log.info(carAdvertEntity.getBody());
        assertNotNull(carAdvertEntity.getBody());
        assertNotNull(carAdvertEntity.getBody().getId());
        assertEquals(HttpStatus.CREATED, carAdvertEntity.getStatusCode());


        CarAdvertRequest carAdvertUpdateRequest = CarAdvertRequest.builder()
                .id(carAdvertEntity.getBody().getId())
                .title("testCar")
                .firstRegistration(LocalDate.now())
                .fuelType("Gasoline")
                .build();

        ResponseEntity<CarAdvertResponse> carAdvertUpdateEntity = carAdvertsControllerImpl.updateCarAdvert(carAdvertEntity.getBody().getId(), carAdvertUpdateRequest);
        log.info(carAdvertUpdateEntity.getBody());
        assertNotNull(carAdvertUpdateEntity.getBody());
        assertNotNull(carAdvertUpdateEntity.getBody().getId());
        assertNotEquals(carAdvertEntity.getBody().getFuelType(), carAdvertUpdateEntity.getBody().getFuelType());
        assertEquals(HttpStatus.OK, carAdvertUpdateEntity.getStatusCode());

    }

    @Test
//    @Disabled
    public void deleteCarAdvert() {
        CarAdvertRequest carAdvertRequest = CarAdvertRequest.builder()
                .id(random.nextLong(1000L))
                .title("testCar")
                .firstRegistration(LocalDate.now())
                .fuelType("Diesel")
                .build();

        ResponseEntity<CarAdvertResponse> carAdvertEntity = carAdvertsControllerImpl.addCarAdvert(carAdvertRequest);
        log.info(carAdvertEntity.getBody());
        assertNotNull(carAdvertEntity.getBody());
        assertNotNull(carAdvertEntity.getBody().getId());
        assertEquals(HttpStatus.CREATED, carAdvertEntity.getStatusCode());

        ResponseEntity<Void> carAdvertDeleteEntity = carAdvertsControllerImpl.deleteCarAdvert(carAdvertEntity.getBody().getId());
        assertEquals(HttpStatus.NO_CONTENT, carAdvertDeleteEntity.getStatusCode());
    }
}
