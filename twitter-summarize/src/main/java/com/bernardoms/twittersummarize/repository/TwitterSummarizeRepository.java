package com.bernardoms.twittersummarize.repository;

import com.bernardoms.twittersummarize.model.TwitterSummarize;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface TwitterSummarizeRepository extends CrudRepository<TwitterSummarize, String> {

}
