package com.bernardoms.twitterinfo.integration.controller;

import com.bernardoms.twitterinfo.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TwitterInfoControllerTest extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static String URL_PATH = "/v1/tweets";

    @Test
    public void test_get_tweet_summarized_by_followers_method() throws Exception {

        mockMvc.perform(
                get(URL_PATH + "/summarized/byfollowers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("ByFollowers")))
                .andExpect(jsonPath("$[0].grouper", is("Test6")))
                .andExpect(jsonPath("$[0].count", is(500)))
                .andExpect(jsonPath("$[1].type", is("ByFollowers")))
                .andExpect(jsonPath("$[1].grouper", is("Test4")))
                .andExpect(jsonPath("$[1].count", is(400)))
                .andExpect(jsonPath("$[2].type", is("ByFollowers")))
                .andExpect(jsonPath("$[2].grouper", is("Test5")))
                .andExpect(jsonPath("$[2].count", is(300)))
                .andExpect(jsonPath("$[3].type", is("ByFollowers")))
                .andExpect(jsonPath("$[3].grouper", is("Test3")))
                .andExpect(jsonPath("$[3].count", is(200)))
                        .andExpect(jsonPath("$[4].type", is("ByFollowers")))
                .andExpect(jsonPath("$[4].grouper", is("Test2")))
                .andExpect(jsonPath("$[4].count", is(100)))
        .andExpect(jsonPath("$[5].type").doesNotHaveJsonPath());
    }

    @Test
    public void test_get_tweet_summarized_by_date_method() throws Exception {

        mockMvc.perform(
                get(URL_PATH + "/summarized/byDate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("ByDate")))
                .andExpect(jsonPath("$[0].grouper", is("2020-03-16T02:00")))
                .andExpect(jsonPath("$[0].count", is(2)))
                .andExpect(jsonPath("$[1].type", is("ByDate")))
                .andExpect(jsonPath("$[1].grouper", is("2020-03-16T03:00")))
                .andExpect(jsonPath("$[1].count", is(5)))
                .andExpect(jsonPath("$[2].type", is("ByDate")))
                .andExpect(jsonPath("$[2].grouper", is("2020-03-16T04:00")))
                .andExpect(jsonPath("$[2].count", is(3)));
    }

    @Test
    public void test_get_tweet_summarized_by_hash_date_and_language_method() throws Exception {

        mockMvc.perform(
                get(URL_PATH + "/summarized/ByHashTagAndLanguage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type", is("ByHashTagAndLanguage")))
                .andExpect(jsonPath("$[0].grouper", is("en #awsapigateway")))
                .andExpect(jsonPath("$[0].count", is(2)))
                .andExpect(jsonPath("$[1].type", is("ByHashTagAndLanguage")))
                .andExpect(jsonPath("$[1].grouper", is("en #approov")))
                .andExpect(jsonPath("$[1].count", is(5)))
                .andExpect(jsonPath("$[2].type", is("ByHashTagAndLanguage")))
                .andExpect(jsonPath("$[2].grouper", is("en #test")))
                .andExpect(jsonPath("$[2].count", is(3)));
    }
}
