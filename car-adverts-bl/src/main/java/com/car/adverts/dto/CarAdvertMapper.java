package com.car.adverts.dto;


import hr.ericsson.eb.car.adverts.bl.api.model.CarAdvertResponse;

import java.sql.Date;
import java.util.Map;

public class CarAdvertMapper {

    public static CarAdvertResponse mapRowToCarAdvertResponse(Map<String, Object> row) {
        return CarAdvertResponse.builder()
                .id((Long) row.get("id"))
                .title((String) row.get("title"))
                .fuelType((String) row.get("fuel_type"))
                .price((Integer) row.get("price"))
                .isNew((Boolean) row.get("is_new"))
                .mileage((Integer) row.get("mileage"))
                .firstRegistration(row.get("first_registration") != null ? ((Date) row.get("first_registration")).toLocalDate() : null)
                .build();
    }
}
