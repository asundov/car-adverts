package test.java.com.car.adverts.common.repository;

import com.car.adverts.CarAdvertsApplication;
import com.car.adverts.admin.api.AuthApiControllerImplTest;
import com.car.adverts.common.domain.conf.UserSession;
import com.car.adverts.common.model.CarAdvertsAuthUser;
import com.car.adverts.common.model.TokenData;
import com.car.adverts.common.repository.conf.UserSessionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CarAdvertsApplication.class)
public class UserSessionRepositoryTest {

    @Autowired
    private UserSessionRepository userSessionRepository;

    private static final Logger log = LogManager.getLogger(AuthApiControllerImplTest.class);

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
