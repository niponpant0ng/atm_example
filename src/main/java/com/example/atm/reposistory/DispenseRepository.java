package com.example.atm.reposistory;

import com.example.atm.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DispenseRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT n FROM Note n WHERE n.counting > 0 ORDER BY n.amount DESC")
    List<Note> getAvailableNotes();

    @Modifying
    @Query("UPDATE FROM Note n SET n.counting = n.counting - :counting WHERE amount = :amount")
    int decreaseNoteCounting(@Param("amount") Integer amount, @Param("counting") Integer counting);

}
