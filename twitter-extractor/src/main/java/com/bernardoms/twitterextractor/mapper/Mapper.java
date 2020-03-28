package com.bernardoms.twitterextractor.mapper;

import org.springframework.stereotype.Component;

public interface Mapper<MODEL, DTO> {
    MODEL toDocument(DTO dto);
}
