package com.example.bookservice.exceptions;

import java.text.MessageFormat;

public class NotBookWithSuchIdException extends RuntimeException {
    public NotBookWithSuchIdException(Integer id) {
        super(MessageFormat.format("Книга с id {0} отсутствует.", id));
    }
}
