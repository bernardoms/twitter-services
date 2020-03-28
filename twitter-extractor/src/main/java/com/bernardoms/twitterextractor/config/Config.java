package com.bernardoms.twitterextractor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class Config {
    @Value("${twitter.consumer.key}")
    private String consumerKey;
    @Value("${twitter.consumerSecret.key}")
    private String consumerSecretKey;
    @Value("${twitter.accessToken.key}")
    private String accessToken;
    @Value("${twitter.accessTokenSecret.key}")
    private String accessTokenSecret;

    @Bean
    public Twitter getTwitterTemplate() {
        return new TwitterTemplate(consumerKey, consumerSecretKey, accessToken, accessTokenSecret);
    }
}
