package com.car.adverts.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends AbstractDomainCore {

    private String username;

    private String firstName;

    private String lastName;

    private Boolean admin;

    private List<UserXRole> userXRoles;
    private Integer status;

}
