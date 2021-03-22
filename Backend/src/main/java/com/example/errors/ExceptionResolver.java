package com.example.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.persistence.RollbackException;
import java.util.Objects;

@ControllerAdvice
public class ExceptionResolver {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    ErrorInfo handleAnnotationValidation(MethodArgumentNotValidException ex) {
        return new ErrorInfo(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    ErrorInfo handleInvalidInputFormat(Exception ex) {
        return new ErrorInfo("Invalid input format. Cause: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RollbackException.class)
    @ResponseBody
    ErrorInfo handleEntityExistsException(RollbackException ex) {
        // TODO This exception message could be misleading, because a RollbackException could occur for numerous other
        // reasons besides a unique constraint conflict. For example, trying to delete an entity that is referenced by
        // other entities.
        return new ErrorInfo("Object already exists. " + ex.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ErrorInfo handleSQLException(Exception ex) {
        return new ErrorInfo(ex.getLocalizedMessage());
    }

}
