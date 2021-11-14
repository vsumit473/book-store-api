package com.smartdubai.controller;

import com.smartdubai.exceptions.DuplicateBookException;
import com.smartdubai.exceptions.GenericBookException;
import com.smartdubai.exceptions.InvalidBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class BookExceptionHandlerController {

    @ExceptionHandler(InvalidBookException.class)
    public ResponseEntity<String> handleInvalidDataException(final InvalidBookException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericBookException.class)
    public ResponseEntity<String> handleGenericException(final InvalidBookException e){
        return new ResponseEntity<String>("Server error please try after a while", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<String> duplicateBookException(final DuplicateBookException e){
        return new ResponseEntity<String>("Book already exist in the system with this name", HttpStatus.NOT_ACCEPTABLE);
    }
}
