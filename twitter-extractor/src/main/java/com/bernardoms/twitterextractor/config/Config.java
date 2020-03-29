package com.bernardoms.twitterextractor.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

    @Value("${sqs.endpoint}")
    private String endpoint;
    @Value("${sqs.region}")
    private String region;
    @Value("${sqs.accessKey}")
    private String accessKey;
    @Value("${sqs.secretKey}")
    private String secretKey;

    @Bean
    public Twitter getTwitterTemplate() {
        return new TwitterTemplate(consumerKey, consumerSecretKey, accessToken, accessTokenSecret);
    }


    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .build();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(
            AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }
}
