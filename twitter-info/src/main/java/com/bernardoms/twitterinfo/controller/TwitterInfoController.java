package com.bernardoms.twitterinfo.controller;

import com.bernardoms.twitterinfo.model.TwitterSummarize;
import com.bernardoms.twitterinfo.service.TwiterInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tweets")
@RequiredArgsConstructor
public class TwitterInfoController {
    private final TwiterInfoService twiterInfoService;

    @GetMapping(value = "/summarized/{method}")
    @ResponseStatus(HttpStatus.OK)
    public List<TwitterSummarize> getTweetSummarizedByMethod(@PathVariable String method){
        return twiterInfoService.getSummaryInfo(method);
    }
}
