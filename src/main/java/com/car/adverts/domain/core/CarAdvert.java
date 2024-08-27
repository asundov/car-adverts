package com.car.adverts.domain.core;

import com.car.adverts.domain.AbstractDomainCore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CarAdvert extends AbstractDomainCore {

    private String title;

    private String fuelType;

    private Integer price;

    private Boolean isNew;

    private Integer mileage;

    private LocalDate firstRegistration;
}
