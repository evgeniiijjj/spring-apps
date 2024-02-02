package com.example.newsservice.exceptions;

public class DeleteByIdException extends RuntimeException {
    public DeleteByIdException(String message) {
        super(message);
    }
}
