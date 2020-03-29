package com.bernardoms.twittersummarize.service;

import com.bernardoms.twittersummarize.model.Tweet;
import com.bernardoms.twittersummarize.model.TwitterSummarize;
import com.bernardoms.twittersummarize.repository.TwitterRepository;
import com.bernardoms.twittersummarize.repository.TwitterSummarizeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterSummarizeService {
    private final TwitterSummarizeRepository twitterSummarizeRepository;
    private final TwitterRepository twitterRepository;

    @SqsListener(value = "summarize", deletionPolicy = ON_SUCCESS)
    private void receiveFromQueue() {
        log.info("received new tweets from sqs, summarizing!");
        this.group();
    }

    public void group() {
        List<Tweet> tweets = new ArrayList<>(twitterRepository.findAll());
        groupByFollowers(tweets);
        groupTweetByHour(tweets);
        groupTweetByHashTag(tweets);
    }

    private void groupByFollowers(List<Tweet> tweets) {
        log.info("Summarizing by followers " +  tweets.size() + "tweets");
        var collect = tweets.stream().collect(toMap(t -> t.getUser().getName(), tweet -> tweet.getUser().getFollowers(), (e1, e2) -> e1));

        var byFollowers = collect.entrySet().stream().map((k) -> TwitterSummarize.builder().grouper(k.getKey()).count(k.getValue()).type("ByFollowers").build()).collect(toList());

        twitterSummarizeRepository.saveAll(byFollowers);
    }

    private void groupTweetByHour(List<Tweet> tweets) {
        log.info("Summarizing by hour " +  tweets.size() + "tweets");
        var collect = tweets.stream().collect(groupingBy(t -> t.getCreatedAt().truncatedTo(ChronoUnit.HOURS), counting()));
        var byDate = collect.entrySet().stream().map((k) -> TwitterSummarize.builder().grouper(k.getKey().toString()).count(k.getValue()).type("ByDate").build()).collect(toList());

        twitterSummarizeRepository.saveAll(byDate);
    }

    private void groupTweetByHashTag(List<Tweet> tweets) {
        log.info("Summarizing by hash tag and language " +  tweets.size() + "tweets");
        List<TwitterSummarize> twitterSummarizes = new ArrayList<>();

      var hashTagsByLanguage = tweets.stream()
                .flatMap(g -> g.getHashTags().stream()
                        .map(e -> new AbstractMap.SimpleEntry<>(g.getLanguageCode(), e.toLowerCase())))
                .collect(groupingBy(Map.Entry::getKey,
                        groupingBy(Map.Entry::getValue, counting())));

        hashTagsByLanguage.forEach((key, value) -> value.forEach((key1, value1) -> {
            TwitterSummarize twitterSummarize = TwitterSummarize.builder().type("ByHashTagAndLanguage").build();
            twitterSummarize.setCount(value1);
            twitterSummarize.setGrouper(key + " " + "#"+key1);
            twitterSummarizes.add(twitterSummarize);
        }));

        twitterSummarizeRepository.saveAll(twitterSummarizes);
    }
}
