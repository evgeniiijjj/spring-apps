package com.example.bookingservice.advice;

import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.exceptions.UserAlreadyExistsException;
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

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(
            Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, UserAlreadyExistsException.class })
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
