package com.car.adverts.codebook.api;

import com.car.adverts.codebook.CarAdvertsCodebookApplication;
import com.car.adverts.common.model.CarAdvertsAuthUser;
import hr.ericsson.eb.car.adverts.codebook.api.model.CodebookSimpleResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CarAdvertsCodebookApplication.class)
public class CodebookControllerImplTest {

    @Autowired
    private CodebookApiControllerImpl codebookApiControllerImpl;

    private static final Logger log = LogManager.getLogger(CodebookControllerImplTest.class);
    @BeforeEach
    public void setup() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        CarAdvertsAuthUser authUser = new CarAdvertsAuthUser("johndoe", "", authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUser, null, authUser.getAuthorities());

        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
    }
    @Test
//    @Disabled
    public void testGetFuelTypes() {
        ResponseEntity<List<CodebookSimpleResponse>> fuelTypesEntity = codebookApiControllerImpl.getFuelTypes();
        log.info(fuelTypesEntity.getBody());
        assertEquals(HttpStatus.OK, fuelTypesEntity.getStatusCode());
    }
}
