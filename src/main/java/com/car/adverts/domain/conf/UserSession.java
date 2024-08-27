package com.car.adverts.domain.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    private Long id;

    private Integer status;

    private Long createdBy;

    private LocalDateTime createdDate;

    private Long modifiedBy;

    private LocalDateTime modifiedDate;

    private User user;

    private LocalDateTime loginDate;

    private LocalDateTime logoutDate;

    private String ipAddress;

    private String refreshToken;

    private LocalDateTime refreshTokenCreationDate;

    private LocalDateTime refreshTokenExpirationDate;
}
