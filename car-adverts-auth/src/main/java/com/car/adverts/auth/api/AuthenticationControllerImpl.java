package com.car.adverts.auth.api;


import com.car.adverts.auth.services.AuthenticationService;
import hr.ericsson.eb.car.adverts.auth.api.AuthenticationApi;
import hr.ericsson.eb.car.adverts.auth.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthenticationControllerImpl implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    /**
     * POST /authentication/auth/authenticate : user authenticate call for test purposes
     *
     * @param authenticationRequest (optional)
     * @return successful login (status code 200)
     */
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    /**
     * POST /authentication/auth/refresh-token : refresh token
     *
     * @param tokenRefreshRequest (optional)
     * @return successful login (status code 200)
     */
    @Override
    public ResponseEntity<TokenRefreshResponse> refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(authenticationService.refreshtoken(tokenRefreshRequest));
    }

    /**
     * GET /authentication/auth/get-users : get login users for test purposes
     *
     * @return list of users for login (status code 200)
     */
    @Override
    public ResponseEntity<List<AuthenticationUser>> getUsers() {
        return ResponseEntity.ok(authenticationService.getUsers());
    }

    /**
     * POST /authentication/auth/logout : user logout call for test purposes
     *
     * @return successful logout (status code 200)
     */
    @Override
    public ResponseEntity<Void> logout() {
        authenticationService.logout();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}