package com.bernardoms.twitterinfo.service;

import com.bernardoms.twitterinfo.model.TwitterSummarize;
import com.bernardoms.twitterinfo.repository.TwitterSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwiterInfoService {
    private final TwitterSummaryRepository twitterSummaryRepository;

    public List<TwitterSummarize> getSummaryInfo(String method) {

        List<TwitterSummarize> allByType;

        log.info("Getting Twitter info summarized " + method);

        if ("ByFollowers".equalsIgnoreCase(method)) {
            allByType = twitterSummaryRepository.findFirst5ByTypeIgnoreCaseOrderByCountDesc(method);
        } else {
            allByType = twitterSummaryRepository.findAllByTypeIgnoreCase(method);
        }

        log.info("Found " + allByType.size() + " aggregations");

        return allByType;
    }
}
