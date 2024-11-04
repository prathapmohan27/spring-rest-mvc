package com.springframewok.springrestmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity<?> handleJPAViolation(TransactionSystemException exception) {
        ResponseEntity.BodyBuilder res = ResponseEntity.badRequest();
        if (exception.getCause().getCause() instanceof ConstraintViolationException cve) {
            List<Map<String, String>> errors = cve.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String, String> error = new HashMap<>();
                        error.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                        return error;
                    }).toList();
            return res.body(errors);
        }
        return res.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        List<Map<String, String>> errors = exception.getFieldErrors().stream().map(data -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put(data.getField(), data.getDefaultMessage());
            return errorMap;
        }).toList();

        return ResponseEntity.badRequest().body(errors);
    }
}
