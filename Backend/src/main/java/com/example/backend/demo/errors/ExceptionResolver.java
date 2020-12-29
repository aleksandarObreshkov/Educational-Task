package com.example.backend.demo.errors;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Objects;

@ControllerAdvice
public class ExceptionResolver{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTypeIdException.class)
    @ResponseBody ErrorInfo
    handleCharacterTypeMissingException(InvalidTypeIdException ex) {
        return new ErrorInfo(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody ErrorInfo
    handleAnnotationValidation(MethodArgumentNotValidException ex) {
        return new ErrorInfo(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidFormatException.class, JsonMappingException.class})
    @ResponseBody ErrorInfo
    handleWrongFormatException(Exception ex) {
        return new ErrorInfo("Invalid input format. Cause: "+ex.getMessage());
    }
}
