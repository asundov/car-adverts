package com.car.adverts.common.services;


import com.car.adverts.common.auth.JwtService;
import com.car.adverts.common.config.exception.CarAdvertsException;
import com.car.adverts.common.config.exception.TokenRefreshException;
import com.car.adverts.common.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.common.domain.conf.User;
import com.car.adverts.common.domain.conf.UserSession;
import com.car.adverts.common.model.CarAdvertsAuthUser;
import com.car.adverts.common.model.TokenData;
import com.car.adverts.common.repository.conf.UserRepository;
import com.car.adverts.common.repository.conf.UserSessionRepository;
import com.car.adverts.common.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserService extends AbstractService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;

    @Transactional
    public CarAdvertsAuthUser loginPlainUser(String username) {
        return getCarAdvertsAuthUser(username, true);
    }

    public User getLoggedUser() {
        CarAdvertsAuthUser authUser = getCarAdvertsAuthUser();
        return userRepository.findById(authUser.getUserId()).orElseThrow(() -> new CarAdvertsException(CarAdvertsErrorMessagesConstants.UNAUTHORIZED_ACCESS));
    }

    public CarAdvertsAuthUser getCarAdvertsAuthUser(String username, boolean persistLogin) {
        List<String> roles = userRepository.findRolesByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        User user = userRepository.findByUsername(username).orElseThrow(() -> new CarAdvertsException(CarAdvertsErrorMessagesConstants.UNAUTHORIZED_ACCESS));
        CarAdvertsAuthUser authUser = getCarAdvertsAuthUser(user, authorities);
        if (persistLogin) {
            persistUserLogin(authUser, user);
        }


        return authUser;
    }

    private CarAdvertsAuthUser getCarAdvertsAuthUser(User user, List<GrantedAuthority> authorities) {
        CarAdvertsAuthUser authUser = new CarAdvertsAuthUser(user.getUsername(), "", authorities);
        authUser.setUserId(user.getId());
        authUser.setFirstname(user.getFirstName());
        authUser.setLastname(user.getLastName());
        authUser.setIpAddress(getRequest() != null ? HttpUtils.getClientIpAddress(getRequest()) : null);
        return authUser;
    }

    private void persistUserLogin(CarAdvertsAuthUser authUser, User user) {

        userSessionRepository.updateExistingSessions(user.getId());
        authUser.setRefreshToken(jwtService.getRefreshToken(authUser.getUsername()));

        userSessionRepository.addNewSession(authUser, user.getId());
    }

    public CarAdvertsAuthUser fetchByRefreshToken(String token) {
        UserSession us = userSessionRepository.findByRefreshToken(token);
        List<String> roles = userRepository.findRolesByUsername(us.getUser().getUsername());

        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        if (!us.getRefreshTokenExpirationDate().isBefore(LocalDateTime.now())) {
            CarAdvertsAuthUser authUser = new CarAdvertsAuthUser(us.getUser().getUsername(), "", authorities);
            TokenData newAccessToken = jwtService.generateToken(us.getUser().getUsername());
            TokenData newRefreshToken = jwtService.getRefreshToken(authUser.getUsername());

            userSessionRepository.updateRefreshToken(newRefreshToken, us.getUser().getId());

            authUser.setJwtToken(newAccessToken);
            authUser.setRefreshToken(newRefreshToken);
            return authUser;
        } else
            throw new TokenRefreshException(CarAdvertsErrorMessagesConstants.USER_SESSION_EXPIRED_ERROR);
    }

    public void logoutUser(String username) {
        User user = getLoggedUser();
        log.info("Logger user: {}", user);
        userSessionRepository.logoutUser(user);
    }

    public List<String> getAllUniqueRolesByUser() {
        User user = userRepository.findById(getCarAdvertsAuthUser().getUserId()).orElseThrow(() -> new CarAdvertsException(CarAdvertsErrorMessagesConstants.UNAUTHORIZED_ACCESS));

        log.info("Get all unique user roles!");
        List<String> roles = userRepository.findRolesByUsername(user.getUsername());

        log.info("Allowed roles: {}", roles);
        return roles;
    }


}
