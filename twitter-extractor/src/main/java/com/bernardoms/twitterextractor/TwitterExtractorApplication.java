package com.bernardoms.twitterextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.messaging.MessagingAutoConfiguration;

@SpringBootApplication(exclude = {ContextRegionProviderAutoConfiguration.class, ContextStackAutoConfiguration.class, MessagingAutoConfiguration.class})
public class TwitterExtractorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterExtractorApplication.class, args);
    }

}
