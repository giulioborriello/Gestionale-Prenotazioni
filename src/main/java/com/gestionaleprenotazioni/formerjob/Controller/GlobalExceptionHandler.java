package com.gestionaleprenotazioni.formerjob.Controller;

// Assicurati di importare le tue eccezioni già esistenti
import com.gestionaleprenotazioni.formerjob.Exception.EventNotFoundException;
import com.gestionaleprenotazioni.formerjob.Exception.UserNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Quando il Service lancia UserNotFoundException...
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        // ...rispondiamo con un 404 Not Found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Quando il Service lancia EventNotFoundException...
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleEventNotFound(EventNotFoundException ex) {
        // ...rispondiamo anche qui con un 404 Not Found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}