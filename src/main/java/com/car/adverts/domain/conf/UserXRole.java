package com.car.adverts.domain.conf;

import com.car.adverts.domain.AbstractDomainCore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
