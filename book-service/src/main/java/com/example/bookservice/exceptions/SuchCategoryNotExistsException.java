package com.example.bookservice.exceptions;

import java.text.MessageFormat;

public class SuchCategoryNotExistsException extends RuntimeException {
    public SuchCategoryNotExistsException(String category) {
        super(MessageFormat.format("Категория - {0} отсутствует.", category));
    }
}
