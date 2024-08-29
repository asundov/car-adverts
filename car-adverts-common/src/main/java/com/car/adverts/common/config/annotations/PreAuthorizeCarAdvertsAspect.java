package com.car.adverts.common.config.annotations;

import com.car.adverts.common.model.CarAdvertsAuthUser;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Aspect
@Component
@NoArgsConstructor
@Log4j2
public class PreAuthorizeCarAdvertsAspect {

    @Before("@annotation(PreAuthorizeCarAdverts)")
    public Object preAuthorizeCarAdverts(JoinPoint joinPoint) throws Throwable {

        String controllerName = joinPoint.getTarget().getClass().getSimpleName();
        //to avoid methods with same name in different controllers
        String method = joinPoint.getSignature().getName();

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        final CarAdvertsAuthUser authUser = (CarAdvertsAuthUser) auth.getPrincipal();
        log.debug(authUser.getUsername());

        //check if user have any restrictions defined for targeted controller
        if (!checkRolesToControllers(authUser.getAuthorities(), controllerName)) {
            log.warn("User = {} access denied for {}", authUser.getUsername(), controllerName);
            throw new AccessDeniedException("Request denied");
        }

        //check if restrictions are defined for specific method inside controller
        if (!checkRolesToMethods(authUser.getAuthorities(), method)) {
            log.warn("User = {} access denied for {}", authUser.getUsername(), method);
            throw new AccessDeniedException("Request denied");
        }
        log.warn("User = {} access allowed for {} in {}", authUser.getUsername(), method, controllerName);
        return true;
    }

    private boolean checkRolesToMethods(Collection<GrantedAuthority> roles, String method) {
        Set<String> authRoles = CarAdvertsRolesToMethodMapping.ROLES_TO_METHODS_MAP.get(method);

        log.debug("-------------------- ROLE TO METHOD --------------------------------");
        log.debug("Check access for: {}, methodRoles: {}, userRoles: {}", method, authRoles, roles);

        if (authRoles == null) {
            log.debug("No restrictions for this method.");
            return true;
        }
        for (GrantedAuthority role : roles) {
            if (authRoles.contains(role.getAuthority())) {
                log.debug("User has proper roles for this method.");
                return true;
            }
        }
        log.debug("User does NOT have proper roles for this method.");
        return false;
    }

    private boolean checkRolesToControllers(Collection<GrantedAuthority> roles, String controllerName) {
        Set<String> authRoles = CarAdvertsRolesToMethodMapping.ROLES_TO_CONTROLLERS_MAP.get(controllerName);

        log.debug("-------------------- ROLE TO CONTROLLER------------------------------");
        log.debug("Check access for: {}, methodRoles: {}, userRoles: {}", controllerName, authRoles, roles);

        if (authRoles == null) {
            log.debug("No restrictions for this controller.");
            return true;
        }
        for (GrantedAuthority role : roles) {
            if (authRoles.contains(role.getAuthority())) {
                log.debug("User has proper roles for this controller.");
                return true;
            }
        }
        log.debug("User does NOT have proper roles for this controller.");
        return false;
    }

}

