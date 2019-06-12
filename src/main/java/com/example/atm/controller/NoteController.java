package com.example.atm.controller;

import com.example.atm.model.Note;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @PostMapping
    public List<Note> initNotes(@RequestBody List<Note> notes) {
        return null;
    }
}
