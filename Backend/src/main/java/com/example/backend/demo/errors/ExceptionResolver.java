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

    //the first two annotations, I understand, but formatting the return type on one line and the method name on the next
    // reminds me of C/C++
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
        //this line is hard to read
        //TODO split it into more readable parts
        return Objects.requireNonNull(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }
}
