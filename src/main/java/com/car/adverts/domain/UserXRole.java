package com.car.adverts.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserXRole extends AbstractDomainCore {
    

    private UserRole userRole;

    private User user;
}
