package com.bernardoms.twitterextractor.mapper;

import com.bernardoms.twitterextractor.model.TweetModel;
import com.bernardoms.twitterextractor.model.UserModel;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.stream.Collectors;

@Component
public class TweetMapper implements Mapper<TweetModel, Tweet> {

    @Override
    public TweetModel toDocument(Tweet tweet) {
        return TweetModel.builder().createdAt(tweet.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .text(tweet.getText())
                .languageCode(tweet.getLanguageCode()).retweets(tweet.getRetweetCount())
                .id(tweet.getId())
                .hashTags(tweet.getEntities().getHashTags().stream().map(HashTagEntity::getText).collect(Collectors.toList()))
                .user(UserModel.builder().followers(tweet.getUser().getFollowersCount())
                        .friends(tweet.getUser().getFriendsCount())
                        .name(tweet.getUser().getName())
                        .profileUrl(tweet.getUser().getProfileUrl())
                        .build())
                .build();
    }
}
