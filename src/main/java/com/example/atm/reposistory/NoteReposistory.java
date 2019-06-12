package com.example.atm.reposistory;

import com.example.atm.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteReposistory extends JpaRepository<Note, Integer> {
}
