package com.example.atm.reposistory;

import com.example.atm.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT n FROM Note n WHERE n.counting > 0 ORDER BY n.amount DESC")
    List<Note> findAvailableNotes();

}
