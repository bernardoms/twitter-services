package com.bernardoms.twitterextractor.service;

import com.bernardoms.twitterextractor.repository.TwitterRepository;
import com.bernardoms.twitterextractor.mapper.Mapper;
import com.bernardoms.twitterextractor.model.TweetModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterExtractorService {
    private final Twitter twitter;
    private final TwitterRepository twitterRepository;
    private final Mapper<TweetModel, Tweet> mapper;

    public void extractAndSaveAllTweetsByHashTag(String hashTag) {

        log.info("Getting all recent tweets by hash tag" + hashTag);

        var tweets = twitter.searchOperations().search(
                new SearchParameters("%23" + hashTag)
                        .resultType(SearchParameters.ResultType.RECENT)
                        .count(100)
                        .includeEntities(true)).getTweets().stream()
                .filter(tweet -> tweet.getEntities().getHashTags()
                        .stream().anyMatch(hashTagEntity -> hashTag.equalsIgnoreCase(hashTagEntity.getText())))
                .collect(Collectors.toList());
        log.info("Saving " + tweets.size() + "tweets by hash tag " + hashTag );
        save(tweets);
    }

    private void save(List<Tweet> tweets) {
        var tweetModels = tweets.stream().map(mapper::toDocument).collect(Collectors.toList());
        twitterRepository.saveAll(tweetModels);
    }
}
