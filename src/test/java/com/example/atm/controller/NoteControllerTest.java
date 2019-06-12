package com.example.atm.controller;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldNotInitNotesWhenBodyRequestIsEmpty() throws Exception {
        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .content("");

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotInitNotesWhenAmountOfBodyRequestIsEmpty() throws Exception {
        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .content("{\"notes\": [{\"counting\": 5}]}");

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotInitNotesWhenCountingOfBodyRequestIsEmpty() throws Exception {
        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .content("{\"notes\": [{\"amount\": 50}]}");

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotInitNotesWhenAmountOfBodyRequestIsNotNumber() throws Exception {
        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .content("{\"notes\": [{\"amount\": \"xxx\", \"counting\": 5}]}");

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotInitNotesWhenCountingOfBodyRequestIsNotNumber() throws Exception {
        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .content("{\"notes\": [{\"amount\": 100, \"counting\": 5}, {\"amount\": 50, \"counting\": \"xxx\"}]}");

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldInitNotesWhenBodyRequestInCorrectFormat() throws Exception {
        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .content("{\"notes\": [{\"amount\": 100, \"counting\": 5}, {\"amount\": 50, \"counting\": 2}]}");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes[0].amount", CoreMatchers.is(100)))
                .andExpect(jsonPath("$.notes[0].counting", CoreMatchers.is(5)))
                .andExpect(jsonPath("$.notes[1].amount", CoreMatchers.is(50)))
                .andExpect(jsonPath("$.notes[1].counting", CoreMatchers.is(2)));
    }
}