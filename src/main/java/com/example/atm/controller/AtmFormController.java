package com.example.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/form")
public class AtmFormController {

    @GetMapping("/initNotes")
    public String showInitNotesForm() {
        return "initNote";
    }

    @GetMapping("/dispenseForm")
    public String showDispenseForm() {
        return "dispenseForm";
    }
}
