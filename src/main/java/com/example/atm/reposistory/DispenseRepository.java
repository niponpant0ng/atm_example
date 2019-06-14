package com.example.atm.reposistory;

import com.example.atm.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DispenseRepository extends JpaRepository<Note, Integer> {

    @Modifying
    @Query("UPDATE FROM Note n SET n.counting = n.counting - :counting WHERE amount = :amount")
    int decreaseNoteCounting(@Param("amount") Integer amount, @Param("counting") Integer counting);

}
