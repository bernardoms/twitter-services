package com.bernardoms.twittersummarize.integration.service;

import com.bernardoms.twittersummarize.integration.IntegrationTest;
import com.bernardoms.twittersummarize.model.TwitterSummarize;
import com.bernardoms.twittersummarize.repository.TwitterSummarizeRepository;
import com.bernardoms.twittersummarize.service.TwitterSummarizeService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TwitterSummarizeServiceTest extends IntegrationTest {
    @Autowired
    private TwitterSummarizeService twitterSummarizeService;
    @MockBean
    private TwitterSummarizeRepository twitterSummarizeRepository;
    @Captor
    private ArgumentCaptor<List<TwitterSummarize>> twitterSummarizes;

    @Test
    public void test_group_tweets() {
        twitterSummarizeService.group();

        verify(twitterSummarizeRepository, times(3)).saveAll(twitterSummarizes.capture());

        var twitterSummarizedByFollowers = this.twitterSummarizes.getAllValues().get(0);
        var twitterSummarizedByDate = this.twitterSummarizes.getAllValues().get(1);
        var twitterSummarizedByHashTagAndCode = this.twitterSummarizes.getAllValues().get(2);

        assertEquals(twitterSummarizedByFollowers.size(), 6);
        assertEquals(twitterSummarizedByFollowers.stream().filter(f->f.getGrouper().equals("VXTOR")).findFirst().get().getCount(), 21);
        assertEquals(twitterSummarizedByFollowers.stream().filter(f->f.getGrouper().equals("Joe Merriman")).findFirst().get().getCount(), 1254);
        assertEquals(twitterSummarizedByFollowers.stream().filter(f->f.getGrouper().equals("Fred Jones")).findFirst().get().getCount(), 2365);
        assertEquals(twitterSummarizedByFollowers.stream().filter(f->f.getGrouper().equals("Arthur Fonseca")).findFirst().get().getCount(), 178);
        assertEquals(twitterSummarizedByFollowers.stream().filter(f->f.getGrouper().equals("NICE Actimize")).findFirst().get().getCount(), 2681);
        assertEquals(twitterSummarizedByFollowers.stream().filter(f->f.getGrouper().equals("Lutz Kiesewetter")).findFirst().get().getCount(), 1290);


        assertEquals(twitterSummarizedByDate.size(), 5);
        assertEquals(twitterSummarizedByDate.stream().filter(d->d.getGrouper().equals("2020-03-22T15:00")).findFirst().get().getCount(), 2);

        assertEquals(twitterSummarizedByHashTagAndCode.size(), 11);
        assertEquals(twitterSummarizedByHashTagAndCode.stream().filter(h->h.getGrouper().equals("en #openbanking")).findFirst().get().getCount(), 4);
    }
}
