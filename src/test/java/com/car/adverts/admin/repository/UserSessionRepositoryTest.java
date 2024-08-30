package com.car.adverts.admin.repository;

import com.car.adverts.CarAdvertsApplication;
import com.car.adverts.domain.conf.UserSession;
import com.car.adverts.model.CarAdvertsAuthUser;
import com.car.adverts.model.TokenData;
import com.car.adverts.repository.conf.UserSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CarAdvertsApplication.class)
public class UserSessionRepositoryTest {

    @Autowired
    private UserSessionRepository userSessionRepository;

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
    public void testAddNewSession() {

        CarAdvertsAuthUser authUser = new CarAdvertsAuthUser("johndoe", "**", List.of());
        authUser.setRefreshToken(TokenData.builder()
                .created(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(2L))
                .token("3691c9bd1a4bd35e10af2f06cc9500a51fa91eff37327d24960a19ea52ef3754")
                .build());
        authUser.setIpAddress("127.0.0.1");
        int rowsAffected = userSessionRepository.addNewSession(authUser, 1L);
        assert (rowsAffected > 0);
    }


    @Test
//    @Disabled
    public void testLogoutSessions() {
        int rowsAffected = userSessionRepository.updateExistingSessions(1L);
        assert (rowsAffected > 0);
    }

    @Test
//    @Disabled
    public void testFindByRefreshToken() {

        String oldToken = "3691c9bd1a4erf5e10af2f06cc9500a51fa91eff37327d24960a19ea52ef3754";
        CarAdvertsAuthUser authUser = new CarAdvertsAuthUser("johndoe", "**", Arrays.asList());
        authUser.setRefreshToken(TokenData.builder()
                .created(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(2L))
                .token(oldToken)
                .build());
        authUser.setIpAddress("127.0.0.1");
        int rowsAffected = userSessionRepository.addNewSession(authUser, 1L);
        assert (rowsAffected > 0);

        UserSession userSession = userSessionRepository.findByRefreshToken(oldToken);
        assertNotNull(userSession.getId());
    }

    @Test
//    @Disabled
    public void testUpdateRefreshToken() {

        String oldToken = "3691c9bd1a4erf5e10af2f06ff37327d24960a19ea52ef3754";
        CarAdvertsAuthUser authUser = new CarAdvertsAuthUser("johndoe", "**", Arrays.asList());
        authUser.setRefreshToken(TokenData.builder()
                .created(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(2L))
                .token(oldToken)
                .build());
        authUser.setIpAddress("127.0.0.1");
        int rowsAffected = userSessionRepository.addNewSession(authUser, 1L);
        assert (rowsAffected > 0);

        String newToken = "cdsdcsf2f06cc9500a51fa91eff37327d24960a19ea52ef3754";
        TokenData newData = TokenData.builder()
                .created(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(2L))
                .token(newToken)
                .build();
        userSessionRepository.updateRefreshToken(newData, 1L);

        UserSession userSession = userSessionRepository.findByRefreshToken(newToken);

        assertNotEquals(oldToken, userSession.getRefreshToken());

    }
}
