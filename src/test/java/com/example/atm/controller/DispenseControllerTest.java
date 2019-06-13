package com.example.atm.controller;

import com.example.atm.config.exception.AvailableNoteEmptyException;
import com.example.atm.config.exception.AvailableNoteNotCoverException;
import com.example.atm.config.exception.InValidDispenseAmount;
import com.example.atm.model.Note;
import com.example.atm.service.DispenseService;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DispenseControllerTest {

    private final String URL = "/dispense/%d";

    @MockBean
    private DispenseService dispenseService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldNotDispenseWhenAmountIsNotNumber() throws Exception {
        MockHttpServletRequestBuilder request = post("/dispense/xxx")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotDispenseWhenAmountIsLessThanDispenseAmount() throws Exception {
        MockHttpServletRequestBuilder request = post("/dispense/-500")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());

        mvc.perform(request).andExpect(status()
                .isBadRequest())
                .andExpect(jsonPath("$.message", CoreMatchers.is("Amount not valid value")));
    }

    @Test
    public void shouldNotDispenseWhenAmountIsNotCoverWithAvailableNotes() throws Exception {
        Integer amount = 150;
        doThrow(new InValidDispenseAmount()).when(dispenseService).dispense(amount);

        MockHttpServletRequestBuilder request = post(String.format(URL, amount))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());

        mvc.perform(request).andExpect(status()
                .isBadRequest())
                .andExpect(jsonPath("$.message", CoreMatchers.is("Amount not valid value")));
    }

    @Test
    public void shouldNotDispenseWhenAvailableNotesAreEmpty() throws Exception {
        Integer amount = 150;
        doThrow(new AvailableNoteEmptyException()).when(dispenseService).dispense(amount);

        MockHttpServletRequestBuilder request = post(String.format(URL, amount))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());

        mvc.perform(request).andExpect(status()
                .isBadRequest())
                .andExpect(jsonPath("$.message", CoreMatchers.is("Available note are empty")));
    }

    @Test
    public void shouldNotDispenseWhenAvailableNotesNotCoverAmount() throws Exception {
        Integer amount = 150;
        doThrow(new AvailableNoteNotCoverException()).when(dispenseService).dispense(amount);

        MockHttpServletRequestBuilder request = post(String.format(URL, amount))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());

        mvc.perform(request).andExpect(status()
                .isBadRequest())
                .andExpect(jsonPath("$.message", CoreMatchers.is("Available note not cover dispense amount")));
    }

    @Test
    public void shouldDispenseWhenAvailableNotesCoverAmount() throws Exception {
        Integer amount = 150;
        List<Note> notes = Arrays.asList(new Note(100, 1), new Note(50, 1));
        doReturn(notes).when(dispenseService).dispense(amount);

        MockHttpServletRequestBuilder request = post(String.format(URL, amount))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes[0].amount", CoreMatchers.is(100)))
                .andExpect(jsonPath("$.notes[0].counting", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.notes[1].amount", CoreMatchers.is(50)))
                .andExpect(jsonPath("$.notes[1].counting", CoreMatchers.is(1)));
    }
}