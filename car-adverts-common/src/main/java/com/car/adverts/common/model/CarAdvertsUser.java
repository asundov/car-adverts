package com.car.adverts.common.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarAdvertsUser implements Serializable {

    private Long userId;

    private String firstname;

    private String lastname;

    private String pin;

    private String ipAddress;

    private TokenData jwtToken;

    private TokenData refreshToken;
}

