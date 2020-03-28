package com.bernardoms.twitterinfo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "summary")
public class TwitterSummarize {
    private String type;
    @Id
    private String grouper;
    private long count;
}
