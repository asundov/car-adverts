package com.car.adverts.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CarAdvertsAuthUser extends User {

    private String ipAddress;

    private TokenData jwtToken;

    private TokenData refreshToken;

    private CarAdvertsUser represents;

    private String firstname;

    private String lastname;

    private String pin;

    private Long userId;

    private Long employeeId;

    public CarAdvertsAuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}

