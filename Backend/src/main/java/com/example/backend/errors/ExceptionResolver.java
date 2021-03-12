package com.example.backend.errors;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import java.sql.SQLException;
import java.util.Objects;

@ControllerAdvice
public class ExceptionResolver{

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidTypeIdException.class)
    @ResponseBody ErrorInfo
    handleCharacterTypeMissingException(InvalidTypeIdException ex) {
        return new ErrorInfo(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody ErrorInfo
    handleAnnotationValidation(MethodArgumentNotValidException ex) {
        return new ErrorInfo(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({InvalidFormatException.class, JsonMappingException.class})
    @ResponseBody ErrorInfo
    handleWrongFormatException(Exception ex) {
            return new ErrorInfo("Invalid input format. Cause: "+ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RollbackException.class)
    @ResponseBody ErrorInfo
    handleEntityExistsException(RollbackException ex) {
        return new ErrorInfo("Object already exists. "+ex.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PersistenceException.class)
    @ResponseBody ErrorInfo
    handleSQLException(PersistenceException ex) {
        return new ErrorInfo(ex.getLocalizedMessage());
    }
}
