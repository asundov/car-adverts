package com.car.adverts.api;

import com.car.adverts.config.annotations.PreAuthorizeCarAdverts;
import com.car.adverts.services.CarAdvertService;
import hr.ericsson.eb.car.adverts.api.CarApi;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertRequest;
import hr.ericsson.eb.car.adverts.api.model.CarAdvertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CarAdvertsControllerImpl implements CarApi {

    private final CarAdvertService carAdvertService;

    /**
     * GET /car/adverts : get car adverts where user have access
     *
     * @param sortby parameter sortby (optional)
     * @return Return list of car adverts (status code 200)
     */
    @Override
    @PreAuthorizeCarAdverts
    public ResponseEntity<List<CarAdvertResponse>> getCarAdverts(String sortby) {
        return ResponseEntity.ok(carAdvertService.getCarAdverts(sortby));
    }

    /**
     * GET /car/adverts/:id : get car advert with id where user have access
     *
     * @param id parameter car advert id (required)
     * @return Return car advert (status code 200)
     * or No car advert with given id was found (status code 404)
     */
    @Override
    @PreAuthorizeCarAdverts
    public ResponseEntity<CarAdvertResponse> getCarAdvert(Long id) {
        return ResponseEntity.ok(carAdvertService.getCarAdvert(id));
    }

    /**
     * GET /car/adverts-paginated : get car adverts paginated where user have access
     *
     * @param sortby parameter sortby (optional)
     * @param limit  parameter limit (optional)
     * @param offset parameter offset (optional)
     * @return Return list of car adverts (status code 200)
     */
    @Override
    @PreAuthorizeCarAdverts
    public ResponseEntity<List<CarAdvertResponse>> getCarAdvertsPaged(String sortby, Integer limit, Integer offset) {
        return ResponseEntity.ok(carAdvertService.getCarAdvertsPaged(sortby, limit, offset));
    }

    /**
     * POST /car/adverts : add car advert
     *
     * @param carAdvertRequest (optional)
     * @return Car advert added successfully (status code 201)
     * or This is returned if json is invalid or cannot be parsed (status code 400)
     * or Validation failed (status code 422)
     */
    @Override
    @PreAuthorizeCarAdverts
    public ResponseEntity<CarAdvertResponse> addCarAdvert(CarAdvertRequest carAdvertRequest) {
        return new ResponseEntity<>(carAdvertService.addCarAdvert(carAdvertRequest), HttpStatus.CREATED);
    }

    /**
     * PUT /car/adverts/:id : update car advert
     *
     * @param id               parameter car advert id (required)
     * @param carAdvertRequest (optional)
     * @return Return updated car advert (status code 200)
     * or This is returned if json is invalid or cannot be parsed (status code 400)
     * or This is returned if a car advert with given id is not found (status code 404)
     * or Validation failed (status code 422)
     */
    @Override
    @PreAuthorizeCarAdverts
    public ResponseEntity<CarAdvertResponse> updateCarAdvert(Long id, CarAdvertRequest carAdvertRequest) {
        return ResponseEntity.ok(carAdvertService.updateCarAdvert(id, carAdvertRequest));
    }

    /**
     * DELETE /car/adverts/:id : delete car advert
     *
     * @param id parameter car advert id (required)
     * @return Car advert successfully deleted (status code 204)
     * or This is returned if a car advert with given id is not found (status code 404)
     */
    @Override
    @PreAuthorizeCarAdverts
    public ResponseEntity<Void> deleteCarAdvert(Long id) {
        carAdvertService.deleteCarAdvert(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
