package com.bernardoms.twittersummarize.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class Config {
    @Value("${summarizing.run.time.cron:0 0 */1 * * *}")
    private String summarizingTime;
}
