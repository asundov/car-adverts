package com.car.adverts.repository.conf;

import com.car.adverts.config.exception.CarAdvertsException;
import com.car.adverts.constants.CarAdvertsConstants;
import com.car.adverts.domain.conf.User;
import com.car.adverts.domain.conf.UserSession;
import com.car.adverts.model.CarAdvertsAuthUser;
import com.car.adverts.model.TokenData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserSessionRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<UserSession> findByRefreshToken(String refreshToken) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        try {
            UserSession userSession = jdbcTemplate.queryForObject(
                    FIND_USER_SESSION_BY_REFRESH_TOKEN,
                    (rs, rowNum) -> UserSession.builder()
                            .id(rs.getLong("session_id"))
                            .user(User.builder()
                                    .id(rs.getLong("user_id"))
                                    .username(rs.getString("username"))
                                    .build())
                            .refreshTokenExpirationDate(rs.getString("refresh_token_expiration_date") != null ?
                                    LocalDateTime.parse(rs.getString("refresh_token_expiration_date"), formatter) : null)
                            .build(),
                    refreshToken);

            return Optional.ofNullable(userSession);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateExistingSessions(Long userId) {
        jdbcTemplate.update(UPDATE_EXISTING_SESSIONS, CarAdvertsConstants.STATUS_INACTIVE, userId);
    }

    public void addNewSession(CarAdvertsAuthUser authUser, Long id) {

        int affectedRowsCount = jdbcTemplate.update(ADD_NEW_SESSIONS,
                id,
                authUser.getRefreshToken().getCreated(),
                authUser.getRefreshToken().getToken(),
                authUser.getIpAddress(),
                authUser.getRefreshToken().getCreated(),
                authUser.getRefreshToken().getExpiresAt(),
                CarAdvertsConstants.STATUS_ACTIVE,
                LocalDateTime.now(),
                id);

        log.info("Number of newly created sessions: " + affectedRowsCount);

    }

    public void updateRefreshToken(TokenData newRefreshToken, Long id) {

        Object[] params = new Object[]{
                newRefreshToken.getToken(), // new refresh token
                newRefreshToken.getCreated(), // refresh token creation date
                newRefreshToken.getExpiresAt(), // refresh token expiration date
                LocalDateTime.now(), // modified date
                id, // ID of the user making the update
                id // ID of the user session to update
        };

        int rowsUpdated = jdbcTemplate.update(UPDATE_REFRESH_TOKEN, params);

        if (rowsUpdated == 0) {
            throw new CarAdvertsException("User session not found or update failed!");
        }

    }

    public void logoutUser(User user) {

        Object[] params = new Object[]{
                LocalDateTime.now(), // logout_date
                CarAdvertsConstants.STATUS_INACTIVE, // new status
                LocalDateTime.now(), // modified_date
                user.getId(), // modified_by
                user.getUsername(), // username for the subquery
                CarAdvertsConstants.STATUS_ACTIVE,
                CarAdvertsConstants.STATUS_ACTIVE
        };

        int rowsUpdated = jdbcTemplate.update(LOGOUT_USER, params);

        if (rowsUpdated == 0) {
            throw new CarAdvertsException("Unauthorized access or user/session not found!");
        }
    }

    private static final String FIND_USER_SESSION_BY_REFRESH_TOKEN = "SELECT " +
            "    us.id AS session_id, " +
            "    us.user_id, " +
            "    us.refresh_token_expiration_date, " +
            "    u.username " +
            "FROM conf.user_session us " +
            "LEFT JOIN conf.user u ON u.id = us.user_id " +
            "WHERE us.status = 1 AND us.refresh_token = ?";

    private static final String UPDATE_EXISTING_SESSIONS = "UPDATE conf.user_session " +
            "SET status = ? " +
            "WHERE user_id = ? ";

    private static final String ADD_NEW_SESSIONS = "INSERT INTO conf.user_session (user_id, login_date, refresh_token, ip_address, refresh_token_creation_date, " +
            "refresh_token_expiration_date, status, created_date, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_REFRESH_TOKEN = "UPDATE conf.user_session " +
            "   SET " +
            "   refresh_token = ?, " +
            "   refresh_token_creation_date = ?, " +
            "   refresh_token_expiration_date = ?, " +
            "   modified_date = ?, " +
            "   modified_by = ? " +
            "   WHERE id = ?";
    private static final String LOGOUT_USER =
            "UPDATE conf.user_session us " +
                    "SET " +
                    "    us.logout_date = ?, " +
                    "    us.status = ?, " +
                    "    us.modified_date = ?, " +
                    "    us.modified_by = ? " +
                    " WHERE " +
                    "   us.user_id = ( " +
                    "     SELECT u.id " +
                    "     FROM users u " +
                    "     WHERE u.username = ? " +
                    "    AND u.active = 1 " +
                    " ) " +
                    " AND us.status = 1";
}
