package com.example.backend.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@ControllerAdvice
public class ExceptionResolver{

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(IOException.class)
    @ResponseBody
    String
    handleIOException(IOException ex) {
        ex.printStackTrace();
        return ex.getMessage();
    }


    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    String
    handleBadInput(MethodArgumentNotValidException ex) {
        return Objects.requireNonNull(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }
}
