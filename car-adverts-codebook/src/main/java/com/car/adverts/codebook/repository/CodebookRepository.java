package com.car.adverts.codebook.repository;

import hr.ericsson.eb.car.adverts.codebook.api.model.CodebookSimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CodebookRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<CodebookSimpleResponse> getSimpleCodebook(String tableName) {
        String findAllEntitiesQuery = String.format(FIND_SIMPLE_CODEBOOK, tableName);

        return jdbcTemplate.query(findAllEntitiesQuery,
                (rs, rowNum) -> new CodebookSimpleResponse(
                        rs.getLong("id"),
                        rs.getString("ucode"),
                        rs.getString("uname")));
    }

    private static final String FIND_SIMPLE_CODEBOOK = "SELECT id, ucode, uname " +
            "FROM codebook.%s d " +
            "WHERE d.active = 1 " +
            "order by uname";
}
