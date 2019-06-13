package com.example.atm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note implements Serializable {

    @Id
    @NotNull
    Integer amount;

    @NotNull
    Integer counting;

    @Transient
    public Integer calcAvailableCounting(Integer amount) {
        int noteCounting = (int) amount / this.amount;

        if(noteCounting > this.counting) {
            noteCounting = this.counting;
        }

        return noteCounting;
    }

}
