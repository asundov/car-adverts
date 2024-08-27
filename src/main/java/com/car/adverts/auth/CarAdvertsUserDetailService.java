package com.car.adverts.auth;

import com.car.adverts.services.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CarAdvertsUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CarAdvertsUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Cacheable(value = "userCache")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getCarAdvertsAuthUser(username, false);
    }
}

