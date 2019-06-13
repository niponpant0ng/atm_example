package com.example.atm.service;

import com.example.atm.model.Note;
import com.example.atm.reposistory.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional
    public List<Note> initNotes(List<Note> notes) {
        return noteRepository.saveAll(notes);
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }
}
