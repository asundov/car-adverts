package com.car.adverts.domain.conf;

import com.car.adverts.domain.AbstractDomainCore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
