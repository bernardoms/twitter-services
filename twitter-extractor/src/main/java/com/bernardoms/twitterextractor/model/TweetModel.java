package com.bernardoms.twitterextractor.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "tweets")
@Data
@Builder
public class TweetModel {
    @Id
    private long id;
    private String text;
    private List<String> hashTags;
    private String languageCode;
    private UserModel user;
    private long retweets;
    private LocalDateTime createdAt;
}
