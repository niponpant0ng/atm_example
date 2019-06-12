package com.example.atm.controller;

import com.example.atm.model.Note;
import com.example.atm.service.NoteService;
import junitx.framework.ListAssert;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @MockBean
    private NoteService noteService;

    @Captor
    private ArgumentCaptor<List<Note>> noteArgCaptor;

    @Autowired
    private MockMvc mvc;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldNotInitNotesWhenBodyRequestIsEmpty() throws Exception {
        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());

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
        List<Note> savedNotes = Collections.singletonList(new Note(100, 10));
        doReturn(savedNotes).when(noteService).initNotes(noteArgCaptor.capture());

        MockHttpServletRequestBuilder request = post("/notes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .content("{\"notes\": [{\"amount\": 100, \"counting\": 5}, {\"amount\": 50, \"counting\": 2}]}");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes[0].amount", CoreMatchers.is(100)))
                .andExpect(jsonPath("$.notes[0].counting", CoreMatchers.is(10)));

        ListAssert.assertContains(noteArgCaptor.getValue(), new Note(100, 5));
        ListAssert.assertContains(noteArgCaptor.getValue(), new Note(50, 2));
    }

    @Test
    public void shouldGetNotes() throws Exception {
        List<Note> notes = Collections.singletonList(new Note(100, 10));
        doReturn(notes).when(noteService).getNotes();

        MockHttpServletRequestBuilder request = get("/notes");

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.notes[0].amount", CoreMatchers.is(100)))
            .andExpect(jsonPath("$.notes[0].counting", CoreMatchers.is(10)));
    }
}