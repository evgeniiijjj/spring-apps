package com.example.bookservice.exceptions;

import java.text.MessageFormat;

public class BookWithSuchIdNotExistsException extends RuntimeException {
    public BookWithSuchIdNotExistsException(Integer id) {
        super(MessageFormat.format("Книга с id {0} отсутствует.", id));
    }
}
