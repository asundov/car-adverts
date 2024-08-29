package com.car.adverts.common.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenData implements Serializable {
    private String token;
    private LocalDateTime created;
    private LocalDateTime expiresAt;
}