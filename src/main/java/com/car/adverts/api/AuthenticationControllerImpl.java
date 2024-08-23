package com.car.adverts.api;

import hr.ericsson.eb.car.adverts.api.AuthenticationApi;
import hr.ericsson.eb.car.adverts.api.model.AuthenticationRequest;
import hr.ericsson.eb.car.adverts.api.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthenticationControllerImpl implements AuthenticationApi {

    /**
     * POST /authentication/auth/authenticate : user authenticate call for test purposes
     *
     * @param authenticationRequest (optional)
     * @return successful login (status code 200)
     */
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .firstName("jeeeej")
                .build());
    }
}