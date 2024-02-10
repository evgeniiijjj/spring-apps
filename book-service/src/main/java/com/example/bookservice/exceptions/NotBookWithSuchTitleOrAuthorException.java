package com.example.bookservice.exceptions;

import java.text.MessageFormat;

public class NotBookWithSuchTitleOrAuthorException extends RuntimeException {
    public NotBookWithSuchTitleOrAuthorException(String title, String author) {
        super(MessageFormat.format("Книга с названием - {0} автора - {1} отсутствует.", title, author));
    }
}
