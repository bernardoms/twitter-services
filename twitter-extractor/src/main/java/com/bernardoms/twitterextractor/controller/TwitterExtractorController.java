package com.bernardoms.twitterextractor.controller;

import com.bernardoms.twitterextractor.service.TwitterExtractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tweets")
@RequiredArgsConstructor
public class TwitterExtractorController {

    private final TwitterExtractorService twitterExtractorService;

    @PostMapping(value = "{hashTag}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void extractAndSaveAllTweetsByHashTag(@PathVariable String hashTag)  {
        twitterExtractorService.extractAndSaveAllTweetsByHashTag(hashTag);
    }

}
