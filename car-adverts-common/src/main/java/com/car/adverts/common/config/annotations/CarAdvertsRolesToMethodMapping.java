package com.car.adverts.common.config.annotations;

import java.util.*;

public class CarAdvertsRolesToMethodMapping {

    //defines which roles can access to specific controllers, but we still need to set annotation on methods, not on controller class
    public static final Map<String, Set<String>> ROLES_TO_CONTROLLERS_MAP = new HashMap<>();
    //defines which roles can access to specific methods
    public static final Map<String, Set<String>> ROLES_TO_METHODS_MAP = new HashMap<>();

    static {
        ROLES_TO_CONTROLLERS_MAP.put("CodebookApiControllerImpl",
                new HashSet<>(Arrays.asList("admin")));
    }

    static {
        ROLES_TO_METHODS_MAP.put("addCarAdvert",
                new HashSet<>(Arrays.asList("admin")));
        ROLES_TO_METHODS_MAP.put("updateCarAdvert",
                new HashSet<>(Arrays.asList("admin")));
        ROLES_TO_METHODS_MAP.put("deleteCarAdvert",
                new HashSet<>(Arrays.asList("admin")));
    }

}

