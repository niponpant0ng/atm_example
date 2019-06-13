package com.example.atm.controller;

import com.example.atm.model.Notes;
import com.example.atm.service.DispenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping(value = "/dispense", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class DispenseController {

    private DispenseService dispenseService;

    @Autowired
    public DispenseController(DispenseService dispenseService) {
        this.dispenseService = dispenseService;
    }

    @PostMapping("/{amount}")
    public Notes dispense(@PathVariable("amount") @Min(value = 1) Integer amount) {
        return new Notes(dispenseService.dispense(amount));
    }
}
