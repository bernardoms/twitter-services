package com.bernardoms.twitterextractor.integration.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.social.twitter.api.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TwitterExtractorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Twitter twitter;

    @MockBean
    private QueueMessagingTemplate queueMessagingTemplate;

    private static String URL_PATH = "/v1/tweets";

    @Test
    public void test_extract_and_save_tweet() throws Exception {

        var tweet = new Tweet(123, "test tweet", new Date(), "test_user", "http://test.com", 1L, 2, "en", "");
        tweet.setLanguageCode("en");
        tweet.setRetweetCount(100);
        tweet.setEntities(new Entities(Collections.emptyList(), Collections.singletonList(new HashTagEntity("test", IntStream.of(1).toArray())), Collections.emptyList(), Collections.emptyList()));
        tweet.setUser(new TwitterProfile(1, "test", "test", "", "", "", "", new Date()));

        var searchResults = new SearchResults(Collections.singletonList(tweet), new SearchMetadata(0, 0));

        when(twitter.searchOperations()).thenReturn(Mockito.mock(SearchOperations.class));
        when(twitter.searchOperations().search(any(SearchParameters.class))).thenReturn(searchResults);

        mockMvc.perform(
                post(URL_PATH+ "/hashtag"))
                .andExpect(status().isNoContent());
    }
}
