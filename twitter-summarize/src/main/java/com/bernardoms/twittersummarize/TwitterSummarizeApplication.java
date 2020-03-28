package com.bernardoms.twittersummarize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TwitterSummarizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterSummarizeApplication.class, args);
    }

}
