package com.bernardoms.twitterextractor.integration.service;

import com.bernardoms.twitterextractor.service.TwitterExtractorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.social.twitter.api.*;

import java.util.Collections;
import java.util.Date;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class TwitterExtractorServiceTest {

    @Autowired
    private TwitterExtractorService twitterExtractorService;

    @MockBean
    private Twitter twitter;

    @Test
    public void test_extract_and_save_a_hashtag() {
        var tweet = new Tweet(123, "test tweet", new Date(), "test_user", "http://test.com", 1L, 2, "en", "");
        tweet.setLanguageCode("en");
        tweet.setRetweetCount(100);
        tweet.setEntities(new Entities(Collections.emptyList(), Collections.singletonList(new HashTagEntity("test", IntStream.of(1).toArray())), Collections.emptyList(), Collections.emptyList()));
        tweet.setUser(new TwitterProfile(1, "test", "test", "", "", "", "", new Date()));

        var searchResults = new SearchResults(Collections.singletonList(tweet), new SearchMetadata(0, 0));

        when(twitter.searchOperations()).thenReturn(Mockito.mock(SearchOperations.class));
        when(twitter.searchOperations().search(any(SearchParameters.class))).thenReturn(searchResults);

        twitterExtractorService.extractAndSaveAllTweetsByHashTag("test");
    }
}
