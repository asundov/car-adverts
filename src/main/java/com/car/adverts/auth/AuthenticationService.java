package com.car.adverts.auth;

import com.car.adverts.config.exception.CarAdvertsNotFoundException;
import com.car.adverts.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.domain.conf.User;
import com.car.adverts.model.CarAdvertsAuthUser;
import com.car.adverts.repository.conf.UserRepository;
import com.car.adverts.services.AbstractService;
import com.car.adverts.services.UserService;
import hr.ericsson.eb.car.adverts.api.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationService extends AbstractService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final Environment env;
    private final UserService userService;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        CarAdvertsAuthUser authUser = userService.loginPlainUser(request.getUsername());
        return authenticateMe(authUser);
    }

    private AuthenticationResponse authenticateMe(CarAdvertsAuthUser authUser) {
        authUser.setJwtToken(jwtService.generateToken(authUser.getUsername()));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUser, null, authUser.getAuthorities());

        System.out.println("shrek i fiona " + usernamePasswordAuthenticationToken);
        System.out.println("shrek i fiona " + authUser);

        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        log.info("User: {} logged at time: {}, token: {} ", authUser.getUsername(), authUser.getJwtToken().getCreated(), authUser.getJwtToken().getToken());

        List<String> assignedFunctions = userService.getAllUniqueRolesByUser();

        return AuthenticationResponse.builder()
                .userId(authUser.getUserId())
                .token(authUser.getJwtToken().getToken())
                .expiresAt(authUser.getJwtToken().getExpiresAt().atOffset(ZoneOffset.UTC))
                .username(authUser.getUsername())
                .firstName(authUser.getFirstname())
                .lastName(authUser.getLastname())
                .refreshToken(authUser.getRefreshToken().getToken())
                .pin(authUser.getPin())
                .roles(assignedFunctions)
                .build();
    }

    public TokenRefreshResponse refreshtoken(TokenRefreshRequest request) {
        String token = request.getRefreshToken();

        CarAdvertsAuthUser authUser = userService.fetchByRefreshToken(token);

        return TokenRefreshResponse.builder()
                .accessToken(authUser.getJwtToken().getToken())
                .refreshToken(authUser.getRefreshToken().getToken())
                .build();
    }

    public List<AuthenticationUser> getUsers() {
        if (!"prod".equals(env.getProperty("environment"))) {
            List<User> users = userRepository.findAllActiveUsers();
            return users
                    .stream()
                    .map(
                            p -> AuthenticationUser.builder()
                                    .username(p.getUsername())
                                    .firstName(p.getFirstName())
                                    .lastName(p.getLastName())
                                    .build())
                    .collect(Collectors.toList());
        }
        throw new CarAdvertsNotFoundException();

    }

    public void logout() {
        try {
            CarAdvertsAuthUser authUser = (CarAdvertsAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.logoutUser(authUser.getUsername());
        } catch (ClassCastException cce) {
            throw new CarAdvertsNotFoundException(CarAdvertsErrorMessagesConstants.UNAUTHORIZED_ACCESS);
        }
    }
}

