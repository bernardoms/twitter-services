package com.bernardoms.twittersummarize.repository;

import com.bernardoms.twittersummarize.model.Tweet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TwitterRepository extends MongoRepository<Tweet, ObjectId> {
}
