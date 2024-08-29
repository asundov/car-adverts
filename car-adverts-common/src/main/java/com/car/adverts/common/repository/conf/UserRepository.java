package com.car.adverts.common.repository.conf;

import com.car.adverts.common.config.exception.CarAdvertsNotFoundException;
import com.car.adverts.common.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.common.domain.conf.User;
import com.car.adverts.common.dto.UserRowMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Log4j2
@AllArgsConstructor
@Service
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<User> findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(
                    FIND_USER_BY_ID,
                    (rs, rowNum) -> User.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .build(),
                    id);

            return Optional.ofNullable(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        try {
            User user = jdbcTemplate.queryForObject(
                    FIND_USER_BY_USERNAME,
                    (rs, rowNum) -> User.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .build(),
                    username);

            return Optional.ofNullable(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<String> findRolesByUsername(String username) {

        List<String> roleUnames = jdbcTemplate.query(FIND_ROLES_BY_USERNAME, (rs, rowNum) -> rs.getString("role_uname"), username);

        if (roleUnames.isEmpty()) {
            String userCheckSql = "SELECT COUNT(*) FROM conf.user WHERE username = ? AND active = 1";
            Integer userCount = jdbcTemplate.queryForObject(userCheckSql, Integer.class, username);
            if (userCount == null || userCount == 0) {
                throw new CarAdvertsNotFoundException(CarAdvertsErrorMessagesConstants.FIND_USER_ERROR);
            }
        }
        return roleUnames;
    }

    public List<User> findAllActiveUsers() {
        return jdbcTemplate.query(FIND_ACTIVE_USERS, new UserRowMapper());

    }

    private static final String FIND_USER_BY_ID = "SELECT id, username " +
            "FROM conf.user u " +
            "WHERE u.active = 1 " +
            "AND u.id = ?";

    private static final String FIND_USER_BY_USERNAME = "SELECT * " +
            "FROM conf.user u " +
            "WHERE u.active = 1 " +
            "AND u.username = ?";

    private static final String FIND_ROLES_BY_USERNAME = "SELECT ur.uname AS role_uname " +
            "FROM conf.user u " +
            "LEFT JOIN conf.user_x_role uxr ON u.id = uxr.user_id " +
            "LEFT JOIN conf.user_role ur ON uxr.role_id = ur.id " +
            "WHERE u.username = ? AND u.active = 1";

    private static final String FIND_ACTIVE_USERS = "SELECT id, username, first_name, last_name " +
            "FROM conf.user u " +
            "WHERE u.active = 1";
}
