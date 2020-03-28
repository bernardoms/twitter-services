package com.bernardoms.twitterinfo.unit.service;

import com.bernardoms.twitterinfo.model.TwitterSummarize;
import com.bernardoms.twitterinfo.repository.TwitterSummaryRepository;
import com.bernardoms.twitterinfo.service.TwiterInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TwitterInfoServiceTest {
    @InjectMocks
    private TwiterInfoService twiterInfoService;

    @Mock
    private TwitterSummaryRepository twitterSummaryRepository;

    @Test
    public void test_get_summary_info_by_top_5_followers() {
        var twitterSummarize1 = TwitterSummarize.builder().count(10).grouper("Test").type("ByFollowers").build();
        var twitterSummarize2 = TwitterSummarize.builder().count(100).grouper("Test2").type("ByFollowers").build();
        var twitterSummarize3 = TwitterSummarize.builder().count(200).grouper("Test3").type("ByFollowers").build();
        var twitterSummarize4 = TwitterSummarize.builder().count(400).grouper("Test4").type("ByFollowers").build();
        var twitterSummarize5 = TwitterSummarize.builder().count(300).grouper("Test5").type("ByFollowers").build();

        when(twitterSummaryRepository.findFirst5ByTypeIgnoreCaseOrderByCountDesc("ByFollowers")).thenReturn(Arrays.asList(twitterSummarize1,
                twitterSummarize2,twitterSummarize3,
                twitterSummarize4,twitterSummarize5));

        var byFollowers = twiterInfoService.getSummaryInfo("ByFollowers");

        assertEquals(byFollowers.size(), 5);

        assertEquals(byFollowers.get(0).getGrouper(), "Test");
        assertEquals(byFollowers.get(1).getGrouper(), "Test2");
        assertEquals(byFollowers.get(2).getGrouper(), "Test3");
        assertEquals(byFollowers.get(3).getGrouper(), "Test4");
        assertEquals(byFollowers.get(4).getGrouper(), "Test5");
    }

    @Test
    public void test_get_summary_info_by_date() {
        var twitterSummarize1 = TwitterSummarize.builder().count(2).grouper("2020-03-16T02:00").type("ByDate").build();
        var twitterSummarize2 = TwitterSummarize.builder().count(5).grouper("2020-03-16T03:00").type("ByDate").build();
        var twitterSummarize3 = TwitterSummarize.builder().count(3).grouper("2020-03-16T04:00").type("ByDate").build();

        when(twitterSummaryRepository.findAllByTypeIgnoreCase("ByDate")).thenReturn(Arrays.asList(twitterSummarize1,
                twitterSummarize2,twitterSummarize3));

        var byFollowers = twiterInfoService.getSummaryInfo("ByDate");

        assertEquals(byFollowers.size(), 3);

        assertEquals(byFollowers.get(0).getGrouper(), "2020-03-16T02:00");
        assertEquals(byFollowers.get(1).getGrouper(), "2020-03-16T03:00");
        assertEquals(byFollowers.get(2).getGrouper(), "2020-03-16T04:00");

        assertEquals(byFollowers.get(0).getCount(), 2);
        assertEquals(byFollowers.get(1).getCount(), 5);
        assertEquals(byFollowers.get(2).getCount(), 3);
    }

    @Test
    public void test_get_summary_info_by_hashtag_and_language() {
        var twitterSummarize1 = TwitterSummarize.builder().count(2).grouper("en #awsapigateway").type("ByHashTagAndLanguage").build();
        var twitterSummarize2 = TwitterSummarize.builder().count(5).grouper("en #approov").type("ByHashTagAndLanguage").build();
        var twitterSummarize3 = TwitterSummarize.builder().count(3).grouper("en #test").type("ByHashTagAndLanguage").build();

        when(twitterSummaryRepository.findAllByTypeIgnoreCase("ByHashTagAndLanguage")).thenReturn(Arrays.asList(twitterSummarize1,
                twitterSummarize2,twitterSummarize3));

        var byFollowers = twiterInfoService.getSummaryInfo("ByHashTagAndLanguage");

        assertEquals(byFollowers.size(), 3);

        assertEquals(byFollowers.get(0).getGrouper(), "en #awsapigateway");
        assertEquals(byFollowers.get(1).getGrouper(), "en #approov");
        assertEquals(byFollowers.get(2).getGrouper(), "en #test");

        assertEquals(byFollowers.get(0).getCount(), 2);
        assertEquals(byFollowers.get(1).getCount(), 5);
        assertEquals(byFollowers.get(2).getCount(), 3);
    }
}
