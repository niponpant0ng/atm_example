package com.example.atm.service;

import com.example.atm.model.Note;
import com.example.atm.reposistory.NoteReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {
    private NoteReposistory noteReposistory;

    @Autowired
    public NoteService(NoteReposistory noteReposistory) {
        this.noteReposistory = noteReposistory;
    }

    @Transactional
    public List<Note> initNotes(List<Note> notes) {
        return noteReposistory.saveAll(notes);
    }

    public List<Note> getNotes() {
        return noteReposistory.findAll();
    }
}
