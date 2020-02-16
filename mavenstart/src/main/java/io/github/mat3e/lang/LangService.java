package io.github.mat3e.lang;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

class LangService {

    private final Logger logger = LoggerFactory.getLogger(LangService.class);
    private LangRepository repository;

    LangService(LangRepository repository) {
        this.repository = repository;
    }

    List<LangDTO> findAll(){
        return repository.findAll().stream()
                .map(LangDTO::new)
                .collect(Collectors.toList());
    }
}
