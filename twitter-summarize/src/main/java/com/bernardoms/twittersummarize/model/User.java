package com.bernardoms.twittersummarize.model;

import lombok.Data;

@Data
public class User {
    private String name;
    private String profileUrl;
    private long followers;
    private long friends;
}
