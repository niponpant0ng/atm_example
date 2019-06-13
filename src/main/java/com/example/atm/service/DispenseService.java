package com.example.atm.service;

import com.example.atm.config.exception.AvailableNoteEmptyException;
import com.example.atm.config.exception.InValidDispenseAmount;
import com.example.atm.model.Note;
import com.example.atm.reposistory.DispenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DispenseService {

    private DispenseRepository dispenseRepository;

    @Autowired
    public DispenseService(DispenseRepository dispenseRepository) {
        this.dispenseRepository = dispenseRepository;
    }

    @Transactional
    public List<Note> dispense(Integer amount) {
        List<Note> availableNotes = dispenseRepository.getAvailableNotes();
        if(availableNotes.isEmpty()) {
            throw new AvailableNoteEmptyException();
        }

        Note leastNote = availableNotes.get(availableNotes.size() - 1);
        if(leastNote.getAmount() > amount) {
            throw new InValidDispenseAmount();
        }

        List<Note> dispenseNotes = new ArrayList<>();
        for(Note availableNote : availableNotes) {
            Integer noteCounting = availableNote.calcAvailableCounting(amount);
            if(noteCounting.equals(0)) {
                continue;
            }

            dispenseNotes.add(new Note(availableNote.getAmount(), noteCounting));
            amount -= availableNote.getAmount() * noteCounting;

            if(amount.equals(0)) {
                break;
            }
        }

        if(amount > 0) {
            throw new InValidDispenseAmount();
        }

        dispenseNotes.forEach(n -> dispenseRepository.decreaseNoteCounting(n.getAmount(), n.getCounting()));

        return dispenseNotes;
    }
}
