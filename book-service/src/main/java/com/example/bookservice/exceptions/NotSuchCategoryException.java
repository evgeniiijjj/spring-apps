package com.example.bookservice.exceptions;

import java.text.MessageFormat;

public class NotSuchCategoryException extends RuntimeException {
    public NotSuchCategoryException(String category) {
        super(MessageFormat.format("Категория - {0} отсутствует.", category));
    }
}
