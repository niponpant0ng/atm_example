package com.example.atm.service;

import com.example.atm.model.Note;
import com.example.atm.reposistory.NoteReposistory;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class NoteServiceTest {
    @Mock
    private NoteReposistory noteReposistory;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void shouldInitNotesWithNoteList() {
        List<Note> savedNotes = Arrays.asList(new Note(100, 2), new Note(50, 1));
        List<Note> notes = Arrays.asList(new Note(100, 4), new Note(50, 2));
        doReturn(savedNotes).when(noteReposistory).saveAll(notes);

        List<Note> initNotes = noteService.initNotes(notes);

        ListAssert.assertEquals(initNotes, savedNotes);
    }

}