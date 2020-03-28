package com.bernardoms.twittersummarize.unit.service;

import com.bernardoms.twittersummarize.model.Tweet;
import com.bernardoms.twittersummarize.model.TwitterSummarize;
import com.bernardoms.twittersummarize.repository.TwitterRepository;
import com.bernardoms.twittersummarize.repository.TwitterSummarizeRepository;
import com.bernardoms.twittersummarize.service.TwitterSummarizeService;
import com.bernardoms.twittersummarize.util.MillisOrLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TwitterSummarizeServiceTest {
    @Mock
    private TwitterSummarizeRepository twitterSummarizeRepository;
    @Mock
    private TwitterRepository twitterRepository;
    @Captor
    private ArgumentCaptor<List<TwitterSummarize>> twitterSummarizes;

    @InjectMocks
    private TwitterSummarizeService twitterSummarizeService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test_group_tweets() throws IOException {
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new MillisOrLocalDateTimeDeserializer());

        mapper.registerModule(javaTimeModule);

        var tweetList = Arrays.stream(mapper.readValue(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("tweets.json")), Tweet[].class)).collect(Collectors.toList());

        when(twitterRepository.findAll()).thenReturn(tweetList);

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
