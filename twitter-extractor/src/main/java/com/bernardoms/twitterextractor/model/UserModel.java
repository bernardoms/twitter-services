package com.bernardoms.twitterextractor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private String name;
    private String profileUrl;
    private long followers;
    private long friends;
}
