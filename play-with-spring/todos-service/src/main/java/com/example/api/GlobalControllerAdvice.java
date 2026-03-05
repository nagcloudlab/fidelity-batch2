package com.example.api;

import com.example.exception.HttpError;
import com.example.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpError handleTodoNotFoundException(TodoNotFoundException ex) {
        return new HttpError("/api/v1/todos/{id}", HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
