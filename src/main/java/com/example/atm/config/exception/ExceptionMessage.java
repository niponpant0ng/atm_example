package com.example.atm.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ExceptionMessage implements Serializable {
    String message;
}
