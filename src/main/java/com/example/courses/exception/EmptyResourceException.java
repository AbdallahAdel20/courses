package com.example.courses.exception;

//@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)

public class EmptyResourceException extends RuntimeException{

    public EmptyResourceException(String message) {
        super(message);
    }
}
