package com.example.atm.config;

import com.example.atm.config.exception.AvailableNoteEmptyException;
import com.example.atm.config.exception.AvailableNoteNotCoverException;
import com.example.atm.config.exception.ExceptionMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandleConfig extends ResponseEntityExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(AvailableNoteEmptyException.class)
    public ResponseEntity<String> availableNoteEmpty(HttpServletRequest req, Exception ex) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.writeValueAsString(new ExceptionMessage("Available note are empty")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AvailableNoteNotCoverException.class)
    public ResponseEntity<String> availableNoteNotCover(HttpServletRequest req, Exception ex) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.writeValueAsString(new ExceptionMessage("Available note not cover dispense amount")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> notValidAmount(HttpServletRequest req, Exception ex) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.writeValueAsString(new ExceptionMessage("Amount not valid value")), HttpStatus.BAD_REQUEST);
    }
}
