package com.afs.restapi.advice;

import com.afs.restapi.exception.EmployeeNotFoundException;
import com.afs.restapi.exception.NoMatchIdFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GlobalControllerAdvice {
    @ExceptionHandler({EmployeeNotFoundException.class})
    public ErrorResponse handleNotFound(Exception exception) {
        return new ErrorResponse(404, "Entity Not Found.");
    }
}
