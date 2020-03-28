package com.bernardoms.twitterinfo.repository;

import com.bernardoms.twitterinfo.model.TwitterSummarize;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TwitterSummaryRepository extends MongoRepository<TwitterSummarize, String> {
    List<TwitterSummarize> findAllByTypeIgnoreCase(String type);
    List<TwitterSummarize> findFirst5ByTypeIgnoreCaseOrderByCountDesc(String type);
}
