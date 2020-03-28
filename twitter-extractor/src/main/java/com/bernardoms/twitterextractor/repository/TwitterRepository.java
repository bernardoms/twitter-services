package com.bernardoms.twitterextractor.repository;

import com.bernardoms.twitterextractor.model.TweetModel;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface TwitterRepository extends CrudRepository<TweetModel, ObjectId> {
}
