package com.gym.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ObjectRestExceptionHandler {

    // Customer not found exception
    @ExceptionHandler
    public ResponseEntity<ObjectErrorResponse> handleException(ObjectNotFoundException exc) {

        ObjectErrorResponse error = new ObjectErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Catch all exceptions
    @ExceptionHandler
    public ResponseEntity<ObjectErrorResponse> handleException(Exception exc) {

        ObjectErrorResponse error = new ObjectErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
