package com.bernardoms.twitterinfo.integration;

import com.bernardoms.twitterinfo.model.TwitterSummarize;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public abstract class IntegrationTest {


    private static boolean alreadySaved = false;

    @Autowired
    MongoTemplate mongoTemplate;


    @BeforeEach
    public void setUp() {

        if (alreadySaved) {
            return;
        }

        mongoTemplate.save(TwitterSummarize.builder().count(100).grouper("Test2").type("ByFollowers").build());
        mongoTemplate.save(TwitterSummarize.builder().count(200).grouper("Test3").type("ByFollowers").build());
        mongoTemplate.save(TwitterSummarize.builder().count(400).grouper("Test4").type("ByFollowers").build());
        mongoTemplate.save(TwitterSummarize.builder().count(300).grouper("Test5").type("ByFollowers").build());
        mongoTemplate.save(TwitterSummarize.builder().count(10).grouper("Test1").type("ByFollowers").build());
        mongoTemplate.save(TwitterSummarize.builder().count(500).grouper("Test6").type("ByFollowers").build());

        mongoTemplate.save(TwitterSummarize.builder().count(2).grouper("2020-03-16T02:00").type("ByDate").build());
        mongoTemplate.save(TwitterSummarize.builder().count(5).grouper("2020-03-16T03:00").type("ByDate").build());
        mongoTemplate.save(TwitterSummarize.builder().count(3).grouper("2020-03-16T04:00").type("ByDate").build());

        mongoTemplate.save(TwitterSummarize.builder().count(2).grouper("en #awsapigateway").type("ByHashTagAndLanguage").build());
        mongoTemplate.save(TwitterSummarize.builder().count(5).grouper("en #approov").type("ByHashTagAndLanguage").build());
        mongoTemplate.save(TwitterSummarize.builder().count(3).grouper("en #test").type("ByHashTagAndLanguage").build());

        alreadySaved = true;
    }
}
