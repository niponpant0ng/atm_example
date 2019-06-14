package com.example.atm.service;

import com.example.atm.config.exception.AvailableNoteEmptyException;
import com.example.atm.config.exception.AvailableNoteNotCoverException;
import com.example.atm.model.Note;
import com.example.atm.reposistory.DispenseRepository;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DispenseServiceTest {

    @InjectMocks
    private DispenseService dispenseService;

    @Mock
    private DispenseRepository dispenseRepository;

    @Test(expected = AvailableNoteNotCoverException.class)
    public void shouldInValidAmountWhenDispenseAmountLessThanLeastAmountOfAvailableNotes() {
        Integer dispenseAmount = 30;
        List<Note> notes = Arrays.asList(new Note(100, 5), new Note(50, 3));
        doReturn(notes).when(dispenseRepository).getAvailableNotes();

        dispenseService.dispense(dispenseAmount);
    }

    @Test(expected = AvailableNoteEmptyException.class)
    public void ShouldAvailableNoteEmptyWhenAvailableNotesAreEmpty() {
        Integer dispenseAmount = 30;
        List<Note> notes = Collections.emptyList();
        doReturn(notes).when(dispenseRepository).getAvailableNotes();

        dispenseService.dispense(dispenseAmount);
    }

    @Test
    public void shouldDispenseWhenAvailableNotesCoverDispenseAmountAndCountingOfEachNoteIsCovering() {
        Integer dispenseAmount = 170;
        List<Note> notes = Arrays.asList(new Note(100, 5), new Note(50, 2), new Note(20, 3));
        doReturn(notes).when(dispenseRepository).getAvailableNotes();

        List<Note> dispenseNotes = dispenseService.dispense(dispenseAmount);

        verify(dispenseRepository, times(1)).decreaseNoteCounting(100, 1);
        verify(dispenseRepository, times(1)).decreaseNoteCounting(50, 1);
        verify(dispenseRepository, times(1)).decreaseNoteCounting(20, 1);
        assertThat(dispenseNotes.size(), is(3));
        ListAssert.assertContains(dispenseNotes, new Note(100, 1));
        ListAssert.assertContains(dispenseNotes, new Note(50, 1));
        ListAssert.assertContains(dispenseNotes, new Note(20, 1));
    }

    @Test
    public void shouldDispenseWhenAvailableNotesCoverDispenseAmountBeforeLatestOfAvailableNotesCovering() {
        Integer dispenseAmount = 150;
        List<Note> notes = Arrays.asList(new Note(100, 5), new Note(50, 2), new Note(20, 3));
        doReturn(notes).when(dispenseRepository).getAvailableNotes();

        List<Note> dispenseNotes = dispenseService.dispense(dispenseAmount);

        verify(dispenseRepository, times(1)).decreaseNoteCounting(100, 1);
        verify(dispenseRepository, times(1)).decreaseNoteCounting(50, 1);
        assertThat(dispenseNotes.size(), is(2));
        ListAssert.assertContains(dispenseNotes, new Note(100, 1));
        ListAssert.assertContains(dispenseNotes, new Note(50, 1));
    }

    @Test
    public void shouldDispenseWhenAvailableNotesCoverDispenseAmountAndEachAvailableNoteBeforeLatestNotesNotCovering() {
        Integer dispenseAmount = 50;
        List<Note> notes = Arrays.asList(new Note(100, 5), new Note(50, 3));
        doReturn(notes).when(dispenseRepository).getAvailableNotes();

        List<Note> dispenseNotes = dispenseService.dispense(dispenseAmount);

        verify(dispenseRepository, times(1)).decreaseNoteCounting(50, 1);
        assertThat(dispenseNotes.size(), is(1));
        ListAssert.assertContains(dispenseNotes, new Note(50, 1));
    }

    @Test
    public void shouldDispenseWhenAvailableNotesCoverDispenseAmountAndCountingOfEachNoteIsNotCoveringAtAll() {
        Integer dispenseAmount = 310;
        List<Note> notes = Arrays.asList(new Note(100, 2), new Note(50, 1), new Note(20, 3));
        doReturn(notes).when(dispenseRepository).getAvailableNotes();

        List<Note> dispenseNotes = dispenseService.dispense(dispenseAmount);

        verify(dispenseRepository, times(1)).decreaseNoteCounting(100, 2);
        verify(dispenseRepository, times(1)).decreaseNoteCounting(50, 1);
        verify(dispenseRepository, times(1)).decreaseNoteCounting(20, 3);
        assertThat(dispenseNotes.size(), is(3));
        ListAssert.assertContains(dispenseNotes, new Note(100, 2));
        ListAssert.assertContains(dispenseNotes, new Note(50, 1));
        ListAssert.assertContains(dispenseNotes, new Note(20, 3));
    }

    @Test(expected = AvailableNoteNotCoverException.class)
    public void shouldInValidAmountWhenAvailableNotesNotCoverDispenseAmount() {
        Integer dispenseAmount = 130;
        List<Note> notes = Arrays.asList(new Note(100, 5), new Note(50, 3));
        doReturn(notes).when(dispenseRepository).getAvailableNotes();

        dispenseService.dispense(dispenseAmount);
    }
}