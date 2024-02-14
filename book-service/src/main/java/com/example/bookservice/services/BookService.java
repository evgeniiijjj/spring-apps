package com.example.bookservice.services;

import com.example.bookservice.dtos.BookDto;
import com.example.bookservice.entities.Book;

import java.util.List;

public interface BookService {
    List<BookDto> getBooksByCategory(String category);
    BookDto getBookByTitleAndAuthor(String title, String author);
    BookDto createBook(BookDto book);
    BookDto updateBook(BookDto book);
    Integer deleteBook(Book book);
    Book getBookById(Integer id);
}