package com.example.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)

public class DaplicateRecoredException extends RuntimeException{

    public DaplicateRecoredException(String message) {
        super(message);
    }
}
