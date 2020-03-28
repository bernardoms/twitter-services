package com.bernardoms.twitterextractor.unit.mapper;

import com.bernardoms.twitterextractor.mapper.TweetMapper;
import com.bernardoms.twitterextractor.model.TweetModel;
import org.junit.jupiter.api.Test;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.Collections;
import java.util.Date;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TweetMapperTest {

    private TweetMapper tweetMapper = new TweetMapper();

    @Test
    public void test_mapping_tweet_to_document() {
        var tweet = new Tweet(123, "test tweet", new Date(), "test_user", "http://test.com", 1L, 2, "en", "");
        tweet.setUser(new TwitterProfile(1, "test", "test", "", "", "", "", new Date()));
        tweet.setEntities(new Entities(Collections.emptyList(), Collections.singletonList(new HashTagEntity("test", IntStream.of(1).toArray())), Collections.emptyList(), Collections.emptyList()));
        tweet.setRetweetCount(123);

        var tweetModel = tweetMapper.toDocument(tweet);

        assertEquals(tweetModel.getHashTags().get(0), "test");
        assertEquals(tweetModel.getId(), tweet.getId());
        assertEquals(tweetModel.getLanguageCode(), tweet.getLanguageCode());
        assertEquals(tweetModel.getRetweets(), tweet.getRetweetCount().longValue());
        assertEquals(tweetModel.getText(), tweet.getText());
        assertEquals(tweetModel.getUser().getName(), tweet.getUser().getName());
        assertEquals(tweetModel.getUser().getProfileUrl(), tweet.getUser().getProfileUrl());
    }
}
