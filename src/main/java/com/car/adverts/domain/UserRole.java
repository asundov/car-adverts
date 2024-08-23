package com.car.adverts.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserRole extends AbstractDomainCore {
    
    private String uname;
    private String ucode;

    private List<UserXRole> userXRoles;
}
