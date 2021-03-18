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
import java.util.Objects;

@ControllerAdvice
public class ExceptionResolver {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // TODO This along with InvalidFormatException.class and JsonMappingException.class can be handled in a single
    // method, because they have a common base class: JsonProcessingException.class
    @ExceptionHandler(InvalidTypeIdException.class)
    @ResponseBody
    ErrorInfo handleCharacterTypeMissingException(InvalidTypeIdException ex) {
        return new ErrorInfo(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    ErrorInfo handleAnnotationValidation(MethodArgumentNotValidException ex) {
        return new ErrorInfo(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({InvalidFormatException.class, JsonMappingException.class})
    @ResponseBody
    ErrorInfo handleWrongFormatException(Exception ex) {
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
    // TODO You can just handle Exception.class with this method, because it doesn't really add any contextual
    // information to the error message. Besides it's nice to have some form of catch-all error handling that is called
    // whenever neither of the other exception handlers match the exception. It would make sure that the user never sees
    // a stack trace.
    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    ErrorInfo handleSQLException(PersistenceException ex) {
        return new ErrorInfo(ex.getLocalizedMessage());
    }

}
