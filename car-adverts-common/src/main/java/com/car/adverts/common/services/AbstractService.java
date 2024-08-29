package com.car.adverts.common.services;


import com.car.adverts.common.config.exception.CarAdvertsException;
import com.car.adverts.common.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.common.model.CarAdvertsAuthUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Log4j2
public abstract class AbstractService {

    public CarAdvertsAuthUser getCarAdvertsAuthUser() {
        try {
            return (CarAdvertsAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException cce) {
            throw new CarAdvertsException(CarAdvertsErrorMessagesConstants.UNAUTHORIZED_ACCESS);
        }
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}

