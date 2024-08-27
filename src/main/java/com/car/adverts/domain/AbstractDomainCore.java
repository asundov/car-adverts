package com.car.adverts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@MappedSuperclass
//@EntityListeners(AbstractDomainCoreListener.class)
public abstract class AbstractDomainCore implements Serializable {

    private Long id;

    private Integer active;

    private Long createdBy;

    private LocalDateTime createdDate;

    private Long modifiedBy;

    private LocalDateTime modifiedDate;
}
