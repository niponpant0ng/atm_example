package com.example.atm.reposistory;

import com.example.atm.model.Note;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    @Transactional
    public void shouldEmptyWhenCountingOfEachNoteIs0() {
        testEntityManager.persistAndFlush(new Note(100, 0));

        List<Note> notes = noteRepository.findAvailableNotes();

        assertThat(notes.size(), is(0));
    }

    @Test
    @Transactional
    public void shouldGetAvailableNoteWhenCountingOfEachNoteIsMoreThan0() {
        testEntityManager.persistAndFlush(new Note(100, 5));

        List<Note> notes = noteRepository.findAvailableNotes();

        assertThat(notes.size(), is(1));
        ListAssert.assertContains(notes, new Note(100, 5));
    }

    @Test
    @Transactional
    public void shouldGetAvailableNoteWhenCountingOfEachNoteIsBothMoreThan0AndIs0() {
        testEntityManager.persistAndFlush(new Note(100, 5));
        testEntityManager.persistAndFlush(new Note(50, 0));
        testEntityManager.persistAndFlush(new Note(20, 10));

        List<Note> notes = noteRepository.findAvailableNotes();

        assertThat(notes.size(), is(2));
        ListAssert.assertContains(notes, new Note(100, 5));
        ListAssert.assertContains(notes, new Note(20, 10));
    }
}