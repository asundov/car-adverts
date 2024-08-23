package com.car.adverts.config.annotations;

import java.util.*;

public class CarAdvertsRolesToMethodMapping {

    //defines which roles can access to specific controllers, but we still need to set annotation on methods, not on controller class
    public static final Map<String, Set<String>> ROLES_TO_CONTROLLERS_MAP = new HashMap<>();
    //defines which roles can access to specific methods
    public static final Map<String, Set<String>> ROLES_TO_METHODS_MAP = new HashMap<>();

    static {
        ROLES_TO_CONTROLLERS_MAP.put("CountryApiControllerImpl",
                new HashSet<>(Arrays.asList("admin", "skladistar")));
    }

    static {
        ROLES_TO_METHODS_MAP.put("CountryApiControllerImpl.countries",
                new HashSet<>(Arrays.asList("admin")));
    }

}

