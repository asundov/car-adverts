package com.car.adverts.common.model;

import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken {

    private User user;

    private String token;

    private Instant expiryDate;
}
