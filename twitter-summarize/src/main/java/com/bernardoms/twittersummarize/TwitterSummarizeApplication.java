package com.bernardoms.twittersummarize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.messaging.MessagingAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {ContextRegionProviderAutoConfiguration.class, ContextStackAutoConfiguration.class, MessagingAutoConfiguration.class})
public class TwitterSummarizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterSummarizeApplication.class, args);
    }

}
