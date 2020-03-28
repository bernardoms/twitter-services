package com.bernardoms.twittersummarize.integration.controller;


import com.bernardoms.twittersummarize.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TwitterSummarizeControllerTest extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static String URL_PATH = "/v1/tweets/summarize";
    @Test
    public void test_summarize_tweets() throws Exception {
        mockMvc.perform(
                post(URL_PATH))
                .andExpect(status().isNoContent());
    }
}
