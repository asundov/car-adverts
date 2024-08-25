package com.car.adverts.dto;

import com.car.adverts.domain.conf.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();
    }
}
