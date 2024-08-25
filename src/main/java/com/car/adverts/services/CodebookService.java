package com.car.adverts.services;

import com.car.adverts.repository.CodebookRepository;
import hr.ericsson.eb.car.adverts.api.model.CodebookSimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CodebookService {
    private final CodebookRepository codebookRepository;

    public List<CodebookSimpleResponse> getSimpleCodebook(String tableName) {
        return codebookRepository.getSimpleCodebook(tableName);
    }
}
