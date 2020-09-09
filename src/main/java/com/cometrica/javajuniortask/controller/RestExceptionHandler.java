package com.cometrica.javajuniortask.controller;

import com.cometrica.javajuniortask.dto.ErrorDTO;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataRetrievalFailureException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleDataRetrievalFailureEx(DataRetrievalFailureException ex){
        return new ErrorDTO(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleConcurrencyFailureEx(ConcurrencyFailureException ex){
        return new ErrorDTO(HttpStatus.CONFLICT, "concurrent resource modification; try later");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleDataIntegrityViolationEx(DataIntegrityViolationException ex){
        return new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleDataAccessEx(DataAccessException ex){
        return new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleConstraintViolationEx(ConstraintViolationException ex){
        String[] errs =ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toArray(String[]::new);
        return new ErrorDTO(HttpStatus.BAD_REQUEST, errs);
    }

}


