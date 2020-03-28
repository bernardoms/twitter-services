package com.bernardoms.twittersummarize.integration;

import com.bernardoms.twittersummarize.model.Tweet;
import com.bernardoms.twittersummarize.util.MillisOrLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
public abstract class IntegrationTest {


    private static boolean alreadySaved = false;

    @Autowired
    MongoTemplate mongoTemplate;

    public ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setUp() throws IOException {

        if (alreadySaved) {
            return;
        }
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new MillisOrLocalDateTimeDeserializer());

        mapper.registerModule(javaTimeModule);

        List<Tweet> tweetList = Arrays.stream(mapper.readValue(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("tweets.json")), Tweet[].class)).collect(Collectors.toList());
        tweetList.forEach(t->mongoTemplate.save(t));
        alreadySaved = true;
    }
}
