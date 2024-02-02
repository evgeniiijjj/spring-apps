package com.example.newsservice.exceptions;

public class AccessDeniedExceptions extends RuntimeException {
    public AccessDeniedExceptions(String message) {
        super(message);
    }
}
