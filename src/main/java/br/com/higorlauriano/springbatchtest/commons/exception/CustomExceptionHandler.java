package br.com.higorlauriano.springbatchtest.commons.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity handle(CustomException ex) {

        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}
