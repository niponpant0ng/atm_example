package com.example.atm.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "notes")
public class Notes implements Serializable {

    @Valid
    @Size(min = 1)
    List<Note> notes;
}
