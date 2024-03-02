package com.example.tasktracker.exceptions;

public class AlreadyExistsUserException extends RuntimeException {
    public AlreadyExistsUserException(String message) {
        super(message);
    }
}
