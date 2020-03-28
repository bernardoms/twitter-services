package com.bernardoms.twitterinfo.unit.controller;

import com.bernardoms.twitterinfo.controller.TwitterInfoController;
import com.bernardoms.twitterinfo.model.TwitterSummarize;
import com.bernardoms.twitterinfo.service.TwiterInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class TwitterInfoControllerTest {

    @InjectMocks
    private TwitterInfoController twitterInfoController;

    private MockMvc mockMvc;

    @Mock
    private TwiterInfoService twiterInfoService;

    private static String URL_PATH = "/v1/tweets";

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(twitterInfoController).build();
    }

    @Test
    public void test_get_tweet_summarized_by_a_method() throws Exception {

        var twitterSummarize1 = TwitterSummarize.builder().count(10).grouper("Test").type("ByFollowers").build();
        var twitterSummarize2 = TwitterSummarize.builder().count(100).grouper("Test2").type("ByFollowers").build();
        var twitterSummarize3 = TwitterSummarize.builder().count(200).grouper("Test3").type("ByFollowers").build();

        when(twiterInfoService.getSummaryInfo("byfollowers")).thenReturn(Arrays.asList(twitterSummarize1,
                twitterSummarize2,twitterSummarize3));

        mockMvc.perform(
                get(URL_PATH + "/summarized/byfollowers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("ByFollowers")))
                .andExpect(jsonPath("$[0].grouper", is("Test")))
                .andExpect(jsonPath("$[0].count", is(10)))
                .andExpect(jsonPath("$[1].type", is("ByFollowers")))
                .andExpect(jsonPath("$[1].grouper", is("Test2")))
                .andExpect(jsonPath("$[1].count", is(100)))
                .andExpect(jsonPath("$[2].type", is("ByFollowers")))
                .andExpect(jsonPath("$[2].grouper", is("Test3")))
                .andExpect(jsonPath("$[2].count", is(200)));

    }
}
