package com.bernardoms.twitterextractor.unit.service;

import com.bernardoms.twitterextractor.mapper.Mapper;
import com.bernardoms.twitterextractor.model.TweetModel;
import com.bernardoms.twitterextractor.model.UserModel;
import com.bernardoms.twitterextractor.repository.TwitterRepository;
import com.bernardoms.twitterextractor.service.TwitterExtractorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.social.twitter.api.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TwitterExtractorServiceTest {
    @InjectMocks
    private TwitterExtractorService twitterExtractorService;
    @Mock
    private TwitterRepository twitterRepository;
    @Mock
    private Twitter twitter;
    @Mock
    private Mapper<TweetModel, Tweet> mapper;
    @Mock
    private QueueMessagingTemplate queueMessagingTemplate;

    @Test
    public void test_extract_and_save_a_hashtag() {
        var tweetModel = TweetModel.builder().createdAt(LocalDateTime.now()).id(2).text("test").languageCode("en").user(UserModel.builder().followers(500).name("test_user").build()).build();

        var tweet = new Tweet(123, "test tweet", new Date(), "test_user", "http://test.com", 1L, 2, "en", "");

        tweet.setEntities(new Entities(Collections.emptyList(), Collections.singletonList(new HashTagEntity("test", IntStream.of(1).toArray())), Collections.emptyList(), Collections.emptyList()));

        var searchResults = new SearchResults(Collections.singletonList(tweet), new SearchMetadata(0, 0));

        when(twitter.searchOperations()).thenReturn(Mockito.mock(SearchOperations.class));
        when(twitter.searchOperations().search(any(SearchParameters.class))).thenReturn(searchResults);
        when(mapper.toDocument(any())).thenReturn(tweetModel);

        twitterExtractorService.extractAndSaveAllTweetsByHashTag("test");

        verify(twitterRepository, times(1)).saveAll(Collections.singletonList(tweetModel));
        verify(queueMessagingTemplate, times(1)).convertAndSend(anyString(), anyString());
    }

    @Test
    public void test_extract_and_save_a_hashtag_wrong_hashtag_from_twitter() {
        var tweet = new Tweet(123, "test tweet", new Date(), "test_user", "http://test.com", 1L, 2, "en", "");

        tweet.setEntities(new Entities(Collections.emptyList(), Collections.singletonList(new HashTagEntity("#otherHashTag", IntStream.of(1).toArray())), Collections.emptyList(), Collections.emptyList()));

        var searchResults = new SearchResults(Collections.singletonList(tweet), new SearchMetadata(0, 0));

        when(twitter.searchOperations()).thenReturn(Mockito.mock(SearchOperations.class));
        when(twitter.searchOperations().search(any(SearchParameters.class))).thenReturn(searchResults);

        twitterExtractorService.extractAndSaveAllTweetsByHashTag("test");

        verify(twitterRepository, times(0)).saveAll(Collections.emptyList());
        verify(queueMessagingTemplate, times(0)).convertAndSend(anyString(), anyString());
    }
}
