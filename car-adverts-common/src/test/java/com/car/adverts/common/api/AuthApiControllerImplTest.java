package test.java.com.car.adverts.common.api;


import com.car.adverts.CarAdvertsApplication;
import com.car.adverts.auth.AuthenticationControllerImpl;
import com.car.adverts.common.config.exception.CarAdvertsNotFoundException;
import com.car.adverts.common.domain.conf.User;
import com.car.adverts.common.model.CarAdvertsAuthUser;
import com.car.adverts.common.repository.conf.UserRepository;
import com.car.adverts.common.repository.conf.UserSessionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = CarAdvertsApplication.class)
public class AuthApiControllerImplTest {

    @Autowired
    private AuthenticationControllerImpl authApiControllerImpl;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    private static final Logger log = LogManager.getLogger(AuthApiControllerImplTest.class);

    @Test
//    @Disabled
    public void testAuthenticate() {
        User user = userRepository.findByUsername("johndoe").orElseThrow();
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(user.getUsername()).password("***").build();
        ResponseEntity<AuthenticationResponse> responseEntity = authApiControllerImpl.authenticate(authenticationRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
//    @Disabled
    public void testGetUsers() {
        ResponseEntity<List<AuthenticationUser>> authUsersEntity = authApiControllerImpl.getUsers();
        log.info(authUsersEntity.getBody());
        assertEquals(HttpStatus.OK, authUsersEntity.getStatusCode());
    }

    @Test
//    @Disabled
    public void testRefreshToken() {
        User user = userRepository.findByUsername("johndoe").orElseThrow();
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(user.getUsername()).password("***").build();
        ResponseEntity<AuthenticationResponse> authenticationResponse = authApiControllerImpl.authenticate(authenticationRequest);
        assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());
        assertNotNull(authenticationResponse.getBody());

        String oldToken = authenticationResponse.getBody().getRefreshToken();
        log.info("Old token: {}", oldToken);
        ResponseEntity<TokenRefreshResponse> refreshTokenResponse = authApiControllerImpl.refreshToken(TokenRefreshRequest.builder()
                .refreshToken(oldToken)
                .build());
        assertNotNull(refreshTokenResponse.getBody());
        log.info("New token: {}", refreshTokenResponse.getBody().getRefreshToken());
        assertEquals(HttpStatus.OK, refreshTokenResponse.getStatusCode());
        assertNotEquals(oldToken, refreshTokenResponse.getBody().getRefreshToken());
    }

    @Test
//    @Disabled
    public void testLogout() {
        User user = userRepository.findByUsername("johndoe").orElseThrow();
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(user.getUsername()).password("***").build();
        ResponseEntity<AuthenticationResponse> authenticationResponse = authApiControllerImpl.authenticate(authenticationRequest);
        assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());
        assertNotNull(authenticationResponse.getBody());

        CarAdvertsAuthUser authUser = (CarAdvertsAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Logged user: {}", authUser);
        ResponseEntity<Void> logoutResponseEntity = authApiControllerImpl.logout();
        assertEquals(HttpStatus.NO_CONTENT, logoutResponseEntity.getStatusCode());

        assertThrows(CarAdvertsNotFoundException.class, () -> {
            userSessionRepository.findByRefreshToken(authenticationResponse.getBody().getRefreshToken());
        });
    }
}
