package com.example.bookservice.advice;

import com.example.bookservice.exceptions.BookWithSuchIdNotExistsException;
import com.example.bookservice.exceptions.BookWithSuchTitleOrAuthorNotExistsException;
import com.example.bookservice.exceptions.SuchCategoryNotExistsException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({ SuchCategoryNotExistsException.class,
            BookWithSuchIdNotExistsException.class,
            BookWithSuchTitleOrAuthorNotExistsException.class })
    public ResponseEntity<Object> handleException(
            Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(String.join("\n", errorMessages));
    }
}
