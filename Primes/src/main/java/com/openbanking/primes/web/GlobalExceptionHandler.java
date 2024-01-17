package com.openbanking.primes.web;

import com.openbanking.primes.model.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse illegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Please provide a positive number.");
    }

    @ExceptionHandler(value = { TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse typeMismatchException(TypeMismatchException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Non-numeric value is not accepted, please provide a positive number.");
    }

    @ExceptionHandler(value = { Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse internalServerError (Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred.");
    }

}
