package com.car.adverts.constants;

import java.util.Set;

public class CarAdvertsConstants {
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_INACTIVE = 0;
    public static final Set<String> VALID_SORT_COLUMNS = Set.of(
            "id",
            "title",
            "fuel_type",
            "is_new",
            "price",
            "mileage",
            "first_registration"
    );
}
