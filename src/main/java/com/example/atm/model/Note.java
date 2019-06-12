package com.example.atm.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Note {
    @Id
    Integer amount;

    Integer counting;
}
