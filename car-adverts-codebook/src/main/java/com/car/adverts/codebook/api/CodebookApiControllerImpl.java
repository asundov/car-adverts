package com.car.adverts.codebook.api;

import com.car.adverts.common.config.annotations.PreAuthorizeCarAdverts;
import com.car.adverts.codebook.services.CodebookService;
import hr.ericsson.eb.car.adverts.codebook.api.CodebooksApi;
import hr.ericsson.eb.car.adverts.codebook.api.model.CodebookSimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api")
public class CodebookApiControllerImpl implements CodebooksApi {
    private final CodebookService codebookService;

    /**
     * GET /codebooks/fuel-types : get fuel types
     *
     * @return Return list of fuel types (status code 200)
     */
    @Override
    @PreAuthorizeCarAdverts
    public ResponseEntity<List<CodebookSimpleResponse>> getFuelTypes() {
        return ResponseEntity.ok(codebookService.getSimpleCodebook("fuel_type"));
    }
}
