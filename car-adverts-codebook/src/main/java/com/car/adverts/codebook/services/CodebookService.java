package com.car.adverts.codebook.services;

import com.car.adverts.codebook.repository.CodebookRepository;
import hr.ericsson.eb.car.adverts.codebook.api.model.CodebookSimpleResponse;
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
