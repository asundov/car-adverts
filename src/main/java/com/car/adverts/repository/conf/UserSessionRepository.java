package com.car.adverts.repository.conf;

import com.car.adverts.config.exception.CarAdvertsException;
import com.car.adverts.config.exception.CarAdvertsNotFoundException;
import com.car.adverts.constants.CarAdvertsConstants;
import com.car.adverts.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.domain.conf.User;
import com.car.adverts.domain.conf.UserSession;
import com.car.adverts.model.CarAdvertsAuthUser;
import com.car.adverts.model.TokenData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserSessionRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserSession findByRefreshToken(String refreshToken) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            return jdbcTemplate.queryForObject(
                    FIND_USER_SESSION_BY_REFRESH_TOKEN,
                    (rs, rowNum) -> {
                        String dateTimeStr = rs.getString("refresh_token_expiration_date");

                        if (dateTimeStr != null) {
                            int dotIndex = dateTimeStr.indexOf('.');
                            if (dotIndex != -1) {
                                dateTimeStr = dateTimeStr.substring(0, dotIndex);
                            }
                        }

                        return UserSession.builder()
                                .id(rs.getLong("session_id"))
                                .user(User.builder()
                                        .id(rs.getLong("user_id"))
                                        .username(rs.getString("username"))
                                        .build())
                                .refreshTokenExpirationDate(dateTimeStr != null ?
                                        LocalDateTime.parse(dateTimeStr, formatter) : null)
                                .build();
                    },
                    refreshToken);
        } catch (Exception e) {
            throw new CarAdvertsNotFoundException(CarAdvertsErrorMessagesConstants.FIND_USER_SESSION_ERROR);
        }
    }

    public int updateExistingSessions(Long userId) {
        return jdbcTemplate.update(UPDATE_EXISTING_SESSIONS, CarAdvertsConstants.STATUS_INACTIVE, userId);
    }

    public int addNewSession(CarAdvertsAuthUser authUser, Long id) {

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
        return affectedRowsCount;
    }

    private String formatSql(String sql, Object[] params) {
        for (Object param : params) {
            String value = (param instanceof String) ? "'" + param + "'" : param.toString();
            sql = sql.replaceFirst("\\?", value);
        }
        return sql;
    }

    public void updateRefreshToken(TokenData newRefreshToken, Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedNow = LocalDateTime.now().format(formatter);
        String formattedCreated = newRefreshToken.getCreated().format(formatter);
        String formattedExpiresAt = newRefreshToken.getExpiresAt().format(formatter);

        int rowsUpdated = jdbcTemplate.update(UPDATE_REFRESH_TOKEN,
                newRefreshToken.getToken(),
                formattedCreated,
                formattedExpiresAt,
                formattedNow,
                id,
                id);

        if (rowsUpdated == 0) {
            throw new CarAdvertsException("User session not found or update failed!");
        }
    }

    public void logoutUser(User user) {

        Long userSessionId = jdbcTemplate.queryForObject(
                FIND_USER_SESSION_BY_USER,
                (rs, rowNum) -> rs.getLong("id"),
                user.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedLogout = LocalDateTime.now().format(formatter);


        Object[] params = new Object[]{
                formattedLogout,
                CarAdvertsConstants.STATUS_INACTIVE,
                formattedLogout,
                user.getId(),
                userSessionId
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

    private static final String FIND_USER_SESSION_BY_USER = "SELECT " +
            "us.id " +
            "FROM conf.user_session us " +
            "LEFT JOIN conf.user u ON u.id = us.user_id " +
            "WHERE us.status = 1 AND u.id = ?";

    private static final String UPDATE_EXISTING_SESSIONS = "UPDATE conf.user_session " +
            "SET status = ? " +
            "WHERE user_id = ? ";

    private static final String ADD_NEW_SESSIONS = "INSERT INTO conf.user_session (user_id, login_date, refresh_token, ip_address, refresh_token_creation_date, " +
            "refresh_token_expiration_date, status, created_date, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_REFRESH_TOKEN = "" +
            "UPDATE conf.user_session " +
            "SET refresh_token = ?, " +
            "    refresh_token_creation_date = CAST(? AS TIMESTAMP), " +
            "    refresh_token_expiration_date = CAST(? AS TIMESTAMP), " +
            "    modified_date = CAST(? AS TIMESTAMP), " +
            "    modified_by = ? " +
            "WHERE user_id = ?";
    private static final String LOGOUT_USER =
            "UPDATE conf.user_session " +
                    "SET  " +
                    "    logout_date = CAST(? AS TIMESTAMP),  " +
                    "    status = ?,  " +
                    "    modified_date = CAST(? AS TIMESTAMP),  " +
                    "    modified_by = ? " +
                    "WHERE id = ? ";
}
