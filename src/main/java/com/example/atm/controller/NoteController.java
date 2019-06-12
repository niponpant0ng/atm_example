package com.example.atm.controller;

import com.example.atm.model.Notes;
import com.example.atm.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Notes initNotes(@Valid @RequestBody Notes notes) {
        return new Notes(noteService.initNotes(notes.getNotes()));
    }

    @GetMapping
    public Notes getNotes() {
        return new Notes(noteService.getNotes());
    }
}
