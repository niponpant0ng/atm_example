package com.example.atm.controller;

import com.example.atm.model.Notes;
import com.example.atm.service.DispenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dispense", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DispenseController {

    private DispenseService dispenseService;

    @Autowired
    public DispenseController(DispenseService dispenseService) {
        this.dispenseService = dispenseService;
    }

    @GetMapping("/{amount}")
    public Notes dispense(@PathVariable("amount") Integer amount) {
        return new Notes(dispenseService.dispense(amount));
    }
}
